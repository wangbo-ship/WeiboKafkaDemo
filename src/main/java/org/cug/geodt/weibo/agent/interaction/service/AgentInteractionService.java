package org.cug.geodt.weibo.agent.interaction.service;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.memory.TaskContextStore;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionRequest;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionResponse;
import org.cug.geodt.weibo.agent.interaction.model.ExecutionPlan;
import org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.model.TaskTrace;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Agent 交互编排服务：多轮补参、规划确认、执行调度与汇报整合。
 *
 * <p>闭环流程：建立会话 → 抽参 → 识意图 → 记日志 → 出计划 →（确认后）调 Skill → 存内存 → 返回响应
 */
@Service
@Slf4j
public class AgentInteractionService {

    private final TaskContextStore contextStore;
    private final IntentRecognitionService intentRecognitionService;
    private final ParameterExtractionService parameterExtractionService;
    private final TaskPlanningService taskPlanningService;
    private final TaskExecutorRegistry taskExecutorRegistry;
    private final ProvenanceService provenanceService;
    private final InteractionResponseBuilder responseBuilder;

    public AgentInteractionService(TaskContextStore contextStore,
                                   IntentRecognitionService intentRecognitionService,
                                   ParameterExtractionService parameterExtractionService,
                                   TaskPlanningService taskPlanningService,
                                   TaskExecutorRegistry taskExecutorRegistry,
                                   ProvenanceService provenanceService,
                                   InteractionResponseBuilder responseBuilder) {
        this.contextStore = contextStore;
        this.intentRecognitionService = intentRecognitionService;
        this.parameterExtractionService = parameterExtractionService;
        this.taskPlanningService = taskPlanningService;
        this.taskExecutorRegistry = taskExecutorRegistry;
        this.provenanceService = provenanceService;
        this.responseBuilder = responseBuilder;
    }

    public AgentInteractionResponse chat(AgentInteractionRequest request) throws Exception {
        // ① 解析会话：按 conversationId 加载已有 TaskContext，首轮无 ID 则新建（见 resolveContext）
        TaskContext context = resolveContext(request.getConversationId());
        // 仅保留本轮用户输入；完整聊天历史不由基座存储，GET /context 返回此字段
        context.setLastUserMessage(request.getUserMessage());

        // ② 抽取参数：识别任务类型（见 parameterExtractionService.detectTaskType）
        if (StringUtils.hasText(request.getUserMessage())) {
            TaskType detectedType = parameterExtractionService.detectTaskType(request.getUserMessage(), context);
            if (detectedType != TaskType.UNKNOWN) {
                context.setTaskType(detectedType);
            }
            parameterExtractionService.extractAndMerge(context, request.getUserMessage());
        }
        // ③ 识别意图：调用意图识别服务（见 intentRecognitionService.recognize）
        UserIntent intent = intentRecognitionService.recognize(
                context, request.getUserMessage(), request.getConfirmed(), request.getCancel());
        context.setLastUserIntent(intent.name());
        provenanceService.recordInteraction(context, "user_input",
                request.getUserMessage(), "intent=" + intent.name(), true);
        // ④ 处理任务流：根据意图决定下一步（见 processTaskFlow）
        AgentInteractionResponse response;
        if (intent == UserIntent.CANCEL) {
            response = finalizeCancel(context, intent);
        } else {
            response = processTaskFlow(context, intent);
        }

        // ⑤ 结束本轮：持久化 TaskContext 并返回响应（见 finishTurn）
        finishTurn(context);
        return response;
    }

