package org.cug.geodt.weibo.sos.codec.decode;

import net.opengis.gml.x32.TimeInstantType;
import net.opengis.gml.x32.TimePeriodType;
import net.opengis.sensorml.x20.*;
import net.opengis.swe.x20.AbstractDataComponentType;
import net.opengis.swe.x20.QuantityType;
import net.opengis.swe.x20.TextType;
import net.opengis.swe.x20.VectorType;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.domain.capabilities.ContactInfoEntity;
import org.cug.geodt.weibo.sos.domain.capabilities.PhoneEntity;
import org.cug.geodt.weibo.sos.domain.describeSensor.*;
import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;
import org.isotc211.x2005.gco.CharacterStringPropertyType;
import org.isotc211.x2005.gmd.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName SensorMLDecode
 * @Author WJW
 * @Date 2023/7/20 10:10
 * @Description sensorML解码器 从顶层传入sensorML的xml编码，输出DescribeSensorResponseEntity对象。
 */

public class SensorMLDecode {

    public static DescribeSensorResponseEntity convert(String xml) throws XmlException, IOException {
        DescribeSensorResponseEntity describeSensorResponseEntity = new DescribeSensorResponseEntity();
        PhysicalComponentDocument physicalComponentDocument = PhysicalComponentDocument.Factory.parse(xml);
        PhysicalComponentType physicalComponent = physicalComponentDocument.getPhysicalComponent();
        if (physicalComponent.getIdentifier() != null && physicalComponent.getIdentifier().getStringValue() != null) {
            String identifier = physicalComponent.getIdentifier().getStringValue();
            describeSensorResponseEntity.setIdentifier(identifier);
        }
        if (physicalComponent.getDescription() != null ) {
            String stringValue = physicalComponent.getDescription().getStringValue();
            describeSensorResponseEntity.setDescription(stringValue);
            System.out.println(stringValue);
        }
        if (physicalComponent.getIdentificationArray() != null) {
            List<IdentifierEntity> identifierEntityList = convert(physicalComponent.getIdentificationArray());
            describeSensorResponseEntity.setIdentifierEntityList(identifierEntityList);
        }
        if (physicalComponent.getCharacteristicsArray() != null) {
            List<CharacteristicsEntity> characteristicsEntityList = convert(physicalComponent.getCharacteristicsArray());
            describeSensorResponseEntity.setCharacteristicsEntity(characteristicsEntityList);
        }
        if (physicalComponent.getCapabilitiesArray() != null) {
            List<CapabilitiesEntity> capabilitiesEntityList = convert(physicalComponent.getCapabilitiesArray());
            describeSensorResponseEntity.setCapabilitiesEntity(capabilitiesEntityList);
        }
        if (physicalComponent.getContactsArray() != null) {
            List<ContactsEntity> contactsEntityList = convert(physicalComponent.getContactsArray());
            describeSensorResponseEntity.setContactsEntity(contactsEntityList);
        }
        if (physicalComponent.getHistoryArray() != null) {
            List<HistoryEntity> historyEntityList = convert(physicalComponent.getHistoryArray());
            describeSensorResponseEntity.setHistoryEntity(historyEntityList);
        }
        if (physicalComponent.getParameters() != null) {
            ParametersEntity parametersEntity = convert(physicalComponent.getParameters());
            describeSensorResponseEntity.setParameterEntity(parametersEntity);
        }
        if (physicalComponent.getPositionArray() != null) {
            List<PositionEntity> positionEntityList = convert(physicalComponent.getPositionArray());
            describeSensorResponseEntity.setPositionEntityList(positionEntityList);
        }
        if (physicalComponent.getInputs() != null) {
            InputsEntity inputsEntity = convert(physicalComponent.getInputs());
            describeSensorResponseEntity.setInputEntities(inputsEntity);
        }
        if (physicalComponent.getOutputs() != null) {
            OutputEntity outputEntity = convert(physicalComponent.getOutputs());
            describeSensorResponseEntity.setOutputEntities(outputEntity);
        }
        return describeSensorResponseEntity;
    }


    public static List<IdentifierEntity> convert(IdentifierListPropertyType[] identifierListPropertyTypes) {
        List<IdentifierEntity> identifierEntityList = new ArrayList<>();
        for (IdentifierListPropertyType identifierListPropertyType : identifierListPropertyTypes) {
            IdentifierEntity identifierEntity = new IdentifierEntity();
            if (identifierListPropertyType.getIdentifierList() != null) {
                List<TermEntity> termEntityList = convert(identifierListPropertyType.getIdentifierList());
                identifierEntity.setTermEntityList(termEntityList);
            }
            identifierEntityList.add(identifierEntity);
        }
        return identifierEntityList;
    }

