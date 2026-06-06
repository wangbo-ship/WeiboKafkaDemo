package org.cug.geodt.weibo.sos.exception;

import org.cug.geodt.weibo.sos.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public ExceptionResult doSystemException(SystemException ex) {
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员
        return new ExceptionResult(null, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doSystemException(BusinessException ex) {
        return new Result(ex.getCode(), ex.getMessage());
//        return new ExceptionResult(null, ex.getCode(), ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public Result doException(Exception ex){
//        System.out.println("异常");
//        return new Result(null, Code.SYSTEM_UNKNOW_ERR, "系统繁忙，请稍后再试");
//    }
}
