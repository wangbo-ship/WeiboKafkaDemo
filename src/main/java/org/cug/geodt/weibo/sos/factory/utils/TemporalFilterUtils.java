package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.TemporalFilter;
import org.cug.geodt.weibo.sos.factory.entity.TimeInstant;
import org.cug.geodt.weibo.sos.factory.entity.TimePeriod;
import org.geotools.filter.AttributeExpressionImpl;
import org.geotools.filter.LiteralExpressionImpl;
import org.geotools.filter.temporal.*;
import org.geotools.temporal.object.DefaultInstant;
import org.geotools.temporal.object.DefaultPeriod;
import org.opengis.filter.temporal.BinaryTemporalOperator;
import org.opengis.temporal.Instant;
import org.opengis.temporal.Position;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/20 10:32
 */

public class TemporalFilterUtils {
    public static TemporalFilter<?> apply(BinaryTemporalOperator temporalOperator) {
        String temporalRelation = getTemporalRelation(temporalOperator.getClass());

        AttributeExpressionImpl expression1 = (AttributeExpressionImpl) temporalOperator.getExpression1();
        LiteralExpressionImpl expression2 = (LiteralExpressionImpl) temporalOperator.getExpression2();
        Object timeValue = expression2.getValue();
        if (timeValue instanceof DefaultInstant) {
            TemporalFilter<TimeInstant> temporalFilter = new TemporalFilter<>();
            temporalFilter.setValueReference(expression1.toString());
            temporalFilter.setTemporalOps(temporalRelation);
            Position position = ((DefaultInstant) timeValue).getPosition();
            Date time = position.getDate();
            temporalFilter.setTemporal(new TimeInstant(time));
            return temporalFilter;
        } else if (timeValue instanceof DefaultPeriod) {
            DefaultPeriod defaultPeriod = (DefaultPeriod) timeValue;
            Instant beginning = defaultPeriod.getBeginning();
            Date beginTime = beginning.getPosition().getDate();
            Instant ending = defaultPeriod.getEnding();
            Date endTime = ending.getPosition().getDate();
            TemporalFilter<TimePeriod> temporalFilter = new TemporalFilter<>();
            temporalFilter.setValueReference(expression1.toString());
            temporalFilter.setTemporalOps(temporalRelation);
            TimePeriod timePeriod = new TimePeriod(new TimeInstant(beginTime), new TimeInstant(endTime));
            temporalFilter.setTemporal(timePeriod);
            return temporalFilter;
        } else {
            return new TemporalFilter<>();
        }
    }

    public static String getTemporalRelation(Class<?> cl) {
        Map<Class<?>, String> temporalOperatorMap = new HashMap<>();
        temporalOperatorMap.put(TEqualsImpl.class, "TEquals");
        temporalOperatorMap.put(AfterImpl.class, "After");
        temporalOperatorMap.put(BeforeImpl.class, "Before");
        temporalOperatorMap.put(BeginsImpl.class, "Begins");
        temporalOperatorMap.put(BegunByImpl.class, "BegunBy");
        temporalOperatorMap.put(DuringImpl.class, "During");
        temporalOperatorMap.put(EndsImpl.class, "Ends");
        temporalOperatorMap.put(EndedByImpl.class, "EndedBy");
        temporalOperatorMap.put(TOverlapsImpl.class, "TOverlaps");
        return temporalOperatorMap.get(cl);
    }
}