    public static List<TermEntity> convert(IdentifierListType identifierList) {
        List<TermEntity> termEntityList = new ArrayList<>();
        for (IdentifierListType.Identifier identifier : identifierList.getIdentifier2Array()) {
            if (identifier.getTerm() != null) {
                TermEntity termEntity = convert(identifier.getTerm());
                termEntityList.add(termEntity);
            }
        }
        return termEntityList;
    }

    public static TermEntity convert(TermType term) {
        TermEntity termEntity = new TermEntity();
        if (term.getDefinition() != null) {
            termEntity.setDefinition(term.getDefinition());
        }
        if (term.getLabel() != null) {
            termEntity.setLabel(term.getLabel());
        }
        if (term.getValue() != null) {
            termEntity.setValue(term.getValue());
        }
        return termEntity;
    }

    public static List<CharacteristicsEntity> convert(DescribedObjectType.Characteristics[] characteristicsArray) {
        List<CharacteristicsEntity> characteristicsEntityList= new ArrayList<>();
        for (DescribedObjectType.Characteristics characteristics : characteristicsArray) {
            CharacteristicsEntity characteristicsEntity = new CharacteristicsEntity();
            if (characteristics.getName() != null) {
                characteristicsEntity.setName(characteristics.getName());
            }
            if (characteristics.getCharacteristicList() != null) {
                List<CharacteristicEntity> characteristicEntityList = convert(characteristics.getCharacteristicList());
                characteristicsEntity.setCharacteristicEntityList(characteristicEntityList);
            }
            characteristicsEntityList.add(characteristicsEntity);
        }
        return characteristicsEntityList;
    }

    public static List<CharacteristicEntity> convert(CharacteristicListType characteristicList) {
        List<CharacteristicEntity> characteristicEntityList = new ArrayList<>();
        for (CharacteristicListType.Characteristic characteristic : characteristicList.getCharacteristicList()) {
            CharacteristicEntity characteristicEntity = new CharacteristicEntity();
            if (characteristic.getName() != null) {
                characteristicEntity.setName(characteristic.getName());
            }
            AbstractDataComponentType abstractDataComponent = characteristic.getAbstractDataComponent();
//
////            TextType textType = (TextType) abstractDataComponent;
//            String label1 = textType.getLabel();
//            String value = textType.getValue();
//
//            Class<? extends Node> aClass = abstractDataComponent.getDomNode().getClass();
//
////            XmlObject extensionArray = abstractDataComponent.getExtensionArray(0);

            if (abstractDataComponent instanceof TextType) {
                TextType textType = (TextType) abstractDataComponent;
                TextEntity textEntity = convert(textType);
                characteristicEntity.setTextEntity(textEntity);
            }
            characteristicEntityList.add(characteristicEntity);
        }
        return characteristicEntityList;
    }

    public static TextEntity convert(TextType textType) {
        TextEntity textEntity = new TextEntity();
        if (textType.getDefinition() != null) {
            textEntity.setDefinition(textType.getDefinition());
        }
        if (textType.getLabel() != null) {
            textEntity.setLabel(textType.getLabel());
        }
        if (textType.getValue() != null) {
            textEntity.setValue(textType.getValue());
        }
        return textEntity;
    }


    public static List<CapabilitiesEntity> convert(DescribedObjectType.Capabilities[] capabilitiesArray) {
        List<CapabilitiesEntity> capabilitiesEntityList = new ArrayList<>();
        for (DescribedObjectType.Capabilities capabilities : capabilitiesArray) {
            CapabilitiesEntity capabilitiesEntity = new CapabilitiesEntity();
            if (capabilities.getName() != null) {
                capabilitiesEntity.setName(capabilities.getName());
            }
            if (capabilities.getCapabilityList() != null) {
                List<CapabilityEntity> capabilityEntityList = convert(capabilities.getCapabilityList());
                capabilitiesEntity.setCapabilityEntity(capabilityEntityList);
            }
            capabilitiesEntityList.add(capabilitiesEntity);
        }
        return capabilitiesEntityList;
    }

