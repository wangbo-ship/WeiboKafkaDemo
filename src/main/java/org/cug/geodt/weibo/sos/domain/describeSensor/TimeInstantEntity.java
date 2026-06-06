package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/9 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeInstantEntity {
    private String gmlId;
    private String timePosition;
}
