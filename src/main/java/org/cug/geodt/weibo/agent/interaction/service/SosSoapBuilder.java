package org.cug.geodt.weibo.agent.interaction.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class SosSoapBuilder {

    private static final String WEIBO_PROCEDURE = "http://www.org.cug.geodt/procedure/weibo_theme";
    private static final String WEIBO_OFFERING = "http://www.org.cug.geodt/offerings/weibo_theme";

    public String buildFromSlots(Map<String, Object> slots) {
        return "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\" " +
                "xmlns:sos=\"http://www.opengis.net/sos/2.0\" " +
                "xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:swe=\"http://www.opengis.net/swe/2.0\" " +
                "xmlns:swes=\"http://www.opengis.net/swes/2.0\" " +
                "xmlns:fes=\"http://www.opengis.net/fes/2.0\" " +
                "xmlns:gml=\"http://www.opengis.net/gml/3.2\" " +
                "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
                "xmlns:om=\"http://www.opengis.net/om/1.0\" " +
                "xsi:schemaLocation=\"http://www.w3.org/2003/05/soap-envelope http://www.w3.org/2003/05/soap-envelope/soap-envelope.xsd " +
                "http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd\">" +
                "<soap12:Header>" +
                "<wsa:To>http://www.ogc.org/SOS</wsa:To>" +
                "<wsa:Action>http://www.opengis.net/def/serviceOperation/sos/core/2.0/GetObservation</wsa:Action>" +
                "<wsa:ReplyTo><wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address></wsa:ReplyTo>" +
                "<wsa:MessageID>urn:uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
                "</soap12:Header>" +
                "<soap12:Body>" +
                "<sos:GetObservation service=\"SOS\" version=\"2.0.0\">" +
                "<sos:procedure>" + WEIBO_PROCEDURE + "</sos:procedure>" +
                "<sos:offering>" + WEIBO_OFFERING + "</sos:offering>" +
                "<sos:featureOfInterest>" + slots.get("featureOfInterest") + "</sos:featureOfInterest>" +
                "<sos:observedProperty>" + slots.get("observedProperty") + "</sos:observedProperty>" +
                "<sos:temporalFilter>" +
                "<fes:And>" +
                "<fes:TOverlaps>" +
                "<fes:ValueReference>SimpleTrajectory/gml:TimePeriod</fes:ValueReference>" +
                "<gml:TimePeriod gml:id=\"TP1\">" +
                "<gml:begin><gml:TimeInstant gml:id=\"TI1\" id=\"1\"><gml:timePosition>" + slots.get("beginTime") + "</gml:timePosition></gml:TimeInstant></gml:begin>" +
                "<gml:end><gml:TimeInstant gml:id=\"TI2\" id=\"2\"><gml:timePosition>" + slots.get("endTime") + "</gml:timePosition></gml:TimeInstant></gml:end>" +
                "</gml:TimePeriod>" +
                "</fes:TOverlaps>" +
                "</fes:And>" +
                "</sos:temporalFilter>" +
                "</sos:GetObservation>" +
                "</soap12:Body>" +
                "</soap12:Envelope>";
    }
}
