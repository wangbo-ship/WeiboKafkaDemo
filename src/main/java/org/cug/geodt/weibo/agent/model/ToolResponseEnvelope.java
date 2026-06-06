package org.cug.geodt.weibo.agent.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ToolResponseEnvelope {
    private boolean success;
    private String toolName;
    private String message;
    private String requestId;
    private Map<String, Object> data;
    private Map<String, Object> meta;
    private Map<String, Object> error;

    public static ToolResponseEnvelope ok(String toolName, String message, Map<String, Object> data, Map<String, Object> meta) {
        ToolResponseEnvelope envelope = new ToolResponseEnvelope();
        envelope.success = true;
        envelope.toolName = toolName;
        envelope.message = message;
        envelope.requestId = UUID.randomUUID().toString();
        envelope.data = data;
        envelope.meta = meta;
        return envelope;
    }

    public static ToolResponseEnvelope fail(String toolName, String message, String errorCode, Exception exception) {
        ToolResponseEnvelope envelope = new ToolResponseEnvelope();
        envelope.success = false;
        envelope.toolName = toolName;
        envelope.message = message;
        envelope.requestId = UUID.randomUUID().toString();
        envelope.error = new LinkedHashMap<>();
        envelope.error.put("code", errorCode);
        envelope.error.put("detail", exception == null ? null : exception.getMessage());
        return envelope;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }
}
