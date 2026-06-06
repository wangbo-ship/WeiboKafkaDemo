package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/9 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityEntityV2 {
    private String name;
    private String definition;
    private String label;
    private String value;
    private String description;
    private String code;
}
