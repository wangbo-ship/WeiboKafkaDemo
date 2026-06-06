package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/10 20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacteristicsEntity {
    private String name;
    List<CharacteristicEntity>  characteristicEntityList;
}
