package org.cug.geodt.weibo.sos.utils;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.target.EmptyTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Author WJW
 * Date 2023/7/11 16:21
 */
@Component
public class BeanUtils implements ApplicationContextAware {
    protected static ApplicationContext applicationContext ;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (applicationContext == null) {
            applicationContext = arg0;
        }

    }
    public static Object getBean(String name) {
        //name表示其他要注入的注解name名
        return applicationContext.getBean(name);
    }

    /**
     * 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static void setFieldsToNull(Object obj) throws IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            // 设置字段为可访问
            field.setAccessible(true);

            // 如果字段不是静态字段，则设置为 null
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.set(obj, null);
            }
        }
    }

    public static void setProxyToNull(Object proxy) {
        if (AopUtils.isCglibProxy(proxy)) {
            Field hField = null;
            try {
                hField = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_1");
                hField.setAccessible(true);
                Object callback = hField.get(proxy);
                if (callback instanceof MethodInterceptor) {
                    MethodInterceptor methodInterceptor = (MethodInterceptor) callback;
                    Field advisedField = methodInterceptor.getClass().getDeclaredField("advised");
                    advisedField.setAccessible(true);
                    ProxyFactory advised = (ProxyFactory) advisedField.get(methodInterceptor);
                    advised.setTargetSource(EmptyTargetSource.INSTANCE);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setCallbackToNull(Object proxy) {
        try {
            Field callbackField = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_1");
            callbackField.setAccessible(true);
            callbackField.set(proxy, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

