package org.cug.geodt.weibo.sos.exception;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.exception
 * @Description
 * @date 2022/12/24 15:57
 */
public class CosyException extends RuntimeException{
    /**错误编号*/
    private int code;
    /**错误信息*/
    private String errorMessage;

    public CosyException(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
