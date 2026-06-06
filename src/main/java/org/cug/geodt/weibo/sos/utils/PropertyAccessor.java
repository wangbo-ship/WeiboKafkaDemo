package org.cug.geodt.weibo.sos.utils;

import net.hydromatic.linq4j.function.Function1;

import java.lang.reflect.Method;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.utils
 * @Description
 * @date 2022/12/28 20:54
 */
public class PropertyAccessor<T,R> implements Function1<T, R> {
    Class<T> clazz;
    String propertyName;

    public PropertyAccessor(Class<T> clazz, String propertyName) {
        this.clazz = clazz;
        this.propertyName = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }

    @Override
    public R apply(T t) {
        R value;
        try {
            Method method = clazz.getDeclaredMethod("get" + propertyName);
            value = (R) method.invoke(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
