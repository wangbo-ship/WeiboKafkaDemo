package org.cug.geodt.weibo.sos.expression.aggregator.Imp;

import net.hydromatic.linq4j.Enumerable;
import net.hydromatic.linq4j.function.FloatFunction1;
import org.cug.geodt.weibo.sos.expression.aggregator.AggOp;
import org.cug.geodt.weibo.sos.utils.PropertyAccessor;

import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.aggregator
 * @Description
 * @date 2023/1/3 17:14
 */
public class AverageOperation implements AggOp {
    private Map<String,Object> parameters;

    public AverageOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Object eval(Object obj) {
        String targetFieldName = (String) parameters.get("targetFieldName");
        Enumerable data =(Enumerable) obj;
        Class clazz = (Class) parameters.get("class");
        Object average =  data.average((FloatFunction1) o -> {
                PropertyAccessor propertyAccessor = new PropertyAccessor(clazz, targetFieldName);
                Object value = propertyAccessor.apply(o);
                return Float.parseFloat(value.toString());
            });
        return average;
    }

    @Override
    public String getLabel() {
        return (String) parameters.get("targetFieldName");
    }

    @Override
    public String getOp() {
        return "average";
    }

}
