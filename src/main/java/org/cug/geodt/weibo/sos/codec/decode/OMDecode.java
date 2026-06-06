package org.cug.geodt.weibo.sos.codec.decode;

import net.opengis.gml.x32.*;
import net.opengis.om.x20.OMObservationDocument;
import net.opengis.om.x20.OMProcessPropertyType;
import net.opengis.om.x20.TimeObjectPropertyType;
import net.opengis.swe.x20.CountPropertyType;
import net.opengis.swe.x20.*;
import net.opengis.swe.x20.impl.EncodedValuesPropertyTypeImpl;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimePeriodEntity;
import org.cug.geodt.weibo.sos.domain.observation.*;
import org.cug.geodt.weibo.sos.utils.StringUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.cug.geodt.weibo.sos.codec.decode.SensorMLDecode.convertQuantity;
import static org.cug.geodt.weibo.sos.codec.decode.SensorMLDecode.convertTimePeriod;

/**
 * @FileName OMDecode
 * @Author WJW
 * @Date 2023/7/20 10:10
 * @Description OM解码器, 输入OM的xml编码，
 * 输出一个ObservationDataEntity对象
 */
public class OMDecode {

    public static ObservationDataEntity convert(String xml) throws XmlException, IOException, JAXBException {
        ObservationDataEntity observationDataEntity = new ObservationDataEntity();
        OMObservationDocument omObservationDocument = OMObservationDocument.Factory.parse(xml);
        if (omObservationDocument.getOMObservation() != null) {
            if (omObservationDocument.getOMObservation().getPhenomenonTime() != null) {
                PhenomenonTimeEntity phenomenonTimeEntity = convert(omObservationDocument.getOMObservation().getPhenomenonTime());
                observationDataEntity.setPhenomenonTimeEntity(phenomenonTimeEntity);
            }
            if (omObservationDocument.getOMObservation().getResultTime() != null) {
                String resultTime = convert(omObservationDocument.getOMObservation().getResultTime());
                observationDataEntity.setResultTime(resultTime);
            }
            if (omObservationDocument.getOMObservation().getProcedure() != null) {
                String procedure= convert(omObservationDocument.getOMObservation().getProcedure());
                observationDataEntity.setProcedure(procedure);
            }
            if (omObservationDocument.getOMObservation().getObservedProperty() != null) {
                String observedProperty = convert(omObservationDocument.getOMObservation().getObservedProperty());
                observationDataEntity.setObservedProperty(observedProperty);
            }
            if (omObservationDocument.getOMObservation().getFeatureOfInterest() != null) {
                FeatureOfInterestEntity featureOfInterestEntity = convert(omObservationDocument.getOMObservation().getFeatureOfInterest());
                observationDataEntity.setFeatureOfInterestEntity(featureOfInterestEntity);
            }
            if (omObservationDocument.getOMObservation().getResult() != null) {
                ResultEntity resultEntity = new ResultEntity();
                DataArrayEntity convert = convert(omObservationDocument.getOMObservation().getResult());
                resultEntity.setDataArrayEntity(convert);
                observationDataEntity.setResultEntity(resultEntity);
            }
        }
        return observationDataEntity;
    }

    public static PhenomenonTimeEntity convert(TimeObjectPropertyType phenomenonTime) {
        PhenomenonTimeEntity phenomenonTimeEntity = new PhenomenonTimeEntity();
        if (phenomenonTime.getAbstractTimeObject() != null) {
            if (phenomenonTime.getAbstractTimeObject() instanceof TimeInstantType) {
                TimeInstantType timeInstantType = (TimeInstantType) phenomenonTime.getAbstractTimeObject();
                if (timeInstantType.getTimePosition() != null && timeInstantType.getTimePosition().getStringValue() != null) {
                    phenomenonTimeEntity.setTimePosition(timeInstantType.getTimePosition().getStringValue());
                }
            }
            if (phenomenonTime.getAbstractTimeObject() instanceof TimePeriodType) {
                TimePeriodType timePeriodType = (TimePeriodType) phenomenonTime.getAbstractTimeObject();
                TimePeriodEntity timePeriodEntity = convertTimePeriod(timePeriodType);
                phenomenonTimeEntity.setTimePeriodEntity(timePeriodEntity);
            }
        }
        return phenomenonTimeEntity;
    }

    public static String convert(TimeInstantPropertyType resultTime) {
        String s = "";
        if (resultTime.getHref() != null) {
            s = resultTime.getHref();
        }
        return s;
    }

    public static String convert(OMProcessPropertyType procedure) {
        String s = "";
        if (procedure.getHref() != null) {
            s = procedure.getHref();
        }
        return s;
    }

    public static String convert(ReferenceType observedProperty) {
        String s = "";
        if (observedProperty.getHref() != null) {
            s = observedProperty.getHref();
        }
        return s;
    }

