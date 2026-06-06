package org.cug.geodt.weibo.geomesa.service;

import org.geotools.data.DataStore;
import org.locationtech.geomesa.utils.interop.SimpleFeatureTypes;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @className: GeoMesaService
 * @author: caiyixun
 * @description: geomesa通用方法类
 * @date: 2024/8/15 14:56
 * @version: 1.0
 */
@Service
public class GeoMesaService {

    @Autowired
    private DataStore dataStore;

    public void createSchema(String typeName, String schema) throws IOException {
//        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
//        builder.setName("poi");
//        builder.add("poi_id", String.class);
//        builder.add("city_code", String.class);
//        builder.add("poi_name", String.class);
//        builder.add("large_category", String.class);
//        builder.add("medium_category", String.class);
//        builder.add("lng", Double.class);
//        builder.add("lat", Double.class);
//        builder.add("province", String.class);
//        builder.add("city", String.class);
//        builder.add("area", String.class);
//        builder.add("weibo_num", String.class);
//        builder.add("end_time", Date.class);
//        builder.add("grid_id", String.class);
//        builder.add("sorted_index", String.class);
//        builder.add("active_level", String.class);
//        builder.add("geom", Point.class, 4326);

        SimpleFeatureType sft = SimpleFeatureTypes.createType(typeName, schema);
        dataStore.createSchema(sft);
    }

    public void deleteSchema(String typeName) throws IOException {
        dataStore.removeSchema(typeName);
    }

}
