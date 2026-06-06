package org.cug.geodt.weibo.agent.interaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionRequest;
import org.cug.geodt.weibo.agent.interaction.model.AgentInteractionResponse;
import org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
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

    @PostMapping("/chat")
    @ApiOperation("多轮对话：参数抽取、补参、规划与确认执行")
    public AgentInteractionResponse chat(@RequestBody AgentInteractionRequest request) throws Exception {
        return agentInteractionService.chat(request);
    }

    @GetMapping("/context/{conversationId}")
    @ApiOperation("查询会话任务上下文")
    public TaskContext getContext(@PathVariable String conversationId) {
        return agentInteractionService.getContext(conversationId);
    }

    @GetMapping("/provenance/{conversationId}")
    @ApiOperation("查询完整数据溯源链")
    public ProvenanceChain getProvenance(@PathVariable String conversationId) {
        return agentInteractionService.getProvenance(conversationId);
    }
}
