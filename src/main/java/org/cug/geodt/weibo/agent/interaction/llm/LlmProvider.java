package org.cug.geodt.weibo.agent.interaction.llm;

/**
 * 支持的 LLM 提供商。均通过 OpenAI 兼容 Chat Completions API 调用。
 */
public enum LlmProvider {
    OPENROUTER("https://openrouter.ai/api/v1", "openrouter/free"),
    DEEPSEEK("https://api.deepseek.com/v1", "deepseek-chat"),
    OPENAI("https://api.openai.com/v1", "gpt-4o-mini"),
    QWEN("https://dashscope.aliyuncs.com/compatible-mode/v1", "qwen-plus"),
    DOUBAO("https://ark.cn-beijing.volces.com/api/v3", "doubao-pro-32k"),
    CURSOR("https://api.cursor.com/v1", "gpt-4o"),
    CUSTOM("", "");

    private final String defaultBaseUrl;
    private final String defaultModel;

    LlmProvider(String defaultBaseUrl, String defaultModel) {
        this.defaultBaseUrl = defaultBaseUrl;
        this.defaultModel = defaultModel;
    }

    public String getDefaultBaseUrl() {
        return defaultBaseUrl;
    }

    public String getDefaultModel() {
        return defaultModel;
    }

    public static LlmProvider from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return OPENROUTER;
        }
        for (LlmProvider provider : values()) {
            if (provider.name().equalsIgnoreCase(value.trim())) {
                return provider;
            }
        }
        return CUSTOM;
    }
}
