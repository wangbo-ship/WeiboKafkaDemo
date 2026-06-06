package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/8 9:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooleanEntity {
    private String name;
    private String definition;
    private String label;
    private String description;
    private Boolean value;
}
