package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengFl
 * @version 1.0
 * @description: Header
 * @date 2023/6/15 9:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Header {
    private String replyTo;
    private String action;
    private String to;
    private String messageID;
}
