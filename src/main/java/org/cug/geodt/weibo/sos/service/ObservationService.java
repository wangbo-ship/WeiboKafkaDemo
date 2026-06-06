package org.cug.geodt.weibo.sos.service;

import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Repository
public interface ObservationService {
    ReturnVO getLatestMetricValueById(List<String> sensorId, Float intervalInMin);

    ReturnVO getLatestMetricValueByIdAndMetricName(List<String> sensorId, Float intervalInMin, String metricName);

    ReturnVO getMetricValueByIdAndTimeRange(List<String> sensorId, Integer startTime, Integer endTime);

    ReturnVO getMetricValueByIdAndTimeRangeAndMetricName(List<String> sensorId, Integer startTime, Integer endTime, String metricName);

    ReturnVO getMetricValueByTypeAndMetricName(String sensorType, List<String> sensorId, Float intervalInMin, String metricName);

    ReturnVO getMetricValueByTypeAndTimeRange(String sensorType, List<String> sensorId,Integer startTime, Integer endTime);

    ReturnVO getMetricValueByTypeAndTimeRangeAndMetricName(String sensorType, List<String> sensorId,Integer startTime, Integer endTime, String metricName);

    ReturnVO getMetricValueByType(String sensorType, List<String> sensorId,Float intervalInMin);

    ReturnVO insertObservation(String observation) throws IOException, ParserConfigurationException, SAXException, XmlException, JAXBException;


    Result getDataEntryByMonth(int num);

    Result getDataEntryByYear(int num);

    Result getDataEntryByDay(int num);

    Result getSpidersData();

    Result getTodayVolume();

    Result getStatisticalDataOnAccessesByDayAndType();

    Result getAllDataEntry();

    Result getTotalVolume();

    Result getTodayEntry();
}
