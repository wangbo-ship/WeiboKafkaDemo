package org.cug.geodt.weibo.sos.common.constants;

/**
 * @FileName ActuatorAddress
 * @Author WJW
 * @Date 2023/10/6 10:31
 * @Description 转发运维监控的接口信息
 */
public class ActuatorWebSite {
    public static final String cpuUsage = "http://localhost:8666/actuator/metrics/system.cpu.usage";
    public static final String memoryUsage = "http://localhost:8666/actuator/metrics/jvm.memory.used";
    public static final String diskUsage = "http://localhost:8666/actuator/health";
}
