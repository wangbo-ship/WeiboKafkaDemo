package org.cug.geodt.weibo.sos.service;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Author WJW
 * Date 2023/7/7 21:12
 * @Describe 传感器插入接口
 */
@Component
public interface InsertSpecificSensor {



    //传感器插入接口
    int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws Exception;

    //分发传感器接口
    int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException;
}
