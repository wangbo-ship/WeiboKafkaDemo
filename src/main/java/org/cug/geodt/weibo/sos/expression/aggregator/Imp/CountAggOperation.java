package org.cug.geodt.weibo.sos.expression.aggregator.Imp;

import net.hydromatic.linq4j.Enumerable;
import org.cug.geodt.weibo.sos.expression.aggregator.AggOp;

import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.expression.aggregator.Imp
 * @Description
 * @date 2023/2/9 11:02
 */
public class CountAggOperation implements AggOp {
    private Map<String,Object> parameters;

    public CountAggOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }


    @Override
    public Object eval(Object obj) {
        Enumerable data = (Enumerable) obj;
        return data.count();
    }

    @Override
    public String getLabel() {
        return (String) parameters.get("targetFieldName");
    }

    @Override
    public String getOp() {
        return "count";
    }

    public static void main(String[] args) {

    }
}
