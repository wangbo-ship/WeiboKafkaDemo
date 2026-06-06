package org.cug.geodt.weibo.agent.service;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.model.AgentDemoModels;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class OpenAiResponsesService {

    private static final String TASK_TYPE_SOS_TO_WFS = "sos_to_wfs";
    private static final String WUHAN_FEATURE = "http://www.org.cug.geodt/feature/city4201";
    private static final String WEIBO_PROCEDURE = "http://www.org.cug.geodt/procedure/weibo_theme";
    private static final String WEIBO_OFFERING = "http://www.org.cug.geodt/offerings/weibo_theme";
    private static final Pattern FIXED_SENTENCE_PATTERN = Pattern.compile(
            "我要(?<city>.+?)(?<beginYear>\\d{4})年(?<beginMonth>\\d{1,2})月(?<beginDay>\\d{1,2})日到(?:(?<endYear>\\d{4})年)?(?<endMonth>\\d{1,2})月(?<endDay>\\d{1,2})日的(?<observedProperty>.+?)数据(?<publishPart>.*)"
    );

    @Autowired
    private SosAgentToolService toolService;

    public AgentDemoModels.AgentChatResponse runConversation(AgentDemoModels.AgentChatRequest request) throws Exception {
        ExtractResult extractResult = extractParams(request.getUserMessage());
        String generatedSoapPreview = extractResult.params == null ? null : buildSosRequestXml(extractResult.params);

        if (!Boolean.TRUE.equals(request.getConfirmed())) {
            return buildPlannedResponse(request, extractResult, generatedSoapPreview);
        }
        return executeWorkflow(request, extractResult, generatedSoapPreview);
    }

    private AgentDemoModels.AgentChatResponse buildPlannedResponse(AgentDemoModels.AgentChatRequest request,
                                                                   ExtractResult extractResult,
                                                                   String generatedSoapPreview) {
        AgentDemoModels.AgentChatResponse response = new AgentDemoModels.AgentChatResponse();
        response.setStatus("planned");
        response.setTaskType(TASK_TYPE_SOS_TO_WFS);
        response.setPlan(buildPlan(extractResult.params));
        response.setRequiredInputs(extractResult.requiredInputs);
        response.setCanExecute(extractResult.requiredInputs.isEmpty());
        response.setNeedConfirmation(true);
        response.setExtractedParams(extractResult.params);
        response.setGeneratedSoapPreview(generatedSoapPreview);
        response.setPreviousResponseId(request.getPreviousResponseId());
        response.setFinalText("已完成参数抽取和执行计划生成，等待确认后执行。");
        return response;
    }

    private AgentDemoModels.AgentChatResponse executeWorkflow(AgentDemoModels.AgentChatRequest request,
                                                              ExtractResult extractResult,
                                                              String generatedSoapPreview) throws Exception {
        if (!extractResult.requiredInputs.isEmpty()) {
            AgentDemoModels.AgentChatResponse response = buildPlannedResponse(request, extractResult, generatedSoapPreview);
            response.setCanExecute(false);
            response.setFinalText("缺少必要输入，暂不执行。");
            return response;
        }

        List<AgentDemoModels.AgentStepLog> stepLogs = new ArrayList<>();
        Map<String, Object> rawResponse = new LinkedHashMap<>();
        rawResponse.put("extractedParams", extractResult.params);
        rawResponse.put("generatedSoapPreview", generatedSoapPreview);

        ToolResponseEnvelope queryResponse = toolService.querySos(generatedSoapPreview);
        stepLogs.add(buildStepLog(1, "query_sos", queryResponse, "{\"sosRequestXml\":\"generated-by-backend\"}"));
        rawResponse.put("querySosResult", queryResponse);
        log.info("executeWorkflow query_sos result summary={}", stepLogs.get(stepLogs.size() - 1).getOutputPreview());

        if (!queryResponse.isSuccess()) {
            AgentDemoModels.AgentChatResponse response = new AgentDemoModels.AgentChatResponse();
            response.setStatus("failed");
            response.setTaskType(TASK_TYPE_SOS_TO_WFS);
            response.setPlan(buildPlan(extractResult.params));
            response.setRequiredInputs(extractResult.requiredInputs);
            response.setCanExecute(true);
            response.setNeedConfirmation(false);
            response.setExtractedParams(extractResult.params);
            response.setGeneratedSoapPreview(generatedSoapPreview);
            response.setResponseId(UUID.randomUUID().toString());
            response.setPreviousResponseId(request.getPreviousResponseId());
            response.setFinalText("query_sos 执行失败。");
            response.setStepLogs(stepLogs);
            response.setRawResponse(rawResponse);
            return response;
        }

        ToolResponseEnvelope publishResponse = null;
        if (Boolean.TRUE.equals(extractResult.params.getShouldPublishWfs())) {
            String sosResponseXml = queryResponse.getData() == null ? null : String.valueOf(queryResponse.getData().get("sosResponseXml"));
            publishResponse = toolService.publishWfs(sosResponseXml);
            stepLogs.add(buildStepLog(2, "publish_wfs", publishResponse, "{\"sosResponseXml\":\"from-query-sos\"}"));
            rawResponse.put("publishWfsResult", publishResponse);
            log.info("executeWorkflow publish_wfs result summary={}", stepLogs.get(stepLogs.size() - 1).getOutputPreview());
        }

        AgentDemoModels.AgentChatResponse response = new AgentDemoModels.AgentChatResponse();
        response.setStatus("completed");
        response.setTaskType(TASK_TYPE_SOS_TO_WFS);
        response.setPlan(buildPlan(extractResult.params));
        response.setRequiredInputs(extractResult.requiredInputs);
        response.setCanExecute(true);
        response.setNeedConfirmation(false);
        response.setExtractedParams(extractResult.params);
        response.setGeneratedSoapPreview(generatedSoapPreview);
        response.setResponseId(UUID.randomUUID().toString());
        response.setPreviousResponseId(request.getPreviousResponseId());
        response.setFinalText(buildFinalText(queryResponse, publishResponse));
        response.setStepLogs(stepLogs);
        response.setRawResponse(rawResponse);
        return response;
    }

    private AgentDemoModels.AgentStepLog buildStepLog(int round, String toolName, ToolResponseEnvelope result, String argumentsJson) {
        AgentDemoModels.AgentStepLog stepLog = new AgentDemoModels.AgentStepLog();
        stepLog.setRound(round);
        stepLog.setToolName(toolName);
        stepLog.setCallId(UUID.randomUUID().toString());
        stepLog.setArgumentsJson(argumentsJson);
        stepLog.setSuccess(result != null && result.isSuccess());
        stepLog.setOutputPreview(buildPreview(result));
        return stepLog;
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

    private String buildFinalText(ToolResponseEnvelope queryResponse, ToolResponseEnvelope publishResponse) {
        if (publishResponse != null && publishResponse.isSuccess() && publishResponse.getData() != null) {
            return "已完成 SOS 查询并发布 WFS。WFS URL: " + publishResponse.getData().get("wfsUrl");
        }
        if (queryResponse != null && queryResponse.isSuccess()) {
            return "已完成 SOS 查询。";
        }
        return "执行完成。";
    }

    private List<String> buildPlan(AgentDemoModels.ExtractedParams params) {
        List<String> plan = new ArrayList<>();
        plan.add("调用 SOS 查询工具获取观测结果");
        if (params != null && Boolean.TRUE.equals(params.getShouldPublishWfs())) {
            plan.add("调用 WFS 发布工具发布结果");
        }
        return plan;
    }

    private ExtractResult extractParams(String userMessage) {
        ExtractResult result = new ExtractResult();
        result.requiredInputs = new ArrayList<>();
        AgentDemoModels.ExtractedParams params = new AgentDemoModels.ExtractedParams();

        if (!StringUtils.hasText(userMessage)) {
            result.requiredInputs.add("userMessage");
            result.params = params;
            return result;
        }

        Matcher matcher = FIXED_SENTENCE_PATTERN.matcher(userMessage.replace(" ", ""));
        if (!matcher.find()) {
            result.requiredInputs.add("city");
            result.requiredInputs.add("beginDate");
            result.requiredInputs.add("endDate");
            result.requiredInputs.add("observedProperty");
            result.params = params;
            return result;
        }

        String city = matcher.group("city");
        String beginYear = matcher.group("beginYear");
        String beginMonth = matcher.group("beginMonth");
        String beginDay = matcher.group("beginDay");
        String endYear = matcher.group("endYear");
        String endMonth = matcher.group("endMonth");
        String endDay = matcher.group("endDay");
        String observedProperty = matcher.group("observedProperty");
        String publishPart = matcher.group("publishPart");

        if (!StringUtils.hasText(endYear)) {
            endYear = beginYear;
        }

        params.setCity(city);
        params.setFeatureOfInterest(mapFeatureOfInterest(city));
        params.setBeginDate(formatDate(beginYear, beginMonth, beginDay));
        params.setEndDate(formatDate(endYear, endMonth, endDay));
        params.setBeginTime(params.getBeginDate() + "T00:00:00Z");
        params.setEndTime(params.getEndDate() + "T23:59:59Z");
        params.setObservedProperty(observedProperty);
        params.setShouldPublishWfs(publishPart != null && publishPart.contains("WFS"));

        if (!StringUtils.hasText(params.getFeatureOfInterest())) {
            result.requiredInputs.add("supportedCity");
        }
        if (!StringUtils.hasText(params.getObservedProperty())) {
            result.requiredInputs.add("observedProperty");
        }

        log.info("extractParams result city={}, beginDate={}, endDate={}, observedProperty={}, shouldPublishWfs={}",
                params.getCity(), params.getBeginDate(), params.getEndDate(), params.getObservedProperty(), params.getShouldPublishWfs());

        result.params = params;
        return result;
    }

    private String mapFeatureOfInterest(String city) {
        if ("武汉市".equals(city)) {
            return WUHAN_FEATURE;
        }
        return null;
    }

    private String formatDate(String year, String month, String day) {
        LocalDate date = LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day)
        );
        return date.toString();
    }

    private String buildSosRequestXml(AgentDemoModels.ExtractedParams params) {
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
                "<sos:featureOfInterest>" + params.getFeatureOfInterest() + "</sos:featureOfInterest>" +
                "<sos:observedProperty>" + params.getObservedProperty() + "</sos:observedProperty>" +
                "<sos:temporalFilter>" +
                "<fes:And>" +
                "<fes:TOverlaps>" +
                "<fes:ValueReference>SimpleTrajectory/gml:TimePeriod</fes:ValueReference>" +
                "<gml:TimePeriod gml:id=\"TP1\">" +
                "<gml:begin><gml:TimeInstant gml:id=\"TI1\" id=\"1\"><gml:timePosition>" + params.getBeginTime() + "</gml:timePosition></gml:TimeInstant></gml:begin>" +
                "<gml:end><gml:TimeInstant gml:id=\"TI2\" id=\"2\"><gml:timePosition>" + params.getEndTime() + "</gml:timePosition></gml:TimeInstant></gml:end>" +
                "</gml:TimePeriod>" +
                "</fes:TOverlaps>" +
                "</fes:And>" +
                "</sos:temporalFilter>" +
                "</sos:GetObservation>" +
                "</soap12:Body>" +
                "</soap12:Envelope>";
    }

    private static class ExtractResult {
        private AgentDemoModels.ExtractedParams params;
        private List<String> requiredInputs;
    }
}
