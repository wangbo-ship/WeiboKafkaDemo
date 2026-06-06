package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName RoleEntity
 * @Author WJW
 * @Date 2023/7/21 9:45
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    private String codeList;
    private String codeListValue;
    private String value;
}
