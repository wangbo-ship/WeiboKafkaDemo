package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/14 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterCapabilitiesEntity {
    private TemporalCapabilitiesEntity temporalCapabilities;
    private SpatialCapabilitiesEntity spatialCapabilitiesEntity;
    private ScalarCapabilitiesEntity scalarCapabilitiesEntity;
    private IdCapabilitiesEntity idCapabilitiesEntity;
    private FunctionsEntity functionsEntity;
    private ConformanceEntity conformanceEntity;
}
