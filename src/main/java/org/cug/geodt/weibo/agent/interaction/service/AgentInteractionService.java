package org.cug.geodt.weibo.agent.interaction.service;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.memory.TaskContextStore;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionRequest;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionResponse;
import org.cug.geodt.weibo.agent.interaction.model.ExecutionPlan;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Agent 交互编排服务：多轮补参、规划确认、执行与汇报整合。
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

    public AgentInteractionService(TaskContextStore contextStore,
                                   IntentRecognitionService intentRecognitionService,
                                   ParameterExtractionService parameterExtractionService,
                                   TaskPlanningService taskPlanningService,
                                   TaskExecutorRegistry taskExecutorRegistry,
                                   ProvenanceService provenanceService) {
        this.contextStore = contextStore;
        this.intentRecognitionService = intentRecognitionService;
        this.parameterExtractionService = parameterExtractionService;
        this.taskPlanningService = taskPlanningService;
        this.taskExecutorRegistry = taskExecutorRegistry;
        this.provenanceService = provenanceService;
    }

    public AgentInteractionResponse chat(AgentInteractionRequest request) throws Exception {
        TaskContext context = resolveContext(request.getConversationId());
        context.setTurnCount(context.getTurnCount() + 1);
        context.setLastUserMessage(request.getUserMessage());

        UserIntent intent = intentRecognitionService.recognize(
                context, request.getUserMessage(), request.getConfirmed(), request.getCancel());
        context.setLastUserIntent(intent.name());
        provenanceService.recordInteraction(context, "user_input",
                request.getUserMessage(), "intent=" + intent.name(), true);

        if (intent == UserIntent.CANCEL) {
            context.setStatus(InteractionStatus.CANCELLED);
            context.setConfirmed(false);
            contextStore.save(context);
            return buildResponse(context, "已取消当前任务。", intent);
        }

        if (shouldExtractParams(intent)) {
            TaskType detectedType = parameterExtractionService.detectTaskType(request.getUserMessage(), context);
            if (detectedType != TaskType.UNKNOWN) {
                context.setTaskType(detectedType);
            }
            parameterExtractionService.extractAndMerge(context, request.getUserMessage());
        }

        ExecutionPlan plan = taskPlanningService.buildPlan(context);
        context.setPlan(plan);
        context.setStatus(context.getMissingInputs().isEmpty()
                ? InteractionStatus.PLANNED
                : InteractionStatus.COLLECTING_PARAMS);

        provenanceService.recordInteraction(context, "planning",
                String.valueOf(context.getSlots()), String.valueOf(plan.getSteps()), true);

        if (intent == UserIntent.CONFIRM_EXECUTE && context.getMissingInputs().isEmpty()) {
            return executeConfirmedTask(context, intent);
        }

        contextStore.save(context);
        return buildResponse(context, buildCollectOrPlanReply(context), intent);
    }

    public TaskContext getContext(String conversationId) {
        return contextStore.get(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在: " + conversationId));
    }

    public org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain getProvenance(String conversationId) {
        TaskContext context = getContext(conversationId);
        return provenanceService.buildChain(context);
    }

    private AgentInteractionResponse executeConfirmedTask(TaskContext context, UserIntent intent) throws Exception {
        context.setConfirmed(true);
        context.setStatus(InteractionStatus.EXECUTING);
        contextStore.save(context);

        TaskExecutor executor = taskExecutorRegistry.getExecutor(context.getTaskType());
        if (executor == null) {
            context.setStatus(InteractionStatus.FAILED);
            contextStore.save(context);
            return buildResponse(context, "未找到任务类型对应的执行器：" + context.getTaskType(), intent);
        }

        TaskExecutor.ExecutionResult result = executor.execute(context);
        context.setStatus(result.isSuccess() ? InteractionStatus.COMPLETED : InteractionStatus.FAILED);
        if (result.getArtifacts() != null) {
            context.getArtifacts().putAll(result.getArtifacts());
        }
        contextStore.save(context);
        return buildResponse(context, result.getMessage(), intent);
    }

    private TaskContext resolveContext(String conversationId) {
        if (StringUtils.hasText(conversationId)) {
            return contextStore.get(conversationId)
                    .orElseGet(() -> contextStore.create(conversationId));
        }
        return contextStore.create(UUID.randomUUID().toString());
    }

    private boolean shouldExtractParams(UserIntent intent) {
        return intent == UserIntent.NEW_TASK
                || intent == UserIntent.SUPPLY_PARAMS
                || intent == UserIntent.CHANGE_TASK;
    }

    private String buildCollectOrPlanReply(TaskContext context) {
        if (!context.getMissingInputs().isEmpty()) {
            return "我已识别到部分参数，当前还缺少："
                    + String.join("、", context.getMissingInputs())
                    + "。请补充后继续。";
        }
        ExecutionPlan plan = context.getPlan();
        String steps = plan.getSteps().stream()
                .map(step -> "- " + step)
                .collect(Collectors.joining("\n"));
        return "我将执行以下步骤：\n" + steps + "\n是否确认执行？";
    }

    private AgentInteractionResponse buildResponse(TaskContext context, String replyText, UserIntent intent) {
        AgentInteractionResponse response = new AgentInteractionResponse();
        response.setConversationId(context.getConversationId());
        response.setTaskId(context.getTaskId());
        response.setStatus(context.getStatus());
        response.setTaskType(context.getTaskType());
        response.setRecognizedIntent(intent);
        response.setExtractedParams(new LinkedHashMap<>(context.getSlots()));
        response.setMissingInputs(context.getMissingInputs());
        response.setPlan(context.getPlan());
        response.setCanExecute(context.getMissingInputs().isEmpty());
        response.setNeedConfirmation(context.getStatus() == InteractionStatus.PLANNED);
        response.setReplyText(replyText);
        response.setUnderstandingSummary(buildUnderstandingSummary(context));
        response.setResultArtifacts(new LinkedHashMap<>(context.getArtifacts()));
        response.setStepLogs(context.getStepLogs());
        response.setTurnCount(context.getTurnCount());
        return response;
    }

    private String buildUnderstandingSummary(TaskContext context) {
        StringBuilder builder = new StringBuilder();
        builder.append("任务类型：").append(context.getTaskType().getLabel()).append("\n");
        if (context.getPlan() != null) {
            for (String point : context.getPlan().getSummaryPoints()) {
                builder.append("- ").append(point).append("\n");
            }
        }
        if (!context.getMissingInputs().isEmpty()) {
            builder.append("缺失参数：").append(String.join("、", context.getMissingInputs()));
        }
        return builder.toString().trim();
    }
}
