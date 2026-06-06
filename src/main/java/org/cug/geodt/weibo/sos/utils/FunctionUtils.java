package org.cug.geodt.weibo.sos.utils;

import net.hydromatic.linq4j.function.Function1;
import net.hydromatic.linq4j.function.Predicate1;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.utils
 * @Description
 * @date 2022/12/29 23:23
 */
public class FunctionUtils {
    public static <T> Predicate1<T> toPredicate(final Function1<T, Boolean> function) {
        return v1 -> (Boolean)function.apply(v1);
    }
    public static <T> Function1<T,Boolean> andFilters(Function1<T,Boolean> function1, Function1<T,Boolean> function2){
        return t -> function1.apply(t)&&function2.apply(t);
    }

    public static <T> Function1<T,Boolean> orFilters(Function1<T,Boolean> function1, Function1<T,Boolean> function2){
        return t -> function1.apply(t)||function2.apply(t);
    }

    //非



}
