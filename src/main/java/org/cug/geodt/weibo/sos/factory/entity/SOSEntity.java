package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/15 10:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SOSEntity {
    private Header header;
    private Body body;
}
