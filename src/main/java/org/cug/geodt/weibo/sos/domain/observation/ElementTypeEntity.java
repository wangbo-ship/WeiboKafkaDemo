package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/5 19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElementTypeEntity {
    private String name;
    private String gmlId;
    private String Href;
    private List<FieldEntity> fieldEntityList;

}
