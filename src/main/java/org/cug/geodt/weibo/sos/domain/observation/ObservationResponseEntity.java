package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/5 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObservationResponseEntity {
    private List<ObservationDataEntity> observationDataEntityList;
}