    public static List<CapabilityEntity> convert(CapabilityListType capabilityList) {
        List<CapabilityEntity> capabilityEntityList = new ArrayList<>();
        if (capabilityList.getCapabilityArray() != null) {
            for (CapabilityListType.Capability capability : capabilityList.getCapabilityArray()) {
                CapabilityEntity capabilityEntity = convert(capability);
                capabilityEntityList.add(capabilityEntity);
            }
        }
        return capabilityEntityList;
    }

    private static CapabilityEntity convert(CapabilityListType.Capability capability) {
        CapabilityEntity capabilityEntity = new CapabilityEntity();
        if (capability.getName() != null) {
            capabilityEntity.setName(capability.getName());
        }
        if (capability.getAbstractDataComponent() instanceof QuantityType) {
            QuantityType quantityType = (QuantityType) capability.getAbstractDataComponent();
            QuantityEntity quantityEntity = convertQuantity(quantityType);
            capabilityEntity.setQuantityEntity(quantityEntity);
        }
        return capabilityEntity;
    }

    public static List<ContactsEntity> convert(ContactListPropertyType[] contactsArray) {
        List<ContactsEntity> contactsEntitiesList = new ArrayList<>();
        for (ContactListPropertyType contactListPropertyType : contactsArray) {
            ContactsEntity contactsEntity = new ContactsEntity();
            if (contactListPropertyType.getTitle() != null) {
                contactsEntity.setTitle(contactListPropertyType.getTitle());
            }
            if (contactListPropertyType.getContactList() != null) {
                List<ContactEntity> contactEntityList = convert(contactListPropertyType.getContactList());
                contactsEntity.setContactEntities(contactEntityList);
            }
            contactsEntitiesList.add(contactsEntity);
        }
        return contactsEntitiesList;
    }

    public static List<ContactEntity> convert(ContactListType contactList) {
        List<ContactEntity> contactEntityList = new ArrayList<>();
        if (contactList.getContactArray() != null) {
            for (CIResponsiblePartyPropertyType ciResponsiblePartyPropertyType : contactList.getContactArray()) {
                ContactEntity contactEntity = new ContactEntity();
                if (ciResponsiblePartyPropertyType.getTitle() != null) {
                    contactEntity.setTitle(ciResponsiblePartyPropertyType.getTitle());
                }
                if (ciResponsiblePartyPropertyType.getCIResponsibleParty() != null) {
                    if (ciResponsiblePartyPropertyType.getCIResponsibleParty().getContactInfo() != null ) {
                        ContactInfoEntity contactInfoEntity = convert(ciResponsiblePartyPropertyType.getCIResponsibleParty().getContactInfo());
                        contactEntity.setContactInfoEntity(contactInfoEntity);
                    }
                    if (ciResponsiblePartyPropertyType.getCIResponsibleParty().getIndividualName() != null) {
                        IndividualNameEntity individualNameEntity = convert(ciResponsiblePartyPropertyType.getCIResponsibleParty().getIndividualName());
                        contactEntity.setIndividualNameEntity(individualNameEntity);
                    }
                    if (ciResponsiblePartyPropertyType.getCIResponsibleParty().getOrganisationName() != null) {
                        OrganisationNameEntity organisationNameEntity = convertOrganisationName(ciResponsiblePartyPropertyType.getCIResponsibleParty().getOrganisationName());
                        contactEntity.setOrganisationNameEntity(organisationNameEntity);
                    }
                    if (ciResponsiblePartyPropertyType.getCIResponsibleParty().getPositionName() != null) {
                        PositionNameEntity positionNameEntity = convertPositionName(ciResponsiblePartyPropertyType.getCIResponsibleParty().getPositionName());
                        contactEntity.setPositionNameEntity(positionNameEntity);
                    }
                    if (ciResponsiblePartyPropertyType.getCIResponsibleParty().getRole() != null) {
                        RoleEntity roleEntity = convert(ciResponsiblePartyPropertyType.getCIResponsibleParty().getRole());
                        contactEntity.setRoleEntity(roleEntity);
                    }
                    contactEntityList.add(contactEntity);
                }

            }
        }
        return contactEntityList;
    }

