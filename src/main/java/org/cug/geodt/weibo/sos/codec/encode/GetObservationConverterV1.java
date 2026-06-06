package org.cug.geodt.weibo.sos.codec.encode;

import net.opengis.gml.FeaturePropertyType;
import net.opengis.gml.TimePeriodType;
import net.opengis.gml.TimePositionType;
import net.opengis.om.x10.*;
import net.opengis.swe.x101.*;
import net.opengis.swe.x101.TextDocument.Text;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimeEntity;
import org.cug.geodt.weibo.sos.domain.observation.*;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/6/15 14:54
 * @Description ObservationResponseDocument文档编码器，输入一个ObservationResponseEntity对象
 * 输出ObservationCollectionDocument文档（1.0版本）
 */
public class GetObservationConverterV1 {

    public static ObservationCollectionDocument convert(ObservationResponseEntity observationResponseEntity) throws NullPointerException{
        ObservationCollectionDocument observationCollectionDocument = ObservationCollectionDocument.Factory.newInstance();
        ObservationCollectionType observationCollectionType = ObservationCollectionType.Factory.newInstance();
        if (observationResponseEntity.getObservationDataEntityList() != null ) {
            ObservationPropertyType[] observationPropertyTypes = convert(observationResponseEntity.getObservationDataEntityList());
            observationCollectionType.setMemberArray(observationPropertyTypes);
        }
        observationCollectionDocument.setObservationCollection(observationCollectionType);
        return observationCollectionDocument;
    }

    public static ObservationPropertyType[] convert(List<ObservationDataEntity> observationDataEntityList) throws NullPointerException{
        ArrayList<ObservationPropertyType> observationPropertyTypes = new ArrayList<>();
        for (ObservationDataEntity observationDataEntity : observationDataEntityList) {
            ObservationPropertyType observationPropertyType = ObservationPropertyType.Factory.newInstance();
            ObservationType observationType = ObservationType.Factory.newInstance();
            if (observationDataEntity.getTimeEntity() != null) {
                TimeObjectPropertyType timeObjectPropertyType = convert(observationDataEntity.getTimeEntity());
                observationType.setSamplingTime(timeObjectPropertyType);
            }
            if (observationDataEntity.getProcedure() != null) {
                ProcessPropertyType processPropertyType = convert(observationDataEntity.getProcedure());
                observationType.setProcedure(processPropertyType);
            }
            if (observationDataEntity.getObservedProperty() != null) {
                PhenomenonPropertyType phenomenonPropertyType = convertObservedProperty(observationDataEntity.getObservedProperty());
                observationType.setObservedProperty(phenomenonPropertyType);
            }
            if (observationDataEntity.getFeatureOfInterestEntity() != null) {
                FeaturePropertyType featurePropertyType = convert(observationDataEntity.getFeatureOfInterestEntity());
                observationType.setFeatureOfInterest(featurePropertyType);
            }
            if (observationDataEntity.getResultEntity() != null) {
                DataArrayDocument dataArrayDocument = convert(observationDataEntity.getResultEntity());
                observationType.setResult(dataArrayDocument);
            }
            observationPropertyType.setObservation(observationType);
            observationPropertyTypes.add(observationPropertyType);
        }
        return observationPropertyTypes.toArray(new ObservationPropertyType[observationPropertyTypes.size()]);
    }

    public static DataArrayDocument convert(ResultEntity resultEntity) throws NullPointerException{
        DataArrayDocument dataArrayDocument = DataArrayDocument.Factory.newInstance();
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getElementCountEntity() != null) {
            AbstractDataArrayType.ElementCount elementCount = convert(resultEntity.getDataArrayEntity().getElementCountEntity());
            dataArrayType.setElementCount(elementCount);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getElementTypeEntity() != null) {
            DataComponentPropertyType dataComponentPropertyType = convert(resultEntity.getDataArrayEntity().getElementTypeEntity());
            dataArrayType.setElementType(dataComponentPropertyType);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getEncodingEntity() != null) {
            BlockEncodingPropertyType blockEncodingPropertyType = convert(resultEntity.getDataArrayEntity().getEncodingEntity());
            dataArrayType.setEncoding(blockEncodingPropertyType);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getValuesEntity() != null) {
            DataValuePropertyType dataValuePropertyType = convert(resultEntity.getDataArrayEntity().getValuesEntity());
            dataArrayType.setValues(dataValuePropertyType);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getId() != null) {
            dataArrayType.setId(resultEntity.getDataArrayEntity().getId());
            dataArrayType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        dataArrayDocument.setDataArray1(dataArrayType);
        return dataArrayDocument;
    }

    public static AbstractDataArrayType.ElementCount convert(ElementCountEntity elementCountEntity) throws NullPointerException{
        AbstractDataArrayType.ElementCount elementCount = AbstractDataArrayType.ElementCount.Factory.newInstance();
        CountDocument.Count count = CountDocument.Count.Factory.newInstance();
        count.setValue(BigInteger.valueOf(elementCountEntity.getCountValues()));
        elementCount.setCount(count);
        return elementCount;
    }

