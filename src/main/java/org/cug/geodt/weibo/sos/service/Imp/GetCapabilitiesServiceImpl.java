package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.capabilities.*;
import org.cug.geodt.weibo.sos.domain.describeSensor.*;
import org.cug.geodt.weibo.sos.enums.DescribeSensorGetEnum;
import org.cug.geodt.weibo.sos.enums.GetCapabilitiesPostEnum;
import org.cug.geodt.weibo.sos.enums.GetObservationGetEnum;
import org.cug.geodt.weibo.sos.factory.SensorObservationServiceFactory;
import org.cug.geodt.weibo.sos.mapper.CapabilityMapper;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.service.GetCapabilitiesService;
import org.cug.geodt.weibo.sos.utils.DateUtils;
import org.cug.geodt.weibo.sos.utils.LocationHandle;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Author WJW
 * Date 2023/4/19 17:23
 */
@Service
public class GetCapabilitiesServiceImpl implements GetCapabilitiesService {

    @Resource
    private InfoMapper infoMapper;
    @Resource
    private CapabilityMapper capabilityMapper;

    @Resource
    SensorObservationServiceFactory sensorObservationServiceFactory;

    @Override
    public String getGeoDTCapabilities() {
        List<OfferingProcedureFeatureSensorInfo> offeringProcedureFeatures = capabilityMapper.getCapabilityInfoBySensorIdsV2(null);
        CapabilitiesResponseEntity capabilitiesResponseEntity = new CapabilitiesResponseEntity();

        //添加 ServiceIdentification 元素:包含有关服务的标识信息，如服务名称、摘要、服务类型版本、费用和访问限制等
        ServiceIdentificationEntity serviceIdentificationEntity = generateServiceIdentification();

        //添加 ServiceProvider 元素:包含有关服务提供者的信息，如提供者名称、提供者站点和服务联系人等
        ServiceProviderEntity serviceProviderEntity = generateServiceProvider();

        OperationsMetadataEntity operationsMetadataEntity = generateOperationsMetadata(offeringProcedureFeatures);
        ContentsEntity contentsEntity = generateContents(offeringProcedureFeatures);

        capabilitiesResponseEntity.setServiceIdentificationEntity(serviceIdentificationEntity);
        capabilitiesResponseEntity.setServiceProviderEntity(serviceProviderEntity);
        capabilitiesResponseEntity.setOperationMetadataEntity(operationsMetadataEntity);
        capabilitiesResponseEntity.setContentsEntity(contentsEntity);

        return sensorObservationServiceFactory.getCapabilitiesDocument("2.0", capabilitiesResponseEntity);
    }

