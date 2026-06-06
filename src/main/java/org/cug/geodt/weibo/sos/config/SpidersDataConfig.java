package org.cug.geodt.weibo.sos.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName SpidersDataConfig
 * @Author WJW
 * @Date 2023/9/14 16:34
 * @Description
 */
public class SpidersDataConfig {
    // 静态成员变量
    public static List<String> staticList = new ArrayList<>(
            Arrays.asList("微博","百度地图","中国气象网","推特","中国天气网","全国空气质量网"));

    public static String MicroBlog = "微博";

    public static String BaiduMap = "百度地图";

    public static String ChinaMeteorologicalNet = "中国气象网";

    public static String Twitter = "推特";

    public static String ChinaWeatherNetwork = "中国天气网";

    public static String NationalAirQualityNetwork = "全国空气质量网";

}
