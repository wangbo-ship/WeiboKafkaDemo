package org.cug.geodt.weibo.sos.exception;

public class ExceptionResult {
    private Boolean success = true;
    private String message;
    private Integer code;
    private Object result;
    private long timestamp = System.currentTimeMillis();

//    public ExceptionResult() {
//    }
//
//    public ExceptionResult(Object result, Integer code) {
//        this.result = result;
//        this.code = code;
//    }

    public ExceptionResult(Object result, Integer code, String message) {
        this.success = false;
        this.message = message;
        this.code = code;
        this.result = result;
//        this.timestamp = System.currentTimeMillis();
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + result +
                ", code=" + code +
                ", msg='" + message + '\'' +
                '}';
    }
}
