package org.cug.geodt.weibo.agent.interaction.support;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数槽位的中文展示名，用于缺参提示与理解摘要。
 */
public final class SlotLabels {

    private static final Map<String, String> LABELS = new LinkedHashMap<>();

    static {
        LABELS.put("city", "城市");
        LABELS.put("beginDate", "开始日期");
        LABELS.put("endDate", "结束日期");
        LABELS.put("observedProperty", "观测属性");
        LABELS.put("shouldPublishWfs", "是否发布 WFS");
        LABELS.put("featureOfInterest", "兴趣区域");
        LABELS.put("supportedCity", "支持的城市（当前仅武汉市可用）");
        LABELS.put("wfsLayer", "WFS 图层");
        LABELS.put("analysisType", "分析类型");
        LABELS.put("outputLayerName", "输出图层名");
        LABELS.put("eventKeyword", "事件关键词");
        LABELS.put("analysisDepth", "分析深度");
        LABELS.put("taskType", "任务类型");
    }

    private SlotLabels() {
    }

    public static String label(String slotKey) {
        return LABELS.getOrDefault(slotKey, slotKey);
    }

    public static String joinLabels(List<String> slotKeys) {
        return slotKeys.stream()
                .map(SlotLabels::label)
                .collect(Collectors.joining("、"));
    }
}
