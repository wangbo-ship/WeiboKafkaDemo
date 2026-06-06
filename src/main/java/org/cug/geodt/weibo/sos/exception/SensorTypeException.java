package org.cug.geodt.weibo.sos.exception;

/**
 * Author WJW
 * Date 2023/7/7 21:01
 */
public class SensorTypeException extends RuntimeException{

    private Integer code;

    private String errorMessage;

    public SensorTypeException(Integer code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
