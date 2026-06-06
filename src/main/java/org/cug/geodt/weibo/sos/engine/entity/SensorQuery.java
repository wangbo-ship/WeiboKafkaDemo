package org.cug.geodt.weibo.sos.engine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine.entity
 * @Description
 * @date 2023/6/26 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorQuery {
    private List<String> sensorIds;
    private Query query;
    private Integer startTime;
    private Integer endTime;
}
