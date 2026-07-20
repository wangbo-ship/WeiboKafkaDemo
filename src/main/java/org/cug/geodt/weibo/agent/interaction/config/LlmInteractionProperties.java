package org.cug.geodt.weibo.agent.interaction.config;

import lombok.Data;
import org.cug.geodt.weibo.agent.interaction.llm.LlmProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "agent.interaction.llm")
@Data
public class LlmInteractionProperties {

    /** 当前使用的 LLM 提供商：openrouter / deepseek / openai / qwen / doubao / cursor / custom */
    private String provider = "openrouter";

    /** API Key，建议通过环境变量 LLM_API_KEY 注入 */
    private String apiKey = "";

    /** 覆盖默认 base-url（custom 时必填） */
    private String baseUrl = "";

    /** 覆盖默认 model */
    private String model = "";

    /** 采样温度，越低越稳定 */
    private double temperature = 0.2;

    /** HTTP 超时（秒） */
    private int timeoutSeconds = 60;

    public LlmProvider resolvedProvider() {
        return LlmProvider.from(provider);
    }

    public String resolvedBaseUrl() {
        if (StringUtils.hasText(baseUrl)) {
            return trimTrailingSlash(baseUrl);
        }
        LlmProvider p = resolvedProvider();
        return p == LlmProvider.CUSTOM ? "" : trimTrailingSlash(p.getDefaultBaseUrl());
    }

    public String resolvedModel() {
        if (StringUtils.hasText(model)) {
            return model;
        }
        LlmProvider p = resolvedProvider();
        return p == LlmProvider.CUSTOM ? "gpt-4o-mini" : p.getDefaultModel();
    }

    public String resolvedApiKey() {
        if (StringUtils.hasText(apiKey)) {
            return apiKey;
        }
        String env = System.getenv("LLM_API_KEY");
        if (StringUtils.hasText(env)) {
            return env;
        }
        return System.getenv("OPENROUTER_API_KEY");
    }

    private static String trimTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
