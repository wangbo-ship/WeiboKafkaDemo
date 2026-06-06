package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.DescribeSensor;

import java.util.HashMap;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/21 9:14
 */

public class DescribeSensorUtils {
    public static DescribeSensor apply(HashMap<String, ?> map){
        DescribeSensor describeSensor = new DescribeSensor();
        try{
            if(map.containsKey("service")){
                describeSensor.setService(map.get("service").toString());
            }
            if(map.containsKey("version")){
                describeSensor.setVersion(map.get("version").toString());
            }
            if(map.containsKey("procedure")){
                describeSensor.setProcedure(map.get("procedure").toString());
            }
            if(map.containsKey("procedureDescriptionFormat")){
                describeSensor.setProcedureDescriptionFormat(map.get("procedureDescriptionFormat").toString());
            }
        }catch (NullPointerException err){
            System.err.println(err.getMessage());
        }
        return describeSensor;
    }
}
