package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/10 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyWords {
    private List<String> keyList;
}
