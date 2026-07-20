package org.cug.geodt.weibo.agent.interaction.llm;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.config.LlmInteractionProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenAI 兼容 Chat Completions 客户端，支持 OpenRouter / DeepSeek / Qwen / 豆包等。
 */
@Component
@Slf4j
public class LlmChatClient {

    private final LlmInteractionProperties properties;
    private final Gson gson = new Gson();
    private RestTemplate restTemplate;

    public LlmChatClient(LlmInteractionProperties properties) {
        this.properties = properties;
    }

    public String chat(String systemPrompt, String userPrompt) {
        validateConfig();

        JsonObject body = new JsonObject();
        body.addProperty("model", properties.resolvedModel());
        body.addProperty("temperature", properties.getTemperature());

        JsonArray messages = new JsonArray();
        messages.add(buildMessage("system", systemPrompt));
        messages.add(buildMessage("user", userPrompt));
        body.add("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.resolvedApiKey());
        if (properties.resolvedProvider() == LlmProvider.OPENROUTER) {
            headers.set("HTTP-Referer", "http://localhost:54323");
            headers.set("X-Title", "WeiboKafkaDemo Agent Interaction");
        }

        String url = properties.resolvedBaseUrl() + "/chat/completions";
        log.info("LLM request provider={}, model={}, url={}",
                properties.getProvider(), properties.resolvedModel(), url);

        ResponseEntity<String> response = restTemplate().postForEntity(
                url, new HttpEntity<>(gson.toJson(body), headers), String.class);

        JsonObject json = gson.fromJson(response.getBody(), JsonObject.class);
        if (json == null || !json.has("choices")) {
            throw new IllegalStateException("LLM 返回格式异常: " + response.getBody());
        }
        JsonArray choices = json.getAsJsonArray("choices");
        if (choices.size() == 0) {
            throw new IllegalStateException("LLM 未返回 choices");
        }
        JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
        return message.get("content").getAsString();
    }

    private void validateConfig() {
        if (!StringUtils.hasText(properties.resolvedApiKey())) {
            throw new IllegalStateException(
                    "未配置 LLM API Key。请在 application.yml 设置 agent.interaction.llm.api-key，"
                            + "或设置环境变量 LLM_API_KEY / OPENROUTER_API_KEY");
        }
        if (!StringUtils.hasText(properties.resolvedBaseUrl())) {
            throw new IllegalStateException(
                    "provider=custom 时必须配置 agent.interaction.llm.base-url");
        }
    }

    private JsonObject buildMessage(String role, String content) {
        JsonObject msg = new JsonObject();
        msg.addProperty("role", role);
        msg.addProperty("content", content);
        return msg;
    }

    private RestTemplate restTemplate() {
        if (restTemplate == null) {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(properties.getTimeoutSeconds() * 1000);
            factory.setReadTimeout(properties.getTimeoutSeconds() * 1000);
            restTemplate = new RestTemplate(factory);
        }
        return restTemplate;
    }
}
