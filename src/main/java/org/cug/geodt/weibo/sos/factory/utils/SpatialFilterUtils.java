package org.cug.geodt.weibo.sos.factory.utils;

import org.cug.geodt.weibo.sos.factory.entity.SpatialFilter;
import org.geotools.filter.LiteralExpressionImpl;
import org.geotools.filter.spatial.*;
import org.locationtech.jts.geom.*;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.spatial.SpatialOperator;

/**
 * @author ChengFl
 * @version 1.0
 * @description: SpatialFilterUtils 解析类
 * @date 2023/6/19 15:40
 */

public class SpatialFilterUtils {
    public static SpatialFilter<?> apply(SpatialOperator spatialOperator) {
        String spatialRelation = null;
        Expression expression1 = null;
        LiteralExpressionImpl expression2 = null;

        if (spatialOperator instanceof WithinImpl) {
            spatialRelation = "Within";
            WithinImpl withinImpl = (WithinImpl) spatialOperator;
            expression1 = withinImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) withinImpl.getExpression2();
        } else if (spatialOperator instanceof OverlapsImpl) {
            spatialRelation = "Overlaps";
            OverlapsImpl overlapsImpl = (OverlapsImpl) spatialOperator;
            expression1 = overlapsImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) overlapsImpl.getExpression2();
        } else if (spatialOperator instanceof CrossesImpl) {
            spatialRelation = "Crosses";
            CrossesImpl crossesImpl = (CrossesImpl) spatialOperator;
            expression1 = crossesImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) crossesImpl.getExpression2();
        } else if (spatialOperator instanceof DisjointImpl) {
            spatialRelation = "Disjoint";
            DisjointImpl disjointImpl = (DisjointImpl) spatialOperator;
            expression1 = disjointImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) disjointImpl.getExpression2();
        } else if (spatialOperator instanceof EqualsImpl) {
            spatialRelation = "Equals";
            EqualsImpl equalsImpl = (EqualsImpl) spatialOperator;
            expression1 = equalsImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) equalsImpl.getExpression2();
        } else if (spatialOperator instanceof TouchesImpl) {
            spatialRelation = "Touches";
            TouchesImpl touchesImpl = (TouchesImpl) spatialOperator;
            expression1 = touchesImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) touchesImpl.getExpression2();
        } else if (spatialOperator instanceof IntersectsImpl) {
            spatialRelation = "Intersects";
            IntersectsImpl intersectsImpl = (IntersectsImpl) spatialOperator;
            expression1 = intersectsImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) intersectsImpl.getExpression2();
        } else if (spatialOperator instanceof ContainsImpl) {
            spatialRelation = "Contains";
            ContainsImpl containsImpl = (ContainsImpl) spatialOperator;
            expression1 = containsImpl.getExpression1();
            expression2 = (LiteralExpressionImpl) containsImpl.getExpression2();
        }
        assert expression1 != null;
        assert expression2 != null;
        Object geometry = expression2.getValue();
        if (geometry instanceof Polygon) {
            SpatialFilter<Polygon> spatialFilter = new SpatialFilter<>();
            Polygon polygon = (Polygon) geometry;
            spatialFilter.setGeometry(polygon);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof Envelope) {
            SpatialFilter<Envelope> spatialFilter = new SpatialFilter<>();
            Envelope envelope = (Envelope) geometry;
            spatialFilter.setGeometry(envelope);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof Point) {
            SpatialFilter<Point> spatialFilter = new SpatialFilter<>();
            Point point = (Point) geometry;
            spatialFilter.setGeometry(point);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof LineString) {
            SpatialFilter<LineString> spatialFilter = new SpatialFilter<>();
            LineString lineString = (LineString) geometry;
            spatialFilter.setGeometry(lineString);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof MultiPoint) {
            SpatialFilter<MultiPoint> spatialFilter = new SpatialFilter<>();
            MultiPoint multiPoint = (MultiPoint) geometry;
            spatialFilter.setGeometry(multiPoint);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof MultiLineString) {
            SpatialFilter<MultiLineString> spatialFilter = new SpatialFilter<>();
            MultiLineString multiLineString = (MultiLineString) geometry;
            spatialFilter.setGeometry(multiLineString);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof MultiPolygon) {
            SpatialFilter<MultiPolygon> spatialFilter = new SpatialFilter<>();
            MultiPolygon multiPolygon = (MultiPolygon) geometry;
            spatialFilter.setGeometry(multiPolygon);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else if (geometry instanceof Geometry) {
            SpatialFilter<Geometry> spatialFilter = new SpatialFilter<>();
            Geometry geometry1 = (Geometry) geometry;
            spatialFilter.setGeometry(geometry1);
            spatialFilter.setSpatialOps(spatialRelation);
            spatialFilter.setValueReference(expression1.toString());
            return spatialFilter;
        } else {
            return new SpatialFilter<>();
        }
    }
}
