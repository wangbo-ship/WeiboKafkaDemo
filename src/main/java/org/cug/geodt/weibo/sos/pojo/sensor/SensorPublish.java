package org.cug.geodt.weibo.sos.pojo.sensor;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @FileName SensorPublish
 * @Author WJW
 * @Date 2023/8/25 15:41
 * @Description
 */
@TableName("sensor_publish")
public class SensorPublish {

    @TableId("sensor_id")
    private String sensorId;
    private Long publishedTime;
        private String publishedUser;
    private String description;

    public SensorPublish() {
    }

    public SensorPublish(String sensorId, Long publishedTime, String publishedUser, String description) {
        this.sensorId = sensorId;
        this.publishedTime = publishedTime;
        this.publishedUser = publishedUser;
        this.description = description;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Long publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getPublishedUser() {
        return publishedUser;
    }

    public void setPublishedUser(String publishedUser) {
        this.publishedUser = publishedUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
