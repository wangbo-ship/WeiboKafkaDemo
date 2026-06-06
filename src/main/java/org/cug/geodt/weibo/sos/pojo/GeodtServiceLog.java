package org.cug.geodt.weibo.sos.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @FileName Log
 * @Author WJW
 * @Date 2023/10/10 11:17
 * @Description
 */
@TableName("geodt_service_log")
public class GeodtServiceLog {


    private int id;

    private String requestUri;
    private String remoteAddress;
    private String remoteUser;
    private String method;
    private String requestUrl;
    private String remotePort;
    private String protocol;

    private Date createTime;

    public GeodtServiceLog() {
    }


    public GeodtServiceLog(int id, String requestUri, String remoteAddress, String remoteUser, String method, String requestUrl, String remotePort, String protocol, Date createTime) {
        this.id = id;
        this.requestUri = requestUri;
        this.remoteAddress = remoteAddress;
        this.remoteUser = remoteUser;
        this.method = method;
        this.requestUrl = requestUrl;
        this.remotePort = remotePort;
        this.protocol = protocol;
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "GeodtServiceLog{" +
                "id=" + id +
                ", requestUri='" + requestUri + '\'' +
                ", remoteAddress='" + remoteAddress + '\'' +
                ", remoteUser='" + remoteUser + '\'' +
                ", method='" + method + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", remotePort='" + remotePort + '\'' +
                ", protocol='" + protocol + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

