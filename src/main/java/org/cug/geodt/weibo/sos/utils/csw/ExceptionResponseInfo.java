package org.cug.geodt.weibo.sos.utils.csw;

/**
 * @Classname ExceptionResponseInfo
 * @Description 错误请求信息
 * @Date 2023/8/25 15:00
 * @Created by mjh
 */
public class ExceptionResponseInfo implements ResponseInfo{
    private String exceptionCode;

    private String ExceptionText;

    public ExceptionResponseInfo(String exceptionCode, String exceptionText) {
        this.exceptionCode = exceptionCode;
        ExceptionText = exceptionText;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionText() {
        return ExceptionText;
    }

    public void setExceptionText(String exceptionText) {
        ExceptionText = exceptionText;
    }

    @Override
    public String getResponseType() {
        return "Exception";
    }

    @Override
    public String toString() {
        return "ExceptionResponseInfo{" +
                "exceptionCode='" + exceptionCode + '\'' +
                ", ExceptionText='" + ExceptionText + '\'' +
                '}';
    }
}
