package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/20 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriod {
    private TimeInstant begin;
    private TimeInstant end;
}
