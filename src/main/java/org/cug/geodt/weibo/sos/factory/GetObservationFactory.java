package org.cug.geodt.weibo.sos.factory;

import org.cug.geodt.weibo.sos.factory.entity.SOSEntity;
import org.cug.geodt.weibo.sos.factory.utils.SOSUtils;
import org.geotools.filter.v2_0.FESConfiguration;
import org.geotools.xsd.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author ChengFl
 * @version 1.0
 * @description: SOS——解析 Observation
 * @date 2023/6/19 17:05
 */

public class GetObservationFactory {
    public static SOSEntity parse(Reader reader) throws IOException, ParserConfigurationException, SAXException {
        FESConfiguration configuration = new FESConfiguration();
        Parser parser = new Parser(configuration);
        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(reader);
        return SOSUtils.apply(map);
    }
    public static SOSEntity parse(String str) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        FESConfiguration configuration = new FESConfiguration();
        Parser parser = new Parser(configuration);
        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(inputStream);
        return SOSUtils.apply(map);
    }
}
