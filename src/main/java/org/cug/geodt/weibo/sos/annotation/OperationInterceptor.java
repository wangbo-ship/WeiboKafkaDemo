package org.cug.geodt.weibo.sos.annotation;

import java.lang.annotation.*;

/**
 * 自定义 操作日志记录 注解
 * 被这个注解注释的方法 都将被OperationAspect 切面
 * @FileName OperationInterceptor
 * @Author WJW
 * @Date 2023/10/10 10:58
 * @Description
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationInterceptor {
}
