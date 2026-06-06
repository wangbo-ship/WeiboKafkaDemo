package org.cug.geodt.weibo.sos.domain.describeSensor;


import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 21:27
 */
@Repository
public class DescribeSensorResponseEntity {
    private String Id;
    private String description;
    private String identifier;
    private List<KeyWordEntity>  keyWordEntityList;
    private List<IdentifierEntity> identifierEntityList;
    private List<ClassifierEntity> classifierEntityList;
    private List<ValidTimeEntity> validTimeEntity;
    private List<CharacteristicsEntity> characteristicsEntity;
    private List<CapabilitiesEntity> capabilitiesEntity;
    private List<ContactsEntity> contactsEntity;
    private List<DocumentsEntity> documentsEntity;
    private List<HistoryEntity> historyEntity;
    private List<PositionEntity> positionEntityList;
    private InputsEntity inputEntities;
    private OutputEntity outputEntities;
    private ParametersEntity parameterEntity;
    private List<SensorDescriptionEntity> sensorDescriptionEntityList; //1.0

    public DescribeSensorResponseEntity() {
    }

    public DescribeSensorResponseEntity(String id, String description, String identifier, List<KeyWordEntity> keyWordEntityList, List<IdentifierEntity> identifierEntityList, List<ClassifierEntity> classifierEntityList, List<ValidTimeEntity> validTimeEntity, List<CharacteristicsEntity> characteristicsEntity, List<CapabilitiesEntity> capabilitiesEntity, List<ContactsEntity> contactsEntity, List<DocumentsEntity> documentsEntity, List<HistoryEntity> historyEntity, List<PositionEntity> positionEntityList, InputsEntity inputEntities, OutputEntity outputEntities, ParametersEntity parameterEntity, List<SensorDescriptionEntity> sensorDescriptionEntityList) {
        Id = id;
        this.description = description;
        this.identifier = identifier;
        this.keyWordEntityList = keyWordEntityList;
        this.identifierEntityList = identifierEntityList;
        this.classifierEntityList = classifierEntityList;
        this.validTimeEntity = validTimeEntity;
        this.characteristicsEntity = characteristicsEntity;
        this.capabilitiesEntity = capabilitiesEntity;
        this.contactsEntity = contactsEntity;
        this.documentsEntity = documentsEntity;
        this.historyEntity = historyEntity;
        this.positionEntityList = positionEntityList;
        this.inputEntities = inputEntities;
        this.outputEntities = outputEntities;
        this.parameterEntity = parameterEntity;
        this.sensorDescriptionEntityList = sensorDescriptionEntityList;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<KeyWordEntity> getKeyWordEntityList() {
        return keyWordEntityList;
    }

    public void setKeyWordEntityList(List<KeyWordEntity> keyWordEntityList) {
        this.keyWordEntityList = keyWordEntityList;
    }

    public List<IdentifierEntity> getIdentifierEntityList() {
        return identifierEntityList;
    }

    public void setIdentifierEntityList(List<IdentifierEntity> identifierEntityList) {
        this.identifierEntityList = identifierEntityList;
    }

    public List<ClassifierEntity> getClassifierEntityList() {
        return classifierEntityList;
    }

    public void setClassifierEntityList(List<ClassifierEntity> classifierEntityList) {
        this.classifierEntityList = classifierEntityList;
    }

    public List<ValidTimeEntity> getValidTimeEntity() {
        return validTimeEntity;
    }

    public void setValidTimeEntity(List<ValidTimeEntity> validTimeEntity) {
        this.validTimeEntity = validTimeEntity;
    }

    public List<CharacteristicsEntity> getCharacteristicsEntity() {
        return characteristicsEntity;
    }

    public void setCharacteristicsEntity(List<CharacteristicsEntity> characteristicsEntity) {
        this.characteristicsEntity = characteristicsEntity;
    }

    public List<CapabilitiesEntity> getCapabilitiesEntity() {
        return capabilitiesEntity;
    }

    public void setCapabilitiesEntity(List<CapabilitiesEntity> capabilitiesEntity) {
        this.capabilitiesEntity = capabilitiesEntity;
    }

    public List<ContactsEntity> getContactsEntity() {
        return contactsEntity;
    }

    public void setContactsEntity(List<ContactsEntity> contactsEntity) {
        this.contactsEntity = contactsEntity;
    }

    public List<DocumentsEntity> getDocumentsEntity() {
        return documentsEntity;
    }

    public void setDocumentsEntity(List<DocumentsEntity> documentsEntity) {
        this.documentsEntity = documentsEntity;
    }

    public List<HistoryEntity> getHistoryEntity() {
        return historyEntity;
    }

    public void setHistoryEntity(List<HistoryEntity> historyEntity) {
        this.historyEntity = historyEntity;
    }

    public List<PositionEntity> getPositionEntityList() {
        return positionEntityList;
    }

    public void setPositionEntityList(List<PositionEntity> positionEntityList) {
        this.positionEntityList = positionEntityList;
    }

    public InputsEntity getInputEntities() {
        return inputEntities;
    }

    public void setInputEntities(InputsEntity inputEntities) {
        this.inputEntities = inputEntities;
    }

    public OutputEntity getOutputEntities() {
        return outputEntities;
    }

    public void setOutputEntities(OutputEntity outputEntities) {
        this.outputEntities = outputEntities;
    }

    public ParametersEntity getParameterEntity() {
        return parameterEntity;
    }

    public void setParameterEntity(ParametersEntity parameterEntity) {
        this.parameterEntity = parameterEntity;
    }

    public List<SensorDescriptionEntity> getSensorDescriptionEntityList() {
        return sensorDescriptionEntityList;
    }

    public void setSensorDescriptionEntityList(List<SensorDescriptionEntity> sensorDescriptionEntityList) {
        this.sensorDescriptionEntityList = sensorDescriptionEntityList;
    }
}
