package org.cug.geodt.weibo.agent.interaction.memory;

import org.cug.geodt.weibo.agent.interaction.config.AgentInteractionProperties;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTaskContextStore implements TaskContextStore {

    private final Map<String, TaskContext> store = new ConcurrentHashMap<>();
    private final Map<String, Long> expireAt = new ConcurrentHashMap<>();
    private final AgentInteractionProperties properties;

    public InMemoryTaskContextStore(AgentInteractionProperties properties) {
        this.properties = properties;
    }

    @Override
    public TaskContext create(String conversationId) {
        evictExpired();
        TaskContext context = new TaskContext();
        context.setConversationId(conversationId);
        context.setTaskId(UUID.randomUUID().toString());
        context.setStatus(InteractionStatus.COLLECTING_PARAMS);
        long now = System.currentTimeMillis();
        context.setCreatedAt(now);
        context.setUpdatedAt(now);
        save(context);
        return context;
    }

    @Override
    public Optional<TaskContext> get(String conversationId) {
        evictExpired();
        return Optional.ofNullable(store.get(conversationId));
    }

    @Override
    public void save(TaskContext context) {
        context.setUpdatedAt(System.currentTimeMillis());
        store.put(context.getConversationId(), context);
        expireAt.put(context.getConversationId(),
                System.currentTimeMillis() + properties.getSessionTtlSeconds() * 1000);
    }

    @Override
    public void delete(String conversationId) {
        store.remove(conversationId);
        expireAt.remove(conversationId);
    }

    private void evictExpired() {
        long now = System.currentTimeMillis();
        expireAt.entrySet().removeIf(entry -> {
            if (entry.getValue() < now) {
                store.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }
}
