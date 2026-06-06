package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.Body;
import org.cug.geodt.weibo.sos.factory.entity.LogicTemporalFilter;
import org.cug.geodt.weibo.sos.factory.entity.SpatialFilter;
import org.cug.geodt.weibo.sos.factory.entity.TemporalFilter;
import org.geotools.filter.LogicFilterImpl;
import org.opengis.filter.spatial.SpatialOperator;
import org.opengis.filter.temporal.BinaryTemporalOperator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ChengFl
 * @version 1.0
 * @description: 解析 Body
 * @date 2023/6/19 15:31
 */

public class BodyUtils {
    public static Body apply(HashMap<String, ?> map) {
        Body body = new Body();
        try {
            if (map.containsKey("service")) {
                String service = (String) map.get("service");
                body.setService(service);
            }
            if (map.containsKey("offering")) {
                ArrayList<String> arrayList = new ArrayList<>();
                Object offering1 = map.get("offering");
                if (offering1 instanceof String) {
                    arrayList.add((String) offering1);
                    body.setOffering(arrayList);
                } else {
                    ArrayList<String> offering = (ArrayList<String>) map.get("offering");
                    body.setOffering(offering);
                }
            }
            if (map.containsKey("observedProperty")) {
                Object observedProperty1 = map.get("observedProperty");
                ArrayList<String> arrayList2 = new ArrayList<>();
                if (observedProperty1 instanceof String) {
                    arrayList2.add((String) observedProperty1);
                    body.setObservedProperty(arrayList2);
                } else {
                    ArrayList<String> observedProperty = (ArrayList<String>) map.get("observedProperty");
                    body.setObservedProperty(observedProperty);
                }
            }
            if (map.containsKey("procedure")) {
                ArrayList<String> arrayList3 = new ArrayList<>();
                Object procedure1 = map.get("procedure");
                if (procedure1 instanceof String) {
                    arrayList3.add((String) procedure1);
                    body.setProcedure(arrayList3);
                } else {
                    ArrayList<String> procedure = (ArrayList<String>) map.get("procedure");
                    body.setProcedure(procedure);
                }
            }
            if (map.containsKey("version")) {
                String version = (String) map.get("version");
                body.setVersion(version);
            }
            if (map.containsKey("spatialFilter")) {
                SpatialOperator spatialFilter = (SpatialOperator) map.get("spatialFilter");
                SpatialFilter<?> spatialFilterApply = SpatialFilterUtils.apply(spatialFilter);
                body.setSpatialFilter(spatialFilterApply);
            }
            if (map.containsKey("temporalFilter")) {
                Object temporalFilter = map.get("temporalFilter");
                if (temporalFilter instanceof LogicFilterImpl) {
                    LogicFilterImpl logicFilter = (LogicFilterImpl) temporalFilter;
                    LogicTemporalFilter logicTemporalFilter = LogicFilterUtils.apply(logicFilter);
                    body.setTemporalFilter(logicTemporalFilter);
                } else {
                    BinaryTemporalOperator temporalFilter1 = (BinaryTemporalOperator) map.get("temporalFilter");
                    TemporalFilter<?> temporalFilterApply = TemporalFilterUtils.apply(temporalFilter1);
                    LogicTemporalFilter logicTemporalFilter = new LogicTemporalFilter();
                    ArrayList<TemporalFilter<?>> temporalFilters = new ArrayList<>();
                    temporalFilters.add(temporalFilterApply);
                    logicTemporalFilter.setTemporalFilters(temporalFilters);
                    body.setTemporalFilter(logicTemporalFilter);
                }
            }
            if (map.containsKey("featureOfInterest")) {
                Object featureOfInterest1 = map.get("featureOfInterest");
                ArrayList<String> arrayList4 = new ArrayList<>();
                if (featureOfInterest1 instanceof String) {
                    arrayList4.add((String) featureOfInterest1);
                    body.setFeatureOfInterest(arrayList4);
                } else {
                    ArrayList<String> featureOfInterest2 = (ArrayList<String>) map.get("observedProperty");
                    body.setObservedProperty(featureOfInterest2);
                }
            }
            if (map.containsKey("responseFormat")) {
                body.setResponseFormat(map.get("responseFormat").toString());
            }
        } catch (NullPointerException err) {
            System.err.println(err.getMessage());
        }
        return body;
    }
}
