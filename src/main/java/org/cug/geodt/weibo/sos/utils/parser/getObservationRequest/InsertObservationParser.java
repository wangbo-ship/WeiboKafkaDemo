package org.cug.geodt.weibo.sos.utils.parser.getObservationRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.codec.decode.OMDecode;
import org.cug.geodt.weibo.sos.common.proceduremanage.ProcedureSegmentationStrategy;
import org.cug.geodt.weibo.sos.domain.observation.ElementTypeEntity;
import org.cug.geodt.weibo.sos.domain.observation.EncodingEntity;
import org.cug.geodt.weibo.sos.domain.observation.ObservationDataEntity;
import org.cug.geodt.weibo.sos.mapper.SensorDataFloatMapper;
import org.cug.geodt.weibo.sos.mapper.SensorDataStringMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author WJW
 * Date 2023/7/13 15:27
 * @Description 插入观测值及数据打包操作
 */
@Repository
@Component
@Slf4j
public class InsertObservationParser {

    private int index = 3;

    //用于记录观测值的类型，V = 0 为Float, V =1 为 String。
    private HashMap<String,Integer> observedValueTypeMap;

    @Autowired
    private ProcedureSegmentationStrategy procedureSegmentationStrategy;

    @Autowired
    SensorDataFloat sensorDataFloat;

    @Autowired
    SensorDataFloatMapper sensorDataFloatMapper;

    @Autowired
    SensorDataStringMapper sensorDataStringMapper;

    @Autowired
    SensorDataString sensorDataString;


    /**
     * 将xml解析为对象，再将中间层对象，再将中间层对象转换为数据库实体对象类型，
     * 进行数据记录切分更新操作
     * @param xml
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XmlException
     * @throws JAXBException
     */
    public void OMConvertToObject(String xml) throws IOException, ParserConfigurationException, SAXException, XmlException, JAXBException {
        observedValueTypeMap = new HashMap<>();
        ObservationDataEntity observationDataEntity = OMDecode.convert(xml);
        int recordsNumber = observationDataEntity.getResultEntity().getDataArrayEntity().getElementCountEntity().getCountValues();
        //获取传感器id
        String key = observationDataEntity.getProcedure();
        sensorDataFloat.setSensorId(key);
        sensorDataString.setSensorId(key);

        //提取OM编码中的观测属性
        ElementTypeEntity elementTypeEntity = observationDataEntity.getResultEntity().getDataArrayEntity().getElementTypeEntity();
        List<String> observedPropertyList = elementTypeEntity.getFieldEntityList().stream().filter(
                fieldEntity ->
                        !fieldEntity.getName().equals("time") && !fieldEntity.getName().equals("longitude") && !fieldEntity.getName().equals("latitude")
        ).map(
                fieldEntity -> fieldEntity.getName()
        ).collect(Collectors.toList());

        //存储观测值的类型属于string还是float
        elementTypeEntity.getFieldEntityList().forEach(
                fieldEntity -> {
                    if (fieldEntity.getQuantity() != null) {
                        if (fieldEntity.getQuantity().getUom().equals("string")) {
                            observedValueTypeMap.put(fieldEntity.getName(),0);
                        } else {
                            observedValueTypeMap.put(fieldEntity.getName(),1);
                        }
                    }
                });

        //获取数据编码格式
        EncodingEntity encodingEntity = observationDataEntity.getResultEntity().getDataArrayEntity().getEncodingEntity();
        String blockSeparator = encodingEntity.getBlockSeparator();
        String tokenSeparator = encodingEntity.getTokenSeparator();

        //获取观测值
        ArrayList<ArrayList<String>> values = StringUtils.valuesParser(observationDataEntity.getResultEntity().getDataArrayEntity().getValuesEntity().getValue(), blockSeparator, tokenSeparator);

        //将观测值插入数据库
        values.forEach(
                (singleValueRecord) -> {
                    //按观测属性插入数据库
                    observedPropertyList.forEach(
                            observedProperty -> {
                                if (observedValueTypeMap.get(observedProperty) == 0) {
                                    sensorDataString.setObsTimestamp(Long.parseLong(singleValueRecord.get(0)));
                                    sensorDataString.setLongitude(Float.parseFloat(singleValueRecord.get(1)));
                                    sensorDataString.setLatitude(Float.parseFloat(singleValueRecord.get(2)));
                                    sensorDataString.setMetricName(observedProperty);
                                    sensorDataString.setMetricValue(singleValueRecord.get(index++));
                                    sensorDataStringMapper.insert(sensorDataString);

                                } else {
                                    sensorDataFloat.setObsTimestamp(Long.parseLong(singleValueRecord.get(0)));
                                    sensorDataFloat.setLongitude(Float.parseFloat(singleValueRecord.get(1)));
                                    sensorDataFloat.setLatitude(Float.parseFloat(singleValueRecord.get(2)));
                                    sensorDataFloat.setMetricName(observedProperty);
                                    if (!singleValueRecord.get(index).equals("")) {
                                        sensorDataFloat.setMetricValue(Float.parseFloat(singleValueRecord.get(index++)));
                                    }
                                    sensorDataFloatMapper.insert(sensorDataFloat);
                                }
                            }
                    );
                    index = 3;
                }
        );

        //procedure 更新
        procedureSegmentationStrategy.updateProcedureSegmentationMap(key, Long.parseLong(String.valueOf(recordsNumber)));
    }
}









