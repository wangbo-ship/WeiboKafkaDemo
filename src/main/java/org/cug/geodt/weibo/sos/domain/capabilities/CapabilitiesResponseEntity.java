package org.cug.geodt.weibo.sos.domain.capabilities;


/**
 * Author WJW
 * Date 2023/6/7 9:20
 */


public class CapabilitiesResponseEntity {
    private ServiceIdentificationEntity serviceIdentificationEntity;
    private ServiceProviderEntity serviceProviderEntity;
    private OperationsMetadataEntity operationMetadataEntity;
    private ContentsEntity contentsEntity;
    private FilterCapabilitiesEntity filterCapabilitiesEntity;
    private String updateSequence;
    private String version;

    public CapabilitiesResponseEntity() {
    }

    public CapabilitiesResponseEntity(ServiceIdentificationEntity serviceIdentificationEntity, ServiceProviderEntity serviceProviderEntity, OperationsMetadataEntity operationMetadataEntity, ContentsEntity contentsEntity, FilterCapabilitiesEntity filterCapabilitiesEntity, String updateSequence, String version) {
        this.serviceIdentificationEntity = serviceIdentificationEntity;
        this.serviceProviderEntity = serviceProviderEntity;
        this.operationMetadataEntity = operationMetadataEntity;
        this.contentsEntity = contentsEntity;
        this.filterCapabilitiesEntity = filterCapabilitiesEntity;
        this.updateSequence = updateSequence;
        this.version = version;
    }

    public ServiceIdentificationEntity getServiceIdentificationEntity() {
        return serviceIdentificationEntity;
    }

    public void setServiceIdentificationEntity(ServiceIdentificationEntity serviceIdentificationEntity) {
        this.serviceIdentificationEntity = serviceIdentificationEntity;
    }

    public ServiceProviderEntity getServiceProviderEntity() {
        return serviceProviderEntity;
    }

    public void setServiceProviderEntity(ServiceProviderEntity serviceProviderEntity) {
        this.serviceProviderEntity = serviceProviderEntity;
    }

    public OperationsMetadataEntity getOperationMetadataEntity() {
        return operationMetadataEntity;
    }

    public void setOperationMetadataEntity(OperationsMetadataEntity operationMetadataEntity) {
        this.operationMetadataEntity = operationMetadataEntity;
    }

    public ContentsEntity getContentsEntity() {
        return contentsEntity;
    }

    public void setContentsEntity(ContentsEntity contentsEntity) {
        this.contentsEntity = contentsEntity;
    }

    public FilterCapabilitiesEntity getFilterCapabilitiesEntity() {
        return filterCapabilitiesEntity;
    }

    public void setFilterCapabilitiesEntity(FilterCapabilitiesEntity filterCapabilitiesEntity) {
        this.filterCapabilitiesEntity = filterCapabilitiesEntity;
    }

    public String getUpdateSequence() {
        return updateSequence;
    }

    public void setUpdateSequence(String updateSequence) {
        this.updateSequence = updateSequence;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
