package org.cug.geodt.weibo.sos.pojo.sensor.sos;


import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;

/**
 * @FileName SensorPublishedResponse
 * @Author WJW
 * @Date 2023/8/25 15:00
 * @Description
 */


public class SensorPublishedResponse extends SensorInfo {

    private String isPublished;



    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }
}
