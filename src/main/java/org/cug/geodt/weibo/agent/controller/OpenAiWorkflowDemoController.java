package org.cug.geodt.weibo.agent.controller;

import org.cug.geodt.weibo.agent.model.AgentDemoModels;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.cug.geodt.weibo.agent.service.OpenAiResponsesService;
import org.cug.geodt.weibo.agent.service.SosAgentToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent-demo")
public class OpenAiWorkflowDemoController {

    @Autowired
    private SosAgentToolService toolService;

    @Autowired
    private OpenAiResponsesService openAiResponsesService;

    @PostMapping("/tools/query-sos")
    public ToolResponseEnvelope querySos(@RequestBody AgentDemoModels.QuerySosRequest request) {
        return toolService.querySos(request.getSosRequestXml());
    }

    @PostMapping("/tools/publish-wfs")
    public ToolResponseEnvelope publishWfs(@RequestBody AgentDemoModels.PublishWfsRequest request) {
        return toolService.publishWfs(request.getSosResponseXml());
    }

    @PostMapping("/responses")
    public AgentDemoModels.AgentChatResponse runResponsesDemo(@RequestBody AgentDemoModels.AgentChatRequest request) throws Exception {
        System.out.println("responses request body = " + request);
        return openAiResponsesService.runConversation(request);
    }
}
