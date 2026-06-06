package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/9 9:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermEntity {
    private String name;
    private String definition;
    private String value;
    private String label;
}
