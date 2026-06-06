package org.cug.geodt.weibo.sos.factory;

import org.cug.geodt.weibo.sos.domain.capabilities.CapabilitiesResponseEntity;
import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.domain.observation.ObservationResponseEntity;

/**
 * Author WJW
 * Date 2023/6/15 19:55
 */

public interface SensorObservationServiceFactory<T> {

    String getCapabilitiesDocument(String version, CapabilitiesResponseEntity capabilitiesResponseEntity) throws NullPointerException;

    String getDescribeSensorDocument(String version, DescribeSensorResponseEntity describeSensorResponseEntity) throws NullPointerException;

    String getObservationDocument(String version, ObservationResponseEntity observationResponseEntity) throws NullPointerException;


}
