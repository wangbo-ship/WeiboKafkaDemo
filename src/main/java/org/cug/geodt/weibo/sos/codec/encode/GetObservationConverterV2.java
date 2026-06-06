package org.cug.geodt.weibo.sos.codec.encode;


import net.opengis.gml.x32.*;
import net.opengis.om.x20.*;
import net.opengis.sos.x20.GetObservationResponseDocument;
import net.opengis.sos.x20.GetObservationResponseType;
import net.opengis.swe.x20.CountPropertyType;
import net.opengis.swe.x20.TimeType;
import net.opengis.swe.x20.*;
import org.cug.geodt.weibo.sos.domain.observation.*;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/6/5 21:23
 * @Description ObservationResponseDocument文档编码器，输入ObservationResponseEntity对象，
 * 输出GetObservationResponseDocument文档（2.0版本）
 */
public class GetObservationConverterV2 {

    public static GetObservationResponseDocument convert(ObservationResponseEntity observationResponseEntity) throws NullPointerException {
        GetObservationResponseDocument getObservationResponseDocument = GetObservationResponseDocument.Factory.newInstance();
        GetObservationResponseType getObservationResponseType = GetObservationResponseType.Factory.newInstance();
        if(observationResponseEntity.getObservationDataEntityList() != null) {
            GetObservationResponseType.ObservationData[] observationDataArray = convert(observationResponseEntity.getObservationDataEntityList());
            getObservationResponseType.setObservationDataArray(observationDataArray);
        }
        getObservationResponseDocument.setGetObservationResponse(getObservationResponseType);
        return getObservationResponseDocument;
    }

    public static GetObservationResponseType.ObservationData[] convert(List<ObservationDataEntity> observationDataEntityList) throws NullPointerException {
        ArrayList<GetObservationResponseType.ObservationData> observationDataArrayList = new ArrayList<>();
        for (ObservationDataEntity observationDataEntity : observationDataEntityList) {
            if (observationDataEntity != null) {
                GetObservationResponseType.ObservationData observationData = convert(observationDataEntity);
                observationDataArrayList.add(observationData);
            }
        }
        GetObservationResponseType.ObservationData[] observationDataArray =  observationDataArrayList.toArray(new GetObservationResponseType.ObservationData[observationDataArrayList.size()]);
        return observationDataArray;
    }

    public static GetObservationResponseType.ObservationData convert(ObservationDataEntity observationDataEntity) throws NullPointerException {
        GetObservationResponseType.ObservationData observationData = GetObservationResponseType.ObservationData.Factory.newInstance();
        OMObservationType omObservationType = OMObservationType.Factory.newInstance();
        TimeObjectPropertyType phenomenonTime = omObservationType.addNewPhenomenonTime();
        if (observationDataEntity.getNameEntity() != null) {
            CodeType[] nameArray = convertNameArray(observationDataEntity.getNameEntity());
            omObservationType.setNameArray(nameArray);
        }
        if (observationDataEntity.getPhenomenonTimeEntity() != null) {
            phenomenonTime = convert(observationDataEntity.getPhenomenonTimeEntity());
            omObservationType.setPhenomenonTime(phenomenonTime);
        }
        if (observationDataEntity.getFeatureOfInterestEntity() != null) {
            FeaturePropertyType featureOfInterest = convert(observationDataEntity.getFeatureOfInterestEntity());
            omObservationType.setFeatureOfInterest(featureOfInterest);
        }
        if (observationDataEntity.getObservedProperty() != null) {
            ReferenceType observedProperty = convertObservedProperty(observationDataEntity.getObservedProperty());
            omObservationType.setObservedProperty(observedProperty);
        }
        if (observationDataEntity.getOMType() != null) {
            ReferenceType OMtype = convertOMtype(observationDataEntity.getOMType());
            omObservationType.setType(OMtype);
        }
        if (observationDataEntity.getProcedure() != null) {
            OMProcessPropertyType procedure = convertProcedure(observationDataEntity.getProcedure());
            omObservationType.setProcedure(procedure);
        }
        if (observationDataEntity.getResultTime() != null) {
            TimeInstantPropertyType resultTime = convert(observationDataEntity.getResultTime());
            omObservationType.setResultTime(resultTime);
        }
        if (observationDataEntity.getResultEntity() != null) {
            DataArrayType convert = convert(observationDataEntity.getResultEntity());
            omObservationType.setResult(convert);
        }
        if (observationDataEntity.getRelatedFeatures() != null) {
            ObservationContextPropertyType[] observationContextPropertyTypeArray = convert(observationDataEntity.getRelatedFeatures());
            omObservationType.setRelatedObservationArray(observationContextPropertyTypeArray);
        }
        if (observationDataEntity.getRelatedFeatures() != null) {
            ObservationContextPropertyType[] observationContextPropertyTypeArray = convert(observationDataEntity.getRelatedFeatures());
            omObservationType.setRelatedObservationArray(observationContextPropertyTypeArray);
        }
        observationData.setOMObservation(omObservationType);
        return observationData;
    }

