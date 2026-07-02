package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 识别用户本轮意图。当前为规则实现，后续可替换为 LLM 调用。
 */
@Service
public class IntentRecognitionService {

    private static final List<String> CONFIRM_KEYWORDS = Arrays.asList(
            "确认", "执行", "开始", "好的", "可以", "没问题", "go", "yes", "ok"
    );
    private static final List<String> CANCEL_KEYWORDS = Arrays.asList(
            "取消", "不要", "算了", "停止", "cancel", "no"
    );
    private static final List<String> CHANGE_KEYWORDS = Arrays.asList(
            "改成", "改为", "换成", "修改", "不要了改", "重新"
    );

    public UserIntent recognize(TaskContext context, String userMessage, Boolean explicitConfirmed, Boolean explicitCancel) {
        if (Boolean.TRUE.equals(explicitCancel)) {
            return UserIntent.CANCEL;
        }
        if (Boolean.TRUE.equals(explicitConfirmed)) {
            return UserIntent.CONFIRM_EXECUTE;
        }
        if (!StringUtils.hasText(userMessage)) {
            return UserIntent.UNKNOWN;
        }

        String normalized = userMessage.trim().toLowerCase();

        if (containsAny(normalized, CANCEL_KEYWORDS)) {
            return UserIntent.CANCEL;
        }
        if (containsAny(normalized, CHANGE_KEYWORDS)) {
            return UserIntent.CHANGE_TASK;
        }
        if (isFirstTurn(context)) {
            return UserIntent.NEW_TASK;
        }
        if (!context.getMissingInputs().isEmpty()) {
            return UserIntent.SUPPLY_PARAMS;
        }
        if (containsAny(normalized, CONFIRM_KEYWORDS)) {
            return UserIntent.CONFIRM_EXECUTE;
        }
        return UserIntent.SUPPLY_PARAMS;
    }

    private boolean isFirstTurn(TaskContext context) {
        return context.getTurnCount() == 0
                || context.getTaskType() == null
                || "unknown".equalsIgnoreCase(context.getTaskType().getCode());
    }

    private boolean containsAny(String text, List<String> keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
