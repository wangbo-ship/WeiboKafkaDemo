package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/8 9:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fields {
    private String name;
    private List<EnvelopeEntity> envelopeEntityList;
    private List<BooleanEntity> booleanEntityList;
    private List<TextEntity> textEntitiesList;


}
