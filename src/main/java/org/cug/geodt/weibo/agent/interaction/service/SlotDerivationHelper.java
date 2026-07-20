package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 执行前对 LLM 抽取的 slots 做派生字段补全（非 NLU，仅服务于已有 Tool）。
 */
@Component
public class SlotDerivationHelper {

    private static final String WUHAN_FEATURE = "http://www.org.cug.geodt/feature/city4201";

    public void enrich(TaskContext context) {
        Map<String, Object> slots = context.getSlots();
        if (slots == null) {
            return;
        }
        if (slots.containsKey("beginDate") && StringUtils.hasText(String.valueOf(slots.get("beginDate")))) {
            slots.put("beginTime", slots.get("beginDate") + "T00:00:00Z");
        }
        if (slots.containsKey("endDate") && StringUtils.hasText(String.valueOf(slots.get("endDate")))) {
            slots.put("endTime", slots.get("endDate") + "T23:59:59Z");
        }
        if (!slots.containsKey("shouldPublishWfs")) {
            slots.put("shouldPublishWfs", Boolean.FALSE);
        }
        Object city = slots.get("city");
        if (city != null && !StringUtils.hasText(stringVal(slots.get("featureOfInterest")))) {
            String cityStr = String.valueOf(city);
            if (cityStr.contains("武汉")) {
                slots.put("featureOfInterest", WUHAN_FEATURE);
            }
        }
        context.setSlots(new LinkedHashMap<>(slots));
    }

    private static String stringVal(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
