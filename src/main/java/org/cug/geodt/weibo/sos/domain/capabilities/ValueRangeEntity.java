package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/8 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueRangeEntity {
    private String MaxValue;
    private String MinValue;
}
