package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/7 20:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterEntity {
    private String name;
    private AllowedValuesEntity allowedValuesEntity;

}
