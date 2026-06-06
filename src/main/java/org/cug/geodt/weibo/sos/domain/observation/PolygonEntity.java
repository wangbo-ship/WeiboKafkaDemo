package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author wjw
 * @version 1.0
 * @description: Polygon
 * @date 2023/4/27 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolygonEntity {
    private String id;
    private String srsName;
    private ExteriorEntity exterior;
}

