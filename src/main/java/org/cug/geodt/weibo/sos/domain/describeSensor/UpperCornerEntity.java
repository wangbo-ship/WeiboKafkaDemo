package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/8 9:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpperCornerEntity {
    private List<Double> listValue;
    private List<CoordinateEntity> coordinateEntityList;
}
