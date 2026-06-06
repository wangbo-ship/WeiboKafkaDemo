package org.cug.geodt.weibo.sos.service.Imp;

import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.WeatherRadarWeatherRadarSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.WeatherRadarWeatherRadarSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/7 21:22
 */
//@Component
//@Service
public class InsertWeatherRadarWeatherRadarSensor extends InsertSensorInfo implements InsertSpecificSensor {

    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertWeatherRadarWeatherRadarSensor.parameter = parameter;
    }

//    private WeatherRadarWeatherRadarSensor  weatherRadarWeatherRadarSensor = BeanUtils.getBean(WeatherRadarWeatherRadarSensor.class);

    private WeatherRadarWeatherRadarSensorMapper  weatherRadarWeatherRadarSensorMapper = BeanUtils.getBean(WeatherRadarWeatherRadarSensorMapper.class);

    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);
    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws XmlException, IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        int i = super.insertSensorInfo(describeSensorResponseEntity);
        int i1 = parseSpecificAttribute(describeSensorResponseEntity);
        return i & i1;
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException {
        WeatherRadarWeatherRadarSensor  weatherRadarWeatherRadarSensor = new WeatherRadarWeatherRadarSensor();
        if (describeSensorResponseEntity.getCapabilitiesEntity().size() > 0
                && describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                != null) {
            describeSensorResponseEntity
                    .getCapabilitiesEntity()
                    .get(0)
                    .getCapabilityEntity()
                    .forEach(
                            (capabilityEntity) -> {
                                if (capabilityEntity.getName() != null
                                        && capabilityEntity.getQuantityEntity() != null
                                        && capabilityEntity.getQuantityEntity().getValue()
                                        != null) {
                                    if (capabilityEntity.getName().equals(parameter[0]) && capabilityEntity.getQuantityEntity().getDefinition() != null) {
                                        weatherRadarWeatherRadarSensor.setPolarizationDescription(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getDefinition());
                                    } else if (capabilityEntity.getName().equals(parameter[1]) && capabilityEntity.getQuantityEntity().getDefinition() != null) {
                                        weatherRadarWeatherRadarSensor.setMaxSensingDistance(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getDefinition()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        weatherRadarWeatherRadarSensor.setTimeResolution(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        weatherRadarWeatherRadarSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = weatherRadarWeatherRadarSensorMapper.selectAllSensorId();
        if (strings.contains(weatherRadarWeatherRadarSensor.getSensorId())) {
            int update = weatherRadarWeatherRadarSensorMapper.updateById(weatherRadarWeatherRadarSensor);
            return update;
        } else {
            int insert =  weatherRadarWeatherRadarSensorMapper.insertWeatherRadarWeatherRadarSensor(
                    weatherRadarWeatherRadarSensor);
            return insert;
        }

    }
}
