package org.cug.geodt.weibo.sos.codec.encode;

import net.opengis.fes.x20.*;
import net.opengis.gml.x32.*;
import net.opengis.ows.x11.*;
import net.opengis.sos.x20.CapabilitiesDocument;
import net.opengis.sos.x20.CapabilitiesType;
import net.opengis.sos.x20.ContentsType;
import net.opengis.sos.x20.ObservationOfferingType;
import net.opengis.swes.x20.AbstractContentsType;
import net.opengis.swes.x20.AbstractOfferingType;
import net.opengis.swes.x20.FeatureRelationshipType;
import org.cug.geodt.weibo.sos.domain.capabilities.*;
import org.cug.geodt.weibo.sos.domain.describeSensor.EnvelopeEntity;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimeEntity;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 8:55
 * @Description 能力文档编码器，输入一个CapabilitiesResponseEntity对象
 * 输出能力文档（CapabilitiesDocument文档2.0版本）
 */
public class GetCapabilitiesConverterV2 {

    //CapabilitiesDocument构造
    public static CapabilitiesDocument convert(CapabilitiesResponseEntity capabilitiesResponseEntity) {
        CapabilitiesDocument capabilitiesDocument = CapabilitiesDocument.Factory.newInstance();
        CapabilitiesType capabilitiesType = CapabilitiesType.Factory.newInstance();
        if(capabilitiesResponseEntity.getServiceProviderEntity() != null) {
            ServiceProviderDocument.ServiceProvider serviceProvider = convert(capabilitiesResponseEntity.getServiceProviderEntity());
            capabilitiesType.setServiceProvider(serviceProvider);
        }
        if(capabilitiesResponseEntity.getServiceIdentificationEntity() != null) {
            ServiceIdentificationDocument.ServiceIdentification serviceIdentification = convert(capabilitiesResponseEntity.getServiceIdentificationEntity());
            capabilitiesType.setServiceIdentification(serviceIdentification);
        }
        if(capabilitiesResponseEntity.getOperationMetadataEntity() != null) {
            OperationsMetadataDocument.OperationsMetadata operationsMetadata = convert(capabilitiesResponseEntity.getOperationMetadataEntity());
            capabilitiesType.setOperationsMetadata(operationsMetadata);
        }
        if(capabilitiesResponseEntity.getContentsEntity() != null) {
            CapabilitiesType.Contents contents = convert(capabilitiesResponseEntity.getContentsEntity());
            capabilitiesType.setContents(contents);
        }
        if(capabilitiesResponseEntity.getFilterCapabilitiesEntity() != null) {
            CapabilitiesType.FilterCapabilities filterCapabilities = convert(capabilitiesResponseEntity.getFilterCapabilitiesEntity());
            capabilitiesType.setFilterCapabilities(filterCapabilities);
        }
        if(capabilitiesResponseEntity.getUpdateSequence() != null) {
            capabilitiesType.setUpdateSequence(capabilitiesResponseEntity.getUpdateSequence());
        } else {
            capabilitiesType.setUpdateSequence(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(capabilitiesResponseEntity.getVersion() != null) {
            capabilitiesType.setVersion(capabilitiesResponseEntity.getVersion());
        } else {
            capabilitiesType.setVersion(XmlOptionHelper.NullPointHandler("string",false));
        }
        capabilitiesDocument.setCapabilities(capabilitiesType);
        return capabilitiesDocument;
    }

    public static CapabilitiesType.FilterCapabilities convert(FilterCapabilitiesEntity filterCapabilitiesEntity) {
        CapabilitiesType.FilterCapabilities filterCapabilities = CapabilitiesType.FilterCapabilities.Factory.newInstance();
        FilterCapabilitiesDocument.FilterCapabilities filterCapability = FilterCapabilitiesDocument.FilterCapabilities.Factory.newInstance();
        TemporalCapabilitiesType temporalCapabilitiesType = convert(filterCapabilitiesEntity.getTemporalCapabilities());
//        SpatialCapabilitiesType spatialCapabilitiesType = convert(filterCapabilitiesEntity.getSpatialCapabilitiesEntity());
//        ScalarCapabilitiesType scalarCapabilitiesType = convert(filterCapabilitiesEntity.getScalarCapabilitiesEntity());
//        IdCapabilitiesType idCapabilitiesType = convert(filterCapabilitiesEntity.getIdCapabilitiesEntity());
//        AvailableFunctionsType availableFunctionsType = convert(filterCapabilitiesEntity.getFunctionsEntity());
        ConformanceType conformanceType = convert(filterCapabilitiesEntity.getConformanceEntity());
        filterCapability.setTemporalCapabilities(temporalCapabilitiesType);
//        filterCapability.setSpatialCapabilities(spatialCapabilitiesType);
//        filterCapability.setScalarCapabilities(scalarCapabilitiesType);
//        filterCapability.setIdCapabilities(idCapabilitiesType);
//        filterCapability.setFunctions(availableFunctionsType);
        filterCapability.setConformance(conformanceType);
        filterCapabilities.setFilterCapabilities(filterCapability);
        return filterCapabilities;
    }

    private static ConformanceType convert(ConformanceEntity conformanceEntity) {
        ConformanceType conformanceType = ConformanceType.Factory.newInstance();
        if(conformanceEntity.getConstraintEntityList() != null) {
            DomainType[] domainTypes = convertConstraint(conformanceEntity.getConstraintEntityList());
            conformanceType.setConstraintArray(domainTypes);
        }
        return conformanceType;
    }

//    private static ScalarCapabilitiesType convert(IdCapabilitiesEntity idCapabilitiesEntity) {
//    }
//
//    private static SpatialCapabilitiesType convert(SpatialCapabilitiesEntity spatialCapabilitiesEntity) {
//    }

    private static TemporalCapabilitiesType convert(TemporalCapabilitiesEntity temporalCapabilities) {
        TemporalCapabilitiesType temporalCapabilitiesType = TemporalCapabilitiesType.Factory.newInstance();
        TemporalOperatorsType temporalOperatorsType = TemporalOperatorsType.Factory.newInstance();
        if(temporalCapabilities.getTemporalOperatorEntity() != null) {
            TemporalOperatorType[] temporalOperatorTypes = convertOperator(temporalCapabilities.getTemporalOperatorEntity());
            temporalOperatorsType.setTemporalOperatorArray(temporalOperatorTypes);
            temporalCapabilitiesType.setTemporalOperators(temporalOperatorsType);
        }
        return null;
    }

    private static TemporalOperatorType[] convertOperator(List<TemporalOperatorEntity> temporalOperatorEntity) {
        ArrayList<TemporalOperatorType> temporalOperatorTypes = new ArrayList<>();
        for (TemporalOperatorEntity operatorEntity : temporalOperatorEntity) {
            TemporalOperatorType temporalOperatorType = TemporalOperatorType.Factory.newInstance();
            TemporalOperandsType temporalOperandsType = TemporalOperandsType.Factory.newInstance();
            if(temporalOperatorEntity != null) {
                TemporalOperandsType.TemporalOperand[] temporalOperandArray = convertOperand(temporalOperatorEntity);
                temporalOperandsType.setTemporalOperandArray(temporalOperandArray);
            }
            if(operatorEntity.getName() != null) {
                temporalOperatorType.setName(operatorEntity.getName());
            } else {
                temporalOperatorType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            temporalOperatorType.setTemporalOperands(temporalOperandsType);
            temporalOperatorTypes.add(temporalOperatorType);
        }
        return temporalOperatorTypes.toArray(new TemporalOperatorType[temporalOperatorTypes.size()]);
    }

    private static TemporalOperandsType.TemporalOperand[] convertOperand(List<TemporalOperatorEntity> temporalOperatorEntity) {
        ArrayList<TemporalOperandsType.TemporalOperand> temporalOperands = new ArrayList<>();
        for (TemporalOperatorEntity operatorEntity : temporalOperatorEntity) {
            TemporalOperandsType.TemporalOperand temporalOperand = TemporalOperandsType.TemporalOperand.Factory.newInstance();
            temporalOperand.setNil();
        }
        return temporalOperands.toArray(new TemporalOperandsType.TemporalOperand[temporalOperands.size()]);
    }

    public static ServiceProviderDocument.ServiceProvider convert(ServiceProviderEntity serviceProviderEntity) {
        ServiceProviderDocument.ServiceProvider serviceProvider = ServiceProviderDocument.ServiceProvider.Factory.newInstance();
        OnlineResourceType onlineResourceType = OnlineResourceType.Factory.newInstance();
        if(serviceProviderEntity.getProviderSite() != null ) {
            onlineResourceType.setHref(serviceProviderEntity.getProviderSite());
        }else {
            onlineResourceType.setHref(XmlOptionHelper.NullPointHandler("href",false));
        }
        if(serviceProviderEntity.getServiceContact() != null) {
            ResponsiblePartySubsetType serviceContact = convert(serviceProviderEntity.getServiceContact());
            serviceProvider.setServiceContact(serviceContact);
        }
        if(serviceProviderEntity.getProviderName() != null) {
            serviceProvider.setProviderName(serviceProviderEntity.getProviderName());
        }else {
            serviceProvider.setProviderName(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(serviceProviderEntity.getProviderSite() != null) {
            serviceProvider.setProviderSite(onlineResourceType);
        }
        return serviceProvider;
    }

    public static ResponsiblePartySubsetType convert(ServiceContactEntity serviceContact) {
        ResponsiblePartySubsetType responsiblePartySubsetType = ResponsiblePartySubsetType.Factory.newInstance();
        ContactType contactType = ContactType.Factory.newInstance();
        TelephoneType telephoneType = TelephoneType.Factory.newInstance();
        if(serviceContact.getContactInfoEntity() != null ) {

        }
        telephoneType.setVoiceArray(serviceContact.getContactInfoEntity().getPhoneEntity().getVoiceList().toArray(new String[serviceContact.getContactInfoEntity().getPhoneEntity().getVoiceList().size()]));
        contactType.setPhone(telephoneType);
        responsiblePartySubsetType.setContactInfo(contactType);
        responsiblePartySubsetType.setIndividualName(serviceContact.getIndividualName());
        responsiblePartySubsetType.setPositionName(serviceContact.getPositionName());
        return responsiblePartySubsetType;
    }


    public static ServiceIdentificationDocument.ServiceIdentification convert(ServiceIdentificationEntity serviceIdentificationEntity) {
        ServiceIdentificationDocument.ServiceIdentification serviceIdentification = ServiceIdentificationDocument.ServiceIdentification.Factory.newInstance();
        if(serviceIdentificationEntity.getKeyWordsList() != null) {
            KeywordsType[] keywordsTypeArray = convertKeywordList(serviceIdentificationEntity.getKeyWordsList());
            serviceIdentification.setKeywordsArray(keywordsTypeArray);
        }
        if(serviceIdentificationEntity.getTitle() != null) {
            LanguageStringType[] titleArray = convertLanguageStringType(serviceIdentificationEntity.getTitle());
            serviceIdentification.setTitleArray(titleArray);
        }
        if (serviceIdentificationEntity.getAbstracts() != null) {
            LanguageStringType[] abstractArray = convertLanguageStringType(serviceIdentificationEntity.getAbstracts());
            serviceIdentification.setAbstractArray(abstractArray);
        }
        if (serviceIdentificationEntity.getProfileList() != null) {
            serviceIdentification.setProfileArray(serviceIdentificationEntity.getProfileList().toArray(new String[serviceIdentificationEntity.getProfileList().size()]));
        }
        if (serviceIdentificationEntity.getServiceType() != null) {
            serviceIdentification.setServiceTypeVersionArray(serviceIdentificationEntity.getServiceType().toArray(new String[serviceIdentificationEntity.getServiceTypeVersion().size()]));
        }
        if (serviceIdentificationEntity.getAccessConstraints() != null) {
            serviceIdentification.setAccessConstraintsArray(serviceIdentificationEntity.getAccessConstraints().toArray(new String[serviceIdentificationEntity.getAccessConstraints().size()]));
        }
        if(serviceIdentificationEntity.getFees() != null) {
            serviceIdentification.setFees(serviceIdentificationEntity.getFees());
        }else {
            serviceIdentification.setFees(XmlOptionHelper.NullPointHandler("string",false));
        }
        return serviceIdentification;
    }

    private static KeywordsType[] convertKeywordList(List<KeywordsEntity> keyWordsList) {
        ArrayList<KeywordsType> keywordsTypes = new ArrayList<>();
        for (KeywordsEntity keywordsEntity : keyWordsList) {
            KeywordsType keywordsType = KeywordsType.Factory.newInstance();
            LanguageStringType[] languageStringTypes = convertLanguageStringType(keywordsEntity.getKeyWordLis());
            keywordsType.setKeywordArray(languageStringTypes);
            keywordsTypes.add(keywordsType);
        }
        return keywordsTypes.toArray(new KeywordsType[keywordsTypes.size()]);
    }


    public static LanguageStringType[] convertLanguageStringType(List<String> title) {
        ArrayList<LanguageStringType> languageStringTypes = new ArrayList<>();
        for (String s : title) {
            LanguageStringType languageStringType = LanguageStringType.Factory.newInstance();
            languageStringType.setStringValue(s);
            languageStringTypes.add(languageStringType);
        }
        LanguageStringType[] languageStringTypeArray = languageStringTypes.toArray(new LanguageStringType[languageStringTypes.size()]);
        return languageStringTypeArray;
    }


    public static OperationsMetadataDocument.OperationsMetadata convert(OperationsMetadataEntity operationMetadataEntity) {
        OperationsMetadataDocument.OperationsMetadata operationsMetadata = OperationsMetadataDocument.OperationsMetadata.Factory.newInstance();
        if (operationMetadataEntity.getOperations() !=null ) {
            OperationDocument.Operation[] operations = convertOperation(operationMetadataEntity.getOperations());
            operationsMetadata.setOperationArray(operations);
        }
        if (operationMetadataEntity.getParameterEntity() != null) {
            DomainType[] parameters = convertParameter(operationMetadataEntity.getParameterEntity());
            operationsMetadata.setParameterArray(parameters);
        }
        return operationsMetadata;
    }

    public static DomainType[] convertParameter(List<ParameterEntity> parameterEntityList) {
        ArrayList<DomainType> domainTypes = new ArrayList<>();
        for (ParameterEntity parameterEntity : parameterEntityList) {
            DomainType domainType = DomainType.Factory.newInstance();
            AllowedValuesDocument.AllowedValues allowedValues = AllowedValuesDocument.AllowedValues.Factory.newInstance();
            if(parameterEntity.getAllowedValuesEntity() != null && parameterEntity.getAllowedValuesEntity().getValueArray() != null ) {
                ValueType[] valueTypes = convertValueType(parameterEntity.getAllowedValuesEntity().getValueArray());
                allowedValues.setValueArray(valueTypes);
            }
            if(parameterEntity.getAllowedValuesEntity() != null && parameterEntity.getAllowedValuesEntity().getValueRangeEntity() != null ) {
                RangeType[] rangeTypes = convertRangeType(parameterEntity.getAllowedValuesEntity().getValueRangeEntity());
                allowedValues.setRangeArray(rangeTypes);
            }
            if(parameterEntity.getName() != null ) {
                domainType.setName(parameterEntity.getName());
            }else {
                domainType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            domainType.setAllowedValues(allowedValues);
            domainTypes.add(domainType);
        }
        return domainTypes.toArray(new DomainType[domainTypes.size()]);
    }

    public static RangeType[] convertRangeType(List<ValueRangeEntity> valueRangeEntity) {
        ArrayList<RangeType> rangeTypes = new ArrayList<>();
        for (ValueRangeEntity rangeEntity : valueRangeEntity) {
            RangeType rangeType = RangeType.Factory.newInstance();
            ValueType valueTypeMax = ValueType.Factory.newInstance();
            ValueType valueTypeMin = ValueType.Factory.newInstance();
            if(rangeEntity.getMaxValue() != null) {
                valueTypeMax.setStringValue(rangeEntity.getMaxValue());
            }
            else {
                valueTypeMax.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(rangeEntity.getMinValue() != null) {
                valueTypeMin.setStringValue(rangeEntity.getMinValue());
            }
            else {
                valueTypeMin.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            rangeType.setMaximumValue(valueTypeMax);
            rangeType.setMinimumValue(valueTypeMin);
            rangeTypes.add(rangeType);
        }
        return rangeTypes.toArray(new RangeType[rangeTypes.size()]);

    }

    public static ValueType[] convertValueType(List<String> valueArray) {
        ArrayList<ValueType> valueTypes = new ArrayList<>();
        for (String s : valueArray) {
            ValueType valueType = ValueType.Factory.newInstance();
            valueType.setStringValue(s);
            valueTypes.add(valueType);
        }
        return valueTypes.toArray(new ValueType[valueTypes.size()]);
    }

    public static OperationDocument.Operation[] convertOperation(List<OperationEntity> operationList) {
        ArrayList<OperationDocument.Operation> operations = new ArrayList<>();
        for (OperationEntity operationEntity : operationList) {
            OperationDocument.Operation operation = OperationDocument.Operation.Factory.newInstance();
            if (operationEntity.getParameterEntity() != null) {
                DomainType[] parameterArray = convertParameter(operationEntity.getParameterEntity());
                operation.setParameterArray(parameterArray);
            }
            if(operationEntity.getEntity() != null) {
                DCPDocument.DCP[] dcpArray= convertDcp(operationEntity.getEntity());
                operation.setDCPArray(dcpArray);
            }
            if(operationEntity.getName() != null) {
                operation.setName(operationEntity.getName());
            }else {
                operation.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            operations.add(operation);
        }
        return operations.toArray(new OperationDocument.Operation[operations.size()]);
    }

    public static DCPDocument.DCP[] convertDcp(List<DCPEntity> dcpEntityList) {
        ArrayList<DCPDocument.DCP> dcps = new ArrayList<>();
        for (DCPEntity dcpEntity : dcpEntityList) {
            DCPDocument.DCP dcp = DCPDocument.DCP.Factory.newInstance();
            HTTPDocument.HTTP http = HTTPDocument.HTTP.Factory.newInstance();
            if(dcpEntity.getHttpEntity().getPostRequestEntityList() != null) {
                RequestMethodType[] postArray = convertHttp(dcpEntity.getHttpEntity().getPostRequestEntityList());
                http.setPostArray(postArray);
            }
            if(dcpEntity.getHttpEntity().getGetRequestEntityList() != null) {
                RequestMethodType[] getArray = convertHttp(dcpEntity.getHttpEntity().getGetRequestEntityList());
                http.setGetArray(getArray);
            }
            dcp.setHTTP(http);
            dcps.add(dcp);
        }
        return dcps.toArray(new DCPDocument.DCP[dcps.size()]);
    }

    public static RequestMethodType[] convertHttp(List<RequestEntity> postList) {
        ArrayList<RequestMethodType> requestMethodTypes = new ArrayList<>();
        for (RequestEntity requestEntity : postList) {
            RequestMethodType requestMethodType = RequestMethodType.Factory.newInstance();
            if(requestEntity.getConstraintEntity() != null) {
                DomainType[] http = convertConstraint(requestEntity.getConstraintEntity());
                requestMethodType.setConstraintArray(http);
            }
            if(requestEntity.getHref() != null) {
                requestMethodType.setHref(requestEntity.getHref());
            }
            else {
                requestMethodType.setHref(XmlOptionHelper.NullPointHandler("href",false));
            }
            requestMethodTypes.add(requestMethodType);
        }
        return requestMethodTypes.toArray(new RequestMethodType[requestMethodTypes.size()]);
    }

    public static DomainType[] convertConstraint(List<ConstraintEntity> constraintEntityList) {
        ArrayList<DomainType> domainTypes = new ArrayList<>();
        for (ConstraintEntity constraintEntity : constraintEntityList) {
            DomainType domainType = DomainType.Factory.newInstance();
            AllowedValuesDocument.AllowedValues allowedValues = AllowedValuesDocument.AllowedValues.Factory.newInstance();
            if(constraintEntity.getAllowedValuesEntity().getValueArray() != null) {
                ValueType[] valueTypes = convertValueType(constraintEntity.getAllowedValuesEntity().getValueArray());
                allowedValues.setValueArray(valueTypes);
            }
            if(constraintEntity.getAllowedValuesEntity().getValueRangeEntity() != null) {
                RangeType[] rangeTypes = convertRangeType(constraintEntity.getAllowedValuesEntity().getValueRangeEntity());
                allowedValues.setRangeArray(rangeTypes);
            }
            if (constraintEntity.getName() !=null) {
                domainType.setName(constraintEntity.getName());
            }else {
                domainType.setName(XmlOptionHelper.NullPointHandler("href",false));
            }
            domainType.setAllowedValues(allowedValues);
            domainTypes.add(domainType);
        }
        return domainTypes.toArray(new DomainType[domainTypes.size()]);
    }


    public static CapabilitiesType.Contents convert(ContentsEntity contentsEntity) {
        CapabilitiesType.Contents contents = CapabilitiesType.Contents.Factory.newInstance();
        ContentsType contentsType = ContentsType.Factory.newInstance();
        if(contentsEntity.getOfferingEntityList() != null) {
            AbstractContentsType.Offering[] offerings = convertOffering(contentsEntity.getOfferingEntityList());
            contentsType.setOfferingArray(offerings);
        }
        contents.setContents(contentsType);
        return contents;
    }

    public static AbstractContentsType.Offering[] convertOffering(List<OfferingEntity> offeringEntityList) {
        ArrayList<AbstractContentsType.Offering> offerings = new ArrayList<>();
        for (OfferingEntity offeringEntity : offeringEntityList) {
            AbstractContentsType.Offering offering = AbstractContentsType.Offering.Factory.newInstance();
            ObservationOfferingType observationOfferingType = ObservationOfferingType.Factory.newInstance();
            if(offeringEntity.getRelatedFeatureEntityList() != null) {
                AbstractOfferingType.RelatedFeature[] relatedFeatureArray = convert(offeringEntity.getRelatedFeatureEntityList());
                observationOfferingType.setRelatedFeatureArray(relatedFeatureArray);
            }
            if(offeringEntity.getIdentifier() != null ){
                observationOfferingType.setIdentifier(offeringEntity.getIdentifier());
            }else {
                observationOfferingType.setIdentifier(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(offeringEntity.getProcedure() != null) {
                observationOfferingType.setProcedure(offeringEntity.getProcedure());
            }else {
                observationOfferingType.setProcedure(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(offeringEntity.getProcedureDescriptionFormatList() != null) {
                observationOfferingType.setProcedureDescriptionFormatArray(offeringEntity.getProcedureDescriptionFormatList().toArray(new String[offeringEntity.getProcedureDescriptionFormatList().size()]));
            }
            if(offeringEntity.getObservablePropertyList() != null) {
                observationOfferingType.setObservablePropertyArray(offeringEntity.getObservablePropertyList().toArray(new String[offeringEntity.getObservablePropertyList().size()]));
            }
            if(offeringEntity.getBoundedByEntity() != null) {
                ObservationOfferingType.ObservedArea observedArea = convert(offeringEntity.getBoundedByEntity());
                observationOfferingType.setObservedArea(observedArea);
            }
            if(offeringEntity.getTimeEntity() != null) {
                ObservationOfferingType.PhenomenonTime phenomenonTime = convert(offeringEntity.getTimeEntity());
                observationOfferingType.setPhenomenonTime(phenomenonTime);
            }
            offering.setAbstractOffering(observationOfferingType);
            offerings.add(offering);
        }
        return offerings.toArray(new AbstractContentsType.Offering[offerings.size()]);
    }

    private static ObservationOfferingType.PhenomenonTime convert(TimeEntity timeEntity) {
        ObservationOfferingType.PhenomenonTime phenomenonTime = ObservationOfferingType.PhenomenonTime.Factory.newInstance();
        TimePeriodType timePeriodType = TimePeriodType.Factory.newInstance();
        TimePositionType timePositionTypeStart = TimePositionType.Factory.newInstance();
        TimePositionType timePositionTypeEnd = TimePositionType.Factory.newInstance();
        if(timeEntity.getTimePeriodEntity().getBeginTime() != null) {
            timePositionTypeStart.setStringValue(timeEntity.getTimePeriodEntity().getBeginTime());
        } else {
            timePositionTypeStart.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(timeEntity.getTimePeriodEntity().getEndTime() != null) {
            timePositionTypeEnd.setStringValue(timeEntity.getTimePeriodEntity().getEndTime());
        } else {
            timePositionTypeEnd.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(timeEntity.getTimePeriodEntity().getId() != null) {
            timePeriodType.setId(timeEntity.getTimePeriodEntity().getId());
        } else {
            timePeriodType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        timePeriodType.setBeginPosition(timePositionTypeStart);
        timePeriodType.setEndPosition(timePositionTypeEnd);
        phenomenonTime.setTimePeriod(timePeriodType);
        return phenomenonTime;
    }

    private static ObservationOfferingType.ObservedArea convert(BoundedByEntityV1 boundedByEntity) {
        ObservationOfferingType.ObservedArea observedArea = ObservationOfferingType.ObservedArea.Factory.newInstance();
        if (boundedByEntity.getEnvelopeEntity() != null) {
            EnvelopeType envelopeType = convert(boundedByEntity.getEnvelopeEntity());
            observedArea.setEnvelope(envelopeType);
        }
        return observedArea;
    }

    private static EnvelopeType convert(EnvelopeEntity envelopeEntity) {
        EnvelopeType envelopeType = EnvelopeType.Factory.newInstance();
        if (envelopeEntity.getLowerCornerEntity() != null && envelopeEntity.getLowerCornerEntity().getListValue() != null) {
            DirectPositionType LowerCorner = convertCorner(envelopeEntity.getLowerCornerEntity().getListValue());
            envelopeType.setLowerCorner(LowerCorner);
        }
        if (envelopeEntity.getUpperCornerEntity() != null && envelopeEntity.getUpperCornerEntity().getListValue() != null) {
            DirectPositionType UpperCorner = convertCorner(envelopeEntity.getUpperCornerEntity().getListValue());
            envelopeType.setUpperCorner(UpperCorner);
        }
        if (envelopeEntity.getSrsName() != null) {
            envelopeType.setSrsName(envelopeEntity.getSrsName());
        }
        return envelopeType;
    }

    private static DirectPositionType convertCorner(List<Double> listValue) {
        DirectPositionType directPositionType = DirectPositionType.Factory.newInstance();
        directPositionType.setListValue(listValue);
        return directPositionType;
    }


    public static AbstractOfferingType.RelatedFeature[] convert(List<RelatedFeatureEntity> relatedFeatureEntityList) {
        ArrayList<AbstractOfferingType.RelatedFeature> relatedFeatures = new ArrayList<>();
        for (RelatedFeatureEntity relatedFeatureEntity : relatedFeatureEntityList) {
            AbstractOfferingType.RelatedFeature relatedFeature = AbstractOfferingType.RelatedFeature.Factory.newInstance();
            FeatureRelationshipType featureRelationshipType = FeatureRelationshipType.Factory.newInstance();
            FeaturePropertyType featurePropertyType = FeaturePropertyType.Factory.newInstance();
            if(relatedFeatureEntity.getRole() != null){
                featureRelationshipType.setRole(relatedFeatureEntity.getRole());
            }else {
                featureRelationshipType.setRole(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(relatedFeatureEntity.getTarget() != null) {
                featurePropertyType.setHref(relatedFeatureEntity.getTarget());
            }else {
                featurePropertyType.setHref(XmlOptionHelper.NullPointHandler("href",false));
            }
            featureRelationshipType.setTarget(featurePropertyType);
            relatedFeature.setFeatureRelationship(featureRelationshipType);
            relatedFeatures.add(relatedFeature);
        }
        return relatedFeatures.toArray(new AbstractOfferingType.RelatedFeature[relatedFeatures.size()]);
    }

}
