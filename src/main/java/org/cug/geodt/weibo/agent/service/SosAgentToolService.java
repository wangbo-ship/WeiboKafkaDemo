package org.cug.geodt.weibo.agent.service;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.cug.geodt.weibo.sos.service.GetObservationService;
import org.cug.geodt.weibo.sos.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class SosAgentToolService {

    private static final String QUERY_SOS_TOOL = "query_sos";
    private static final String PUBLISH_WFS_TOOL = "publish_wfs";

    @Autowired
    private GetObservationService getObservationService;

    @Autowired
    private PublishService publishService;

    public ToolResponseEnvelope querySos(String sosRequestXml) {
        try {
            log.info("REAL TOOL EXECUTED: query_sos");
            log.info("querySos received param type={}, length={}",
                    sosRequestXml == null ? "null" : sosRequestXml.getClass().getName(),
                    sosRequestXml == null ? 0 : sosRequestXml.length());

            if (!StringUtils.hasText(sosRequestXml)) {
                throw new IllegalArgumentException("sosRequestXml must not be empty.");
            }

            log.debug("querySos request preview={}", preview(sosRequestXml, 1000));

            // 直接把完整 SOAP Envelope 传给下游
            String sosResponseXml = getObservationService.getObservationService(sosRequestXml);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("endpoint", "http://localhost:54323/sos/getObservation");
            data.put("sosRequestXml", sosRequestXml);
            data.put("sosResponseXml", sosResponseXml);

            Map<String, Object> meta = new LinkedHashMap<>();
            meta.put("httpMethod", "POST");
            meta.put("contentType", "application/xml");
            meta.put("requestId", UUID.randomUUID().toString());

            return ToolResponseEnvelope.ok(
                    QUERY_SOS_TOOL,
                    "SOS query completed.",
                    data,
                    meta
            );
        } catch (Exception e) {
            log.error("querySos failed", e);
            return ToolResponseEnvelope.fail(
                    QUERY_SOS_TOOL,
                    "SOS query failed.",
                    "QUERY_SOS_ERROR",
                    e
            );
        }
    }

    public ToolResponseEnvelope publishWfs(String sosResponseXml) {
        try {
            log.info("REAL TOOL EXECUTED: publish_wfs");
            log.info("publishWfs received param type={}, length={}",
                    sosResponseXml == null ? "null" : sosResponseXml.getClass().getName(),
                    sosResponseXml == null ? 0 : sosResponseXml.length());

            if (!StringUtils.hasText(sosResponseXml)) {
                throw new IllegalArgumentException("sosResponseXml must not be empty.");
            }

            log.debug("publishWfs response preview={}", preview(sosResponseXml, 1000));

            String wfsUrl = publishService.publishWFS(sosResponseXml);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("endpoint", "http://localhost:54323/publish/observation/WFS");
            data.put("sosResponseXml", sosResponseXml);
            data.put("wfsUrl", wfsUrl);

            Map<String, Object> meta = new LinkedHashMap<>();
            meta.put("httpMethod", "POST");
            meta.put("contentType", "application/xml");
            meta.put("requestId", UUID.randomUUID().toString());

            return ToolResponseEnvelope.ok(
                    PUBLISH_WFS_TOOL,
                    "WFS publish completed.",
                    data,
                    meta
            );
        } catch (Exception e) {
            log.error("publishWfs failed", e);
            return ToolResponseEnvelope.fail(
                    PUBLISH_WFS_TOOL,
                    "WFS publish failed.",
                    "PUBLISH_WFS_ERROR",
                    e
            );
        }
    }

    private String preview(String text, int maxLen) {
        if (text == null) {
            return null;
        }
        String normalized = text.replaceAll("\\s+", " ").trim();
        if (normalized.length() <= maxLen) {
            return normalized;
        }
        return normalized.substring(0, maxLen) + "...";
    }
}