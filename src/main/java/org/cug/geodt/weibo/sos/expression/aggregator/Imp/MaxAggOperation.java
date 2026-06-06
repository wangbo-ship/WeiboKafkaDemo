package org.cug.geodt.weibo.sos.expression.aggregator.Imp;

import net.hydromatic.linq4j.Enumerable;
import net.hydromatic.linq4j.function.Function1;
import org.cug.geodt.weibo.sos.expression.aggregator.AggOp;
import org.cug.geodt.weibo.sos.utils.PropertyAccessor;

import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.aggregator
 * @Description
 * @date 2022/12/31 16:04
 */
public class MaxAggOperation implements AggOp {
    private Map<String,Object> parameters;

    public MaxAggOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Object eval(Object obj) {
        String targetFieldName = (String) parameters.get("targetFieldName");
        Class clazz = (Class) parameters.get("class");
        Enumerable data = (Enumerable) obj;
        Object max = data.max((Function1) o -> {
            PropertyAccessor propertyAccessor = new PropertyAccessor(clazz,targetFieldName);
            Object value = propertyAccessor.apply(o);
            return value;
        });
        return max;
    }

    @Override
    public String getLabel() {
        return (String) parameters.get("targetFieldName");
    }

    @Override
    public String getOp() {
        return "max";
    }
}
