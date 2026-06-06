package org.cug.geodt.weibo.sos.exception;

/**
 * @description: 服务接口类
 * Author WJW
 * Date 2023/7/12 8:42
 */
public interface BaseErrorInfoInterface {

    /**
     * 错误码
     * @return
     */
    String getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMessage();
}
