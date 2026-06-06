package org.cug.geodt.weibo.sos.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.cug.geodt.weibo.sos.exception.BusinessException;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;

import java.util.*;

public class LocationHandle<T> {
    //获取sensor_id的metrics字段中location
    public static Set<String> locationHandle(List<SensorInfo> metrics) {
        Set<String> tbl_locations = new HashSet<>();
        for (SensorInfo i : metrics) {
            JSONArray jsonArray = JSON.parseArray(i.getMetrics());
            if (jsonArray==null) continue;
            for (Object j : jsonArray) {
                JSONObject jsonObject = (JSONObject) j;
                tbl_locations.add(jsonObject.getString("location"));
            }
        }
        return tbl_locations;
    }

    //获取sensor_id的metrics字段中location
    public static List<String> metricNameHandle(List<SensorInfo> metrics) {
        List<String> metricsList = new ArrayList<>();
        for (SensorInfo i : metrics) {
            if(i == null){
                throw new BusinessException("The current sensor measurement item does not exist", 500);
            }else {
                JSONArray jsonArray = JSON.parseArray(i.getMetrics());
                for (Object j : jsonArray) {
                    JSONObject jsonObject = (JSONObject) j;
                    metricsList.add(jsonObject.getString("metricName"));
                }
            }
        }
        //排序加去重
        Set<String> metric = new HashSet<>(metricsList);
        List<String> handeMetric = new ArrayList<>(metric);
        Collections.sort(handeMetric);
        return handeMetric;
    }

    public static List<String> metricNameHandleOne(OfferingProcedureFeatureSensorInfo sensorInfo) {
        List<String> metricsList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(sensorInfo.getMetrics());
        for (Object j : jsonArray) {
            JSONObject jsonObject = (JSONObject) j;
            metricsList.add(jsonObject.getString("metricName"));
        }
        //排序加去重
        Set<String> metric = new HashSet<>(metricsList);
        List<String> handeMetric = new ArrayList<>(metric);
        Collections.sort(handeMetric);
        return handeMetric;
    }


    public static List<String> metricNameHandleOne(SensorInfo metrics) {
        List<String> metricsList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(metrics.getMetrics());
        for (Object j : jsonArray) {
            JSONObject jsonObject = (JSONObject) j;
            metricsList.add(jsonObject.getString("metricName"));
        }
        //排序加去重
        Set<String> metric = new HashSet<>(metricsList);
        List<String> handeMetric = new ArrayList<>(metric);
        Collections.sort(handeMetric);
        return handeMetric;
    }

    public static List<String> metricNameHandleOne(String metrics) {
        List<String> metricsList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(metrics);
        for (Object j : jsonArray) {
            JSONObject jsonObject = (JSONObject) j;
            metricsList.add(jsonObject.getString("metricName"));
        }
        //排序加去重
        Set<String> metric = new HashSet<>(metricsList);
        List<String> handeMetric = new ArrayList<>(metric);
        Collections.sort(handeMetric);
        return handeMetric;
    }


    public static ArrayList<String> metricNameHandleArrayList(List<SensorInfo> metrics) {
        ArrayList<String> metricsList = new ArrayList<>();
        for (SensorInfo i : metrics) {
            JSONArray jsonArray = JSON.parseArray(i.getMetrics());
            for (Object j : jsonArray) {
                JSONObject jsonObject = (JSONObject) j;
                metricsList.add(jsonObject.getString("metricName"));
            }
        }
        //排序加去重
        Set<String> metric = new HashSet<>(metricsList);
        ArrayList<String> handeMetric = new ArrayList<>(metric);
        Collections.sort(handeMetric);
        return handeMetric;
    }



    public static List<String> metricNameHandleOneNew(SensorInfo sensorInfo) {
        List<String> metricsList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(sensorInfo.getMetrics());
        for (Object j : jsonArray) {
            JSONObject jsonObject = (JSONObject) j;
            metricsList.add(jsonObject.getString("metricName"));
        }
        //排序加去重
        Set<String> metric = new HashSet<>(metricsList);
        List<String> handeMetric = new ArrayList<>(metric);
        Collections.sort(handeMetric);
        return handeMetric;
    }
}
