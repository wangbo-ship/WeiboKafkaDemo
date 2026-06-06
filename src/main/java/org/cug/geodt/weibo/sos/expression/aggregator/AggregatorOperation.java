package org.cug.geodt.weibo.sos.expression.aggregator;

import net.hydromatic.linq4j.Enumerable;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.aggregator
 * @Description
 * @date 2022/12/31 14:32
 */
public interface AggregatorOperation{
    Enumerable aggregate();
}