    public static RoleEntity convert(CIRoleCodePropertyType role) {
        RoleEntity roleEntity = new RoleEntity();
        if (role.getCIRoleCode() != null) {
            if (role.getCIRoleCode().getCodeList() != null) {
                roleEntity.setCodeList(role.getCIRoleCode().getCodeList());
            }
            if (role.getCIRoleCode().getCodeListValue() != null) {
                roleEntity.setCodeListValue(role.getCIRoleCode().getCodeListValue());
            }
            if (role.getCIRoleCode().getStringValue() != null) {
                roleEntity.setCodeListValue(role.getCIRoleCode().getStringValue());
            }
        }
        return roleEntity;
    }

    public static PositionNameEntity convertPositionName(CharacterStringPropertyType positionName) {
        PositionNameEntity positionNameEntity = new PositionNameEntity();
        if (positionName.getCharacterString() != null) {
            positionNameEntity.setName(positionName.getCharacterString());
        }
        return positionNameEntity;
    }

    public static OrganisationNameEntity convertOrganisationName(CharacterStringPropertyType organisationName) {
        OrganisationNameEntity organisationNameEntity = new OrganisationNameEntity();
        if (organisationName.getCharacterString() != null) {
            organisationNameEntity.setOrganisationName(organisationName.getCharacterString());
        }
        return organisationNameEntity;
    }

    public static IndividualNameEntity convert(CharacterStringPropertyType individualName) {
        IndividualNameEntity individualNameEntity = new IndividualNameEntity();
        if (individualName.getCharacterString() != null) {
            individualNameEntity.setIndividualName(individualName.getCharacterString());
        }
        return individualNameEntity;
    }

    public static ContactInfoEntity convert(CIContactPropertyType contactInfo) {
        ContactInfoEntity contactInfoEntity = new ContactInfoEntity();
        if (contactInfo.getCIContact() != null) {
            if (contactInfo.getCIContact().getAddress() != null) {
                AddressEntity addressEntity = convert(contactInfo.getCIContact().getAddress());
                contactInfoEntity.setAddressEntity(addressEntity);
            }
            if (contactInfo.getCIContact().getPhone() != null) {
                PhoneEntity phoneEntity = convert(contactInfo.getCIContact().getPhone());
                contactInfoEntity.setPhoneEntity(phoneEntity);
            }
        }
        return contactInfoEntity;
    }

    public static PhoneEntity convert(CITelephonePropertyType phone) {
        PhoneEntity phoneEntity = new PhoneEntity();
        ArrayList<String> voiceList = new ArrayList<>();
        ArrayList<String> facsimileList = new ArrayList<>();
        if (phone.getCITelephone() != null) {
            if (phone.getCITelephone().getVoiceList() != null) {
                for (CharacterStringPropertyType characterStringPropertyType : phone.getCITelephone().getVoiceList()) {
                    if (characterStringPropertyType.getCharacterString() != null) {
                        String characterString = characterStringPropertyType.getCharacterString();
                        voiceList.add(characterString);
                    }
                    phoneEntity.setVoiceList(voiceList);
                }
            }
            if (phone.getCITelephone().getFacsimileArray() != null) {
                for (CharacterStringPropertyType characterStringPropertyType : phone.getCITelephone().getFacsimileArray()) {
                    if (characterStringPropertyType.getCharacterString() != null) {
                        String characterString = characterStringPropertyType.getCharacterString();
                        facsimileList.add(characterString);
                    }
                    phoneEntity.setFacsimile(facsimileList);
                }
            }
        }
        return phoneEntity;
    }

    public static AddressEntity convert(CIAddressPropertyType address) {
        AddressEntity addressEntity = new AddressEntity();
        if (address.getCIAddress() != null) {
            if (address.getCIAddress().getCity() != null) {
                if (address.getCIAddress().getCity().getCharacterString() != null) {
                    addressEntity.setCity(address.getCIAddress().getCity().getCharacterString());
                }
            }
            if (address.getCIAddress().getCountry() != null) {
                if (address.getCIAddress().getCountry().getCharacterString() != null) {
                    addressEntity.setCountry(address.getCIAddress().getCountry().getCharacterString());
                }
            }
            if (address.getCIAddress().getPostalCode() != null) {
                if (address.getCIAddress().getPostalCode().getCharacterString() != null) {
                    addressEntity.setPostalCode(address.getCIAddress().getPostalCode().getCharacterString());
                }
            }
            if (address.getCIAddress().getElectronicMailAddressArray() != null) {
                List<String> emailList = convert(address.getCIAddress().getElectronicMailAddressArray());
                addressEntity.setEmail(emailList);
            }
            if(address.getCIAddress().getAdministrativeArea() != null){
                if(address.getCIAddress().getAdministrativeArea().getCharacterString() != null){
                    addressEntity.setAdministrativeArea(address.getCIAddress().getAdministrativeArea().getCharacterString());
                }
            }
            if(address.getCIAddress().getDeliveryPointArray() != null){
                List<String> deliveryPointList = convert(address.getCIAddress().getDeliveryPointArray());
                addressEntity.setDeliveryPoint(deliveryPointList);
            }
        }
        return addressEntity;
    }

