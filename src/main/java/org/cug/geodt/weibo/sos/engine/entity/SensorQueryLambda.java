package org.cug.geodt.weibo.sos.engine.entity;

import net.hydromatic.linq4j.function.Function1;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine.entity
 * @Description
 * @date 2023/7/26 10:57
 */
public class SensorQueryLambda {
    private List<String> sensorIds;
    private String targetInfo;
    private Function1 function;

    public SensorQueryLambda(List<String> sensorIds, String targetInfo, Function1 function) {
        this.sensorIds = sensorIds;
        this.targetInfo = targetInfo;
        this.function = function;
    }

    public SensorQueryLambda() {
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }

    public String getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(String targetInfo) {
        this.targetInfo = targetInfo;
    }

    public Function1 getFunction() {
        return function;
    }

    public void setFunction(Function1 function) {
        this.function = function;
    }
}
