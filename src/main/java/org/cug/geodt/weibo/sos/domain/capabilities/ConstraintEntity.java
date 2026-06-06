package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/7 20:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintEntity {
    private String name;
    private AllowedValuesEntity allowedValuesEntity;
}
