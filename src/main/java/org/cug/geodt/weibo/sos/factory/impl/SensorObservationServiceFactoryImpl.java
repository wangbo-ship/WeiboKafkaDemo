package org.cug.geodt.weibo.sos.factory.impl;

import org.cug.geodt.weibo.sos.codec.encode.*;
import org.cug.geodt.weibo.sos.domain.capabilities.CapabilitiesResponseEntity;
import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.domain.observation.ObservationResponseEntity;
import org.cug.geodt.weibo.sos.factory.SensorObservationServiceFactory;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;
import org.springframework.stereotype.Service;

/**
 * Author WJW
 * Date 2023/6/15 19:54
 */
@Service
public class SensorObservationServiceFactoryImpl implements SensorObservationServiceFactory {

    @Override
    public String getCapabilitiesDocument(String version, CapabilitiesResponseEntity capabilitiesResponseEntity) {
        if(version .equals("1.0") ){
            return GetCapabilitiesConverterV1.convert(capabilitiesResponseEntity).xmlText(XmlOptionHelper.XmlOption());
        }else if(version.equals("2.0")){
            return GetCapabilitiesConverterV2.convert(capabilitiesResponseEntity).xmlText(XmlOptionHelper.XmlOption());
        }else {
            return "没有匹配的请求版本";
        }
    }

    @Override
    public String getDescribeSensorDocument(String version, DescribeSensorResponseEntity describeSensorResponseEntity) {
        if(version.equals( "1.0")){
            return DescribeSensorConverterV1.convert(describeSensorResponseEntity).xmlText(XmlOptionHelper.XmlOption());
        }else if(version.equals("2.0")){
            return DescribeSensorConverterV2.convert(describeSensorResponseEntity).xmlText(XmlOptionHelper.XmlOption());
        }else {
            return "没有匹配的请求版本";
        }
    }

    @Override
    public String getObservationDocument(String version, ObservationResponseEntity observationResponseEntity) {
        if(version.equals("1.0") ){
            return GetObservationConverterV1.convert(observationResponseEntity).xmlText(XmlOptionHelper.XmlOption());
        }else if(version .equals("2.0") ){
            return GetObservationConverterV2.convert(observationResponseEntity).xmlText(XmlOptionHelper.XmlOption());
        }else {
            return "没有匹配的请求版本";
        }
    }
}
