package org.cug.geodt.weibo.geomesa.config;

import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.geotools.data.DataStore;
import org.geotools.data.memory.MemoryDataStore;
import org.locationtech.geomesa.accumulo.data.AccumuloDataStore;
import org.locationtech.geomesa.accumulo.data.AccumuloDataStoreFactory;
import org.locationtech.geomesa.index.geotools.GeoMesaDataStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: GeoMesaConfig
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/7/24 15:56
 * @version: 1.0
 */
@Configuration
public class GeoMesaConfig {

    @Value("${geomesa.instanceId}")
    private String instanceId;

    @Value("${geomesa.zookeepers}")
    private String zookeepers;

    @Value("${geomesa.user}")
    private String user;

    @Value("${geomesa.password}")
    private String password;

    @Value("${geomesa.catalog}")
    private String catalog;

//    @Bean
    public DataStore geoMesaDataStore() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("instanceId", instanceId);
        params.put("zookeepers", zookeepers);
        params.put("user", user);
        params.put("password", password);
        params.put("tableName", catalog);

        // Convert Map<String, String> to Map<String, Serializable>
        Map<String, Serializable> serializableParams = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            serializableParams.put(entry.getKey(), entry.getValue());
        }

        // Create the data store
        AccumuloDataStoreFactory factory = new AccumuloDataStoreFactory();
        DataStore dataStore = factory.createDataStore(serializableParams);

        return dataStore;
    }

    @Bean
    public DataStore dataStore() {
        // 使用 MemoryDataStore 作为默认实现
        return new MemoryDataStore();
    }
}