    private static List<String> convert(CharacterStringPropertyType[] electronicMailAddressArray) {
        ArrayList<String> emailList = new ArrayList<>();
        for (CharacterStringPropertyType characterStringPropertyType : electronicMailAddressArray) {
            if (characterStringPropertyType.getCharacterString() != null) {
                emailList.add(characterStringPropertyType.getCharacterString());
            }
        }
        return emailList;
    }


    public static List<HistoryEntity> convert(EventListPropertyType[] historyArray) {
        List<HistoryEntity> historyEntityList = new ArrayList<>();
        for (EventListPropertyType eventListPropertyType : historyArray) {
            HistoryEntity historyEntity = new HistoryEntity();
            if (eventListPropertyType.getEventList() != null) {
                List<EventEntity> eventEntityList = convert(eventListPropertyType.getEventList());
                historyEntity.setEventEntityList(eventEntityList);
            }
            historyEntityList.add(historyEntity);
        }
        return historyEntityList;
    }

    public static List<EventEntity> convert(EventListType eventList) {
        List<EventEntity> eventEntityList = new ArrayList<>();
        if (eventList.getEventArray() != null) {
            for (EventPropertyType eventPropertyType : eventList.getEventArray()) {
                EventEntity eventEntity = new EventEntity();
                if (eventPropertyType.getEvent() != null) {
                    if (eventPropertyType.getEvent().getLabel() != null) {
                        eventEntity.setLabel(eventPropertyType.getEvent().getLabel());
                    }
                    if (eventPropertyType.getEvent().getDocumentationArray() != null) {
                        List<DocumentsEntity> documentsEntityList = convert(eventPropertyType.getEvent().getDocumentationArray());
                        eventEntity.setDocumentEntity(documentsEntityList);
                    }
                    if (eventPropertyType.getEvent().getTime() != null) {
                        TimeEntity timeEntity = convert(eventPropertyType.getEvent().getTime());
                        eventEntity.setTimeEntity(timeEntity);
                    }
                }
                eventEntityList.add(eventEntity);
            }
        }
        return eventEntityList;
    }

    public static TimeEntity convert(EventType.Time time) {
        TimeEntity timeEntity = new TimeEntity();
        if (time.getTimeInstant() != null) {
            TimeInstantEntity timeInstantEntity = convert(time.getTimeInstant());
            timeEntity.setTimeInstantEntity(timeInstantEntity);
        }
        if (time.getTimePeriod() != null) {
            TimePeriodEntity timePeriodEntity = convertTimePeriod(time.getTimePeriod());
            timeEntity.setTimePeriodEntity(timePeriodEntity);
        }
        return timeEntity;
    }

    public static TimePeriodEntity convertTimePeriod(TimePeriodType timePeriod) {
        TimePeriodEntity timePeriodEntity = new TimePeriodEntity();
        if (timePeriod.getBeginPosition() != null) {
            if (timePeriod.getBeginPosition().getStringValue() != null) {
                timePeriodEntity.setBeginTime(timePeriod.getBeginPosition().getStringValue());
            }
        }
        if (timePeriod.getEndPosition() != null) {
            if (timePeriod.getEndPosition().getStringValue() != null) {
                timePeriodEntity.setEndTime(timePeriod.getEndPosition().getStringValue());
            }
        }
        return timePeriodEntity;
    }

    public static TimeInstantEntity convert(TimeInstantType timeInstant) {
        TimeInstantEntity timeInstantEntity = new TimeInstantEntity();
        if (timeInstant.getTimePosition() != null && timeInstant.getTimePosition().getStringValue() != null) {
            timeInstantEntity.setTimePosition(timeInstant.getTimePosition().getStringValue());
        }
        if (timeInstant.getId() != null) {
            timeInstantEntity.setGmlId(timeInstant.getId());
        }
        return timeInstantEntity;
    }

