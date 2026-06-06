package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/13 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountEntity {
    private String definition;
    private String label;
    private String description;
    private String value;
}