//        FESConfiguration configuration = new FESConfiguration();
//        Parser parser = new Parser(configuration);
//        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
//        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(inputStream);
//        System.out.println(map);
//
//        //procedure
//        String procedure = map.get("procedure").toString();
//        key = procedure;
//        sensorDataFloat.setSensorId(procedure);
//        sensorDataString.setSensorId(procedure);
//
//        //feature
//        String featureOfInterest = map.get("featureOfInterest").toString();
//        HashMap<String, ?> result = (HashMap<String, ?>) map.get("result");
//        Long elementCount = Long.parseLong(result.get("elementCount").toString());
//        number = elementCount;
//
//        //value
//        String value = result.get("value").toString();
//
//        //encoding
//        HashMap<String, ?> encoding = (HashMap<String, ?>) result.get("encoding");
//        String blockSeparator = encoding.get("blockSeparator").toString();
//        String decimalSeparator = encoding.get("decimalSeparator").toString();
//        String tokenSeparator = encoding.get("tokenSeparator").toString();
//
//        ArrayList<ArrayList<String>> valuesArrayList = StringUtils.valuesParser(value, blockSeparator, tokenSeparator);
//
//        //elementType
//        HashMap<String, ?> elementType = (HashMap<String, ?>) result.get("elementType");
//        for (HashMap<String, ?> field : (ArrayList<HashMap<String, ?>>) elementType.get("field")) {
//            HashMap<String,?> quantity = (HashMap<String,?>) field.get("Quantity");
//            if (quantity.containsKey("uom") && quantity.get("uom").equals("string")){
//
//            }else {
//                fields.add(field.get("name").toString());
//            }
//
//            if(field.containsKey("name")) {
//
//                if(field.get("name").toString().equals("time")) {
//                    DefaultInstant time = (DefaultInstant)field.get("time");
//                }
//                if(field.get("name").toString().equals("longitude")) {
//
//                }
//                if (field.get("name").toString().equals("latitude")) {
//
//                }
//                if (quantity.containsKey("uom") && quantity.get("uom").equals("string")){
//                    Object name = quantity.get("name");
//                }else {
//                    ArrayList<String> metricNames = new ArrayList<>();
//                    metricNames.add(field.get("name").toString());
//                }
//            }
//        }
//        valuesArrayList.stream().map((s) -> s.stream().map(s1 -> {if ()}))

