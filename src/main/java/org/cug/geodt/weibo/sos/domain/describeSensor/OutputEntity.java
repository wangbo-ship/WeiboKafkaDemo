package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputEntity {
    private List<IOEntity> iOEntityList;
}
