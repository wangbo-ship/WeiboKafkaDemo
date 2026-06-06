package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.*;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;


/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine
 * @Description
 * @date 2023/5/10 14:43
 */
@Mapper
@Repository
public interface CapabilityMapper extends BaseMapper<FeatureOfInterest> {

    List<Procedure> getProcedureById(List<String> procedureId);

    List<FeatureOfInterest> getFeatureOfInterestById(List<String> featureId);

    List<Offering> getOfferingById(List<String> offeringId);

    TemporalFilter getFeatureTemporalFilterRangeFromString();
    TemporalFilter getFeatureTemporalFilterRangeFromFloat();

    SpatialFilter getFeatureSpatialFilterRange();

    List<OfferingProduceSensorInfo> getAllOfferingProduceSensorInfo();

    List<SensorInfoFeature> findSensorByGeometric(@Param("geometry") String geometry, @Param("sensorId") List<String> sensorId);

    TemporalFilter getFeatureTemporalFilterRangeFromFloatBySensorId(List<String> sensorId);

    TemporalFilter getFeatureTemporalFilterRangeFromStringBySensorId(List<String> sensorId);

    List<OfferingProcedureFeature> getCapabilityInfoBySensorIds(List<String> sensorId);

    List<SensorInfo> findSensorInfoByDatasetId(BigInteger datasetId);


    List<String> getSensorIdByOfferingIdentifierAndProcedureIdentifier(
            @Param("offeringIdentifier")String offeringIdentifier,
            @Param("procedureIdentifier")String procedureIdentifier,
            @Param("featureOfInterest")String featureOfInterest);


    List<OfferingProcedureFeatureSensorInfo> getCapabilityInfoBySensorIdsV2(List<String> sensorId);


}
