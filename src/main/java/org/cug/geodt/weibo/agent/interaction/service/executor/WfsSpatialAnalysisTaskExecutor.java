package org.cug.geodt.weibo.agent.interaction.service.executor;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.cug.geodt.weibo.agent.interaction.service.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * WFS 空间分析任务执行器占位实现，待 Skill 层完善后替换。
 */
@Component
public class WfsSpatialAnalysisTaskExecutor implements TaskExecutor {

    private final ProvenanceService provenanceService;

    public WfsSpatialAnalysisTaskExecutor(ProvenanceService provenanceService) {
        this.provenanceService = provenanceService;
    }

    @Override
    public TaskType supportedTaskType() {
        return TaskType.WFS_SPATIAL_ANALYSIS;
    }

    @Override
    public ExecutionResult execute(TaskContext context) {
        provenanceService.recordToolCall(context, "wfs_spatial_analysis_skill", "run_spatial_analysis",
                String.valueOf(context.getSlots()),
                "Skill 层尚未接入，已记录计划参数", false, "NOT_IMPLEMENTED");
        return new ExecutionResult(false,
                "wfs_spatial_analysis_skill 尚未接入，请先完成 Skill 层实现。",
                Collections.emptyMap());
    }
}
