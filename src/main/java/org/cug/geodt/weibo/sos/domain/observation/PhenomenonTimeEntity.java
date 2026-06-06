package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimePeriodEntity;

/**
 * Author WJW
 * Date 2023/6/5 16:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhenomenonTimeEntity {
    String gmlId; //自动生成
    String timePosition; //例如2004-01-01T03:00:00.000+01:00 ，表示在 2004 年 1 月 1 日的 3 点整 (时区为 UTC+1)。其中 T 是将日期和时间分隔符号，表示后面的字符串是时间。小数点及后面的数字 .000+01:00 表示相对于协调世界时 (UTC) 偏移了一个小时，即东一区 (UTC+1)，
    TimePeriodEntity timePeriodEntity;
}
