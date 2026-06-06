package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;

/**
 * Author WJW
 * Date 2023/6/12 9:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapabilityEntity {
    private String name;
    private TextEntity textEntity;
    private QuantityEntity quantityEntity;  //用于解析
}
