package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/9 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IOEntityV1 {
    private List<TextEntity> textEntities;
    private List<QuantityEntityV2> quantityEntities;
    private List<BooleanEntity> booleanEntities;
}
