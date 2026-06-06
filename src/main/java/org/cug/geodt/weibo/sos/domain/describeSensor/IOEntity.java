package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;

/**
 * Author WJW
 * Date 2023/6/12 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IOEntity {
    private String name;
    private ObservablePropertyEntity observablePropertyEntity;
    private DataInterfaceEntity dataInterfaceEntity;
    private QuantityEntity quantityEntity;
    private CountEntity countEntity;
}
