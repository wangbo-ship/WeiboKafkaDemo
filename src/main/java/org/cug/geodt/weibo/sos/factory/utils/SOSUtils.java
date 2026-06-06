package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.Body;
import org.cug.geodt.weibo.sos.factory.entity.Header;
import org.cug.geodt.weibo.sos.factory.entity.SOSEntity;

import java.util.HashMap;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/19 16:57
 */

public class SOSUtils {
    public static SOSEntity apply(HashMap<String, ?> map) {
        SOSEntity sosEntity = new SOSEntity();
        try{
            if(map.containsKey("Header")){
                HashMap<String, ?> headerMap = (HashMap<String, ?>) map.get("Header");
                Header headerApply = HeaderUtils.apply(headerMap);
                sosEntity.setHeader(headerApply);
            }
            if(map.containsKey("Body")){
                HashMap<String, ?> bodyMap = (HashMap<String, ?>) map.get("Body");
                Body bodyApply = BodyUtils.apply(bodyMap);
                sosEntity.setBody(bodyApply);
            }
        }catch (NullPointerException err){
            System.err.println(err.getMessage());
        }
        return sosEntity;
    }
}
