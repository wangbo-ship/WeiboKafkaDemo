package org.cug.geodt.weibo.geomesa.controller;

import org.cug.geodt.weibo.entity.Poi;
import org.cug.geodt.weibo.geomesa.service.PoiGeomesaService;
import org.cug.geodt.weibo.geomesa.entity.QueryRequest;
import org.cug.geodt.weibo.service.PoiService;
import org.cug.geodt.weibo.util.POIUtil;
import org.locationtech.jts.io.WKTReader;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.cug.geodt.weibo.util.POIUtil.parseDate;

/**
 * @className: POIController
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/8/3 16:12
 * @version: 1.0
 */
@RestController
@RequestMapping("/geomesa/poi")
public class PoiGeomesaController {

    @Autowired
    private PoiGeomesaService poiGeomesaService;

    @Autowired
    private PoiService poiServiceData;

    @PostMapping("/store-one")
    public String storePOIOne(String typeName, Poi poi) {
        try {
            Double lng = poi.getLng();
            Double lat = poi.getLat();
            String endTime = poi.getEndTime();
            String point = POIUtil.convertToWKT(lng, lat);
            Date date = parseDate(endTime);

            poi.setGeom(point);
            poi.setEndTimeDate(date);
            System.out.println(poi);
            poiGeomesaService.storePOI(typeName, poi);
            return "POI stored";
        } catch (Exception e) {
            return "Failed to store POI: " + e.getMessage();
        }
    }

    @PostMapping("/store-all")
    public String storePOI(String typeName) {
        try {
            List<Poi> list = poiServiceData.list();
            System.out.println("poi-data的数量为：" + list.size());
            for (Poi poi : list) {
                Double lng = poi.getLng();
                Double lat = poi.getLat();
                String endTime = poi.getEndTime();
                String point = POIUtil.convertToWKT(lng, lat);
                Date date = parseDate(endTime);

                poi.setGeom(point);
                poi.setEndTimeDate(date);
                poiGeomesaService.storePOI(typeName, poi);
            }
            return "POI stored";
        } catch (Exception e) {
            return "Failed to store POI: " + e.getMessage();
        }
    }

    @GetMapping("/all")
    public List<SimpleFeature> getAllFeatures(String typeName) {
        try {
            List<SimpleFeature> simpleFeatures = poiGeomesaService.getAllFeatures(typeName);
            System.out.println("SimpleFeature数量为：" + simpleFeatures.size());
            List<Poi> pois = convertToPoiList(simpleFeatures);
            System.out.println("poi的数量为:" + pois.size());
            return simpleFeatures;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all features: " + e.getMessage());
        }
    }

    @GetMapping("/query/cql")
    public List<SimpleFeature> queryByCQL(String cql) {
        long startTime = System.currentTimeMillis();
        try {
            List<SimpleFeature> simpleFeatures = poiGeomesaService.queryByCQL(cql);
            System.out.println("SimpleFeature数量为：" + simpleFeatures.size());
            List<Poi> pois = convertToPoiList(simpleFeatures);
            System.out.println("poi的数量为:" + pois.size());
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + " ms");
            return simpleFeatures;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query by CQL: " + e.getMessage());
        }
    }

    @PostMapping("/query/time-range")
    public List<SimpleFeature> queryByTimeRangeAndPolygon(@RequestBody QueryRequest request) throws ParseException {
        System.out.println(request.getPoi().toString());

        // 解析时间字符串
        Date startDate = parseDate(request.getStartTime());
        Date endDate = parseDate(request.getEndTime());

        long time1 = System.currentTimeMillis();
        try {

            Polygon polygon = null;
            if (request.getPolygonWKT() != null) {
                polygon = (Polygon) new WKTReader().read(request.getPolygonWKT());
            }
            List<SimpleFeature> simpleFeatures = poiGeomesaService.queryByTimeRangeAndPolygon(startDate, endDate, request.getPoi(), polygon);
            System.out.println("SimpleFeature数量为：" + simpleFeatures.size());
            List<Poi> pois = convertToPoiList(simpleFeatures);
            System.out.println("poi的数量为:" + pois.size());
            long time2 = System.currentTimeMillis();
            System.out.println("Execution time: " + (time2 - time1) + " ms");
            return simpleFeatures;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query by year and polygon: " + e.getMessage());
        }
    }

    @GetMapping("/query/year")
    public List<SimpleFeature> queryByYearAndPolygon(int year, Poi poi, String polygonWKT) {
        System.out.println(poi.toString());
        long startTime = System.currentTimeMillis();
        try {

            Polygon polygon = null;
            if (polygonWKT != null) {
                polygon = (Polygon) new WKTReader().read(polygonWKT);
            }
            List<SimpleFeature> simpleFeatures = poiGeomesaService.queryByYearAndPolygon(year, poi, polygon);
            System.out.println("SimpleFeature数量为：" + simpleFeatures.size());
            List<Poi> pois = convertToPoiList(simpleFeatures);
            System.out.println("poi的数量为:" + pois.size());
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + " ms");
            return simpleFeatures;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query by year and polygon: " + e.getMessage());
        }
    }


    private List<Poi> convertToPoiList(List<SimpleFeature> features) {
        List<Poi> pois = new ArrayList<>();
        for (SimpleFeature feature : features) {
            Poi poi = new Poi();
            poi.setPoiId((String) feature.getAttribute("poi_id"));
            poi.setCityCode((String) feature.getAttribute("city_code"));
            poi.setPoiName((String) feature.getAttribute("poi_name"));
            poi.setLargeCategory((String) feature.getAttribute("large_category"));
            poi.setMediumCategory((String) feature.getAttribute("medium_category"));
            poi.setLng((Double) feature.getAttribute("lng"));
            poi.setLat((Double) feature.getAttribute("lat"));
            poi.setProvince((String) feature.getAttribute("province"));
            poi.setCity((String) feature.getAttribute("city"));
            poi.setArea((String) feature.getAttribute("area"));
            poi.setWeiboNum(feature.getAttribute("weibo_num").toString());
            poi.setEndTimeDate((Date) feature.getAttribute("end_time"));
            poi.setGeom(feature.getAttribute("geom").toString()); // 确保 geom 格式正确

            pois.add(poi);
        }
        return pois;
    }
}
