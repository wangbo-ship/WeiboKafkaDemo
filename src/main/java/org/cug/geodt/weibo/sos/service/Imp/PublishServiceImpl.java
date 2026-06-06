package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.service.PublishService;
import org.geotools.data.FeatureWriter;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.type.AttributeDescriptor;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @className: PublishServiceImpl
 * @author: caiyixun
 * @description: sos观测数据发布服务
 * @date: 2024/10/31 16:44
 * @version: 1.0
 */
@Service
public class PublishServiceImpl implements PublishService {

    @Value("${geoserver.geoserver-url}")
    private String GEOSERVER_URL;

    @Value("${geoserver.rest-url}")
    private String GEOSERVER_REST_URL;

    @Value("${geoserver.workspace}")
    private String WORKSPACE;

    @Value("${geoserver.datastore}")
    private String DATASTORE;

    @Value("${geoserver.username}")
    private String USERNAME;

    @Value("${geoserver.password}")
    private String PASSWORD;

    private static final Map<String, String> mappingMap = new HashMap<>();

    static {
        mappingMap.put("情感得分", "theme1");
        mappingMap.put("主题2", "theme2");
        mappingMap.put("time", "time");
        mappingMap.put("longitude", "longitude");
        mappingMap.put("latitude", "latitude");
    }

    @Override
    public String publishWFS(String sosXml) {
        try {
            // 解析 SOS 响应并生成 Observation 数据
            List<Observation> observations = parseSosResponse(sosXml);

            // 将 Observation 数据转换为 Shapefile
            File shapefile = createShapefile(observations);

            // 使用 GeoServer REST API 发布 Shapefile
            String url = uploadShapefileToGeoServer(shapefile);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "WFS 发布失败：" + e.getMessage();
        }
    }

    public static class Observation {
        public Map<String, String> fields;

        public Observation(Map<String, String> fields) {
            this.fields = fields;
        }
    }

    public List<Observation> parseSosResponse(String sosXml) throws Exception {
        List<Observation> observations = new ArrayList<>();

        // 创建 DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(sosXml.getBytes()));

        // 获取所有字段名
        List<String> fieldNames = new ArrayList<>();
        NodeList fieldNodes = document.getElementsByTagName("swe:field");
        for (int i = 0; i < fieldNodes.getLength(); i++) {
            Element fieldElement = (Element) fieldNodes.item(i);
            String fieldName = fieldElement.getAttribute("name");
            fieldNames.add(fieldName);
        }

        // 解析 <swe:values> 节点内容
        NodeList valuesNodes = document.getElementsByTagName("swe:values");
        if (valuesNodes.getLength() > 0) {
            String values = valuesNodes.item(0).getTextContent();
            String[] records = values.split("@@");

            for (String record : records) {
                String[] fields = record.split(",");
                Map<String, String> fieldMap = new HashMap<>();

                // 动态匹配字段名和字段值
                for (int j = 0; j < fields.length && j < fieldNames.size(); j++) {
                    // 去除字段值中的换行和制表符
                    String cleanedValue = fields[j].replaceAll("\\s+", " ").trim();
                    fieldMap.put(fieldNames.get(j), cleanedValue);
                }

                observations.add(new Observation(fieldMap));
            }
        }
        return observations;
    }

