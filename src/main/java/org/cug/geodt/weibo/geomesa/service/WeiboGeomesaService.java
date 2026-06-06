package org.cug.geodt.weibo.geomesa.service;

import org.cug.geodt.weibo.entity.Weibo;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @className: WeiboService
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/8/12 9:58
 * @version: 1.0
 */
@Service
public class WeiboGeomesaService {

    @Value("${weibo.geomesa.schema.with-coordinates}")
    private String weiboWithCoordinates;

    @Value("${weibo.geomesa.schema.without-coordinates}")
    private String weiboWithoutCoordinates;

    @Autowired
    private DataStore dataStore;

    public void storePOI(Weibo weibo) throws IOException {
        SimpleFeatureType sft = dataStore.getSchema(weiboWithCoordinates);
        if (sft == null) {
            throw new IOException("Schema 'poi' does not exist.");
        }
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(sft);
        SimpleFeature feature = weibo.toSimpleFeature(featureBuilder);

        SimpleFeatureStore featureStore = null;
        if(weibo.getLat() != null && weibo.getLng() != null){
            featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(weiboWithCoordinates);
        }else {
            featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(weiboWithoutCoordinates);
        }

        try {
            featureStore.addFeatures(DataUtilities.collection(feature));
        } catch (Exception e) {
            throw new IOException("Failed to store POI", e);
        }
    }



}
