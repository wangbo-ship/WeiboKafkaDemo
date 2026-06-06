package org.cug.geodt.weibo.geomesa.service;

import org.cug.geodt.weibo.entity.Poi;
import org.cug.geodt.weibo.util.POIUtil;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @className: POIQueryService
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/8/3 15:39
 * @version: 1.0
 */
@Service
public class PoiGeomesaService {

    @Autowired
    private DataStore dataStore;

    private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

    public void storePOI(String typeName, Poi poi) throws IOException {
        SimpleFeatureType sft = dataStore.getSchema(typeName);
        if (sft == null) {
            throw new IOException("Schema 'poi' does not exist.");
        }
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(sft);
        SimpleFeature feature = poi.toSimpleFeature(featureBuilder);

        SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(typeName);
        try {
            featureStore.addFeatures(DataUtilities.collection(feature));
        } catch (Exception e) {
            throw new IOException("Failed to store POI", e);
        }
    }

    public List<SimpleFeature> getAllFeatures(String typeName) throws IOException {
        SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
        Query query = new Query(typeName, Filter.INCLUDE); // 使用 INCLUDE 查询所有数据
        return getFeatures(query, featureSource);
    }

    public List<SimpleFeature> queryByCQL(String cql) throws IOException, CQLException {
        // 创建CQL过滤器
        Filter filter = CQL.toFilter(cql);

        // 打印过滤条件
        System.out.println("CQL Filter: " + filter);

        // 获取名为 "poi" 的特征源
        SimpleFeatureSource featureSource = dataStore.getFeatureSource("poi");
        // 创建查询，目标是 "poi" 特征类型，并使用指定的过滤器
        Query query = new Query("poi", filter);

        return getFeatures(query, featureSource);
    }

    public List<SimpleFeature> queryByTimeRangeAndPolygon(Date startTime, Date endTime, Poi poi, Polygon polygon) throws IOException, CQLException {
        List<Filter> filters = new ArrayList<>();

        // 将 Date 对象转换为 CQL 过滤器可识别的字符串格式
        String formattedStartTime = POIUtil.format(startTime);
        String formattedEndTime = POIUtil.format(endTime);

        // 添加时间段过滤条件
        String cqlFilter = "end_time BETWEEN '" + formattedStartTime + "' AND '" + formattedEndTime + "'";
        Filter timeFilter = CQL.toFilter(cqlFilter);
        filters.add(timeFilter);

        // 动态添加属性过滤条件
        if (poi.getPoiId() != null && !poi.getPoiId().isEmpty()) {
            filters.add(ff.equals(ff.property("poi_id"), ff.literal(poi.getPoiId())));
        }
        if (poi.getCityCode() != null && !poi.getCityCode().isEmpty()) {
            filters.add(ff.equals(ff.property("city_code"), ff.literal(poi.getCityCode())));
        }
        if (poi.getPoiName() != null && !poi.getPoiName().isEmpty()) {
            filters.add(ff.equals(ff.property("poi_name"), ff.literal(poi.getPoiName())));
        }
        if (poi.getLargeCategory() != null && !poi.getLargeCategory().isEmpty()) {
            filters.add(ff.equals(ff.property("large_category"), ff.literal(poi.getLargeCategory())));
        }
        if (poi.getMediumCategory() != null && !poi.getMediumCategory().isEmpty()) {
            filters.add(ff.equals(ff.property("medium_category"), ff.literal(poi.getMediumCategory())));
        }
        if (poi.getProvince() != null && !poi.getProvince().isEmpty()) {
            filters.add(ff.equals(ff.property("province"), ff.literal(poi.getProvince())));
        }
        if (poi.getCity() != null && !poi.getCity().isEmpty()) {
            filters.add(ff.equals(ff.property("city"), ff.literal(poi.getCity())));
        }
        if (poi.getArea() != null && !poi.getArea().isEmpty()) {
            filters.add(ff.equals(ff.property("area"), ff.literal(poi.getArea())));
        }

        // 添加空间过滤条件
        if (polygon != null) {
            Filter spatialFilter = ff.within(ff.property("geom"), ff.literal(polygon));
            filters.add(spatialFilter);
        }

        // 组合所有过滤条件
        Filter combinedFilter;
        if (filters.isEmpty()) {
            combinedFilter = Filter.INCLUDE; // 查询所有数据
        } else {
            combinedFilter = ff.and(filters);
        }
        System.out.println("所有过滤条件" + combinedFilter.toString());

        SimpleFeatureSource featureSource = dataStore.getFeatureSource("poi");
        Query query = new Query("poi", combinedFilter);
        return getFeatures(query, featureSource);
    }

