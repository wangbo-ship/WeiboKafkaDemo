package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/5 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GmlIdentifierEntity {
    private String codeSpace; //对应foi中的Identifier
    private String Value; // FOI的id

}