    private ServiceProviderEntity generateServiceProvider() {
        ServiceProviderEntity serviceProvider = new ServiceProviderEntity();
        serviceProvider.setProviderName("CUG-GeoDT");
        serviceProvider.setProviderSite("https://gis.cug.edu.cn/");
        ServiceContactEntity serviceContact = new ServiceContactEntity();
        ContactInfoEntity contactInfoEntity = new ContactInfoEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("湖北武汉");
        addressEntity.setCountry("中国");
        contactInfoEntity.setAddressEntity(addressEntity);
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setVoiceList(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("027-67883061"))));
        contactInfoEntity.setPhoneEntity(phoneEntity);
        serviceContact.setContactInfoEntity(contactInfoEntity);
        serviceProvider.setServiceContact(serviceContact);
        return serviceProvider;
    }

    private ContentsEntity generateContents(List<OfferingProcedureFeatureSensorInfo> o) {
        ContentsEntity contentsEntity = new ContentsEntity();
        List<OfferingEntity> offeringEntityList = new ArrayList<>();
        for (OfferingProcedureFeatureSensorInfo offering:o){
            OfferingEntity offeringEntity = new OfferingEntity();
            offeringEntity.setIdentifier(offering.getOfferingIdentifier());
            offeringEntity.setProcedure(offering.getProcedureIdentifier());
            offeringEntity.setProcedureDescriptionFormatList(Collections.unmodifiableList(Arrays.asList(offering.getProcedureDescriptionFormat())));
            List<String> metrics = LocationHandle.metricNameHandleOneNew(new SensorInfo(offering));
            offeringEntity.setObservablePropertyList(metrics);
            RelatedFeatureEntity relatedFeatureEntity = new RelatedFeatureEntity();
            relatedFeatureEntity.setTarget(offering.getFeatureIdentifier());
            offeringEntity.setRelatedFeatureEntityList(Collections.unmodifiableList(Arrays.asList(relatedFeatureEntity)));

            offeringEntity.setBoundedByEntity(getGeometryEnvelope(offering.getFeatureArea(),offering.getFeatureSRID()));
            offeringEntity.setTimeEntity(generateTimeEntity(offering));
            offeringEntity.setObservationType("http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement");
            offeringEntity.setResponseFormatList(Collections.unmodifiableList(Arrays.asList("http://www.opengis.net/om/2.0")));
            offeringEntityList.add(offeringEntity);
        }
        contentsEntity.setOfferingEntityList(offeringEntityList);
        return contentsEntity;
    }

    private BoundedByEntityV1 getGeometryEnvelope(String featureArea, int featureSRID) {
        try {
            BoundedByEntityV1 boundedByEntityV1 = new BoundedByEntityV1();
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(featureArea);
            geometry.setSRID(featureSRID);
            Envelope envelope = geometry.getEnvelopeInternal();
            EnvelopeEntity envelopeEntity = new EnvelopeEntity();
            envelopeEntity.setName("http://www.opengis.net/def/crs/EPSG/0/"+featureSRID);
            UpperCornerEntity upperCornerEntity = new UpperCornerEntity();
            upperCornerEntity.setListValue(Collections.unmodifiableList(Arrays.asList(envelope.getMaxX(),envelope.getMaxY())));
            LowerCornerEntity lowerCornerEntity = new LowerCornerEntity();
            lowerCornerEntity.setListValue(Collections.unmodifiableList(Arrays.asList(envelope.getMinX(),envelope.getMinY())));
            envelopeEntity.setUpperCornerEntity(upperCornerEntity);
            envelopeEntity.setLowerCornerEntity(lowerCornerEntity);
            boundedByEntityV1.setEnvelopeEntity(envelopeEntity);
            return boundedByEntityV1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private TimeEntity generateTimeEntity(OfferingProcedureFeatureSensorInfo o) {
        TimeEntity timeEntity = new TimeEntity();
        TimePeriodEntity timePeriodEntity = new TimePeriodEntity();
        timePeriodEntity.setBeginTime(o.getOfferingResultTimeStartStr());
        timePeriodEntity.setEndTime(o.getOfferingResultTimeEndStr());
        timePeriodEntity.setId("phenomenonTime");
        timeEntity.setTimePeriodEntity(timePeriodEntity);
        return timeEntity;
    }



    private ServiceIdentificationEntity generateServiceIdentification(){
        ServiceIdentificationEntity serviceIdentificationEntity = new ServiceIdentificationEntity();
        serviceIdentificationEntity.setTitle(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("GeoDT SOS"))));
        serviceIdentificationEntity.setAbstracts(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("Geodt Sensor Observation Service - Data Access for the Sensor Web"))));
        serviceIdentificationEntity.setServiceType(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("OGC:SOS"))));
        serviceIdentificationEntity.setServiceTypeVersion(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("2.0.0"))));
        serviceIdentificationEntity.setFees("None");
        serviceIdentificationEntity.setAccessConstraints(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("None"))));
        return serviceIdentificationEntity;
    }

    private OperationsMetadataEntity generateOperationsMetadata(List<OfferingProcedureFeatureSensorInfo> o){
        OperationsMetadataEntity operationsMetadataEntity = new OperationsMetadataEntity();
        List<OperationEntity> operationEntities = new ArrayList<>();
        operationEntities.add(generateOperateDescribeSensor(o));
        operationEntities.add(generateOperateGetCapabilities());
        operationEntities.add(generateOperateGetObservation(o));
        List<ParameterEntity> parameterEntities = generateOperationsMetadataParameter(o);
        operationsMetadataEntity.setParameterEntity(parameterEntities);
        operationsMetadataEntity.setOperations(operationEntities);
        return operationsMetadataEntity;
    }

    private List<ParameterEntity> generateOperationsMetadataParameter(List<OfferingProcedureFeatureSensorInfo> o) {
        List<ParameterEntity> parameterEntities = new ArrayList<>();
        ParameterEntity parameterCRS = new ParameterEntity();
        parameterCRS.setName("crs");
        parameterCRS.setAllowedValuesEntity(generateCRS(o));
        parameterEntities.add(parameterCRS);

        ParameterEntity parameterLanguage = new ParameterEntity();
        parameterLanguage.setName("language");
        parameterLanguage.setAllowedValuesEntity(generateLanguage());
        parameterEntities.add(parameterLanguage);

        ParameterEntity parameterService = new ParameterEntity();
        parameterService.setName("service");
        parameterService.setAllowedValuesEntity(generateService());
        parameterEntities.add(parameterService);
        return parameterEntities;


    }


    private OperationEntity generateOperateDescribeSensor(List<OfferingProcedureFeatureSensorInfo> o){
        OperationEntity describeSensorOperation = new OperationEntity();
        List<ParameterEntity> parameterEntities = new ArrayList<>();
        List<DCPEntity> dcpEntities = new ArrayList<>();
        describeSensorOperation.setName("DescribeSensor");
        DCPEntity dcpEntity = new DCPEntity();
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.setGetRequestEntityList(generateGetRequestEntity());
        dcpEntity.setHttpEntity(httpEntity);
        dcpEntities.add(dcpEntity);

        ParameterEntity parameter = new ParameterEntity();
        parameter.setName("procedure");
        parameter.setAllowedValuesEntity(generateProduceIdentifier(o));
        parameterEntities.add(parameter);

        ParameterEntity parameterDescriptionFormat = new ParameterEntity();
        parameterDescriptionFormat.setName("procedureDescriptionFormat");
        parameterDescriptionFormat.setAllowedValuesEntity(generateProcedureParameterDescriptionFormat(o));
        parameterEntities.add(parameterDescriptionFormat);

        ParameterEntity parameterValidTime = new ParameterEntity();
        parameterValidTime.setName("validTime");
        parameterEntities.add(parameterValidTime);

        describeSensorOperation.setEntity(dcpEntities);
        describeSensorOperation.setParameterEntity(parameterEntities);
        return describeSensorOperation;
    }

    private List<RequestEntity> generateGetRequestEntity(){
        List<RequestEntity> getRequestEntities = new ArrayList<>();
        DescribeSensorGetEnum[] enums = DescribeSensorGetEnum.values();
        for (DescribeSensorGetEnum e:enums){
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setHref(e.getHref());
            if (e.getConstraintName()!=null){
                AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
                allowedValuesEntity.setValueArray(Collections.unmodifiableList(Arrays.asList(e.getAllowedValue())));
                List<ConstraintEntity> constraintEntities = new ArrayList<>();
                ConstraintEntity constraintEntity = new ConstraintEntity();
                constraintEntity.setName(e.getConstraintName());
                constraintEntity.setAllowedValuesEntity(allowedValuesEntity);
                constraintEntities.add(constraintEntity);
                requestEntity.setConstraintEntity(constraintEntities);
            }
            getRequestEntities.add(requestEntity);
        }
        return getRequestEntities;
    }


    private AllowedValuesEntity generateProduceIdentifier(List<OfferingProcedureFeatureSensorInfo> o){
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(o.stream().map(OfferingProcedureFeatureSensorInfo::getProcedureIdentifier).distinct().collect(Collectors.toList()));
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateProcedureParameterDescriptionFormat(List<OfferingProcedureFeatureSensorInfo> o){
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(o.stream().map(OfferingProcedureFeatureSensorInfo::getProcedureDescriptionFormat).distinct().collect(Collectors.toList()));
        return allowedValuesEntity;
    }

    private OperationEntity generateOperateGetCapabilities(){
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setName("GetCapabilities");
        List<DCPEntity> dcpEntities = new ArrayList<>();
        DCPEntity dcpEntity = new DCPEntity();
        HttpEntity httpEntity = new HttpEntity();
        List<RequestEntity> postRequestEntities = new ArrayList<>();
        List<ParameterEntity> parameterEntities = new ArrayList<>();
        GetCapabilitiesPostEnum[] enums = GetCapabilitiesPostEnum.values();
        for (GetCapabilitiesPostEnum e:enums){
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setHref(e.getHref());
            if (e.getConstraintName()!=null){
                AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
                allowedValuesEntity.setValueArray(e.getAllowedValue());
                List<ConstraintEntity> constraintEntities = new ArrayList<>();
                ConstraintEntity constraintEntity = new ConstraintEntity();
                constraintEntity.setName(e.getConstraintName());
                constraintEntity.setAllowedValuesEntity(allowedValuesEntity);
                constraintEntities.add(constraintEntity);
                requestEntity.setConstraintEntity(constraintEntities);
            }
            postRequestEntities.add(requestEntity);
        }
        httpEntity.setPostRequestEntityList(postRequestEntities);
        dcpEntity.setHttpEntity(httpEntity);
        dcpEntities.add(dcpEntity);


        ParameterEntity parameterAcceptFormats = new ParameterEntity();
        parameterAcceptFormats.setName("AcceptFormats");
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("application/xml"))));
        parameterAcceptFormats.setAllowedValuesEntity(allowedValuesEntity);
        parameterEntities.add(parameterAcceptFormats);

        ParameterEntity parameterAcceptVersions = new ParameterEntity();
        parameterAcceptVersions.setName("AcceptVersions");
        allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("2.0.0"))));
        parameterAcceptVersions.setAllowedValuesEntity(allowedValuesEntity);
        parameterEntities.add(parameterAcceptVersions);

        ParameterEntity parameterSections = new ParameterEntity();
        parameterSections.setName("Sections");
        allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("All","Contents","OperationsMetadata","ServiceIdentification","ServiceProvider"))));
        parameterSections.setAllowedValuesEntity(allowedValuesEntity);
        parameterEntities.add(parameterSections);

        operationEntity.setParameterEntity(parameterEntities);
        operationEntity.setEntity(dcpEntities);

        return operationEntity;
    }

    private OperationEntity generateOperateGetObservation(List<OfferingProcedureFeatureSensorInfo> o) {
        OperationEntity operation = new OperationEntity();
        operation.setName("GetObservation");
        List<DCPEntity> dcpEntities = new ArrayList<>();
        DCPEntity dcpEntity = new DCPEntity();
        HttpEntity httpEntity = new HttpEntity();
        List<RequestEntity> postRequestEntities = new ArrayList<>();
        List<ParameterEntity> parameterEntities = new ArrayList<>();
        GetObservationGetEnum[] enums = GetObservationGetEnum.values();
        for (GetObservationGetEnum e:enums){
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setHref(e.getHref());
            if (e.getConstraintName()!=null){
                AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
                allowedValuesEntity.setValueArray(new ArrayList<>(Collections.unmodifiableList(Arrays.asList(e.getAllowedValue()))));
                List<ConstraintEntity> constraintEntities = new ArrayList<>();
                ConstraintEntity constraintEntity = new ConstraintEntity();
                constraintEntity.setName(e.getConstraintName());
                constraintEntity.setAllowedValuesEntity(allowedValuesEntity);
                constraintEntities.add(constraintEntity);
                requestEntity.setConstraintEntity(constraintEntities);
            }
            postRequestEntities.add(requestEntity);
        }
        httpEntity.setGetRequestEntityList(postRequestEntities);
        dcpEntity.setHttpEntity(httpEntity);
        dcpEntities.add(dcpEntity);

        ParameterEntity parameterFeatureOfInterest = new ParameterEntity();
        parameterFeatureOfInterest.setName("featureOfInterest");
        parameterFeatureOfInterest.setAllowedValuesEntity(generateFeatureOfInterestIdentifier(o));
        parameterEntities.add(parameterFeatureOfInterest);


        ParameterEntity parameterObservedProperty = new ParameterEntity();
        parameterObservedProperty.setName("observedProperty");
        parameterObservedProperty.setAllowedValuesEntity(generateObservedProperty(o));
        parameterEntities.add(parameterObservedProperty);

        ParameterEntity parameterOffering = new ParameterEntity();
        parameterOffering.setName("offering");
        parameterOffering.setAllowedValuesEntity(generateOfferingIdentifier(o));
        parameterEntities.add(parameterOffering);

        ParameterEntity parameterProduce = new ParameterEntity();
        parameterProduce.setName("procedure");
        parameterProduce.setAllowedValuesEntity(generateProduceIdentifier(o));
        parameterEntities.add(parameterProduce);

        ParameterEntity parameterResponseFormat = new ParameterEntity();
        parameterResponseFormat.setName("responseFormat");
        parameterResponseFormat.setAllowedValuesEntity(generateResponseFormat(o));
        parameterEntities.add(parameterResponseFormat);

        ParameterEntity parameterSpatialFilter = new ParameterEntity();
        parameterSpatialFilter.setName("spatialFilter");
        parameterSpatialFilter.setAllowedValuesEntity(generateSpatialFilterRange(o));
        parameterEntities.add(parameterSpatialFilter);

        ParameterEntity parameterTemporalFilter = new ParameterEntity();
        parameterTemporalFilter.setName("temporalFilter");
        parameterTemporalFilter.setAllowedValuesEntity(generateTemporalFilterRange(o));
        parameterEntities.add(parameterTemporalFilter);

        operation.setEntity(dcpEntities);
        operation.setParameterEntity(parameterEntities);

        return operation;
    }

    private AllowedValuesEntity generateObservedProperty(List<OfferingProcedureFeatureSensorInfo> o) {
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        List<SensorInfo> sensorInfos = o.stream().map(SensorInfo::new).collect(Collectors.toList());
        List<String> metrics = LocationHandle.metricNameHandle(sensorInfos);
        allowedValuesEntity.setValueArray(metrics.stream().distinct().collect(Collectors.toList()));
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateService() {
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("SOS"))));
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateLanguage() {
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(new ArrayList<>(Collections.unmodifiableList(Arrays.asList("chinese","eng"))));
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateCRS(List<OfferingProcedureFeatureSensorInfo> o) {
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        List<String> csrList = new ArrayList<>();
        List<Integer> crs = o.stream().map(OfferingProcedureFeatureSensorInfo::getFeatureSRID).distinct().collect(Collectors.toList());
        for (Integer i : crs){
            csrList.add("http://www.opengis.net/def/crs/EPSG/0/"+i);
        }
        allowedValuesEntity.setValueArray(csrList);
        return allowedValuesEntity;
    }




    private AllowedValuesEntity generateTemporalFilterRange(List<OfferingProcedureFeatureSensorInfo> o) {
        Long maxTime = o.stream().max(Comparator.comparing(OfferingProcedureFeatureSensorInfo::getOfferingResultTimeEnd)).get().getOfferingResultTimeEnd();
        Long minTime = o.stream().min(Comparator.comparing(OfferingProcedureFeatureSensorInfo::getOfferingResultTimeStart)).get().getOfferingResultTimeStart();
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        ValueRangeEntity valueRangeEntity = new ValueRangeEntity();
        valueRangeEntity.setMinValue(DateUtils.convertToISO8601(minTime*1000));
        valueRangeEntity.setMaxValue(DateUtils.convertToISO8601(maxTime*1000));
        List<ValueRangeEntity> valueRangeEntities = new ArrayList<>();
        valueRangeEntities.add(valueRangeEntity);
        allowedValuesEntity.setValueRangeEntity(valueRangeEntities);
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateSpatialFilterRange(List<OfferingProcedureFeatureSensorInfo> o)  {
        List<Envelope> envelopes = new ArrayList<>();
        try {
            for (OfferingProcedureFeatureSensorInfo offeringProcedureFeature : o) {
                WKTReader reader = new WKTReader();
                Geometry geometry = reader.read(offeringProcedureFeature.getFeatureArea());
                geometry.setSRID(offeringProcedureFeature.getFeatureSRID());
                envelopes.add(geometry.getEnvelopeInternal());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        if (envelopes.size()>0) {
            AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
            double minX = envelopes.get(0).getMinX();
            double minY = envelopes.get(0).getMinY();
            double maxX = envelopes.get(0).getMaxX();
            double maxY = envelopes.get(0).getMaxY();
            for (Envelope envelope : envelopes) {
                if (envelope.getMinX() < minX) {
                    minX = envelope.getMinX();
                }
                if (envelope.getMaxX() > maxX) {
                    maxX = envelope.getMaxX();
                }
                if (envelope.getMinY() < minY) {
                    minY = envelope.getMinY();
                }
                if (envelope.getMaxY() > maxY) {
                    maxY = envelope.getMaxY();
                }
            }
            List<ValueRangeEntity> valueRangeEntities = new ArrayList<>();
            ValueRangeEntity valueRangeEntity = new ValueRangeEntity();
            valueRangeEntity.setMaxValue(maxX + " " + maxY);
            valueRangeEntity.setMinValue(minX + " " + minY);
            valueRangeEntities.add(valueRangeEntity);
            allowedValuesEntity.setValueRangeEntity(valueRangeEntities);
            return allowedValuesEntity;
        }else {
            throw new RuntimeException();
        }
    }

    private AllowedValuesEntity generateResponseFormat(List<OfferingProcedureFeatureSensorInfo> o) {
        List<String> target = new ArrayList<>();
        target.add("application/json");
        target.add("application/xml");
        target.addAll(o.stream().map(OfferingProcedureFeatureSensorInfo::getProcedureDescriptionFormat).distinct().collect(Collectors.toList()));
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(target);
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateOfferingIdentifier(List<OfferingProcedureFeatureSensorInfo> o) {
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(o.stream().map(OfferingProcedureFeatureSensorInfo::getOfferingIdentifier).distinct().collect(Collectors.toList()));
        return allowedValuesEntity;
    }

    private AllowedValuesEntity generateFeatureOfInterestIdentifier(List<OfferingProcedureFeatureSensorInfo> o) {
        AllowedValuesEntity allowedValuesEntity = new AllowedValuesEntity();
        allowedValuesEntity.setValueArray(o.stream().map(OfferingProcedureFeatureSensorInfo::getFeatureIdentifier).distinct().collect(Collectors.toList()));
        return allowedValuesEntity;
    }

}
