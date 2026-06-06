package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.*;
import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;
import org.cug.geodt.weibo.sos.engine.QueryEngine;
import org.cug.geodt.weibo.sos.engine.entity.Query;
import org.cug.geodt.weibo.sos.engine.utils.EngineUtils;
import org.cug.geodt.weibo.sos.factory.DescribeSensorFactory;
import org.cug.geodt.weibo.sos.factory.SensorObservationServiceFactory;
import org.cug.geodt.weibo.sos.factory.entity.DescribeSensor;
import org.cug.geodt.weibo.sos.mapper.CapabilityMapper;
import org.cug.geodt.weibo.sos.pojo.OfferingProcedureFeature;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.service.GetDescribeSensorService;
import org.cug.geodt.weibo.sos.utils.LocationHandle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.service.Imp
 * @Description
 * @date 2023/6/21 10:12
 */

@Service
public class GetDescribeSensorServiceImpl implements GetDescribeSensorService {

    @Resource
    QueryEngine queryEngine;

    @Resource
    CapabilityMapper capabilityMapper;

    @Resource
    SensorObservationServiceFactory sensorObservationServiceFactory;

    @Override
    public String getGeoDTDescribeSensor(String str) {
        try {
            DescribeSensor describeSensor = DescribeSensorFactory.parse(str);
            Query query = EngineUtils.describeSensorToQuery(describeSensor);
            List<SensorInfo> sensorInfos = queryEngine.selectBySensorIdsAndQueryList(
                    Collections.unmodifiableList(Arrays.asList(describeSensor.getProcedure())),query,null,null);
            DescribeSensorResponseEntity responseEntity = generateDescribeSensorEntity(sensorInfos);
            return sensorObservationServiceFactory.getDescribeSensorDocument("2.0", responseEntity);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private DescribeSensorResponseEntity generateDescribeSensorEntity(List<SensorInfo> sensorInfos) {
        SensorInfo sensorInfo = sensorInfos.get(0);
        OfferingProcedureFeature offeringProcedureFeature = capabilityMapper.getCapabilityInfoBySensorIds(Collections.unmodifiableList(Arrays.asList(sensorInfo.getSensorId()))).get(0);
        DescribeSensorResponseEntity describeSensorResponseEntity = new DescribeSensorResponseEntity();
        ValidTimeEntity validTimeEntity = generateValidTimeEntity(offeringProcedureFeature);
        List<CapabilitiesEntity> capabilitiesEntities = generateCapabilitiesEntity(offeringProcedureFeature);

        describeSensorResponseEntity.setCharacteristicsEntity(generateCharacteristicsEntity(offeringProcedureFeature));
        describeSensorResponseEntity.setCapabilitiesEntity(capabilitiesEntities);
        describeSensorResponseEntity.setValidTimeEntity(Collections.unmodifiableList(Arrays.asList(validTimeEntity)));
        describeSensorResponseEntity.setDescription(sensorInfo.getSensorLongName()+"-"+sensorInfo.getSensorType());
        describeSensorResponseEntity.setIdentifier(sensorInfo.getSensorId());
        describeSensorResponseEntity.setPositionEntityList(generatePositionEntityList(sensorInfo));
        return describeSensorResponseEntity;
    }

    private List<CharacteristicsEntity> generateCharacteristicsEntity(OfferingProcedureFeature offeringProcedureFeature) {
        List<CharacteristicsEntity> characteristicsEntities = new ArrayList<>();
        CharacteristicsEntity characteristics = new CharacteristicsEntity();
        characteristics.setName("LISACaracteristics");
        List<CharacteristicEntity> characteristicEntities = new ArrayList<>();

        CharacteristicEntity characteristicEntity = new CharacteristicEntity();
        characteristicEntity.setName("detectorType");
        TextEntity textEntity = new TextEntity();
        textEntity.setLabel("Detector Type");
        textEntity.setValue("silicon photodiode");
        textEntity.setDefinition("http://www.nexosproject.eu/dictionary/definitions.html#detectorType");
        characteristicEntity.setTextEntity(textEntity);
        characteristicEntities.add(characteristicEntity);

        characteristicEntity = new CharacteristicEntity();
        characteristicEntity.setName("lightSource");
        textEntity = new TextEntity();
        textEntity.setLabel("Detector Type");
        textEntity.setValue("silicon photodiode");
        textEntity.setDefinition("http://www.nexosproject.eu/dictionary/definitions.html#detectorType");
        characteristicEntity.setTextEntity(textEntity);
        characteristicEntities.add(characteristicEntity);

        characteristics.setCharacteristicEntityList(characteristicEntities);
        characteristicsEntities.add(characteristics);
        return characteristicsEntities;
    }

    private List<CapabilitiesEntity> generateCapabilitiesEntity(OfferingProcedureFeature offeringProcedureFeature) {
        List<CapabilitiesEntity> capabilitiesEntities = new ArrayList<>();
        CapabilitiesEntity capabilitiesEntity = new CapabilitiesEntity();
        CapabilityEntity capabilityEntity = new CapabilityEntity();
        capabilityEntity.setName(offeringProcedureFeature.getOfferingName());
        TextEntity textEntity = new TextEntity();
        textEntity.setDefinition("urn:ogc:def:identifier:OGC:offeringID");
        textEntity.setLabel(offeringProcedureFeature.getOfferingName());
        textEntity.setValue(offeringProcedureFeature.getOfferingIdentifier());
        capabilityEntity.setTextEntity(textEntity);
        capabilitiesEntity.setCapabilityEntity(Collections.unmodifiableList(Arrays.asList(capabilityEntity)));
        capabilitiesEntity.setName(offeringProcedureFeature.getOfferingName());
        capabilitiesEntities.add(capabilitiesEntity);
        return capabilitiesEntities;
    }

    private List<PositionEntity> generatePositionEntityList(SensorInfo sensorInfo) {
        List<PositionEntity> positionEntities = new ArrayList<>();
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setDefinition("http://sensorml.com/ont/swe/property/SensorLocation");
        positionEntity.setReferenceFrame("urn:ogc:crs:EPSG:"+sensorInfo.getSrid());
        List<CoordinateEntity> coordinateEntities = new ArrayList<>();
        CoordinateEntity coordinateEntity = new CoordinateEntity();
        //经度
        coordinateEntity.setName("Lat");
        QuantityEntity quantityEntity = new QuantityEntity();
        quantityEntity.setDefinition("http://sensorml.com/ont/swe/property/Latitude");
        quantityEntity.setUom("deg");
        quantityEntity.setValue(Double.valueOf(sensorInfo.getSensorLatitude()));
        coordinateEntity.setQuantityEntity(quantityEntity);
        coordinateEntities.add(coordinateEntity);
        //纬度
        coordinateEntity = new CoordinateEntity();
        coordinateEntity.setName("Lon");
        quantityEntity = new QuantityEntity();
        quantityEntity.setDefinition("http://sensorml.com/ont/swe/property/Longitude");
        quantityEntity.setUom("deg");
        quantityEntity.setValue(Double.valueOf(sensorInfo.getSensorLongitude()));
        coordinateEntity.setQuantityEntity(quantityEntity);
        coordinateEntities.add(coordinateEntity);
        positionEntity.setCoordinateEntity(coordinateEntities);
        positionEntities.add(positionEntity);
        return positionEntities;
    }

    private List<KeyWordEntity> generateDescribeSensorKeyWords(SensorInfo sensorInfo) {
        List<KeyWordEntity> keyWordEntities = new ArrayList<>();
        KeyWordEntity keyWordEntity= new KeyWordEntity();
        List<String> keyWords = LocationHandle.metricNameHandleOne(sensorInfo);
        keyWordEntity.setKeywords(keyWords);
        keyWordEntities.add(keyWordEntity);
        return keyWordEntities;
    }

    private ValidTimeEntity generateValidTimeEntity(OfferingProcedureFeature offeringProcedureFeature) {
            ValidTimeEntity validTimeEntity = new ValidTimeEntity();
            TimePeriodEntity timePeriodEntity = new TimePeriodEntity();
            timePeriodEntity.setId("validityPeriod");
            timePeriodEntity.setBeginTime(offeringProcedureFeature.getOfferingResultTimeStartStr());
            timePeriodEntity.setEndTime(offeringProcedureFeature.getOfferingResultTimeEndStr());
            validTimeEntity.setTimePeriodEntity(timePeriodEntity);
            return validTimeEntity;
    }

}