    public static OMObservationType convertOMObservationType(ObservationDataEntity observationDataEntity) throws NullPointerException {
        OMObservationType omObservationType = OMObservationType.Factory.newInstance();
        TimeObjectPropertyType phenomenonTime = omObservationType.addNewPhenomenonTime();
        if (observationDataEntity.getNameEntity() != null) {
            CodeType[] nameArray = convertNameArray(observationDataEntity.getNameEntity());
            omObservationType.setNameArray(nameArray);
        }
        if (observationDataEntity.getPhenomenonTimeEntity() != null) {
            phenomenonTime = convert(observationDataEntity.getPhenomenonTimeEntity());
            omObservationType.setPhenomenonTime(phenomenonTime);
        }
        if (observationDataEntity.getFeatureOfInterestEntity() != null) {
            FeaturePropertyType featureOfInterest = convert(observationDataEntity.getFeatureOfInterestEntity());
            omObservationType.setFeatureOfInterest(featureOfInterest);
        }
        if (observationDataEntity.getObservedProperty() != null) {
            ReferenceType observedProperty = convertObservedProperty(observationDataEntity.getObservedProperty());
            omObservationType.setObservedProperty(observedProperty);
        }
        if (observationDataEntity.getOMType() != null) {
            ReferenceType OMtype = convertOMtype(observationDataEntity.getOMType());
            omObservationType.setType(OMtype);
        }
        if (observationDataEntity.getProcedure() != null) {
            OMProcessPropertyType procedure = convertProcedure(observationDataEntity.getProcedure());
            omObservationType.setProcedure(procedure);
        }
        if (observationDataEntity.getResultTime() != null) {
            TimeInstantPropertyType resultTime = convert(observationDataEntity.getResultTime());
            omObservationType.setResultTime(resultTime);
        }
        if (observationDataEntity.getResultEntity() != null) {
            DataArrayType convert = convert(observationDataEntity.getResultEntity());
            omObservationType.setResult(convert);
        }
        if (observationDataEntity.getRelatedFeatures() != null) {
            ObservationContextPropertyType[] observationContextPropertyTypeArray = convert(observationDataEntity.getRelatedFeatures());
            omObservationType.setRelatedObservationArray(observationContextPropertyTypeArray);
        }
        if (observationDataEntity.getRelatedFeatures() != null) {
            ObservationContextPropertyType[] observationContextPropertyTypeArray = convert(observationDataEntity.getRelatedFeatures());
            omObservationType.setRelatedObservationArray(observationContextPropertyTypeArray);
        }
        return omObservationType;
    }


    private static CodeType[] convertNameArray(List<String> nameEntity) {
        ArrayList<CodeType> codeTypes = new ArrayList<>();
        for (String s : nameEntity) {
            CodeType codeType = CodeType.Factory.newInstance();
            codeType.setStringValue(s);
            codeTypes.add(codeType);
        }
        return codeTypes.toArray(new CodeType[codeTypes.size()]);
    }


