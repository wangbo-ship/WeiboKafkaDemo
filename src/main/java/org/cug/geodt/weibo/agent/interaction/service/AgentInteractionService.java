package org.cug.geodt.weibo.agent.interaction.service;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.memory.TaskContextStore;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionRequest;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionResponse;
import org.cug.geodt.weibo.agent.interaction.model.LlmAnalysisResult;
import org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.model.TaskTrace;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Agent 交互编排服务：LLM 理解 → 规划确认 → Skill/Tool 执行。
 */
@Service
@Slf4j
public class AgentInteractionService {

    private final TaskContextStore contextStore;
    private final LlmInteractionAnalyzer llmInteractionAnalyzer;
    private final TaskExecutorRegistry taskExecutorRegistry;
    private final ProvenanceService provenanceService;
    private final InteractionResponseBuilder responseBuilder;

    public AgentInteractionService(TaskContextStore contextStore,
                                   LlmInteractionAnalyzer llmInteractionAnalyzer,
                                   TaskExecutorRegistry taskExecutorRegistry,
                                   ProvenanceService provenanceService,
                                   InteractionResponseBuilder responseBuilder) {
        this.contextStore = contextStore;
        this.llmInteractionAnalyzer = llmInteractionAnalyzer;
        this.taskExecutorRegistry = taskExecutorRegistry;
        this.provenanceService = provenanceService;
        this.responseBuilder = responseBuilder;
    }

    public AgentInteractionResponse chat(AgentInteractionRequest request) throws Exception {
        TaskContext context = resolveContext(request.getConversationId());
        context.setLastUserMessage(request.getUserMessage());

        LlmAnalysisResult analysis = llmInteractionAnalyzer.analyze(
                context,
                request.getUserMessage(),
                request.getConfirmed(),
                request.getCancel()
        );
        llmInteractionAnalyzer.applyToContext(context, analysis);

        UserIntent intent = analysis.getIntent();
        context.setLastUserIntent(intent.name());
        provenanceService.recordInteraction(context, "llm_analysis",
                request.getUserMessage(),
                "intent=" + intent.name() + ", skillId=" + context.getSkillId(),
                true);

        AgentInteractionResponse response;
        if (intent == UserIntent.CANCEL) {
            response = finalizeCancel(context, intent, analysis.getReplySuggestion());
        } else {
            context.setStatus(context.getMissingInputs().isEmpty()
                    ? InteractionStatus.PLANNED
                    : InteractionStatus.COLLECTING_PARAMS);
            provenanceService.recordInteraction(context, "planning",
                    String.valueOf(context.getSlots()),
                    String.valueOf(context.getPlan() == null ? null : context.getPlan().getSteps()),
                    true);

            if (intent == UserIntent.CONFIRM_EXECUTE) {
                response = handleConfirm(context, intent, analysis.getReplySuggestion());
            } else {
                String reply = StringUtils.hasText(analysis.getReplySuggestion())
                        ? analysis.getReplySuggestion()
                        : responseBuilder.buildCollectOrPlanReply(context);
                response = responseBuilder.build(context, reply, intent);
            }
        }

        finishTurn(context);
        // 本轮结束后的轮次写回响应，避免一直看到 turnCount=0
        response.setTurnCount(context.getTurnCount());
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

    private AgentInteractionResponse finalizeCancel(TaskContext context, UserIntent intent, String replySuggestion) {
        context.setStatus(InteractionStatus.CANCELLED);
        context.setConfirmed(false);
        provenanceService.recordInteraction(context, "cancelled", context.getLastUserMessage(), "任务已取消", true);
        String reply = StringUtils.hasText(replySuggestion) ? replySuggestion : "已取消当前任务。";
        return responseBuilder.build(context, reply, intent);
    }

    private AgentInteractionResponse handleConfirm(TaskContext context, UserIntent intent, String replySuggestion) throws Exception {
        if (!context.getMissingInputs().isEmpty()) {
            context.setConfirmed(false);
            context.setStatus(InteractionStatus.COLLECTING_PARAMS);
            provenanceService.recordInteraction(context, "confirm_rejected",
                    String.valueOf(context.getMissingInputs()), "参数未齐全，拒绝执行", false);
            return responseBuilder.build(context, responseBuilder.buildMissingParamsOnConfirmReply(context), intent);
        }
        if (context.getTaskType() == TaskType.UNKNOWN
                || context.getSkillDefinition() == null
                || context.getPlan() == null
                || !context.getPlan().isExecutable()) {
            context.setConfirmed(false);
            provenanceService.recordInteraction(context, "confirm_rejected", null, "Skill 未识别或不可执行", false);
            String reply = StringUtils.hasText(replySuggestion)
                    ? replySuggestion
                    : "尚未识别到可执行的 Skill，请先描述您的业务目标。";
            return responseBuilder.build(context, reply, intent);
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
            return responseBuilder.build(context,
                    "未找到 Skill 对应的执行器：" + context.getTaskType(), intent);
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

    private TaskContext resolveContext(String conversationId) {
        if (StringUtils.hasText(conversationId)) {
            return contextStore.get(conversationId)
                    .orElseGet(() -> {
                        log.warn("会话不存在或已过期，将重建空上下文 conversationId={}", conversationId);
                        return contextStore.create(conversationId);
                    });
        }
        return contextStore.create(UUID.randomUUID().toString());
    }
}
