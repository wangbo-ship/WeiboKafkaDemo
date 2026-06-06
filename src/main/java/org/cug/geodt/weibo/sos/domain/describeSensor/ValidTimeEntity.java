package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/7 21:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidTimeEntity {
    private TimePeriodEntity timePeriodEntity;
    private TimeInstantEntity timeInstantEntity;
}