    public static List<DocumentsEntity> convert(DocumentListPropertyType[] documentationArray) {
        List<DocumentsEntity> documentsEntityList = new ArrayList<>();
        for (DocumentListPropertyType documentListPropertyType : documentationArray) {
            DocumentsEntity documentsEntity = new DocumentsEntity();
            if (documentListPropertyType.getDocumentList() != null) {
                List<DocumentEntity> documentEntityList = convert(documentListPropertyType.getDocumentList());
                documentsEntity.setDocumentEntity(documentEntityList);
            }
            documentsEntityList.add(documentsEntity);
        }
        return documentsEntityList;
    }

    public static List<DocumentEntity> convert(DocumentListType documentList) {
        List<DocumentEntity> documentEntityList = new ArrayList<>();
        if (documentList.getDocumentList() != null) {
            for (CIOnlineResourcePropertyType ciOnlineResourcePropertyType : documentList.getDocumentList()) {
                DocumentEntity documentEntity = new DocumentEntity();
                if (ciOnlineResourcePropertyType.getCIOnlineResource() != null && ciOnlineResourcePropertyType.getCIOnlineResource().getDescription() != null) {
                    DescriptionEntity descriptionEntity = convertDescription(ciOnlineResourcePropertyType.getCIOnlineResource().getDescription());
                    documentEntity.setDescriptionEntity(descriptionEntity);
                }
                if (ciOnlineResourcePropertyType.getCIOnlineResource() != null && ciOnlineResourcePropertyType.getCIOnlineResource().getLinkage() != null) {
                    LinkageEntity linkageEntity = convertLinkage(ciOnlineResourcePropertyType.getCIOnlineResource().getLinkage());
                    documentEntity.setLinkageEntity(linkageEntity);
                }
                documentEntityList.add(documentEntity);
            }
        }
        return documentEntityList;
    }

    public static DescriptionEntity convertDescription(CharacterStringPropertyType characterStringPropertyType) {
        DescriptionEntity descriptionEntity = new DescriptionEntity();
        if (characterStringPropertyType.getCharacterString() != null) {
            descriptionEntity.setDescription(characterStringPropertyType.getCharacterString());
        }
        return descriptionEntity;
    }

    public static LinkageEntity convertLinkage(URLPropertyType urlPropertyType) {
        LinkageEntity linkageEntity = new LinkageEntity();
        if (urlPropertyType.getURL() != null) {
            linkageEntity.setUrl(urlPropertyType.getURL());
        }
        return linkageEntity;
    }



    public static ParametersEntity convert(AbstractProcessType.Parameters parameters) {
        ParametersEntity parametersEntity = new ParametersEntity();
        if (parameters.getParameterList() != null) {
            if (parameters.getParameterList().getParameterList() != null) {
                List<ParameterEntities> parameterEntitiesList = convert(parameters.getParameterList().getParameterList());
                parametersEntity.setParameterEntityList(parameterEntitiesList);
            }
        }
        return parametersEntity;
    }

    public static List<ParameterEntities> convert(List<ParameterListType.Parameter> parameterList) {
        List<ParameterEntities> parameterEntitiesList = new ArrayList<>();
        for (ParameterListType.Parameter parameter : parameterList) {
            ParameterEntities parameterEntities = new ParameterEntities();
            if (parameter.getAbstractDataComponent() instanceof QuantityType) {
                QuantityType quantityType = (QuantityType) parameter.getAbstractDataComponent();
                QuantityEntity quantityEntity = convertQuantity(quantityType);
                parameterEntities.setQuantityEntity(quantityEntity);
            }
            if (parameter.getName() != null) {
                parameterEntities.setName(parameter.getName());
            }
            parameterEntitiesList.add(parameterEntities);
        }
        return parameterEntitiesList;
    }

    public static QuantityEntity convertQuantity(QuantityType quantityType) {
        QuantityEntity quantityEntity = new QuantityEntity();
        if (quantityType.getDefinition() != null) {
            quantityEntity.setDefinition(quantityType.getDefinition());
        }
        if (quantityType.getLabel() != null) {
            quantityEntity.setLabel(quantityType.getLabel());
        }
        if (quantityType.getDescription() != null) {
            quantityEntity.setDescription(quantityType.getDescription());
        }
        if (quantityType.getUom() != null && quantityType.getUom().getCode() != null) {
            quantityEntity.setUom(quantityType.getUom().getCode());
        }
        try {
            quantityEntity.setValue(quantityType.getValue());
        } catch(Exception e) {
//            throw new RuntimeException("quantityType.getValue() is null");

        }


        if (quantityType.getAxisID() != null) {
            quantityEntity.setAxisId(quantityType.getAxisID());
        }
        return quantityEntity;
    }


