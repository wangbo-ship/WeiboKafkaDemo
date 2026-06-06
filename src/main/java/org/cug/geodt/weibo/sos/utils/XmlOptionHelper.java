package org.cug.geodt.weibo.sos.utils;

import org.apache.xmlbeans.XmlOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author WJW
 * Date 2023/5/6 9:52
 */
public class XmlOptionHelper {

    private static XmlOptions options;


    private static final XmlOptionHelper hungrySingleton= new XmlOptionHelper();

    private XmlOptionHelper() {
        options = new XmlOptions();
    }



    public static XmlOptions XmlOption() {

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("http://www.opengis.net/sos/1.0","sos");
        stringStringMap.put("http://www.opengis.net/sos/2.0","sos");
        stringStringMap.put("http://www.opengis.net/ows/1.1","ows");
        stringStringMap.put("http://www.w3.org/1999/xlink", "xlink");
        stringStringMap.put("http://www.opengis.net/swes/2.0", "swes");
        stringStringMap.put("http://www.opengis.net/fes/2.0","fes");
        stringStringMap.put("http://www.opengis.net/gml/3.2","gml");
        stringStringMap.put("http://www.opengis.net/swes/2.0","swes");
        stringStringMap.put("http://www.opengis.net/sensorML/1.0.1","sml");
        stringStringMap.put("http://www.opengis.net/swe/2.0","swe");
        stringStringMap.put("http://www.w3.org/1999/xlink","xlink");
        stringStringMap.put("http://www.isotc211.org/2005/gco","gco");
        stringStringMap.put("http://www.isotc211.org/2005/gmd","gmd");
        stringStringMap.put("http://www.opengis.net/sensorml/2.0","sml");
        stringStringMap.put("http://www.opengis.net/om/1.0","om");
        stringStringMap.put("http://www.opengis.net/om/2.0","om");
        stringStringMap.put("http://www.w3.org/1999/xlink","xlink");
        stringStringMap.put("http://www.opengis.net/gml/3.2", "gml");
        stringStringMap.put("http://www.opengis.net/swes/2.0","swes");
        stringStringMap.put("http://www.opengis.net/sensorML/1.0.1","sml");
        stringStringMap.put("http://www.opengis.net/swe/2.0","swe");
        stringStringMap.put("http://www.opengis.net/fes/2.0", "fes");
        stringStringMap.put("http://www.opengis.net/swe/1.0.1", "swe");
        options.setSaveSuggestedPrefixes(stringStringMap);
        return options;


    }

//    public static XmlOptions describeSensorXmlOptions() {
//
//        Map<String, String> stringStringMap = new HashMap<>();
//        stringStringMap.put("http://www.opengis.net/swes/2.0","swes");
//        stringStringMap.put("http://www.opengis.net/sensorML/1.0.1","sml");
//        stringStringMap.put("http://www.opengis.net/swe/1.0.1","swe");
//        stringStringMap.put("http://www.opengis.net/sos/2.0","sos");
//        stringStringMap.put("http://www.opengis.net/fes/2.0", "fes");
//        stringStringMap.put("http://www.opengis.net/gml/3.2", "gml");
//        stringStringMap.put("http://www.opengis.net/swe/2.0","swe");
//        stringStringMap.put("http://www.w3.org/1999/xlink","xlink");
//        stringStringMap.put("http://www.isotc211.org/2005/gco","gco");
//        stringStringMap.put("http://www.isotc211.org/2005/gmd","gmd");
//        stringStringMap.put("http://www.opengis.net/sensorml/2.0","sml");
//        stringStringMap.put("http://www.opengis.net/sensorML/1.0.1","sml");
//        stringStringMap.put("http://www.opengis.net/swe/2.0","swe");
//        stringStringMap.put("http://www.opengis.net/fes/2.0", "fes");
//        stringStringMap.put("http://www.opengis.net/swe/1.0.1", "swe");
//        options.setSaveSuggestedPrefixes(stringStringMap);
//        return options;
//
//    }

//    public static XmlOptions getObservationResponseXmlOptions() {
//
//        Map<String, String> stringStringMap = new HashMap<>();
//        stringStringMap.put("http://www.opengis.net/om/1.0","om");
//        stringStringMap.put("http://www.opengis.net/sos/2.0","sos");
//        stringStringMap.put("http://www.opengis.net/om/2.0","om");
//        stringStringMap.put("http://www.w3.org/1999/xlink","xlink");
//        stringStringMap.put("http://www.opengis.net/gml/3.2", "gml");
//        stringStringMap.put("http://www.opengis.net/swes/2.0","swes");
//        stringStringMap.put("http://www.opengis.net/sensorML/1.0.1","sml");
//        stringStringMap.put("http://www.opengis.net/swe/2.0","swe");
//        stringStringMap.put("http://www.opengis.net/fes/2.0", "fes");
//        stringStringMap.put("http://www.opengis.net/swe/1.0.1", "swe");
//        options.setSaveSuggestedPrefixes(stringStringMap);
//        return options;
//
//    }

    public static <T> T NullPointHandler(String nullType, boolean isList) {
        Map<String, String> nullTypeMap = new HashMap<>();
        nullTypeMap.put("string","无该名称");
        nullTypeMap.put("href","没有对应的链接");
        nullTypeMap.put("num","99999999999999");

        Map<String, ArrayList<String>>  nullListTypeMap = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("没有对应的链接");
        nullListTypeMap.put("href",list);
        if (!isList) {
            return (T) nullTypeMap.get(nullType);
        } else {
            return (T) nullListTypeMap.get(nullType);
        }

    }
}
