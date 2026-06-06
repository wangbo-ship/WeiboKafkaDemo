package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.observation.DataArrayEntity;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/12 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataInterfaceEntity {
    private List<FieldsEntity> fieldEntityList;
    private DataArrayEntity dataArrayEntity;
}