    public TaskContext getContext(String conversationId) {
        return contextStore.get(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在: " + conversationId));
    }

    public ProvenanceChain getProvenance(String conversationId) {
        return provenanceService.buildChain(getContext(conversationId));
    }

    public TaskTrace getTaskTrace(String conversationId) {
        TaskContext context = getContext(conversationId);
        return provenanceService.buildTaskTrace(context, responseBuilder.buildUnderstandingSummary(context));
    }

    private AgentInteractionResponse processTaskFlow(TaskContext context, UserIntent intent) throws Exception {
        ExecutionPlan plan = taskPlanningService.buildPlan(context);
        context.setPlan(plan);
        context.setStatus(context.getMissingInputs().isEmpty()
                ? InteractionStatus.PLANNED
                : InteractionStatus.COLLECTING_PARAMS);

        provenanceService.recordInteraction(context, "planning",
                String.valueOf(context.getSlots()), String.valueOf(plan.getSteps()), true);

        if (intent == UserIntent.CONFIRM_EXECUTE) {
            return handleConfirm(context, intent);
        }
        return responseBuilder.build(context, responseBuilder.buildCollectOrPlanReply(context), intent);
    }

    private AgentInteractionResponse finalizeCancel(TaskContext context, UserIntent intent) {
        context.setStatus(InteractionStatus.CANCELLED);
        context.setConfirmed(false);
        provenanceService.recordInteraction(context, "cancelled", context.getLastUserMessage(), "任务已取消", true);
        return responseBuilder.build(context, "已取消当前任务。", intent);
    }

    private AgentInteractionResponse handleConfirm(TaskContext context, UserIntent intent) throws Exception {
        if (!context.getMissingInputs().isEmpty()) {
            context.setConfirmed(false);
            context.setStatus(InteractionStatus.COLLECTING_PARAMS);
            provenanceService.recordInteraction(context, "confirm_rejected",
                    String.valueOf(context.getMissingInputs()), "参数未齐全，拒绝执行", false);
            return responseBuilder.build(context, responseBuilder.buildMissingParamsOnConfirmReply(context), intent);
        }
        if (context.getTaskType() == TaskType.UNKNOWN || context.getPlan() == null || !context.getPlan().isExecutable()) {
            context.setConfirmed(false);
            provenanceService.recordInteraction(context, "confirm_rejected", null, "任务类型未识别", false);
            return responseBuilder.build(context, "尚未识别到可执行的任务类型，请先描述您的业务目标。", intent);
        }
        return executeConfirmedTask(context, intent);
    }

    private AgentInteractionResponse executeConfirmedTask(TaskContext context, UserIntent intent) throws Exception {
        context.setConfirmed(true);
        context.setStatus(InteractionStatus.EXECUTING);
        provenanceService.recordInteraction(context, "execution_start",
                String.valueOf(context.getSlots()), "用户已确认，开始执行", true);

        TaskExecutor executor = taskExecutorRegistry.getExecutor(context.getTaskType());
        if (executor == null) {
            context.setStatus(InteractionStatus.FAILED);
            provenanceService.recordExecutionSummary(context, false, "未找到执行器");
            return responseBuilder.build(context, "未找到任务类型对应的执行器：" + context.getTaskType(), intent);
        }

        TaskExecutor.ExecutionResult result = executor.execute(context);
        context.setStatus(result.isSuccess() ? InteractionStatus.COMPLETED : InteractionStatus.FAILED);
        if (result.getArtifacts() != null) {
            context.getArtifacts().putAll(result.getArtifacts());
        }
        provenanceService.recordExecutionSummary(context, result.isSuccess(), result.getMessage());
        return responseBuilder.build(context, result.getMessage(), intent);
    }

    private void finishTurn(TaskContext context) {
        context.setTurnCount(context.getTurnCount() + 1);
        contextStore.save(context);
    }

    /**
     * 解析会话上下文，支撑多轮补参。
     * <ul>
     *   <li>无 conversationId：生成新 UUID 并创建空 TaskContext（首轮对话）</li>
     *   <li>有 conversationId：从内存取出已有上下文；不存在则按该 ID 新建</li>
     * </ul>
     * 后续 slots、plan、stepLogs 等均写入此对象，由 {@link #finishTurn} 持久化 把 TaskContext 保存到 InMemoryTaskContextStore 的内存 Map。
     */
    private TaskContext resolveContext(String conversationId) {
        if (StringUtils.hasText(conversationId)) {
            return contextStore.get(conversationId)
                    .orElseGet(() -> contextStore.create(conversationId));
        }
        return contextStore.create(UUID.randomUUID().toString());
    }
}