    private File createShapefile(List<Observation> observations) throws IOException {
        // 创建 Shapefile 的 FeatureType
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Observation");
        builder.add("the_geom", Point.class); // 几何字段
        List<String> keys = new ArrayList<>();
        for (String key : observations.get(0).fields.keySet()) {
            // 截断字段名至10字符以内，并移除非ASCII字符
            String enKey = mappingMap.get(key);
            String truncatedKey = enKey.replaceAll("[^\\x00-\\x7F]", "");  // 移除非ASCII字符
            truncatedKey = truncatedKey.length() > 10 ? truncatedKey.substring(0, 10) : truncatedKey;
            keys.add(key);
            builder.add(truncatedKey, String.class);

        }

        SimpleFeatureType featureType = builder.buildFeatureType();
        GeometryFactory geometryFactory = new GeometryFactory();

        // 创建临时 Shapefile
        File shapefile = File.createTempFile("observation_data_", ".shp");
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        Map<String, Serializable> params = new HashMap<>();
        params.put("url", shapefile.toURI().toURL());
        params.put("create spatial index", Boolean.TRUE);
        ShapefileDataStore dataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
        dataStore.createSchema(featureType);

        // 写入数据
        try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = dataStore.getFeatureWriterAppend(dataStore.getTypeNames()[0], null)) {
            for (Observation obs : observations) {
                SimpleFeature feature = writer.next();
                double longitude = Double.parseDouble(obs.fields.get("longitude"));
                double latitude = Double.parseDouble(obs.fields.get("latitude"));
                Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

                feature.setAttribute("the_geom", point);
                for (String key : keys) {
                    String enKey = mappingMap.get(key);
                    String truncatedKey = enKey.replaceAll("[^\\x00-\\x7F]", "");  // 移除非ASCII字符
                    truncatedKey = truncatedKey.length() > 10 ? truncatedKey.substring(0, 10) : truncatedKey;
                    feature.setAttribute(truncatedKey, obs.fields.get(key));
                }
                writer.write();
            }
        }

