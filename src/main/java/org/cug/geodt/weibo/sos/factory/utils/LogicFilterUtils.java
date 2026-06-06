package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.LogicTemporalFilter;
import org.cug.geodt.weibo.sos.factory.entity.TemporalFilter;
import org.geotools.filter.AndImpl;
import org.geotools.filter.LogicFilterImpl;
import org.geotools.filter.NotImpl;
import org.geotools.filter.OrImpl;
import org.opengis.filter.Filter;
import org.opengis.filter.temporal.BinaryTemporalOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/7/13 20:45
 */

public class LogicFilterUtils {
    public static LogicTemporalFilter apply(LogicFilterImpl logicFilter) {
        LogicTemporalFilter logicTemporalFilter = new LogicTemporalFilter();
        String logicOps = getLogicOps(logicFilter.getClass());

        logicTemporalFilter.setLogicOps(logicOps);
        List<Filter> children = logicFilter.getChildren();

        ArrayList<TemporalFilter<?>> temporalFilters = new ArrayList<>();
        ArrayList<LogicTemporalFilter> logicTemporalFilters = new ArrayList<>();
        for (Filter filter : children) {
            if (filter instanceof BinaryTemporalOperator) {
                BinaryTemporalOperator binaryTemporalOperator = (BinaryTemporalOperator) filter;
                TemporalFilter<?> temporalFilter = TemporalFilterUtils.apply(binaryTemporalOperator);
                temporalFilters.add(temporalFilter);
                System.out.println(temporalFilter);
            } else if (filter instanceof LogicFilterImpl) {
                LogicFilterImpl logicFilter1 = (LogicFilterImpl) filter;
                LogicTemporalFilter logicFilterApply = LogicFilterUtils.apply(logicFilter1);
                logicTemporalFilters.add(logicFilterApply);
            }
        }
        logicTemporalFilter.setTemporalFilters(temporalFilters);
        logicTemporalFilter.setLogicTemporalFilter(logicTemporalFilters);
        return logicTemporalFilter;
    }

    public static String getLogicOps(Class<?> cl) {
        Map<Class<?>, String> logicOpsMap = new HashMap<>();
        logicOpsMap.put(AndImpl.class, "And");
        logicOpsMap.put(OrImpl.class, "Or");
        logicOpsMap.put(NotImpl.class, "Not");
        return logicOpsMap.get(cl);
    }
}
