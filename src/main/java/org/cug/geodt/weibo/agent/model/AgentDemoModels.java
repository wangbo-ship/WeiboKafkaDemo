package org.cug.geodt.weibo.agent.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgentDemoModels {

    public static class QuerySosRequest {
        private String sosRequestXml;

        public String getSosRequestXml() {
            return sosRequestXml;
        }

        public void setSosRequestXml(String sosRequestXml) {
            this.sosRequestXml = sosRequestXml;
        }
    }

    public static class PublishWfsRequest {
        private String sosResponseXml;

        public String getSosResponseXml() {
            return sosResponseXml;
        }

        public void setSosResponseXml(String sosResponseXml) {
            this.sosResponseXml = sosResponseXml;
        }
    }

    public static class AgentChatRequest {
        private String userMessage;
        private Boolean confirmed;
        private String previousResponseId;
        private Integer maxToolRounds;

        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }

        public String getPreviousResponseId() {
            return previousResponseId;
        }

        public void setPreviousResponseId(String previousResponseId) {
            this.previousResponseId = previousResponseId;
        }

        public Boolean getConfirmed() {
            return confirmed;
        }

        public void setConfirmed(Boolean confirmed) {
            this.confirmed = confirmed;
        }

        public Integer getMaxToolRounds() {
            return maxToolRounds;
        }

        public void setMaxToolRounds(Integer maxToolRounds) {
            this.maxToolRounds = maxToolRounds;
        }
    }

    public static class AgentStepLog {
        private int round;
        private String toolName;
        private String callId;
        private String argumentsJson;
        private boolean success;
        private String outputPreview;

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }

        public String getToolName() {
            return toolName;
        }

        public void setToolName(String toolName) {
            this.toolName = toolName;
        }

        public String getCallId() {
            return callId;
        }

        public void setCallId(String callId) {
            this.callId = callId;
        }

        public String getArgumentsJson() {
            return argumentsJson;
        }

        public void setArgumentsJson(String argumentsJson) {
            this.argumentsJson = argumentsJson;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getOutputPreview() {
            return outputPreview;
        }

        public void setOutputPreview(String outputPreview) {
            this.outputPreview = outputPreview;
        }
    }

    public static class ExtractedParams {
        private String city;
        private String featureOfInterest;
        private String beginDate;
        private String endDate;
        private String beginTime;
        private String endTime;
        private String observedProperty;
        private Boolean shouldPublishWfs;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFeatureOfInterest() {
            return featureOfInterest;
        }

        public void setFeatureOfInterest(String featureOfInterest) {
            this.featureOfInterest = featureOfInterest;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getObservedProperty() {
            return observedProperty;
        }

        public void setObservedProperty(String observedProperty) {
            this.observedProperty = observedProperty;
        }

        public Boolean getShouldPublishWfs() {
            return shouldPublishWfs;
        }

        public void setShouldPublishWfs(Boolean shouldPublishWfs) {
            this.shouldPublishWfs = shouldPublishWfs;
        }
    }

    public static class AgentChatResponse {
        private String status;
        private String taskType;
        private List<String> plan = new ArrayList<>();
        private List<String> requiredInputs = new ArrayList<>();
        private Boolean canExecute;
        private Boolean needConfirmation;
        private ExtractedParams extractedParams;
        private String generatedSoapPreview;
        private String responseId;
        private String previousResponseId;
        private String finalText;
        private List<AgentStepLog> stepLogs = new ArrayList<>();
        private Map<String, Object> rawResponse;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public List<String> getPlan() {
            return plan;
        }

        public void setPlan(List<String> plan) {
            this.plan = plan;
        }

        public List<String> getRequiredInputs() {
            return requiredInputs;
        }

        public void setRequiredInputs(List<String> requiredInputs) {
            this.requiredInputs = requiredInputs;
        }

        public Boolean getCanExecute() {
            return canExecute;
        }

        public void setCanExecute(Boolean canExecute) {
            this.canExecute = canExecute;
        }

        public Boolean getNeedConfirmation() {
            return needConfirmation;
        }

        public void setNeedConfirmation(Boolean needConfirmation) {
            this.needConfirmation = needConfirmation;
        }

        public ExtractedParams getExtractedParams() {
            return extractedParams;
        }

        public void setExtractedParams(ExtractedParams extractedParams) {
            this.extractedParams = extractedParams;
        }

        public String getGeneratedSoapPreview() {
            return generatedSoapPreview;
        }

        public void setGeneratedSoapPreview(String generatedSoapPreview) {
            this.generatedSoapPreview = generatedSoapPreview;
        }

        public String getResponseId() {
            return responseId;
        }

        public void setResponseId(String responseId) {
            this.responseId = responseId;
        }

        public String getPreviousResponseId() {
            return previousResponseId;
        }

        public void setPreviousResponseId(String previousResponseId) {
            this.previousResponseId = previousResponseId;
        }

        public String getFinalText() {
            return finalText;
        }

        public void setFinalText(String finalText) {
            this.finalText = finalText;
        }

        public List<AgentStepLog> getStepLogs() {
            return stepLogs;
        }

        public void setStepLogs(List<AgentStepLog> stepLogs) {
            this.stepLogs = stepLogs;
        }

        public Map<String, Object> getRawResponse() {
            return rawResponse;
        }

        public void setRawResponse(Map<String, Object> rawResponse) {
            this.rawResponse = rawResponse;
        }
    }
}