    public static TimeObjectPropertyType convert(PhenomenonTimeEntity phenomenonTimeEntity) throws NullPointerException {
        OMObservationType omObservationType = OMObservationType.Factory.newInstance();
        TimeObjectPropertyType timeObjectPropertyType = omObservationType.addNewPhenomenonTime();
//        TimeObjectPropertyType timeObjectPropertyType = TimeObjectPropertyType.Factory.newInstance();
        TimeInstantDocument timeInstantDocument = TimeInstantDocument.Factory.newInstance();
        TimePeriodDocument timePeriodDocument = TimePeriodDocument.Factory.newInstance();
        TimePositionType timePositionType = TimePositionType.Factory.newInstance();
        TimeInstantType timeInstantType = TimeInstantType.Factory.newInstance();
        TimePeriodType timePeriodType = TimePeriodType.Factory.newInstance();
        if (phenomenonTimeEntity.getGmlId() != null) {
            timeInstantType.setId(phenomenonTimeEntity.getGmlId());
            timePeriodType.setId(phenomenonTimeEntity.getGmlId());
        } else {
            timeInstantType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (phenomenonTimeEntity.getTimePosition() != null) {
            timePositionType.setStringValue(phenomenonTimeEntity.getTimePosition());
            timeInstantType.setTimePosition(timePositionType);
            timeInstantDocument.setTimeInstant(timeInstantType);
            timeObjectPropertyType.set(timeInstantDocument);
        } else {
            timePositionType.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (phenomenonTimeEntity.getTimePeriodEntity() != null && phenomenonTimeEntity.getTimePeriodEntity().getBeginTime() != null && phenomenonTimeEntity.getTimePeriodEntity().getEndTime() != null) {
            TimePositionType timePositionType1 = TimePositionType.Factory.newInstance();
            timePositionType1.setStringValue(phenomenonTimeEntity.getTimePeriodEntity().getBeginTime());
            TimePositionType timePositionType2 = TimePositionType.Factory.newInstance();
            timePositionType2.setStringValue(phenomenonTimeEntity.getTimePeriodEntity().getEndTime());
            timePeriodType.setBeginPosition(timePositionType1);
            timePeriodType.setEndPosition(timePositionType2);
            timePeriodDocument.setTimePeriod(timePeriodType);
            timeObjectPropertyType.set(timePeriodDocument);
        }

        return timeObjectPropertyType;
    }

    public static FeaturePropertyType convert(FeatureOfInterestEntity featureOfInterestEntity) throws NullPointerException {
        FeaturePropertyType featurePropertyType = FeaturePropertyType.Factory.newInstance();
//        AbstractFeatureType abstractFeatureType = featurePropertyType.addNewAbstractFeature();
//        BoundingShapeType boundingShapeType = convert(featureOfInterestEntity.getBoundedByEntity());
//        CodeWithAuthorityType codeWithAuthorityType = convert(featureOfInterestEntity.getGmlIdentifierEntity());
        if (featureOfInterestEntity.getHref() != null) {
            featurePropertyType.setHref(featureOfInterestEntity.getHref());
        } else {
            featurePropertyType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }

//        abstractFeatureType.setBoundedBy(boundingShapeType);
//        abstractFeatureType.setIdentifier(codeWithAuthorityType);
//        featurePropertyType.setAbstractFeature(abstractFeatureType);
        return featurePropertyType;

    }

    public static CodeWithAuthorityType convert(GmlIdentifierEntity gmlIdentifierEntity) throws NullPointerException {
        CodeWithAuthorityType codeWithAuthorityType = CodeWithAuthorityType.Factory.newInstance();
        if (gmlIdentifierEntity.getValue() != null) {
            codeWithAuthorityType.setStringValue(gmlIdentifierEntity.getValue());
            codeWithAuthorityType.setStringValue(gmlIdentifierEntity.getValue());
        }
        codeWithAuthorityType.setCodeSpace(XmlOptionHelper.NullPointHandler("string",false));
        return codeWithAuthorityType;
    }

    public static BoundingShapeType convert(BoundedByEntity boundedByEntity) throws NullPointerException {
        BoundedByDocument boundedByDocument = BoundedByDocument.Factory.newInstance();
        BoundingShapeType boundingShapeType = boundedByDocument.addNewBoundedBy();
        if (boundedByEntity.getPolygonEntity() != null) {
            PolygonDocument polygonDocument = convert(boundedByEntity.getPolygonEntity());
            boundingShapeType.set(polygonDocument);
        }
        return boundingShapeType;
    }

    public static PolygonDocument convert(PolygonEntity polygonEntity) throws NullPointerException {
        PolygonDocument polygonDocument = PolygonDocument.Factory.newInstance();
        PolygonType polygonType = PolygonType.Factory.newInstance();
        if (polygonEntity.getExterior() != null) {
            AbstractRingPropertyType exterior = convert(polygonEntity.getExterior());
            polygonType.setExterior(exterior);
        }
//        AbstractRingPropertyType[] interiorArray = convert(polygon.getInterior());
//        polygonType.setInteriorArray(interiorArray);
        if (polygonEntity.getId() != null) {
            polygonType.setId(polygonEntity.getId());
        } else {
            polygonType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (polygonEntity.getSrsName() != null) {
            polygonType.setSrsName(polygonEntity.getSrsName());
        } else {
            polygonType.setSrsName(XmlOptionHelper.NullPointHandler("string",false));
        }
        polygonDocument.setPolygon(polygonType);
        return polygonDocument;
    }

//    public static AbstractRingPropertyType[] convert(InteriorEntity interior) {
//
//    }

    public static AbstractRingPropertyType convert(ExteriorEntity exteriorEntity) throws NullPointerException {
        PolygonType polygonType = PolygonType.Factory.newInstance();
        AbstractRingPropertyType abstractRingPropertyType = polygonType.addNewExterior();
//        AbstractRingPropertyType abstractRingPropertyType = AbstractRingPropertyType.Factory.newInstance();
        if (exteriorEntity.getLinearRingEntity() != null) {
            LinearRingType linearRingType = convert(exteriorEntity.getLinearRingEntity());
            abstractRingPropertyType.set(linearRingType);
        }
        return abstractRingPropertyType;
    }

    public static LinearRingType convert(LinearRingEntity linearRingEntity) throws NullPointerException {
        LinearRingType linearRingType = LinearRingType.Factory.newInstance();
        CoordinatesType coordinatesType = CoordinatesType.Factory.newInstance();
        DirectPositionListType directPositionListType = DirectPositionListType.Factory.newInstance();
        if (linearRingEntity.getDirectPosList() != null) {
            directPositionListType.setListValue(linearRingEntity.getDirectPosList());
        }
        linearRingType.setDirectPosList(directPositionListType);
        return linearRingType;
    }

    public static ReferenceType convertOMtype(String href) throws NullPointerException {
        ReferenceType referenceType = ReferenceType.Factory.newInstance();
        if (href != null) {
            referenceType.setHref(href);
        } else {
            referenceType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }
        return referenceType;
    }

    public static ReferenceType convertObservedProperty(String href) throws NullPointerException {
        ReferenceType referenceType = ReferenceType.Factory.newInstance();
        if (href != null) {
            referenceType.setHref(href);
        } else {
            referenceType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }
        return referenceType;
    }

    public static OMProcessPropertyType convertProcedure(String procedure) throws NullPointerException {
        OMProcessPropertyType omProcessPropertyType = OMProcessPropertyType.Factory.newInstance();
        if (procedure != null) {
            omProcessPropertyType.setHref(procedure);
        } else {
            omProcessPropertyType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }

        return omProcessPropertyType;
    }

    public static TimeInstantPropertyType convert(String resultTime) throws NullPointerException {
        TimeInstantPropertyType timeInstantPropertyType = TimeInstantPropertyType.Factory.newInstance();
        if (resultTime != null) {
            timeInstantPropertyType.setHref(resultTime);
        } else {
            timeInstantPropertyType.setHref(XmlOptionHelper.NullPointHandler("string",false));
        }
        return timeInstantPropertyType;
    }

    public static DataArrayType convert(ResultEntity resultEntity) throws NullPointerException {
        DataArrayDocument dataArrayDocument = DataArrayDocument.Factory.newInstance();
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getEncodingEntity() != null) {
            DataArrayType.Encoding encoding = convert(resultEntity.getDataArrayEntity().getEncodingEntity());
            dataArrayType.setEncoding(encoding);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getElementTypeEntity() != null) {
            DataArrayType.ElementType elementType = convert(resultEntity.getDataArrayEntity().getElementTypeEntity());
            dataArrayType.setElementType(elementType);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getElementCountEntity() != null) {
            CountPropertyType countPropertyType = convert(resultEntity.getDataArrayEntity().getElementCountEntity());
            dataArrayType.setElementCount(countPropertyType);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getValuesEntity() != null) {
            EncodedValuesPropertyType values = convert(resultEntity.getDataArrayEntity().getValuesEntity());
            dataArrayType.setValues(values);
        }
        if (resultEntity.getDataArrayEntity() != null && resultEntity.getDataArrayEntity().getId() != null) {
            dataArrayType.setId(resultEntity.getDataArrayEntity().getId());
        } else {
            dataArrayType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        dataArrayDocument.setDataArray1(dataArrayType);
        return dataArrayType;
    }

    public static EncodedValuesPropertyType convert(ValuesEntity valuesEntity) throws NullPointerException {
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        EncodedValuesPropertyType encodedValuesPropertyType = dataArrayType.addNewValues();
//        EncodedValuesPropertyType encodedValuesPropertyType = EncodedValuesPropertyType.Factory.newInstance();
        TextType textType = TextType.Factory.newInstance();
        if (valuesEntity.getValue() != null) {
            textType.setValue(valuesEntity.getValue());
        } else {
            textType.setValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        encodedValuesPropertyType.set(textType);
        return encodedValuesPropertyType;
    }

    public static CountPropertyType convert(ElementCountEntity elementCountEntity) throws NullPointerException {
        CountType countType = CountType.Factory.newInstance();
        CountPropertyType countPropertyType = CountPropertyType.Factory.newInstance();
        countType.setValue(BigInteger.valueOf(elementCountEntity.getCountValues()));
        countPropertyType.setCount(countType);
        return countPropertyType;
    }

    public static DataArrayType.ElementType convert(ElementTypeEntity elementTypeEntity) throws NullPointerException {
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        DataArrayType.ElementType elementType = dataArrayType.addNewElementType();
//        DataArrayType.ElementType elementType = DataArrayType.ElementType.Factory.newInstance();
        DataRecordDocument dataRecordDocument = DataRecordDocument.Factory.newInstance();
        DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
        if (elementTypeEntity.getFieldEntityList() != null) {
            DataRecordType.Field[] fieldArray = convertField(elementTypeEntity.getFieldEntityList());
            dataRecordType.setFieldArray(fieldArray);
        }
        dataRecordDocument.setDataRecord(dataRecordType);
        elementType.set(dataRecordDocument);
        if (elementTypeEntity.getName() != null) {
            elementType.setName(elementTypeEntity.getName());
        }
        if (elementTypeEntity.getHref() != null) {
            elementType.setHref(elementTypeEntity.getHref());
        }
        return elementType;
    }

    public static DataRecordType.Field[] convertField(List<FieldEntity> fieldEntityList) throws NullPointerException {
        ArrayList<DataRecordType.Field> dataRecordTypeArrayList = new ArrayList<>();
        for (FieldEntity fieldEntity : fieldEntityList) {
            if (fieldEntity != null) {
                DataRecordType.Field field = convert(fieldEntity);
                dataRecordTypeArrayList.add(field);
            }
        }
        DataRecordType.Field[] fieldArray = dataRecordTypeArrayList.toArray(new DataRecordType.Field[dataRecordTypeArrayList.size()]);
        return fieldArray;
    }

    public static DataRecordType.Field convert(FieldEntity fieldEntity) throws NullPointerException {
        DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
        DataRecordType.Field field = dataRecordType.addNewField();
//        DataRecordType.Field field = DataRecordType.Field.Factory.newInstance();
        AbstractDataComponentType abstractDataComponentType = field.addNewAbstractDataComponent();
        AnyScalarPropertyType anyScalarPropertyType = AnyScalarPropertyType.Factory.newInstance();
//        AbstractDataComponentType abstractDataComponentType = AbstractDataComponentType.Factory.newInstance();
        if (fieldEntity.getTime() != null) {
            TimeType timeType = convertTime(fieldEntity.getTime());
            anyScalarPropertyType.setTime(timeType);
        }
        if (fieldEntity.getQuantity() != null) {
            QuantityType quantityType = convert(fieldEntity.getQuantity());
            anyScalarPropertyType.setQuantity(quantityType);
        }
        abstractDataComponentType.set(anyScalarPropertyType);
        field.set(anyScalarPropertyType);
        if (fieldEntity.getName() != null) {
            field.setName(fieldEntity.getName());
        }
//        field.setAbstractDataComponent(abstractDataComponentType);
        return field;
    }

    public static QuantityType convert(QuantityEntity quantity) throws NullPointerException {
        QuantityType quantityType = QuantityType.Factory.newInstance();
        UnitReference unitReference = UnitReference.Factory.newInstance();
        if (quantity.getUom() != null) {
            unitReference.setCode(quantity.getUom());
        } else {
            unitReference.setCode(XmlOptionHelper.NullPointHandler("string",false));
        }
        quantityType.setUom(unitReference);
        if (quantity.getDefinition() != null) {
            quantityType.setDefinition(quantity.getDefinition());
        } else {
            quantityType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (quantity.getDescription() != null) {
            quantityType.setDescription(quantity.getDescription());
        } else {
            quantityType.setDescription(XmlOptionHelper.NullPointHandler("string",false));
        }
        return quantityType;
    }

    public static TimeType convertTime(String time) throws NullPointerException {
        TimeType timeType = TimeType.Factory.newInstance();
        if (time != null) {
            timeType.setDefinition(time);
        }
        return timeType;
    }

    public static DataArrayType.Encoding convert(EncodingEntity encodingEntity) throws NullPointerException {
        DataArrayType dataArrayType = DataArrayType.Factory.newInstance();
        DataArrayType.Encoding encoding = dataArrayType.addNewEncoding();
//        DataArrayType.Encoding encoding = DataArrayType.Encoding.Factory.newInstance();
        TextEncodingDocument textEncodingDocument = TextEncodingDocument.Factory.newInstance();
        TextEncodingType textEncodingType = TextEncodingType.Factory.newInstance();
        textEncodingType.setBlockSeparator(encodingEntity.getBlockSeparator());
        textEncodingType.setDecimalSeparator(encodingEntity.getDecimalSeparator());
        textEncodingType.setTokenSeparator(encodingEntity.getTokenSeparator());
        textEncodingDocument.setTextEncoding(textEncodingType);
        encoding.set(textEncodingDocument);
        return encoding;
    }

    public static ObservationContextPropertyType[] convert(RelatedFeatures relatedFeatureEntity) throws NullPointerException {
        ArrayList<ObservationContextPropertyType> observationContextPropertyTypeArrayList = new ArrayList<>();
        for (String relatedFeatures : relatedFeatureEntity.getRelatedFeatureList()) {
            ReferenceType relatedObservationReferenceType = ReferenceType.Factory.newInstance();
            ObservationContextType observationContextType = ObservationContextType.Factory.newInstance();
            ObservationContextPropertyType observationContextPropertyType = ObservationContextPropertyType.Factory.newInstance();
            relatedObservationReferenceType.setHref(relatedFeatures);
            observationContextType.setRelatedObservation(relatedObservationReferenceType);
            observationContextPropertyType.setObservationContext(observationContextType);
            observationContextPropertyTypeArrayList.add(observationContextPropertyType);
        }
        ObservationContextPropertyType[] observationContextPropertyTypeArray = observationContextPropertyTypeArrayList.toArray(new ObservationContextPropertyType[observationContextPropertyTypeArrayList.size()]);
        return observationContextPropertyTypeArray;
    }




}
