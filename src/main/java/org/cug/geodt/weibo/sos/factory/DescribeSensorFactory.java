package org.cug.geodt.weibo.sos.factory;

import org.cug.geodt.weibo.sos.factory.entity.DescribeSensor;
import org.cug.geodt.weibo.sos.factory.utils.DescribeSensorUtils;
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
 * @description: TODO
 * @date 2023/6/20 9:16
 */

public class DescribeSensorFactory {
    public static DescribeSensor parse(Reader reader) throws IOException, ParserConfigurationException, SAXException {
        FESConfiguration configuration = new FESConfiguration();
        Parser parser = new Parser(configuration);
        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(reader);
        return DescribeSensorUtils.apply(map);
    }

    public static DescribeSensor parse(String str) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        FESConfiguration configuration = new FESConfiguration();
        Parser parser = new Parser(configuration);
        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(inputStream);
        return DescribeSensorUtils.apply(map);
    }
}
