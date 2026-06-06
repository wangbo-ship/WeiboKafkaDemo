package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/5 19:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldEntity {
    private String name;
    private String Time;
    private QuantityEntity quantity;
}