    public static DataComponentPropertyType convert(ElementTypeEntity elementTypeEntity) throws NullPointerException{
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        DataComponentPropertyType dataComponentPropertyType = dataArrayType.addNewElementType();
        SimpleDataRecordDocument simpleDataRecordDocument = SimpleDataRecordDocument.Factory.newInstance();
        SimpleDataRecordType simpleDataRecordType = SimpleDataRecordType.Factory.newInstance();
        if (elementTypeEntity.getGmlId() != null) {
            simpleDataRecordType.setId(elementTypeEntity.getGmlId());
        } else {
            simpleDataRecordType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (elementTypeEntity.getFieldEntityList() != null) {
            AnyScalarPropertyType[] fieldArray = convertField(elementTypeEntity.getFieldEntityList());
            simpleDataRecordType.setFieldArray(fieldArray);
        }
        simpleDataRecordDocument.setSimpleDataRecord(simpleDataRecordType);
        dataComponentPropertyType.set(simpleDataRecordDocument);
        if (elementTypeEntity.getName() != null) {
            dataComponentPropertyType.setName(elementTypeEntity.getName());
        } else {
            dataComponentPropertyType.setName(XmlOptionHelper.NullPointHandler("string",false));
        }

        return dataComponentPropertyType;
    }

    public static AnyScalarPropertyType[] convertField(List<FieldEntity> fieldEntityList) throws NullPointerException{
        ArrayList<AnyScalarPropertyType> anyScalarPropertyTypes = new ArrayList<>();
        for (FieldEntity fieldEntity : fieldEntityList) {
            AnyScalarPropertyType anyScalarPropertyType = AnyScalarPropertyType.Factory.newInstance();
            if (fieldEntity.getQuantity() != null) {
                QuantityDocument.Quantity quantity = convert(fieldEntity.getQuantity());
                anyScalarPropertyType.setQuantity(quantity);
            }
            if (fieldEntity.getName() != null) {
                anyScalarPropertyType.setName(fieldEntity.getName());
            } else {
                anyScalarPropertyType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            anyScalarPropertyTypes.add(anyScalarPropertyType);
        }
        return anyScalarPropertyTypes.toArray(new AnyScalarPropertyType[anyScalarPropertyTypes.size()]);
    }

    public static QuantityDocument.Quantity convert(QuantityEntity quantityEntity) throws NullPointerException{
        QuantityDocument.Quantity quantity = QuantityDocument.Quantity.Factory.newInstance();
        UomPropertyType uomPropertyType = UomPropertyType.Factory.newInstance();
        if (quantityEntity.getDefinition() != null) {
            quantity.setDefinition(quantityEntity.getDefinition());
        } else {
            quantity.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (quantityEntity.getUom() != null) {
            uomPropertyType.setCode(quantityEntity.getUom());
        }
        quantity.setUom(uomPropertyType);
        return quantity;
    }

    public static BlockEncodingPropertyType convert(EncodingEntity encodingEntity) throws NullPointerException{
        BlockEncodingPropertyType blockEncodingPropertyType = BlockEncodingPropertyType.Factory.newInstance();
        TextBlockDocument.TextBlock textBlock = TextBlockDocument.TextBlock.Factory.newInstance();
        textBlock.setTokenSeparator(encodingEntity.getTokenSeparator());
        textBlock.setDecimalSeparator(encodingEntity.getDecimalSeparator());
        textBlock.setBlockSeparator(encodingEntity.getBlockSeparator());
        blockEncodingPropertyType.setTextBlock(textBlock);
        return blockEncodingPropertyType;
    }

    public static DataValuePropertyType convert(ValuesEntity valuesEntity) throws NullPointerException{
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        DataValuePropertyType dataValuePropertyType = dataArrayType.addNewValues();
        Text text = Text.Factory.newInstance();
        if (valuesEntity.getValue() != null) {
            text.setValue(valuesEntity.getValue());
        } else {
            text.setValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        dataValuePropertyType.set(text);
        return dataValuePropertyType;
    }

    public static FeaturePropertyType convert(FeatureOfInterestEntity featureOfInterestEntity) throws NullPointerException{
        FeaturePropertyType featurePropertyType = FeaturePropertyType.Factory.newInstance();
        if (featureOfInterestEntity.getHref() != null) {
            featurePropertyType.setHref(featureOfInterestEntity.getHref());
        } else {
            featurePropertyType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }
        return featurePropertyType;
    }

    public static PhenomenonPropertyType convertObservedProperty(String observedProperty) throws NullPointerException{
        PhenomenonPropertyType phenomenonPropertyType = PhenomenonPropertyType.Factory.newInstance();
        if (phenomenonPropertyType.getHref() != null) {
            phenomenonPropertyType.setHref(observedProperty);
        } else {
            phenomenonPropertyType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }
        return phenomenonPropertyType;
    }

    private static ProcessPropertyType convert(String procedure) throws NullPointerException{
        ProcessPropertyType processPropertyType = ProcessPropertyType.Factory.newInstance();
        if (processPropertyType.getHref() != null) {
            processPropertyType.setHref(procedure);
        } else {
            processPropertyType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }
        return processPropertyType;
    }

    public static TimeObjectPropertyType convert(TimeEntity timeEntity) throws NullPointerException{
        ObservationType observationType = ObservationType.Factory.newInstance();
        TimeObjectPropertyType timeObjectPropertyType = observationType.addNewSamplingTime();
        TimePeriodType timePeriodType = TimePeriodType.Factory.newInstance();
        TimePositionType timePositionType = TimePositionType.Factory.newInstance();
        if (timeEntity.getTimePeriodEntity() != null && timeEntity.getTimePeriodEntity().getBeginTime() != null) {
            timePositionType.setStringValue(timeEntity.getTimePeriodEntity().getBeginTime());
        }
        timePeriodType.setBeginPosition(timePositionType);
        if (timeEntity.getTimePeriodEntity() != null && timeEntity.getTimePeriodEntity().getEndTime() != null) {
            timePositionType.setStringValue(timeEntity.getTimePeriodEntity().getEndTime());
        }
        timePeriodType.setEndPosition(timePositionType);
        timeObjectPropertyType.set(timePeriodType);
        return timeObjectPropertyType;
    }
}
