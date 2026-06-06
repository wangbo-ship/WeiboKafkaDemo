package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/15 10:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Body {
    private String service;
    private ArrayList<String> offering;
    private ArrayList<String> observedProperty;
    private ArrayList<String> procedure;
    private String version;
    private ArrayList<String> featureOfInterest;
    private String responseFormat;
    private SpatialFilter<?> spatialFilter;
    private LogicTemporalFilter temporalFilter;
}
