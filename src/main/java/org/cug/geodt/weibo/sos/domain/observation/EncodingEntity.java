package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author WJW
 * Date 2023/6/5 20:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncodingEntity {
    private String tokenSeparator;
    private String decimalSeparator;
    private String blockSeparator;
}
