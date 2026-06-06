package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneEntity {
    private List<String> voiceList;
    private List<String> facsimile;
}
