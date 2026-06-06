package org.cug.geodt.weibo.sos.codec.encode;

import net.opengis.gml.*;
import net.opengis.ows.x11.OperationsMetadataDocument;
import net.opengis.ows.x11.ServiceIdentificationDocument;
import net.opengis.ows.x11.ServiceProviderDocument;
import net.opengis.sos.x10.CapabilitiesDocument;
import net.opengis.sos.x10.ContentsDocument;
import net.opengis.sos.x10.ObservationOfferingType;
import net.opengis.swe.x101.PhenomenonPropertyType;
import net.opengis.swe.x101.TimeGeometricPrimitivePropertyType;
import org.cug.geodt.weibo.sos.domain.capabilities.*;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimeEntity;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/6/15 14:52
 * @Description 能力文档编码器，输入一个CapabilitiesResponseEntity对象
 * 输出能力文档（CapabilitiesDocument文档1.0版本）
 */
public class GetCapabilitiesConverterV1 {

    public static CapabilitiesDocument convert(CapabilitiesResponseEntity capabilitiesResponseEntity) throws NullPointerException{
        CapabilitiesDocument capabilitiesDocument = CapabilitiesDocument.Factory.newInstance();
        CapabilitiesDocument.Capabilities capabilities = CapabilitiesDocument.Capabilities.Factory.newInstance();
        if(capabilitiesResponseEntity.getServiceProviderEntity() != null) {
            ServiceProviderDocument.ServiceProvider serviceProvider = GetCapabilitiesConverterV2.convert(capabilitiesResponseEntity.getServiceProviderEntity());
            capabilities.setServiceProvider(serviceProvider);
        }
        if(capabilitiesResponseEntity.getServiceIdentificationEntity() != null) {
            ServiceIdentificationDocument.ServiceIdentification serviceIdentification = GetCapabilitiesConverterV2.convert(capabilitiesResponseEntity.getServiceIdentificationEntity());
            capabilities.setServiceIdentification(serviceIdentification);
        }
        if(capabilitiesResponseEntity.getContentsEntity() != null) {
            ContentsDocument.Contents contents = convert(capabilitiesResponseEntity.getContentsEntity());
            capabilities.setContents(contents);
        }
//        FilterCapabilitiesDocument.FilterCapabilities filterCapabilities = convert(capabilitiesResponseEntity.getFilterCapabilitiesEntity());
        if(capabilitiesResponseEntity.getOperationMetadataEntity() != null) {
            OperationsMetadataDocument.OperationsMetadata operationsMetadata = GetCapabilitiesConverterV2.convert(capabilitiesResponseEntity.getOperationMetadataEntity());
            capabilities.setOperationsMetadata(operationsMetadata);
        }

//        capabilities.setFilterCapabilities(filterCapabilities);
        if(capabilitiesResponseEntity.getUpdateSequence() != null) {
            capabilities.setUpdateSequence(capabilitiesResponseEntity.getUpdateSequence());
        } else {
            capabilities.setUpdateSequence(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(capabilitiesResponseEntity.getVersion() != null) {
            capabilities.setVersion(capabilitiesResponseEntity.getVersion());
        } else {
            capabilities.setVersion(XmlOptionHelper.NullPointHandler("string",false));
        }
        capabilitiesDocument.setCapabilities(capabilities);
        return capabilitiesDocument;
    }

    public static ContentsDocument.Contents convert(ContentsEntity contentsEntity) throws NullPointerException{
        ContentsDocument.Contents contents = ContentsDocument.Contents.Factory.newInstance();
        ContentsDocument.Contents.ObservationOfferingList observationOfferingList = ContentsDocument.Contents.ObservationOfferingList.Factory.newInstance();
        if(contentsEntity.getOfferingEntityList() != null) {
            ObservationOfferingType[] observationOfferingTypeArray = convertOffering(contentsEntity.getOfferingEntityList());
            observationOfferingList.setObservationOfferingArray(observationOfferingTypeArray);
        }
        contents.setObservationOfferingList(observationOfferingList);
        return contents;
    }

    private static ObservationOfferingType[] convertOffering(List<OfferingEntity> offeringEntityList) throws NullPointerException{
        ArrayList<ObservationOfferingType> observationOfferingTypes = new ArrayList<>();
        for (OfferingEntity offeringEntity : offeringEntityList) {
            ObservationOfferingType observationOfferingType = ObservationOfferingType.Factory.newInstance();
            if (offeringEntity.getRelatedFeatureEntityList() != null) {
                ReferenceType[] referenceTypes = convertReferenceType(offeringEntity.getRelatedFeatureEntityList());
                observationOfferingType.setFeatureOfInterestArray(referenceTypes);
            }
            if (offeringEntity.getObservablePropertyList() != null) {
                PhenomenonPropertyType[] observedPropertyArray = convert(offeringEntity.getObservablePropertyList());
                observationOfferingType.setObservedPropertyArray(observedPropertyArray);
            }
            if (offeringEntity.getProcedureList() != null) {
                ReferenceType[] procedureArray = convertProcedure(offeringEntity.getProcedureList());
                observationOfferingType.setProcedureArray(procedureArray);
            }
            if (offeringEntity.getTimeEntity() != null) {
                TimeGeometricPrimitivePropertyType time = convert(offeringEntity.getTimeEntity());
                observationOfferingType.setTime(time);
            }
            if (offeringEntity.getBoundedByEntity() != null) {
                BoundingShapeType boundedBy = convert(offeringEntity.getBoundedByEntity());
                observationOfferingType.setBoundedBy(boundedBy);
            }
            if (offeringEntity.getId() != null) {
                observationOfferingType.setId(offeringEntity.getId());
            } else {
                observationOfferingType.setId(XmlOptionHelper.NullPointHandler("string",false));
            }
//            observationOfferingType.setIntendedApplicationArray();
            observationOfferingType.setResponseFormatArray(offeringEntity.getResponseFormatList().toArray(new String[offeringEntity.getResponseFormatList().size()]));

//            observationOfferingType.setLocation();
//            observationOfferingType.setDescription();
//            observationOfferingType.setNameArray();
//            observationOfferingType.setMetaDataPropertyArray();
            observationOfferingTypes.add(observationOfferingType);
        }
        return observationOfferingTypes.toArray(new ObservationOfferingType[observationOfferingTypes.size()]);
    }

    private static BoundingShapeType convert(BoundedByEntityV1 boundedByEntity) throws NullPointerException{
        BoundingShapeType boundingShapeType = BoundingShapeType.Factory.newInstance();
        EnvelopeType envelopeType = EnvelopeType.Factory.newInstance();
        DirectPositionType directPositionType = DirectPositionType.Factory.newInstance();
        if (boundedByEntity.getEnvelopeEntity() != null && boundedByEntity.getEnvelopeEntity().getName() != null) {
            envelopeType.setSrsName(boundedByEntity.getEnvelopeEntity().getName());
        } else {
            envelopeType.setSrsName(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (boundedByEntity.getEnvelopeEntity() != null && boundedByEntity.getEnvelopeEntity().getLowerCornerEntity() != null && boundedByEntity.getEnvelopeEntity().getLowerCornerEntity().getListValue() !=null){
            directPositionType.setListValue(boundedByEntity.getEnvelopeEntity().getLowerCornerEntity().getListValue());
        }
        envelopeType.setLowerCorner(directPositionType);
        if (boundedByEntity.getEnvelopeEntity() != null && boundedByEntity.getEnvelopeEntity().getUpperCornerEntity() != null && boundedByEntity.getEnvelopeEntity().getUpperCornerEntity().getListValue() != null) {
            directPositionType.setListValue(boundedByEntity.getEnvelopeEntity().getUpperCornerEntity().getListValue());
        }
        envelopeType.setUpperCorner(directPositionType);
        boundingShapeType.setEnvelope(envelopeType);
        return boundingShapeType;
    }

    private static TimeGeometricPrimitivePropertyType convert(TimeEntity timeEntity) throws NullPointerException{
        ObservationOfferingType observationOfferingType = ObservationOfferingType.Factory.newInstance();
        TimeGeometricPrimitivePropertyType timeGeometricPrimitivePropertyType = observationOfferingType.addNewTime();
//        AbstractTimeGeometricPrimitiveType abstractTimeGeometricPrimitiveType = timeGeometricPrimitivePropertyType.addNewTimeGeometricPrimitive();
        TimePeriodType timePeriodType = TimePeriodType.Factory.newInstance();
        TimePositionType timePositionType = TimePositionType.Factory.newInstance();
        if (timeEntity.getTimePeriodEntity() != null && timeEntity.getTimePeriodEntity().getBeginTime() != null) {
            timePositionType.setStringValue(timeEntity.getTimePeriodEntity().getBeginTime());
        }
        timePeriodType.setBeginPosition(timePositionType);
        if (timeEntity.getTimePeriodEntity() != null && timeEntity.getTimePeriodEntity().getEndTime() != null)
            timePositionType.setStringValue(timeEntity.getTimePeriodEntity().getEndTime());
        timePeriodType.setEndPosition(timePositionType);
//        abstractTimeGeometricPrimitiveType.set(timePeriodType);
        TimePeriodDocument timePeriodDocument = TimePeriodDocument.Factory.newInstance();
        timePeriodDocument.setTimePeriod(timePeriodType);
        timeGeometricPrimitivePropertyType.set(timePeriodDocument);
        return timeGeometricPrimitivePropertyType;
    }

    private static ReferenceType[] convertProcedure(List<String> procedureList) throws NullPointerException{
        ArrayList<ReferenceType> referenceTypes = new ArrayList<>();
        for (String s : procedureList) {
            ReferenceType referenceType = ReferenceType.Factory.newInstance();
            if (s != null) {
                referenceType.setHref(s);
            } else {
                referenceType.setHref(XmlOptionHelper.NullPointHandler("href",false));
            }
            referenceTypes.add(referenceType);
        }
        return referenceTypes.toArray(new ReferenceType[referenceTypes.size()]);
    }

    private static PhenomenonPropertyType[] convert(List<String> observablePropertyList) throws NullPointerException{
        ArrayList<PhenomenonPropertyType> phenomenonPropertyTypes = new ArrayList<>();
        for (String s : observablePropertyList) {
            PhenomenonPropertyType phenomenonPropertyType = PhenomenonPropertyType.Factory.newInstance();
            if (s != null) {
                phenomenonPropertyType.setHref(s);
            } else {
                phenomenonPropertyType.setHref(XmlOptionHelper.NullPointHandler("href",false));
            }
            phenomenonPropertyTypes.add(phenomenonPropertyType);
        }
        return phenomenonPropertyTypes.toArray(new PhenomenonPropertyType[phenomenonPropertyTypes.size()]);
    }

    private static ReferenceType[] convertReferenceType(List<RelatedFeatureEntity> relatedFeatureEntityList) throws NullPointerException{
        ArrayList<ReferenceType> referenceTypes = new ArrayList<>();
        for (RelatedFeatureEntity relatedFeatureEntity : relatedFeatureEntityList) {
            ReferenceType referenceType = ReferenceType.Factory.newInstance();
            referenceType.setHref(relatedFeatureEntity.getHref());
            referenceTypes.add(referenceType);
        }
        return referenceTypes.toArray(new ReferenceType[referenceTypes.size()]);
    }

//    public static FilterCapabilitiesDocument.FilterCapabilities convert(FilterCapabilitiesEntity filterCapabilitiesEntity) {
//
//    }

}
