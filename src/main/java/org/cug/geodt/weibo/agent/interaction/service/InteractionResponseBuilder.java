package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionResponse;
import org.cug.geodt.weibo.agent.interaction.model.ExecutionPlan;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.model.TaskTrace;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.cug.geodt.weibo.agent.interaction.support.SlotLabels;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * 汇报整合：将 TaskContext 组装为面向用户的统一响应。
 */
@Component
public class InteractionResponseBuilder {

    private final ProvenanceService provenanceService;

    public InteractionResponseBuilder(ProvenanceService provenanceService) {
        this.provenanceService = provenanceService;
    }

    public AgentInteractionResponse build(TaskContext context, String replyText, UserIntent intent) {
        String summary = buildUnderstandingSummary(context);
        TaskTrace trace = provenanceService.buildTaskTrace(context, summary);

        AgentInteractionResponse response = new AgentInteractionResponse();
        response.setConversationId(context.getConversationId());
        response.setTaskId(context.getTaskId());
        response.setStatus(context.getStatus());
        response.setTaskType(context.getTaskType());
        response.setRecognizedIntent(intent);
        response.setUnderstandingSummary(summary);
        response.setExtractedParams(new LinkedHashMap<>(context.getSlots()));
        response.setMissingInputs(context.getMissingInputs());
        response.setPlan(context.getPlan());
        response.setCanExecute(context.getMissingInputs().isEmpty()
                && context.getStatus() != InteractionStatus.CANCELLED);
        response.setNeedConfirmation(context.getStatus() == InteractionStatus.PLANNED);
        response.setReplyText(replyText);
        response.setResultArtifacts(new LinkedHashMap<>(context.getArtifacts()));
        response.setStepLogs(context.getStepLogs());
        response.setTaskTrace(trace);
        response.setTurnCount(context.getTurnCount());
        return response;
    }

    public String buildCollectOrPlanReply(TaskContext context) {
        if (!context.getMissingInputs().isEmpty()) {
            return "我已识别到部分参数，当前还缺少："
                    + SlotLabels.joinLabels(context.getMissingInputs())
                    + "。请补充后继续。";
        }
        ExecutionPlan plan = context.getPlan();
        if (plan == null || plan.getSteps().isEmpty()) {
            return "尚未识别到可执行的任务，请描述您要完成的业务目标。";
        }
        String steps = plan.getSteps().stream()
                .map(step -> "- " + step)
                .collect(Collectors.joining("\n"));
        return "我将执行以下步骤：\n" + steps + "\n是否确认执行？";
    }

    public String buildMissingParamsOnConfirmReply(TaskContext context) {
        return "参数尚未齐全，暂不能执行。还缺少："
                + SlotLabels.joinLabels(context.getMissingInputs())
                + "。请补充后再确认。";
    }

    public String buildUnderstandingSummary(TaskContext context) {
        StringBuilder builder = new StringBuilder();
        builder.append("任务类型：").append(context.getTaskType().getLabel()).append("\n");
        if (context.getPlan() != null) {
            for (String point : context.getPlan().getSummaryPoints()) {
                builder.append("- ").append(point).append("\n");
            }
        }
        if (!context.getMissingInputs().isEmpty()) {
            builder.append("缺失参数：").append(SlotLabels.joinLabels(context.getMissingInputs()));
        }
        return builder.toString().trim();
    }
}
