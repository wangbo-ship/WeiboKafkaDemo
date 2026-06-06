package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/14 11:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporalCapabilitiesEntity {
    private String name;
    private List<TemporalOperatorEntity> temporalOperatorEntity;
}