    public static FeatureOfInterestEntity convert(FeaturePropertyType featureOfInterest) {
        FeatureOfInterestEntity featureOfInterestEntity = new FeatureOfInterestEntity();
        if (featureOfInterest.getHref() != null) {
            featureOfInterestEntity.setHref(featureOfInterest.getHref());
        }
        return featureOfInterestEntity;
    }

//    public static ResultEntity convert(XmlObject result) throws JAXBException {
//        Class<? extends XmlObject> aClass3 = result.getClass();
//
//        return resultEntity;
//    }

    public static DataArrayEntity convert(DataArrayType dataArrayType) {
        CountPropertyType elementCount = dataArrayType.getElementCount();
        System.out.println(elementCount.toString());
        DataArrayEntity dataArrayEntity = new DataArrayEntity();
        if (dataArrayType.getElementCount() != null) {
            if (dataArrayType.getElementCount().getCount() != null && dataArrayType.getElementCount().getCount().getValue() != null) {
                ElementCountEntity elementCountEntity = new ElementCountEntity();
                elementCountEntity.setCountValues(dataArrayType.getElementCount().getCount().getValue().intValue());
                dataArrayEntity.setElementCountEntity(elementCountEntity);
            }
        }
        if (dataArrayType.getElementType() != null) {
            ElementTypeEntity elementTypeEntity = new ElementTypeEntity();
            if (dataArrayType.getElementType().getAbstractDataComponent() != null && dataArrayType.getElementType().getAbstractDataComponent() instanceof DataRecordType) {
                DataRecordType dataRecordType= (DataRecordType) dataArrayType.getElementType().getAbstractDataComponent();
                elementTypeEntity = convert(dataRecordType.getFieldArray());
            }
            dataArrayEntity.setElementTypeEntity(elementTypeEntity);
        }
        if (dataArrayType.getEncoding() != null) {
            EncodingEntity encodingEntity = new EncodingEntity();
            if (dataArrayType.getEncoding().getAbstractEncoding() != null && dataArrayType.getEncoding().getAbstractEncoding() instanceof TextEncodingType) {
                TextEncodingType textEncodingType = (TextEncodingType)dataArrayType.getEncoding().getAbstractEncoding();
                if (textEncodingType.getBlockSeparator() != null) {
                    encodingEntity.setBlockSeparator(textEncodingType.getBlockSeparator());
                }
                if (textEncodingType.getDecimalSeparator() != null) {
                    encodingEntity.setDecimalSeparator(textEncodingType.getDecimalSeparator());
                }
                if (textEncodingType.getTokenSeparator() != null) {
                    encodingEntity.setTokenSeparator(textEncodingType.getTokenSeparator());
                }
            }
            dataArrayEntity.setEncodingEntity(encodingEntity);
        }
        if (dataArrayType.getValues() != null) {
            Class<? extends EncodedValuesPropertyType> aClass = dataArrayType.getValues().getClass();
            System.out.println(aClass);
            if (dataArrayType.getValues() instanceof EncodedValuesPropertyTypeImpl) {
                EncodedValuesPropertyTypeImpl textType = (EncodedValuesPropertyTypeImpl) dataArrayType.getValues();
                ValuesEntity valuesEntity = new ValuesEntity();
                String wscanon_text = textType.get_wscanon_text();
                String stringValue = textType.getStringValue();
                if (textType.get_wscanon_text() != null) {
                    valuesEntity.setValue(textType.get_wscanon_text());
                }
                dataArrayEntity.setValuesEntity(valuesEntity);
            }
        }
        return dataArrayEntity;
    }

    public static ElementTypeEntity convert(DataRecordType.Field[] fieldArray) {
        ElementTypeEntity elementTypeEntity = new ElementTypeEntity();
        List<FieldEntity> fieldEntityList = new ArrayList<>();
        for (DataRecordType.Field field : fieldArray) {
            FieldEntity fieldEntity = new FieldEntity();
            if (field.getName() != null) {
                fieldEntity.setName(field.getName());
            }
            if (field.getAbstractDataComponent() != null && field.getAbstractDataComponent() instanceof QuantityType) {
                QuantityType quantityType = (QuantityType) field.getAbstractDataComponent();
                QuantityEntity quantityEntity= convertQuantity(quantityType);
                fieldEntity.setQuantity(quantityEntity);
            }
            fieldEntityList.add(fieldEntity);
        }
        elementTypeEntity.setFieldEntityList(fieldEntityList);
        return elementTypeEntity;
    }


    //测试
    public static void main(String [] args) throws XmlException, IOException, JAXBException {
        String path = "D:\\Desktop\\GeoDT\\geodt-service\\geodt-service\\src\\main\\resources\\example\\om.xml";
        ObservationDataEntity convert = convert(path);
        ArrayList<ArrayList<String>> valuesParser = StringUtils.valuesParser(convert.getResultEntity().getDataArrayEntity().getValuesEntity().getValue(), "@@", ",");
        System.out.println(valuesParser.toString());
//        System.out.println(convert.toString());
    }


}
