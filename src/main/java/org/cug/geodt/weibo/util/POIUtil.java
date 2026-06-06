package org.cug.geodt.weibo.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: POIUtil
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/8/3 15:17
 * @version: 1.0
 */
public class POIUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final WKTWriter wktWriter = new WKTWriter();
    private static final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static String convertToWKT(double lng, double lat) {
        Point point = geometryFactory.createPoint(new Coordinate(lng, lat));
        return wktWriter.write(point);
    }

    public static Date parseDate(String dateString) throws ParseException {
        return inputDateFormat.parse(dateString);
    }

    public static String format(Date date) {
        return dateFormat.format(date);
    }

}
