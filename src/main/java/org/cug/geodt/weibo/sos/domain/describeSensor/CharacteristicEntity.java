package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/10 20:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacteristicEntity {
    private String name;
    private TextEntity textEntity;
}
