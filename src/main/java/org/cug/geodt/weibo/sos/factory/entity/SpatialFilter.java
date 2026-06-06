package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/15 9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpatialFilter<T> {
    private String spatialOps;
    private String valueReference;
    private T geometry;
}
