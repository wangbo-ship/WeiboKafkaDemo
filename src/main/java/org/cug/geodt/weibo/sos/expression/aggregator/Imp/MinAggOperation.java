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
 * @date 2023/1/1 9:47
 */
public class MinAggOperation implements AggOp {
    private Map<String,Object> parameters;

    public MinAggOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Object eval(Object obj) {
        String targetFieldName = (String) parameters.get("targetFieldName");
        Class clazz = (Class) parameters.get("class");
        Enumerable data = (Enumerable) obj;
        Object min = data.min((Function1) o -> {
            PropertyAccessor propertyAccessor = new PropertyAccessor(clazz,targetFieldName);
            Object value = propertyAccessor.apply(o);
            return value;
        });
        return min;
    }

    @Override
    public String getLabel() {
        return (String) parameters.get("targetFieldName");
    }

    @Override
    public String getOp() {
        return "min";
    }
}
