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
public class CapabilitiesEntity {
    private String name;
    private String definition; //1.0
    private Fields fields;  //1.0
    private List<CapabilityEntity> capabilityEntity;
}