    public static List<PositionEntity> convert(PositionUnionPropertyType[] positionArray) {
        List<PositionEntity> positionEntityList = new ArrayList<>();
        for (PositionUnionPropertyType positionUnionPropertyType : positionArray) {
            PositionEntity positionEntity = new PositionEntity();
            if (positionUnionPropertyType.getVector() != null) {
                List<CoordinateEntity> coordinateEntityList= convert(positionUnionPropertyType.getVector());
                positionEntity.setCoordinateEntity(coordinateEntityList);
            }
            positionEntityList.add(positionEntity);
        }
        return positionEntityList;
    }

    public static List<CoordinateEntity> convert(VectorType vector) {
        List<CoordinateEntity> coordinateEntityList = new ArrayList<>();
        if (vector.getCoordinateArray() != null) {
            for (VectorType.Coordinate coordinate : vector.getCoordinateArray()) {
                CoordinateEntity coordinateEntity = new CoordinateEntity();
                if (coordinate.getName() != null) {
                    coordinateEntity.setName(coordinate.getName());
                }
                if (coordinate.getQuantity() != null) {
                    QuantityEntity quantityEntity = convertQuantity(coordinate.getQuantity());
                    coordinateEntity.setQuantityEntity(quantityEntity);
                }
                coordinateEntityList.add(coordinateEntity);
            }
        }
        return coordinateEntityList;
    }

//    public static QuantityEntity convert(QuantityEntity quantityEntity) {
//        QuantityEntity quantityEntity = new QuantityEntity();
//
//    }

    private static OutputEntity convert(AbstractProcessType.Outputs outputs) {
        OutputEntity outputEntity = new OutputEntity();
        if (outputs.getOutputList() != null && outputs.getOutputList().getOutputList() != null) {
            List<IOEntity> ioEntityList = convertOutPut(outputs.getOutputList().getOutputList());
            outputEntity.setIOEntityList(ioEntityList);
        }
        return outputEntity;
    }

    private static List<IOEntity> convertOutPut(List<OutputListType.Output> outputList) {
        List<IOEntity> ioEntityList = new ArrayList<>();
        for (OutputListType.Output output : outputList) {
            IOEntity ioEntity = new IOEntity();
            if (output.getName() != null) {
                ioEntity.setName(output.getName());
            }
            if (output.getObservableProperty() != null && output.getObservableProperty().getDefinition() != null) {
                ObservablePropertyEntity observablePropertyEntity = new ObservablePropertyEntity();
                observablePropertyEntity.setDefinition(output.getObservableProperty().getDefinition());
                ioEntity.setObservablePropertyEntity(observablePropertyEntity);
            }
            if (output.getAbstractDataComponent() instanceof  QuantityType) {
                QuantityType quantityType = (QuantityType) output.getAbstractDataComponent();
                QuantityEntity quantityEntity = convertQuantity(quantityType);
                ioEntity.setQuantityEntity(quantityEntity);
            }
            ioEntityList.add(ioEntity);
        }
        return ioEntityList;
    }


    public static InputsEntity convert(AbstractProcessType.Inputs inputs) {
        InputsEntity inputsEntity = new InputsEntity();
        if (inputs.getInputList() != null && inputs.getInputList().getInputList() != null) {
            List<IOEntity> ioEntityList = convertIO(inputs.getInputList().getInputList());
            inputsEntity.setInputEntityList(ioEntityList);
        }
        return inputsEntity;
    }

    private static List<IOEntity> convertIO(List<InputListType.Input> inputList) {
        List<IOEntity> ioEntityList = new ArrayList<>();
        for (InputListType.Input input : inputList) {
            IOEntity ioEntity = new IOEntity();
            if (input.getName() != null) {
                ioEntity.setName(input.getName());
            }
            if (input.getObservableProperty() != null && input.getObservableProperty().getDefinition() != null) {
                ObservablePropertyEntity observablePropertyEntity = new ObservablePropertyEntity();
                observablePropertyEntity.setDefinition(input.getObservableProperty().getDefinition());
                ioEntity.setObservablePropertyEntity(observablePropertyEntity);
            }
            ioEntityList.add(ioEntity);
        }
        return ioEntityList;
    }

}
