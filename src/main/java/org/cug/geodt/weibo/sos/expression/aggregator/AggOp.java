package org.cug.geodt.weibo.sos.expression.aggregator;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.aggregator
 * @Description
 * @date 2023/1/3 23:00
 */
public interface AggOp {
    Object eval(Object obj);

    String getLabel();

    String getOp();
}
