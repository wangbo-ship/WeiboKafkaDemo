package org.cug.geodt.weibo.sos.utils.parser;

import net.opengis.sos.x20.GetObservationDocument;
import net.opengis.sos.x20.GetObservationType;
import org.apache.xmlbeans.XmlException;
import org.w3.x2003.x05.soapEnvelope.Body;
import org.w3.x2003.x05.soapEnvelope.EnvelopeDocument;
import org.w3c.dom.Node;

/**
 * Author WJW
 * Date 2023/6/4 13:26
 */
public class EnvelopeParser {

    public GetObservationType apply(String xml) throws XmlException {
        EnvelopeDocument envelopeDocument = EnvelopeDocument.Factory.parse(xml);
        Body body = envelopeDocument.getEnvelope().getBody();
        Node domNode = body.getDomNode();
        String localName = domNode.getChildNodes().item(1).getLocalName();
        Node item = domNode.getChildNodes().item(1);
        GetObservationDocument parse = GetObservationDocument.Factory.parse(item);
        GetObservationType getObservation = parse.getGetObservation();
        return getObservation;
    }

}
