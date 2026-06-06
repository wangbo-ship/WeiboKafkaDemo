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
 * @date 2023/7/1 15:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryInfo {
    private String offering;
    private String procedure;
    private List<String> observedProperty;
    private String featureOfInterest;
}
