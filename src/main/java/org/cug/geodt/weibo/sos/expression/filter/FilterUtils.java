package org.cug.geodt.weibo.sos.expression.filter;

import net.hydromatic.linq4j.function.Function1;
import org.cug.geodt.weibo.sos.engine.entity.Filter;
import org.cug.geodt.weibo.sos.engine.entity.ReferValue;
import org.cug.geodt.weibo.sos.enums.FilterEnum;
import org.cug.geodt.weibo.sos.expression.filter.Imp.*;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.GeometryNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.LogicAndNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.LogicNotNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.LogicOrNode;
import org.cug.geodt.weibo.sos.expression.utils.ExprWrappedFunction;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.operationB
 * @Description
 * @date 2023/1/6 21:44
 */
public class FilterUtils {
    static Map<String,Class<? extends FilterOperation>> stringToFilterOps;
    static {
        stringToFilterOps = new HashMap<>();
        stringToFilterOps.put(FilterEnum.EQUAL.getValue(), EqualFilterOperation.class);
        stringToFilterOps.put(FilterEnum.GTE.getValue(), GteFilterOperation.class);
        stringToFilterOps.put(FilterEnum.GT.getValue(), GtFilterOperation.class);
        stringToFilterOps.put(FilterEnum.LTE.getValue(), LteFilterOperation.class);
        stringToFilterOps.put(FilterEnum.LT.getValue(), LtFilterOperation.class);
        stringToFilterOps.put(FilterEnum.REGEXLIKE.getValue(), RegexLikeFilterOperation.class);
        stringToFilterOps.put(FilterEnum.IN.getValue(), InFilterOperation.class);
        stringToFilterOps.put(FilterEnum.RANGE.getValue(), RangeFilterOperation.class);
    }
    static public FilterOperation createFilterOperation(String operationName, Map<String,Object> parameters){
        try{
            return stringToFilterOps.get(operationName).getDeclaredConstructor(Map.class).newInstance(parameters);
        }catch (Exception e){
            return null;
        }
    }

    public static ExprNode handleFilter(Filter filter,Class clazz){
        ExprNode root;
        Map<String,Object> parameters;
        if (filter.getReferValues().size()>1 && !filter.getFilterOp().equals("range")){
            List<ReferValue> referValues = new ArrayList<>();
            referValues.add(filter.getReferValues().get(0));
            parameters = putFilterParameters(referValues, filter.getTargetFieldName(), clazz);
            root = createFilterOperation(filter.getFilterOp(),parameters);
            for(int i = 1;i<filter.getReferValues().size();i++){
                referValues = new ArrayList<>();
                referValues.add(filter.getReferValues().get(i));
                parameters = putFilterParameters(referValues, filter.getTargetFieldName(), clazz);
                FilterOperation filterOperation = createFilterOperation(filter.getFilterOp(),parameters);
                root = new LogicOrNode(root,filterOperation);
            }
        }else {
            parameters = putFilterParameters(filter.getReferValues(), filter.getTargetFieldName(), clazz);
            root = createFilterOperation(filter.getFilterOp(),parameters);
        }
        if(filter.getNotLogic().equals(true)){
            root = new LogicNotNode(root);
        }
        return root;
    }

    public static <T> Function1<T,Boolean> generateFilterTree(Class clazz, List<Filter> filters){
        ExprNode root = null;
        if (filters!=null){
            root = handleFilter(filters.get(0),clazz);
            if(filters.size()>1){
                for(int i = 1;i<filters.size();i++){
                    ExprNode rootNode = handleFilter(filters.get(i),clazz);
                    root = new LogicAndNode(root,rootNode);
                }
            }
        }
        Function1<T,Boolean> combineFilter = new ExprWrappedFunction<>(root);
        return combineFilter;
    }


    public static Map<String,Object> putFilterParameters(List<ReferValue> referValues,String targetFieldName,Class clazz){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("referValues", referValues);
        parameters.put("targetFieldName", targetFieldName);
        parameters.put("class", clazz);
        return parameters;
    }

    public static GeometryNode handleGeometryString(ReferValue referValue) {
        try {
            String value = referValue.getValue();
            String[] split = value.split(";");
            String srid = split[0].split(":")[1];
            String wkt = split[1];
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
            // 将WKT字符串解析为Geometry对象
            WKTReader wktReader = new WKTReader(geometryFactory);
            Geometry geometry = wktReader.read(wkt);
            geometry.setSRID(Integer.parseInt(srid));
            return new GeometryNode(geometry);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
