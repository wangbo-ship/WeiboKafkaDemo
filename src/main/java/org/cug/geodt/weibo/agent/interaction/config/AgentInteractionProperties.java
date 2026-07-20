package org.cug.geodt.weibo.agent.interaction.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "agent.interaction")
@Data
public class AgentInteractionProperties {
    /** 会话上下文 TTL（秒），默认 24 小时（内存存储，进程重启仍会丢失） */
    private long sessionTtlSeconds = 86400;
    /** 是否启用内存会话存储 */
    private boolean inMemoryStoreEnabled = true;
}
