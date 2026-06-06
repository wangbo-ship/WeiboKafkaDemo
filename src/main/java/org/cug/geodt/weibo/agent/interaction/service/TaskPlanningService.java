package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.ExecutionPlan;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 根据任务类型与已收集参数生成执行计划。
 */
@Service
public class TaskPlanningService {

    public ExecutionPlan buildPlan(TaskContext context) {
        ExecutionPlan plan = new ExecutionPlan();
        TaskType taskType = context.getTaskType();
        Map<String, Object> slots = context.getSlots();
        boolean executable = context.getMissingInputs().isEmpty();

        switch (taskType) {
            case SOS_TO_WFS:
                buildSosToWfsPlan(plan, slots, executable);
                break;
            case WFS_SPATIAL_ANALYSIS:
                buildSpatialAnalysisPlan(plan, slots, executable);
                break;
            case WEIBO_EVENT_ANALYSIS:
                buildWeiboEventPlan(plan, slots, executable);
                break;
            default:
                plan.getSummaryPoints().add("尚未识别到明确的业务任务类型");
                plan.setExecutable(false);
                return plan;
        }

        plan.setExecutable(executable);
        return plan;
    }

    private void buildSosToWfsPlan(ExecutionPlan plan, Map<String, Object> slots, boolean executable) {
        plan.getSkills().add("sos_to_wfs_skill");
        plan.getTools().add("query_sos");
        plan.getSteps().add("生成 SOS GetObservation 请求");
        plan.getSteps().add("调用 query_sos 查询观测结果");

        plan.getSummaryPoints().add("任务：SOS 查询并可选发布 WFS");
        appendSlotSummary(plan, slots, "city", "城市");
        appendSlotSummary(plan, slots, "beginDate", "开始日期");
        appendSlotSummary(plan, slots, "endDate", "结束日期");
        appendSlotSummary(plan, slots, "observedProperty", "观测属性");

        if (Boolean.TRUE.equals(slots.get("shouldPublishWfs"))) {
            plan.getTools().add("publish_wfs");
            plan.getSteps().add("调用 publish_wfs 发布 WFS 图层");
        }
        if (!executable) {
            plan.getSummaryPoints().add("参数尚未齐全，需继续补充");
        }
    }

    private void buildSpatialAnalysisPlan(ExecutionPlan plan, Map<String, Object> slots, boolean executable) {
        plan.getSkills().add("wfs_spatial_analysis_skill");
        plan.getTools().add("get_wfs_features");
        plan.getTools().add("run_spatial_analysis");
        plan.getSteps().add("读取 WFS 图层要素");
        plan.getSteps().add("执行空间分析");
        plan.getSteps().add("发布分析结果图层");

        plan.getSummaryPoints().add("任务：WFS 图层空间分析");
        appendSlotSummary(plan, slots, "wfsLayer", "WFS 图层");
        appendSlotSummary(plan, slots, "analysisType", "分析类型");
        if (!executable) {
            plan.getSummaryPoints().add("参数尚未齐全，需继续补充");
        }
    }

    private void buildWeiboEventPlan(ExecutionPlan plan, Map<String, Object> slots, boolean executable) {
        plan.getSkills().add("weibo_event_analysis_skill");
        plan.getTools().add("crawl_weibo");
        plan.getTools().add("query_geomesa");
        plan.getSteps().add("抓取微博事件相关内容");
        plan.getSteps().add("查询 GeoMesa 时空记录");
        plan.getSteps().add("汇总事件分析结果");

        plan.getSummaryPoints().add("任务：微博事件分析");
        appendSlotSummary(plan, slots, "city", "城市");
        appendSlotSummary(plan, slots, "eventKeyword", "事件关键词");
        appendSlotSummary(plan, slots, "beginDate", "开始日期");
        appendSlotSummary(plan, slots, "endDate", "结束日期");
        if (!executable) {
            plan.getSummaryPoints().add("参数尚未齐全，需继续补充");
        }
    }

    private void appendSlotSummary(ExecutionPlan plan, Map<String, Object> slots, String key, String label) {
        Object value = slots.get(key);
        if (value != null) {
            plan.getSummaryPoints().add(label + "：" + value);
        }
    }
}
