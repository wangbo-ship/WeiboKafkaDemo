package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.Header;

import java.util.HashMap;

/**
 * @author ChengFl
 * @version 1.0
 * @description: 解析 Header
 * @date 2023/6/19 15:11
 */

public class HeaderUtils {
    public static Header apply(HashMap<String,?> map){
        Header header = new Header();

        String replyTo = (String) map.get("ReplyTo");
        String to = (String) map.get("To");
        String messageID = (String) map.get("MessageID");
        String action = (String) map.get("Action");

        header.setReplyTo(replyTo);
        header.setTo(to);
        header.setMessageID(messageID);
        header.setAction(action);
        return header;
    }
}
