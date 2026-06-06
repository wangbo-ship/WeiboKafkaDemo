package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;

/**
 * Author WJW
 * Date 2023/6/8 9:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateEntity {
    private QuantityEntity quantityEntity;
    private String name;
    private String axisID;
    private String code;
    private Double value;
}
