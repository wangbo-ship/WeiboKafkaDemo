package org.cug.geodt.weibo.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openai.agent")
public class OpenAiAgentProperties {
    private String baseUrl;
    private String apiKey;
    private String model;
    private Integer maxToolRounds = 8;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getMaxToolRounds() {
        return maxToolRounds;
    }

    public void setMaxToolRounds(Integer maxToolRounds) {
        this.maxToolRounds = maxToolRounds;
    }
}
