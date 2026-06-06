package org.cug.geodt.weibo.sos.domain.observation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/5 17:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureOfInterestEntity {
    private String href;
    private GmlIdentifierEntity gmlIdentifierEntity;
    private BoundedByEntity boundedByEntity;
}
