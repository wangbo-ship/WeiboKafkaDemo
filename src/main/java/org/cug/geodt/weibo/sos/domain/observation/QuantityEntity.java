package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/5 20:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityEntity {
    private String label;
    private String definition;
    private String description;
    private String uom; //单位
    private Double value;
    private String axisId;
}
