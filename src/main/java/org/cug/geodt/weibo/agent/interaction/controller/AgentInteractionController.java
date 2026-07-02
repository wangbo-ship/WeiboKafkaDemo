package org.cug.geodt.weibo.agent.interaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionRequest;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionResponse;
import org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.model.TaskTrace;
import org.cug.geodt.weibo.agent.interaction.service.AgentInteractionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent/interaction")
@Api(value = "Agent交互层", tags = "Agent交互/Memory/Provenance")
public class AgentInteractionController {

    private final AgentInteractionService agentInteractionService;

    public AgentInteractionController(AgentInteractionService agentInteractionService) {
        this.agentInteractionService = agentInteractionService;
    }
// 对话入口
    @PostMapping("/chat")
    @ApiOperation("多轮对话：参数抽取、补参、规划与确认执行")
    public AgentInteractionResponse chat(@RequestBody AgentInteractionRequest request) throws Exception {
        return agentInteractionService.chat(request);
    }
// 恢复工作台页面的内容( 页面刷新 → 知道任务做完了没、参数是啥、计划是啥、还能不能接着聊)
    @GetMapping("/context/{conversationId}")
    @ApiOperation("查询会话任务上下文")
    public TaskContext getContext(@PathVariable String conversationId) {
        return agentInteractionService.getContext(conversationId);
    }
// 任务进行到哪了(啥任务、参数齐了没、做到哪、最后出了啥)
    @GetMapping("/trace/{conversationId}")
    @ApiOperation("查询任务追踪视图（状态 + 参数 + 步骤日志 + 产物）")
    public TaskTrace getTaskTrace(@PathVariable String conversationId) {
        return agentInteractionService.getTaskTrace(conversationId);
    }

// 操作流水(后台监控录像，证明每一步怎么跑的 执行失败了 → 看哪一步挂了；审计 → 证明调了哪些 Tool、输入输出是啥)
    @GetMapping("/provenance/{conversationId}")
    @ApiOperation("查询完整数据溯源链")
    public ProvenanceChain getProvenance(@PathVariable String conversationId) {
        return agentInteractionService.getProvenance(conversationId);
    }
}
