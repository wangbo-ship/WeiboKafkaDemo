package org.cug.geodt.weibo.agent.interaction.service.executor;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.cug.geodt.weibo.agent.interaction.service.TaskExecutor;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.cug.geodt.weibo.agent.service.SosAgentToolService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SOS → WFS 任务执行器，对接现有 Tool 层。
 */
@Component
public class SosToWfsTaskExecutor implements TaskExecutor {

    private static final String WEIBO_PROCEDURE = "http://www.org.cug.geodt/procedure/weibo_theme";
    private static final String WEIBO_OFFERING = "http://www.org.cug.geodt/offerings/weibo_theme";

    private final SosAgentToolService toolService;
    private final ProvenanceService provenanceService;

    public SosToWfsTaskExecutor(SosAgentToolService toolService, ProvenanceService provenanceService) {
        this.toolService = toolService;
        this.provenanceService = provenanceService;
    }

    @Override
    public TaskType supportedTaskType() {
        return TaskType.SOS_TO_WFS;
    }

    @Override
    public ExecutionResult execute(TaskContext context) throws Exception {
        Map<String, Object> slots = context.getSlots();
        String soapXml = buildSosRequestXml(slots);
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

    private String buildSosRequestXml(Map<String, Object> slots) {
        return "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\" " +
                "xmlns:sos=\"http://www.opengis.net/sos/2.0\" " +
                "xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:swe=\"http://www.opengis.net/swe/2.0\" " +
                "xmlns:swes=\"http://www.opengis.net/swes/2.0\" " +
                "xmlns:fes=\"http://www.opengis.net/fes/2.0\" " +
                "xmlns:gml=\"http://www.opengis.net/gml/3.2\" " +
                "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
                "xmlns:om=\"http://www.opengis.net/om/1.0\" " +
                "xsi:schemaLocation=\"http://www.w3.org/2003/05/soap-envelope http://www.w3.org/2003/05/soap-envelope/soap-envelope.xsd " +
                "http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd\">" +
                "<soap12:Header>" +
                "<wsa:To>http://www.ogc.org/SOS</wsa:To>" +
                "<wsa:Action>http://www.opengis.net/def/serviceOperation/sos/core/2.0/GetObservation</wsa:Action>" +
                "<wsa:ReplyTo><wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address></wsa:ReplyTo>" +
                "<wsa:MessageID>urn:uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
                "</soap12:Header>" +
                "<soap12:Body>" +
                "<sos:GetObservation service=\"SOS\" version=\"2.0.0\">" +
                "<sos:procedure>" + WEIBO_PROCEDURE + "</sos:procedure>" +
                "<sos:offering>" + WEIBO_OFFERING + "</sos:offering>" +
                "<sos:featureOfInterest>" + slots.get("featureOfInterest") + "</sos:featureOfInterest>" +
                "<sos:observedProperty>" + slots.get("observedProperty") + "</sos:observedProperty>" +
                "<sos:temporalFilter>" +
                "<fes:And>" +
                "<fes:TOverlaps>" +
                "<fes:ValueReference>SimpleTrajectory/gml:TimePeriod</fes:ValueReference>" +
                "<gml:TimePeriod gml:id=\"TP1\">" +
                "<gml:begin><gml:TimeInstant gml:id=\"TI1\" id=\"1\"><gml:timePosition>" + slots.get("beginTime") + "</gml:timePosition></gml:TimeInstant></gml:begin>" +
                "<gml:end><gml:TimeInstant gml:id=\"TI2\" id=\"2\"><gml:timePosition>" + slots.get("endTime") + "</gml:timePosition></gml:TimeInstant></gml:end>" +
                "</gml:TimePeriod>" +
                "</fes:TOverlaps>" +
                "</fes:And>" +
                "</sos:temporalFilter>" +
                "</sos:GetObservation>" +
                "</soap12:Body>" +
                "</soap12:Envelope>";
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
