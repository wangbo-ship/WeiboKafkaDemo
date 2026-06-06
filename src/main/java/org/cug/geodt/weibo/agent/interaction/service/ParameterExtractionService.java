package org.cug.geodt.weibo.agent.interaction.service;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.model.TaskSlotDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数抽取与多轮合并。当前为规则引擎，后续可接入 LLM function calling。
 */
@Service
@Slf4j
public class ParameterExtractionService {

    private static final String WUHAN_FEATURE = "http://www.org.cug.geodt/feature/city4201";
    private static final Map<String, TaskSlotDefinition> SLOT_DEFINITIONS = buildSlotDefinitions();

    private static final Pattern FULL_SOS_PATTERN = Pattern.compile(
            "我要(?<city>.+?)(?<beginYear>\\d{4})年(?<beginMonth>\\d{1,2})月(?<beginDay>\\d{1,2})日到(?:(?<endYear>\\d{4})年)?(?<endMonth>\\d{1,2})月(?<endDay>\\d{1,2})日的(?<observedProperty>.+?)数据(?<publishPart>.*)"
    );
    private static final Pattern CITY_PATTERN = Pattern.compile("(?:我要|城市|地区|查询)?(?<city>[\\u4e00-\\u9fa5]{2,10}?)(?:市|的|地区|$)");
    private static final Pattern DATE_RANGE_PATTERN = Pattern.compile(
            "(?<beginYear>\\d{4})年(?<beginMonth>\\d{1,2})月(?<beginDay>\\d{1,2})日(?:到|至|-)(?:(?<endYear>\\d{4})年)?(?<endMonth>\\d{1,2})月(?<endDay>\\d{1,2})日"
    );
    private static final Pattern OBSERVED_PROPERTY_PATTERN = Pattern.compile("(?:观测属性|属性|的)(?<observedProperty>[\\u4e00-\\u9fa5A-Za-z0-9_]+)");
    private static final Pattern WFS_LAYER_PATTERN = Pattern.compile("(?:图层|layer|wfs)[：:\\s]*(?<layer>[\\w\\-:/.]+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern ANALYSIS_TYPE_PATTERN = Pattern.compile("(?:分析类型|分析|做)(?<analysisType>[\\u4e00-\\u9fa5A-Za-z0-9_]+)");
    private static final Pattern EVENT_KEYWORD_PATTERN = Pattern.compile("(?:事件|关键词|关键字)[：:\\s]*(?<keyword>[\\u4e00-\\u9fa5\\w]+)");

    public TaskType detectTaskType(String userMessage, TaskContext context) {
        if (!StringUtils.hasText(userMessage)) {
            return context.getTaskType() != null ? context.getTaskType() : TaskType.UNKNOWN;
        }
        String text = userMessage.replace(" ", "");
        if (text.contains("WFS") && (text.contains("空间分析") || text.contains("分析"))) {
            return TaskType.WFS_SPATIAL_ANALYSIS;
        }
        if (text.contains("微博") && (text.contains("事件") || text.contains("分析"))) {
            return TaskType.WEIBO_EVENT_ANALYSIS;
        }
        if (text.contains("观测") || text.contains("SOS") || text.contains("数据")
                || FULL_SOS_PATTERN.matcher(text).find()) {
            return TaskType.SOS_TO_WFS;
        }
        if (context.getTaskType() != null && context.getTaskType() != TaskType.UNKNOWN) {
            return context.getTaskType();
        }
        return TaskType.UNKNOWN;
    }

    public Map<String, Object> extractAndMerge(TaskContext context, String userMessage) {
        Map<String, Object> extracted = extractFromMessage(userMessage, context.getTaskType());
        Map<String, Object> merged = new LinkedHashMap<>(context.getSlots());
        extracted.forEach((key, value) -> {
            if (value != null && StringUtils.hasText(String.valueOf(value))) {
                merged.put(key, value);
            }
        });
        context.setSlots(merged);
        refreshDerivedSlots(context);
        context.setMissingInputs(calculateMissingInputs(context.getTaskType(), merged));
        log.info("extractAndMerge conversationId={}, taskType={}, slots={}, missing={}",
                context.getConversationId(), context.getTaskType(), merged, context.getMissingInputs());
        return merged;
    }

    public List<String> calculateMissingInputs(TaskType taskType, Map<String, Object> slots) {
        List<String> missing = new ArrayList<>();
        TaskSlotDefinition definition = SLOT_DEFINITIONS.get(taskType.getCode());
        if (definition == null) {
            missing.add("taskType");
            return missing;
        }
        for (String slot : definition.getRequiredSlots()) {
            Object value = slots.get(slot);
            if (value == null || !StringUtils.hasText(String.valueOf(value))) {
                missing.add(slot);
            }
        }
        if (taskType == TaskType.SOS_TO_WFS && slots.containsKey("city")
                && !StringUtils.hasText(String.valueOf(slots.get("featureOfInterest")))) {
            missing.add("supportedCity");
        }
        return missing;
    }

    private Map<String, Object> extractFromMessage(String userMessage, TaskType taskType) {
        Map<String, Object> slots = new HashMap<>();
        if (!StringUtils.hasText(userMessage)) {
            return slots;
        }
        String text = userMessage.replace(" ", "");

        if (taskType == TaskType.SOS_TO_WFS || taskType == TaskType.UNKNOWN) {
            extractSosSlots(text, slots);
        }
        if (taskType == TaskType.WFS_SPATIAL_ANALYSIS) {
            extractSpatialSlots(text, slots);
        }
        if (taskType == TaskType.WEIBO_EVENT_ANALYSIS) {
            extractWeiboEventSlots(text, slots);
        }
        return slots;
    }

    private void extractSosSlots(String text, Map<String, Object> slots) {
        Matcher fullMatcher = FULL_SOS_PATTERN.matcher(text);
        if (fullMatcher.find()) {
            fillSosDateSlots(slots, fullMatcher);
            slots.put("city", normalizeCity(fullMatcher.group("city")));
            slots.put("observedProperty", fullMatcher.group("observedProperty"));
            String publishPart = fullMatcher.group("publishPart");
            slots.put("shouldPublishWfs", publishPart != null && publishPart.toUpperCase().contains("WFS"));
            return;
        }

        Matcher dateMatcher = DATE_RANGE_PATTERN.matcher(text);
        if (dateMatcher.find()) {
            fillSosDateSlots(slots, dateMatcher);
        }

        Matcher cityMatcher = CITY_PATTERN.matcher(text);
        if (cityMatcher.find()) {
            slots.put("city", normalizeCity(cityMatcher.group("city")));
        }

        Matcher propertyMatcher = OBSERVED_PROPERTY_PATTERN.matcher(text);
        if (propertyMatcher.find()) {
            slots.put("observedProperty", propertyMatcher.group("observedProperty"));
        }

        if (text.toUpperCase().contains("WFS")) {
            slots.put("shouldPublishWfs", true);
        }
        if (text.contains("不发布") || text.contains("无需发布")) {
            slots.put("shouldPublishWfs", false);
        }
    }

    private void extractSpatialSlots(String text, Map<String, Object> slots) {
        Matcher layerMatcher = WFS_LAYER_PATTERN.matcher(text);
        if (layerMatcher.find()) {
            slots.put("wfsLayer", layerMatcher.group("layer"));
        }
        Matcher analysisMatcher = ANALYSIS_TYPE_PATTERN.matcher(text);
        if (analysisMatcher.find()) {
            slots.put("analysisType", analysisMatcher.group("analysisType"));
        }
    }

    private void extractWeiboEventSlots(String text, Map<String, Object> slots) {
        Matcher cityMatcher = CITY_PATTERN.matcher(text);
        if (cityMatcher.find()) {
            slots.put("city", normalizeCity(cityMatcher.group("city")));
        }
        Matcher dateMatcher = DATE_RANGE_PATTERN.matcher(text);
        if (dateMatcher.find()) {
            slots.put("beginDate", formatDate(dateMatcher.group("beginYear"), dateMatcher.group("beginMonth"), dateMatcher.group("beginDay")));
            String endYear = dateMatcher.group("endYear");
            if (!StringUtils.hasText(endYear)) {
                endYear = dateMatcher.group("beginYear");
            }
            slots.put("endDate", formatDate(endYear, dateMatcher.group("endMonth"), dateMatcher.group("endDay")));
        }
        Matcher keywordMatcher = EVENT_KEYWORD_PATTERN.matcher(text);
        if (keywordMatcher.find()) {
            slots.put("eventKeyword", keywordMatcher.group("keyword"));
        }
    }

    private void fillSosDateSlots(Map<String, Object> slots, Matcher matcher) {
        slots.put("beginDate", formatDate(matcher.group("beginYear"), matcher.group("beginMonth"), matcher.group("beginDay")));
        String endYear = matcher.group("endYear");
        if (!StringUtils.hasText(endYear)) {
            endYear = matcher.group("beginYear");
        }
        slots.put("endDate", formatDate(endYear, matcher.group("endMonth"), matcher.group("endDay")));
    }

    private void refreshDerivedSlots(TaskContext context) {
        Map<String, Object> slots = context.getSlots();
        if (slots.containsKey("city")) {
            slots.put("featureOfInterest", mapFeatureOfInterest(String.valueOf(slots.get("city"))));
        }
        if (slots.containsKey("beginDate")) {
            slots.put("beginTime", slots.get("beginDate") + "T00:00:00Z");
        }
        if (slots.containsKey("endDate")) {
            slots.put("endTime", slots.get("endDate") + "T23:59:59Z");
        }
        if (!slots.containsKey("shouldPublishWfs")) {
            slots.put("shouldPublishWfs", Boolean.FALSE);
        }
    }

    private String normalizeCity(String city) {
        if (!StringUtils.hasText(city)) {
            return city;
        }
        return city.endsWith("市") ? city : city + "市";
    }

    private String mapFeatureOfInterest(String city) {
        if ("武汉市".equals(city)) {
            return WUHAN_FEATURE;
        }
        return null;
    }

    private String formatDate(String year, String month, String day) {
        LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return date.toString();
    }

    private static Map<String, TaskSlotDefinition> buildSlotDefinitions() {
        Map<String, TaskSlotDefinition> map = new HashMap<>();
        map.put(TaskType.SOS_TO_WFS.getCode(), new TaskSlotDefinition(TaskType.SOS_TO_WFS.getCode())
                .required("city", "beginDate", "endDate", "observedProperty")
                .optional("shouldPublishWfs", "featureOfInterest"));
        map.put(TaskType.WFS_SPATIAL_ANALYSIS.getCode(), new TaskSlotDefinition(TaskType.WFS_SPATIAL_ANALYSIS.getCode())
                .required("wfsLayer", "analysisType")
                .optional("outputLayerName"));
        map.put(TaskType.WEIBO_EVENT_ANALYSIS.getCode(), new TaskSlotDefinition(TaskType.WEIBO_EVENT_ANALYSIS.getCode())
                .required("city", "eventKeyword", "beginDate", "endDate")
                .optional("analysisDepth"));
        return map;
    }
}
