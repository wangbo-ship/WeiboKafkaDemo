package org.cug.geodt.weibo.agent.interaction.service.executor;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.cug.geodt.weibo.agent.interaction.service.SosSoapBuilder;
import org.cug.geodt.weibo.agent.interaction.service.TaskExecutor;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.cug.geodt.weibo.agent.service.SosAgentToolService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SOS → WFS 任务执行器，对接现有 Tool 层。
 */
@Component
public class SosToWfsTaskExecutor implements TaskExecutor {

    private final SosAgentToolService toolService;
    private final ProvenanceService provenanceService;
    private final SosSoapBuilder sosSoapBuilder;

    public SosToWfsTaskExecutor(SosAgentToolService toolService,
                                ProvenanceService provenanceService,
                                SosSoapBuilder sosSoapBuilder) {
        this.toolService = toolService;
        this.provenanceService = provenanceService;
        this.sosSoapBuilder = sosSoapBuilder;
    }

    @Override
    public TaskType supportedTaskType() {
        return TaskType.SOS_TO_WFS;
    }

    @Override
    public ExecutionResult execute(TaskContext context) throws Exception {
        Map<String, Object> slots = context.getSlots();
        String soapXml = sosSoapBuilder.buildFromSlots(slots);
        context.getArtifacts().put("generatedSoapPreview", soapXml);

        ToolResponseEnvelope queryResult = toolService.querySos(soapXml);
        provenanceService.recordToolCall(context, "sos_to_wfs_skill", "query_sos",
                "{\"sosRequestXml\":\"generated-by-interaction-layer\"}",
                buildPreview(queryResult), queryResult.isSuccess(),
                queryResult.isSuccess() ? null : queryResult.getMessage());

        if (!queryResult.isSuccess()) {
            return new ExecutionResult(false, "SOS 查询失败：" + queryResult.getMessage(), context.getArtifacts());
        }

        context.getArtifacts().put("sosResponseXml",
                queryResult.getData() == null ? null : queryResult.getData().get("sosResponseXml"));

        if (Boolean.TRUE.equals(slots.get("shouldPublishWfs"))) {
            String sosResponseXml = queryResult.getData() == null ? null
                    : String.valueOf(queryResult.getData().get("sosResponseXml"));
            ToolResponseEnvelope publishResult = toolService.publishWfs(sosResponseXml);
            provenanceService.recordToolCall(context, "sos_to_wfs_skill", "publish_wfs",
                    "{\"sosResponseXml\":\"from-query-sos\"}",
                    buildPreview(publishResult), publishResult.isSuccess(),
                    publishResult.isSuccess() ? null : publishResult.getMessage());

            if (publishResult.isSuccess() && publishResult.getData() != null) {
                context.getArtifacts().put("wfsUrl", publishResult.getData().get("wfsUrl"));
                return new ExecutionResult(true,
                        "已完成 SOS 查询并发布 WFS。WFS URL: " + publishResult.getData().get("wfsUrl"),
                        context.getArtifacts());
            }
            return new ExecutionResult(false, "WFS 发布失败：" + publishResult.getMessage(), context.getArtifacts());
        }

        return new ExecutionResult(true, "已完成 SOS 查询。", context.getArtifacts());
    }

    private String buildPreview(ToolResponseEnvelope result) {
        if (result == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("success=").append(result.isSuccess());
        builder.append(", message=").append(result.getMessage());
        if (result.getData() != null && result.getData().get("wfsUrl") != null) {
            builder.append(", wfsUrl=").append(result.getData().get("wfsUrl"));
        }
        return builder.toString();
    }
}
