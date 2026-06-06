package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/15 8:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordsEntity {
    private List<String> keyWordLis;
}