        dataStore.dispose();
        return shapefile;
    }

    private String uploadShapefileToGeoServer(File shapefile) throws IOException {
        System.out.println("shapefile:" + shapefile);
        File zipShapefile = zipShapefile(shapefile);

        createDataStore();

        // Step 1: 上传 Shapefile 压缩包
        URL url = new URL(GEOSERVER_REST_URL + "/workspaces/" + WORKSPACE + "/datastores/" + DATASTORE + "/file.shp");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/zip");
        String auth = USERNAME + ":" + PASSWORD;
        connection.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes()));
        connection.setFixedLengthStreamingMode(zipShapefile.length());  // 设置文件长度

        // 上传 Shapefile 文件
        try (OutputStream os = new BufferedOutputStream(connection.getOutputStream());
             FileInputStream fis = new FileInputStream(zipShapefile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

        // 检查响应代码
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            System.out.println("Failed to upload Shapefile: " + connection.getResponseMessage());

            // 获取详细的错误信息
            try (InputStream errorStream = connection.getErrorStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line).append("\n");
                }
                System.out.println("Error details: " + errorResponse.toString());
            } catch (IOException e) {
                System.out.println("Error reading error stream: " + e.getMessage());
            }

            return null;
        }
        System.out.println("Shapefile上传成功");
        System.out.println("Shapefile:" + zipShapefile);

        // Step 2: 发布图层 (FeatureType) 以启用 WFS 服务
        String layerName = shapefile.getName().substring(shapefile.getName().indexOf(":") + 1)
                .replace("\\", "/")
                .replaceAll("^.*[/\\\\]", "")
                .replace(".shp", ""); // 发布的图层名称应与 Shapefile 名称一致
        System.out.println("layerName:" + layerName);
        URL publishUrl = new URL(GEOSERVER_REST_URL + "/workspaces/" + WORKSPACE + "/datastores/" + DATASTORE + "/featuretypes");
        HttpURLConnection publishConnection = (HttpURLConnection) publishUrl.openConnection();
        publishConnection.setDoOutput(true);
        publishConnection.setRequestMethod("POST");
        publishConnection.setRequestProperty("Content-Type", "application/json");
        publishConnection.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes()));

        // 创建包含属性的 JSON payload
        String jsonPayload = buildJsonPayload(shapefile);
        System.out.println("jsonPayload：" + jsonPayload);

        try (OutputStream os = publishConnection.getOutputStream()) {
            os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            int responseCode1 = publishConnection.getResponseCode();
            if (responseCode1 == HttpURLConnection.HTTP_CREATED || responseCode1 == HttpURLConnection.HTTP_OK) {
                System.out.println("发布图层成功！");
            } else {
                System.out.println("发布图层失败，响应码：" + responseCode1);
            }
        } catch (IOException e) {
            System.out.println("Error obtaining OutputStream: " + e.getMessage());
            e.printStackTrace();
        }

        try (InputStream errorStream = publishConnection.getErrorStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                errorResponse.append(line).append("\n");
            }
            System.out.println("错误信息：" + errorResponse.toString());
        } catch (IOException e) {
            System.out.println("读取错误信息时发生异常：" + e.getMessage());
        }


        String wfsUrl = GEOSERVER_URL + "/" + WORKSPACE + "/ows?service=WFS"
                + "&version=2.0.0&request=GetFeature&typeName=" + WORKSPACE + ":" + layerName
                + "&outputFormat=application/json";
        return wfsUrl;

    }

    private void createDataStore() throws IOException {
        URL url = new URL(GEOSERVER_REST_URL + "/workspaces/" + WORKSPACE + "/datastores");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        String auth = USERNAME + ":" + PASSWORD;
        connection.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes()));

        // JSON payload with specified data store type
        String jsonPayload = "{ \"dataStore\": { \"name\": \"" + DATASTORE + "\", \"type\": \"Shapefile\", \"connectionParameters\": { \"url\": \"file:data/observation_data.zip\" } } }";

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("存储仓库创建成功！");
        } else {
            System.out.println("存储仓库已创建！");
        }
    }

    // 压缩 Shapefile 文件集合为 .zip 文件
    private File zipShapefile(File shapefile) throws IOException {
        String baseName = shapefile.getAbsolutePath().substring(0, shapefile.getAbsolutePath().lastIndexOf("."));
        File zipFile = new File(baseName + ".zip");

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String extension : new String[]{".shp", ".shx", ".dbf", ".prj"}) {
                File file = new File(baseName + extension);
                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        zos.putNextEntry(new ZipEntry(file.getName()));
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            zos.write(buffer, 0, bytesRead);
                        }
                        zos.closeEntry();
                    }
                }
            }
        }

        return zipFile;
    }

    // 动态构建 JSON payload 的方法
    private String buildJsonPayload(File shapefile) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("url", shapefile.toURI().toURL());

        // 使用 GeoTools 获取 Shapefile 数据存储
        DataStore dataStore = DataStoreFinder.getDataStore(map);
        SimpleFeatureSource featureSource = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
        SimpleFeatureType featureType = featureSource.getSchema();

        // 构建 attributes JSON
        StringBuilder attributesJson = new StringBuilder();
        attributesJson.append("[ ");
        List<AttributeDescriptor> attributeDescriptors = featureType.getAttributeDescriptors();
        for (int i = 0; i < attributeDescriptors.size(); i++) {
            AttributeDescriptor descriptor = attributeDescriptors.get(i);
            String fieldName = descriptor.getLocalName();
            String fieldType = descriptor.getType().getBinding().getName();

            // 动态映射 Java 类型到 GeoServer 所需类型
            String binding;
            if (fieldType.equals("org.locationtech.jts.geom.Point")) {
                binding = "com.vividsolutions.jts.geom.Point";  // 几何字段
            } else if (fieldType.equals("java.lang.String")) {
                binding = "java.lang.String";
            } else if (fieldType.equals("java.lang.Integer")) {
                binding = "java.lang.Integer";
            } else if (fieldType.equals("java.lang.Double")) {
                binding = "java.lang.Double";
            } else if (fieldType.equals("java.util.Date")) {
                binding = "java.util.Date";
            } else {
                binding = "java.lang.String";  // 默认类型
            }

            // 添加字段到 attributes JSON
            attributesJson.append("{ \"name\": \"").append(fieldName).append("\", \"binding\": \"").append(binding).append("\" }");
            if (i < attributeDescriptors.size() - 1) {
                attributesJson.append(", ");
            }
        }
        attributesJson.append(" ]");

        // 关闭数据存储
        dataStore.dispose();

        // 构建最终的 JSON payload
        return "{ \"featureType\": { " +
                "\"name\": \"" + featureType.getTypeName() + "\", " +
                "\"nativeName\": \"" + featureType.getTypeName() + "\", " +
                "\"title\": \"" + featureType.getTypeName() + "\", " +
                "\"abstract\": \"Dynamic observation data layer\", " +
                "\"srs\": \"EPSG:4326\", " +
                "\"attributes\": " + attributesJson.toString() +
                " } }";
    }


}