    public List<SimpleFeature> queryByYearAndPolygon(int year, Poi poi, Polygon polygon) throws IOException, CQLException {
        List<Filter> filters = new ArrayList<>();

        // 添加时间过滤条件
        // 添加时间过滤条件
        String cqlFilter = "year(end_time) = " + year;
        Filter timeFilter = CQL.toFilter(cqlFilter);
        filters.add(timeFilter);

        // 动态添加属性过滤条件
        if (poi.getPoiId() != null && !poi.getPoiId().isEmpty()) {
            filters.add(ff.equals(ff.property("poi_id"), ff.literal(poi.getPoiId())));
        }
        if (poi.getCityCode() != null && !poi.getCityCode().isEmpty()) {
            filters.add(ff.equals(ff.property("city_code"), ff.literal(poi.getCityCode())));
        }
        if (poi.getPoiName() != null && !poi.getPoiName().isEmpty()) {
            filters.add(ff.equals(ff.property("poi_name"), ff.literal(poi.getPoiName())));
        }
        if (poi.getLargeCategory() != null && !poi.getLargeCategory().isEmpty()) {
            filters.add(ff.equals(ff.property("large_category"), ff.literal(poi.getLargeCategory())));
        }
        if (poi.getMediumCategory() != null && !poi.getMediumCategory().isEmpty()) {
            filters.add(ff.equals(ff.property("medium_category"), ff.literal(poi.getMediumCategory())));
        }
        if (poi.getProvince() != null && !poi.getProvince().isEmpty()) {
            filters.add(ff.equals(ff.property("province"), ff.literal(poi.getProvince())));
        }
        if (poi.getCity() != null && !poi.getCity().isEmpty()) {
            filters.add(ff.equals(ff.property("city"), ff.literal(poi.getCity())));
        }
        if (poi.getArea() != null && !poi.getArea().isEmpty()) {
            filters.add(ff.equals(ff.property("area"), ff.literal(poi.getArea())));
        }


        // 添加空间过滤条件
        if (polygon != null) {
            Filter spatialFilter = ff.within(ff.property("geom"), ff.literal(polygon));
            filters.add(spatialFilter);
        }

        // 组合所有过滤条件
        Filter combinedFilter;
        if (filters.isEmpty()) {
            combinedFilter = Filter.INCLUDE; // 查询所有数据
        } else {
            combinedFilter = ff.and(filters);
        }
        System.out.println("所有过滤条件" + combinedFilter.toString());

        SimpleFeatureSource featureSource = dataStore.getFeatureSource("poi");
        Query query = new Query("poi", combinedFilter);
        return getFeatures(query, featureSource);
    }

    public List<SimpleFeature> queryByMonth(int year, int month) throws IOException {
        SimpleFeatureSource featureSource = dataStore.getFeatureSource("poi");
        Filter filter = ff.and(
                ff.equals(ff.function("year", ff.property("end_time")), ff.literal(year)),
                ff.equals(ff.function("month", ff.property("end_time")), ff.literal(month))
        );
        Query query = new Query("poi", filter);
        return getFeatures(query, featureSource);
    }

    public List<SimpleFeature> queryByDay(int year, int month, int day) throws IOException {
        SimpleFeatureSource featureSource = dataStore.getFeatureSource("poi");
        Filter yearFilter = ff.equals(ff.function("year", ff.property("end_time")), ff.literal(year));
        Filter monthFilter = ff.equals(ff.function("month", ff.property("end_time")), ff.literal(month));
        Filter dayFilter = ff.equals(ff.function("day", ff.property("end_time")), ff.literal(day));
        Filter filter = ff.and(yearFilter, ff.and(monthFilter, dayFilter));
        Query query = new Query("poi", filter);
        return getFeatures(query, featureSource);
    }

    public List<SimpleFeature> queryByHourRange(int year, int month, int day, int startHour, int endHour) throws IOException {
        SimpleFeatureSource featureSource = dataStore.getFeatureSource("poi");
        Filter yearFilter = ff.equals(ff.function("year", ff.property("end_time")), ff.literal(year));
        Filter monthFilter = ff.equals(ff.function("month", ff.property("end_time")), ff.literal(month));
        Filter dayFilter = ff.equals(ff.function("day", ff.property("end_time")), ff.literal(day));
        Filter hourFilter = ff.between(ff.function("hour", ff.property("end_time")), ff.literal(startHour), ff.literal(endHour));

        Filter timeFilter = ff.and(yearFilter, ff.and(monthFilter, ff.and(dayFilter, hourFilter)));

        Query query = new Query("poi", timeFilter);
        return getFeatures(query, featureSource);
    }

    private List<SimpleFeature> getFeatures(Query query, SimpleFeatureSource featureSource) throws IOException {
        List<SimpleFeature> features = new ArrayList<>();
        try (FeatureIterator<SimpleFeature> iterator = featureSource.getFeatures(query).features()) {
            while (iterator.hasNext()) {
                features.add(iterator.next());
            }
        }
        return features;
    }
}
