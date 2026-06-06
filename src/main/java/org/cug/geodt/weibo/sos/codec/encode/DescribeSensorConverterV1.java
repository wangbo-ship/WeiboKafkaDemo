package org.cug.geodt.weibo.sos.codec.encode;

import lombok.extern.slf4j.Slf4j;
import net.opengis.gml.StringOrRefType;
import net.opengis.gml.TimeInstantType;
import net.opengis.gml.TimePeriodType;
import net.opengis.gml.TimePositionType;
import net.opengis.sensorML.x101.PositionDocument;
import net.opengis.sensorML.x101.*;
import net.opengis.swe.x101.*;
import net.opengis.swes.x20.DescribeSensorResponseDocument;
import net.opengis.swes.x20.DescribeSensorResponseType;
import net.opengis.swes.x20.SensorDescriptionType;
import org.cug.geodt.weibo.sos.domain.describeSensor.*;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 8:51
 * @Description sensorML编码器，输入DescribeSensorResponseEntity对象，
 * 输出DescribeSensorResponseDocument（sensorML1.0xml编码）
 */
@Slf4j
public class DescribeSensorConverterV1 {

    public static DescribeSensorResponseDocument convert(DescribeSensorResponseEntity describeSensorResponse) throws NullPointerException{
        DescribeSensorResponseDocument describeSensorResponseDocument = DescribeSensorResponseDocument.Factory.newInstance();
        DescribeSensorResponseType describeSensorResponseType = DescribeSensorResponseType.Factory.newInstance();
        if(describeSensorResponse.getSensorDescriptionEntityList() != null) {
            DescribeSensorResponseType.Description[] descriptions = convertDescription(describeSensorResponse.getSensorDescriptionEntityList());
            describeSensorResponseType.setDescriptionArray(descriptions);
        }
        describeSensorResponseDocument.setDescribeSensorResponse(describeSensorResponseType);
        return describeSensorResponseDocument;
    }

    public static DescribeSensorResponseType.Description[] convertDescription(List<SensorDescriptionEntity> sensorDescriptionEntityList) throws NullPointerException{
        ArrayList<DescribeSensorResponseType.Description> descriptionArrayList = new ArrayList<>();
        for (SensorDescriptionEntity sensorDescriptionEntity : sensorDescriptionEntityList) {
            DescribeSensorResponseType.Description description = DescribeSensorResponseType.Description.Factory.newInstance();
            SensorDescriptionType sensorDescriptionType = SensorDescriptionType.Factory.newInstance();
            SensorDescriptionType.Data data = sensorDescriptionType.addNewData();
            SensorMLDocument sensorMLDocument = SensorMLDocument.Factory.newInstance();
            SensorMLDocument.SensorML sensorML = SensorMLDocument.SensorML.Factory.newInstance();
            if(sensorDescriptionEntity.getMemberEntityList() != null) {
                SensorMLDocument.SensorML.Member[] members = convertMember(sensorDescriptionEntity.getMemberEntityList());
                sensorML.setMemberArray(members);
            }
            sensorMLDocument.setSensorML(sensorML);
            data.set(sensorMLDocument);
            sensorDescriptionType.setData(data);
            description.setSensorDescription(sensorDescriptionType);
            descriptionArrayList.add(description);
        }
        return descriptionArrayList.toArray(new DescribeSensorResponseType.Description[descriptionArrayList.size()]);
    }

    private static SensorMLDocument.SensorML.Member[] convertMember(List<MemberEntity> memberEntityList) throws NullPointerException{
        ArrayList<SensorMLDocument.SensorML.Member> members = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            SensorMLDocument.SensorML sensorML = SensorMLDocument.SensorML.Factory.newInstance();
            SensorMLDocument.SensorML.Member member = sensorML.addNewMember();
            SystemDocument systemDocument = SystemDocument.Factory.newInstance();
            SystemType systemType = SystemType.Factory.newInstance();
            StringOrRefType stringOrRefType = StringOrRefType.Factory.newInstance();
            if(memberEntity.getKeyWordEntities() != null) {
                KeywordsDocument.Keywords[] keywords = convert(memberEntity.getKeyWordEntities());
                systemType.setKeywordsArray(keywords);
            }
            if(memberEntity.getIdentifierEntityList() != null) {
                IdentificationDocument.Identification[] identifications = convertIdentification(memberEntity.getIdentifierEntityList());
                systemType.setIdentificationArray(identifications);
            }
            if(memberEntity.getClassifierEntityList() != null) {
                ClassificationDocument.Classification[] classifications = convertClassification(memberEntity.getClassifierEntityList());
                systemType.setClassificationArray(classifications);
            }
            if(memberEntity.getValidTimeEntity() != null) {
                ValidTimeDocument.ValidTime validTime = convert(memberEntity.getValidTimeEntity());
                systemType.setValidTime(validTime);
            }
            if(memberEntity.getCapabilitiesEntity() != null) {
                CapabilitiesDocument.Capabilities[] capabilities = convertCapabilities(memberEntity.getCapabilitiesEntity());
                systemType.setCapabilitiesArray(capabilities);
            }
            if(memberEntity.getContactEntity() != null) {
                ContactDocument.Contact[] contacts = convertContact(memberEntity.getContactEntity());
                systemType.setContactArray(contacts);
            }
            if(memberEntity.getPositionEntity() != null) {
                PositionDocument.Position position = convert(memberEntity.getPositionEntity());
                systemType.setPosition(position);
            }
            if(memberEntity.getInputEntities() != null) {
                InputsDocument.Inputs inputs = convertInputs(memberEntity.getInputEntities());
                systemType.setInputs(inputs);
            }
            if(memberEntity.getOutputEntities() != null) {
                OutputsDocument.Outputs outputs= convertOutputs(memberEntity.getOutputEntities());
                systemType.setOutputs(outputs);
            }
            if(memberEntity.getDescription() != null) {
                stringOrRefType.setStringValue(memberEntity.getDescription());
            } else {
                stringOrRefType.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            systemType.setDescription(stringOrRefType);
            systemDocument.setSystem(systemType);
            member.set(systemDocument);
            members.add(member);
        }
        return members.toArray(new SensorMLDocument.SensorML.Member[members.size()]);
    }


    private static KeywordsDocument.Keywords[] convert(List<KeyWordEntity> keywords) throws NullPointerException{
        ArrayList<KeywordsDocument.Keywords> keywordsArrayList = new ArrayList<>();
        for (KeyWordEntity keyWordEntity : keywords) {
            KeywordsDocument.Keywords keyword = KeywordsDocument.Keywords.Factory.newInstance();
            KeywordsDocument.Keywords.KeywordList keywordList = KeywordsDocument.Keywords.KeywordList.Factory.newInstance();
            if(keyWordEntity.getKeywords() != null) {
                keywordList.setKeywordArray(keyWordEntity.getKeywords().toArray(new String[keywords.size()]));
            }
            keyword.setKeywordList(keywordList);
            keywordsArrayList.add(keyword);
        }
        return keywordsArrayList.toArray(new KeywordsDocument.Keywords[keywordsArrayList.size()]);
    }

    private static IdentificationDocument.Identification[] convertIdentification(List<IdentifierEntity> identifierEntityList) throws NullPointerException{
        ArrayList<IdentificationDocument.Identification> identifications = new ArrayList<>();
        for (IdentifierEntity identifierEntity : identifierEntityList) {
            IdentificationDocument.Identification.IdentifierList identifierList = IdentificationDocument.Identification.IdentifierList.Factory.newInstance();
            IdentificationDocument.Identification identification = IdentificationDocument.Identification.Factory.newInstance();
            if(identifierEntity.getTermEntityList() != null) {
                IdentificationDocument.Identification.IdentifierList.Identifier[] identifiers = convertIdentifier(identifierEntity.getTermEntityList());
                identifierList.setIdentifierArray(identifiers);
            }
            identification.setIdentifierList(identifierList);
            identifications.add(identification);
        }
        return identifications.toArray(new IdentificationDocument.Identification[identifications.size()]);
    }

    private static IdentificationDocument.Identification.IdentifierList.Identifier[] convertIdentifier(List<TermEntity> termEntityList) throws NullPointerException{
        ArrayList<IdentificationDocument.Identification.IdentifierList.Identifier> identifiers = new ArrayList<>();
        for (TermEntity termEntity : termEntityList) {
            IdentificationDocument.Identification.IdentifierList.Identifier identifier = IdentificationDocument.Identification.IdentifierList.Identifier.Factory.newInstance();
            TermDocument.Term term = TermDocument.Term.Factory.newInstance();
            if(termEntity.getName() != null) {
                identifier.setName(termEntity.getName());
            } else {
                identifier.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(termEntity.getDefinition() != null) {
                term.setDefinition(termEntity.getDefinition());
            } else {
                term.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(termEntity.getValue() != null) {
                term.setValue(termEntity.getValue());
            } else {
                term.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            identifier.setTerm(term);
            identifiers.add(identifier);
        }
        return identifiers.toArray(new IdentificationDocument.Identification.IdentifierList.Identifier[identifiers.size()]);
    }


    private static ClassificationDocument.Classification[] convertClassification(List<ClassifierEntity> ClassifierEntityList) throws NullPointerException{
        ArrayList<ClassificationDocument.Classification> classifications = new ArrayList<>();
        for (ClassifierEntity classifierEntity : ClassifierEntityList) {
            ClassificationDocument.Classification.ClassifierList classifierList = ClassificationDocument.Classification.ClassifierList.Factory.newInstance();
            ClassificationDocument.Classification classification = ClassificationDocument.Classification.Factory.newInstance();
            if(classifierEntity.getTermEntityList() != null) {
                ClassificationDocument.Classification.ClassifierList.Classifier[] classifiers = convertClassifier(classifierEntity.getTermEntityList());
                classifierList.setClassifierArray(classifiers);
            }
            classification.setClassifierList(classifierList);
            classifications.add(classification);
        }
        return classifications.toArray(new ClassificationDocument.Classification[classifications.size()]);
    }

    private static ClassificationDocument.Classification.ClassifierList.Classifier[] convertClassifier(List<TermEntity> termEntityList) throws NullPointerException{
        ArrayList<ClassificationDocument.Classification.ClassifierList.Classifier> classifiers = new ArrayList<>();
        for (TermEntity termEntity : termEntityList) {
            ClassificationDocument.Classification.ClassifierList.Classifier classifier = ClassificationDocument.Classification.ClassifierList.Classifier.Factory.newInstance();
            TermDocument.Term term = TermDocument.Term.Factory.newInstance();
            if(termEntity.getName() != null) {
                classifier.setName(termEntity.getName());
            } else {
                classifier.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(termEntity.getDefinition() != null) {
                term.setDefinition(termEntity.getDefinition());
            } else {
                term.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(termEntity.getValue() != null) {
                term.setValue(termEntity.getValue());
            } else {
                term.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            classifier.setTerm(term);
            classifiers.add(classifier);
        }
        return classifiers.toArray(new ClassificationDocument.Classification.ClassifierList.Classifier[classifiers.size()]);
    }

    private static ValidTimeDocument.ValidTime convert(ValidTimeEntity validTimeEntity) {
        ValidTimeDocument.ValidTime validTime = ValidTimeDocument.ValidTime.Factory.newInstance();
        TimePeriodType timePeriodType = TimePeriodType.Factory.newInstance();
        TimeInstantType timeInstantType = TimeInstantType.Factory.newInstance();
        TimePositionType timePositionTypeStart = TimePositionType.Factory.newInstance();
        TimePositionType timePositionTypeEnd = TimePositionType.Factory.newInstance();
        if(validTimeEntity.getTimePeriodEntity() != null && validTimeEntity.getTimePeriodEntity().getBeginTime() != null) {
            timePositionTypeStart.setStringValue(validTimeEntity.getTimePeriodEntity().getBeginTime());
        }
        if(validTimeEntity.getTimePeriodEntity() != null && validTimeEntity.getTimePeriodEntity().getEndTime() != null) {
            timePositionTypeEnd.setStringValue(validTimeEntity.getTimePeriodEntity().getEndTime());
        }
        timePeriodType.setBeginPosition(timePositionTypeStart);
        timePeriodType.setEndPosition(timePositionTypeEnd);
        validTime.setTimePeriod(timePeriodType);
//        validTime.setTimeInstant();
        return validTime;
    }

    private static CapabilitiesDocument.Capabilities[] convertCapabilities(List<CapabilitiesEntity> capabilitiesEntity) throws NullPointerException{
        ArrayList<CapabilitiesDocument.Capabilities> capabilitiesArrayList = new ArrayList<>();
        for (CapabilitiesEntity entity : capabilitiesEntity) {
            CapabilitiesDocument capabilitiesDocument = CapabilitiesDocument.Factory.newInstance();
            CapabilitiesDocument.Capabilities capabilities = capabilitiesDocument.addNewCapabilities();
            DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
            DataRecordDocument dataRecordDocument = DataRecordDocument.Factory.newInstance();
            if(entity.getFields() != null) {
                DataComponentPropertyType[] dataComponentPropertyTypesArray = convertDataComponentPropertyType(entity.getFields());
                dataRecordType.setFieldArray(dataComponentPropertyTypesArray);
            }
            if(entity.getDefinition() != null) {
                dataRecordType.setDefinition(entity.getDefinition());
            } else {
                dataRecordType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            dataRecordDocument.setDataRecord(dataRecordType);
            capabilities.set(dataRecordDocument);
            capabilitiesArrayList.add(capabilities);
        }
        return capabilitiesArrayList.toArray(new CapabilitiesDocument.Capabilities[capabilitiesArrayList.size()]);
    }

    private static DataComponentPropertyType[] convertDataComponentPropertyType(Fields fields) throws NullPointerException{
        ArrayList<DataComponentPropertyType> dataComponentPropertyTypes = new ArrayList<>();
        for (BooleanEntity booleanEntity : fields.getBooleanEntityList()) {
            BooleanDocument.Boolean sweBoolean = BooleanDocument.Boolean.Factory.newInstance();
            DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
            DataComponentPropertyType dataComponentPropertyType = dataRecordType.addNewField();
            if(booleanEntity.getDefinition() != null) {
                sweBoolean.setDefinition(booleanEntity.getDefinition());
            } else {
                sweBoolean.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(booleanEntity.getValue() != null) {
                sweBoolean.setValue(booleanEntity.getValue());
            } else {
                sweBoolean.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            dataComponentPropertyType.setBoolean(sweBoolean);
            if(booleanEntity.getName() != null) {
                dataComponentPropertyType.setName(booleanEntity.getName());
            } else {
                dataComponentPropertyType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            dataComponentPropertyTypes.add(dataComponentPropertyType);
        }
        for (TextEntity textEntity : fields.getTextEntitiesList()) {
            TextDocument.Text text = TextDocument.Text.Factory.newInstance();
            DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
            DataComponentPropertyType dataComponentPropertyType = dataRecordType.addNewField();
            if (textEntity.getDefinition() != null) {
                text.setDefinition(textEntity.getDefinition());
            } else {
                text.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (textEntity.getValue() != null) {
                text.setValue(textEntity.getValue());
            } else {
                text.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            dataComponentPropertyType.setText(text);
            if(textEntity.getName() != null) {
                dataComponentPropertyType.setName(textEntity.getName());
            }
            dataComponentPropertyTypes.add(dataComponentPropertyType);
        }
        for (EnvelopeEntity envelopeEntity : fields.getEnvelopeEntityList()) {
            EnvelopeDocument envelopeDocument = EnvelopeDocument.Factory.newInstance();
            EnvelopeType envelopeType = EnvelopeType.Factory.newInstance();
            DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
            DataComponentPropertyType dataComponentPropertyType = dataRecordType.addNewField();
            if (envelopeEntity.getDefinition() != null) {
                envelopeType.setDefinition(envelopeEntity.getDefinition());
            } else {
                envelopeType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (envelopeEntity.getReferenceFrame() != null) {
                envelopeType.setReferenceFrame(envelopeEntity.getReferenceFrame());
            } else {
                envelopeType.setReferenceFrame(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (envelopeEntity.getUpperCornerEntity() !=  null && envelopeEntity.getUpperCornerEntity().getCoordinateEntityList() != null) {
                VectorPropertyType upperCorner = convertVector(envelopeEntity.getUpperCornerEntity().getCoordinateEntityList());
                envelopeType.setUpperCorner(upperCorner);
            }
            if (envelopeEntity.getLowerCornerEntity() != null && envelopeEntity.getLowerCornerEntity().getCoordinateEntityList() != null) {
                VectorPropertyType lowerCorner = convertVector(envelopeEntity.getLowerCornerEntity().getCoordinateEntityList());
                envelopeType.setLowerCorner(lowerCorner);
            }
            envelopeDocument.setEnvelope(envelopeType);
            dataComponentPropertyType.set(envelopeDocument);
            if (envelopeEntity.getName() != null) {
                dataComponentPropertyType.setName(envelopeEntity.getName());
            }
            dataComponentPropertyTypes.add(dataComponentPropertyType);
        }
        return dataComponentPropertyTypes.toArray(new DataComponentPropertyType[dataComponentPropertyTypes.size()]);
    }

    private static ContactDocument.Contact[] convertContact(List<ContactEntity> contactEntity) throws NullPointerException{
        ArrayList<ContactDocument.Contact> contacts = new ArrayList<>();
        for (ContactEntity entity : contactEntity) {
            ContactDocument.Contact contact = ContactDocument.Contact.Factory.newInstance();
            ResponsiblePartyDocument.ResponsibleParty responsibleParty = ResponsiblePartyDocument.ResponsibleParty.Factory.newInstance();
            if (entity.getIndividualName() != null) {
                responsibleParty.setIndividualName(entity.getIndividualName());
            } else {
                responsibleParty.setIndividualName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (entity.getOrganizationName() != null) {
                responsibleParty.setOrganizationName(entity.getOrganizationName());
            } else {
                responsibleParty.setOrganizationName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (entity.getContactInfo() != null) {
                ContactInfoDocument.ContactInfo contactInfo = convert(entity.getContactInfo());
                responsibleParty.setContactInfo(contactInfo);
            }
            if (entity.getGmlId() != null) {
                responsibleParty.setId(entity.getGmlId());
            }
            contact.setResponsibleParty(responsibleParty);
            contacts.add(contact);
        }
        return contacts.toArray(new ContactDocument.Contact[contacts.size()]);
    }

    private static ContactInfoDocument.ContactInfo convert(ContactInfo contactInfo) throws NullPointerException{
        ContactInfoDocument.ContactInfo contactInfo1 = ContactInfoDocument.ContactInfo.Factory.newInstance();
        ContactInfoDocument.ContactInfo.Address address = ContactInfoDocument.ContactInfo.Address.Factory.newInstance();
        if (contactInfo.getElectronicMailAddress() != null) {
            address.setElectronicMailAddress(contactInfo.getElectronicMailAddress());
        } else {
            address.setElectronicMailAddress(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (contactInfo.getCountry() != null) {
            address.setCountry(contactInfo.getCountry());
        } else {
            address.setCountry(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (contactInfo.getPostalCode() != null) {
            address.setPostalCode(contactInfo.getPostalCode());
        } else {
            address.setPostalCode(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(contactInfo.getCity() != null) {
            address.setCity(contactInfo.getCity());
        } else {
            address.setCity(XmlOptionHelper.NullPointHandler("string",false));
        }
        if (contactInfo.getDeliveryPoint() != null) {
            address.setDeliveryPointArray(contactInfo.getDeliveryPoint().toArray(new String[contactInfo.getDeliveryPoint().size()]));
        }
        contactInfo1.setAddress(address);
        return contactInfo1;
    }

    private static VectorPropertyType convertVector(List<CoordinateEntity> coordinateEntityList) throws NullPointerException{
        ArrayList<VectorType.Coordinate> coordinateArrayList = new ArrayList<>();
        VectorPropertyType vectorPropertyType = VectorPropertyType.Factory.newInstance();
        VectorType vectorType = VectorType.Factory.newInstance();
        for (CoordinateEntity coordinateEntity : coordinateEntityList) {
            VectorType.Coordinate coordinate = VectorType.Coordinate.Factory.newInstance();
            UomPropertyType uomPropertyType = UomPropertyType.Factory.newInstance();
            QuantityDocument.Quantity quantity = QuantityDocument.Quantity.Factory.newInstance();
            if (coordinateEntity.getCode() != null) {
                uomPropertyType.setCode(coordinateEntity.getCode());
            }
            quantity.setUom(uomPropertyType);
            if (coordinateEntity.getAxisID() != null) {
                quantity.setAxisID(coordinateEntity.getAxisID());
            } else {
                quantity.setAxisID(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (coordinateEntity.getValue() != null) {
                quantity.setValue(coordinateEntity.getValue());
            } else {
                quantity.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (coordinateEntity.getName() != null) {
                coordinate.setName(coordinateEntity.getName());
            } else {
                coordinate.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            coordinate.setQuantity(quantity);
            coordinateArrayList.add(coordinate);
        }
        vectorType.setCoordinateArray(coordinateArrayList.toArray(new VectorType.Coordinate[coordinateArrayList.size()]));
        vectorPropertyType.setVector(vectorType);
        return vectorPropertyType;
    }

    private static PositionDocument.Position convert(PositionEntity positionEntity) throws NullPointerException{
        PositionDocument positionDocument = PositionDocument.Factory.newInstance();
        PositionDocument.Position position = positionDocument.addNewPosition();
        net.opengis.swe.x101.PositionDocument swePositionDocument = net.opengis.swe.x101.PositionDocument.Factory.newInstance();
        PositionType positionType = PositionType.Factory.newInstance();
        VectorPropertyType LocationVectorPropertyType = VectorPropertyType.Factory.newInstance();
        VectorType LocationVectorType = VectorType.Factory.newInstance();
        if (positionEntity.getCoordinateEntity() != null) {
            VectorType.Coordinate[] coordinates = convertCoordinate(positionEntity.getCoordinateEntity());
            LocationVectorType.setCoordinateArray(coordinates);
        }
        LocationVectorPropertyType.setVector(LocationVectorType);
        positionType.setLocation(LocationVectorPropertyType);
        swePositionDocument.setPosition(positionType);
        position.set(swePositionDocument);
        return position;
    }

    private static VectorType.Coordinate[] convertCoordinate(List<CoordinateEntity> coordinateEntityList) throws NullPointerException{
        ArrayList<VectorType.Coordinate> coordinateArrayList = new ArrayList<>();
        for (CoordinateEntity coordinateEntity : coordinateEntityList) {
            VectorType.Coordinate coordinate = VectorType.Coordinate.Factory.newInstance();
            UomPropertyType uomPropertyType = UomPropertyType.Factory.newInstance();
            QuantityDocument.Quantity quantity = QuantityDocument.Quantity.Factory.newInstance();
            if (coordinateEntity.getCode() != null) {
                uomPropertyType.setCode(coordinateEntity.getCode());
            } else {
                uomPropertyType.setCode(XmlOptionHelper.NullPointHandler("string",false));
            }
            quantity.setUom(uomPropertyType);
            if (coordinateEntity.getAxisID() != null) {
                quantity.setAxisID(coordinateEntity.getAxisID());
            } else {
                quantity.setAxisID(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (coordinateEntity.getValue() != null) {
                quantity.setValue(coordinateEntity.getValue());
            } else {
                quantity.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (coordinateEntity.getName() != null) {
                coordinate.setName(coordinateEntity.getName());
            } else {
                coordinate.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            coordinate.setQuantity(quantity);
            coordinateArrayList.add(coordinate);
        }
        return coordinateArrayList.toArray(new VectorType.Coordinate[coordinateArrayList.size()]);
    }

//    private static InputsDocument.Inputs convertInputs(List<InputsEntity> inputEntityList) {
//        return null;
//    }
//
//    private static OutputsDocument.Outputs convertOutputs(List<OutputEntity> outputEntityList) {
//        OutputsDocument.Outputs outputs = OutputsDocument.Outputs.Factory.newInstance();
//        OutputsDocument.Outputs.OutputList outputList = OutputsDocument.Outputs.OutputList.Factory.newInstance();
//        ArrayList<IoComponentPropertyType> ioComponentPropertyTypeArrayList = new ArrayList<>();
//        IoComponentPropertyType ioComponentPropertyType = IoComponentPropertyType.Factory.newInstance();
//        for (OutputEntity outputEntity : outputEntityList) {
//            ioComponentPropertyType.set
//        }
//        outputList.setOutputArray(ioComponentPropertyTypeArrayList.toArray(new IoComponentPropertyType[ioComponentPropertyTypeArrayList.size()]));
//        outputs.setOutputList(outputList);
//        return null;
//    }

    private static InputsDocument.Inputs convertInputs(IOEntityV1 inputEntities) throws NullPointerException{
        InputsDocument.Inputs inputs = InputsDocument.Inputs.Factory.newInstance();
        InputsDocument.Inputs.InputList inputList = InputsDocument.Inputs.InputList.Factory.newInstance();
        if (inputEntities != null) {
            IoComponentPropertyType[] ioComponentPropertyTypes = convert(inputEntities);
            inputList.setInputArray(ioComponentPropertyTypes);
        }
        inputs.setInputList(inputList);
        return inputs;
    }

    private static OutputsDocument.Outputs convertOutputs(IOEntityV1 outputEntities) throws NullPointerException{
        OutputsDocument.Outputs outputs = OutputsDocument.Outputs.Factory.newInstance();
        OutputsDocument.Outputs.OutputList outputList = OutputsDocument.Outputs.OutputList.Factory.newInstance();
        if (outputEntities != null) {
            IoComponentPropertyType[] ioComponentPropertyTypes = convert(outputEntities);
            outputList.setOutputArray(ioComponentPropertyTypes);
        }
        outputs.setOutputList(outputList);
        return outputs;
    }

    private static IoComponentPropertyType[] convert(IOEntityV1 entities) throws NullPointerException{
        ArrayList<IoComponentPropertyType> ioComponentPropertyTypeArrayList = new ArrayList<>();
        for (QuantityEntityV2 quantityEntity : entities.getQuantityEntities()) {
            QuantityDocument.Quantity quantity = QuantityDocument.Quantity.Factory.newInstance();
            StringOrRefType stringOrRefType = StringOrRefType.Factory.newInstance();
            IoComponentPropertyType ioComponentPropertyType = IoComponentPropertyType.Factory.newInstance();
            if (quantityEntity.getName() != null) {
                ioComponentPropertyType.setName(quantityEntity.getName());
            } else {
                ioComponentPropertyType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (quantityEntity.getDefinition() != null) {
                quantity.setDefinition(quantityEntity.getDefinition());
            } else {
                quantity.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (quantityEntity.getDescription() != null) {
                stringOrRefType.setStringValue(quantityEntity.getDescription());
            } else {
                stringOrRefType.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            quantity.setDescription(stringOrRefType);
            if (quantityEntity.getCode()!= null) {
                UomPropertyType uomPropertyType = UomPropertyType.Factory.newInstance();
                uomPropertyType.setCode(quantityEntity.getCode());
                quantity.setUom(uomPropertyType);
            }

            ioComponentPropertyType.setQuantity(quantity);
            ioComponentPropertyTypeArrayList.add(ioComponentPropertyType);
        }
        for (BooleanEntity booleanEntity : entities.getBooleanEntities()) {
            BooleanDocument.Boolean aBoolean = BooleanDocument.Boolean.Factory.newInstance();
            IoComponentPropertyType ioComponentPropertyType = IoComponentPropertyType.Factory.newInstance();
            if (booleanEntity.getName() != null) {
                ioComponentPropertyType.setName(booleanEntity.getName());
            } else {
                ioComponentPropertyType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (booleanEntity.getDefinition() != null) {
                aBoolean.setDefinition(booleanEntity.getDefinition());
            } else {
                aBoolean.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (booleanEntity.getValue() != null) {
                aBoolean.setValue(booleanEntity.getValue());
            } else {
                aBoolean.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            ioComponentPropertyType.setBoolean(aBoolean);
            ioComponentPropertyTypeArrayList.add(ioComponentPropertyType);
        }
        for (TextEntity textEntity : entities.getTextEntities()) {
            TextDocument.Text text = TextDocument.Text.Factory.newInstance();
            IoComponentPropertyType ioComponentPropertyType = IoComponentPropertyType.Factory.newInstance();
            if (textEntity.getName() != null) {
                ioComponentPropertyType.setName(textEntity.getName());
            } else {
                ioComponentPropertyType.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (textEntity.getValue() != null) {
                text.setValue(textEntity.getValue());
            } else {
                text.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (textEntity.getValue() != null) {
                text.setValue(textEntity.getValue());
            } else {
                text.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if (textEntity.getDefinition() != null) {
                text.setDefinition(textEntity.getDefinition());
            } else {
                text.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            ioComponentPropertyType.setText(text);
            ioComponentPropertyTypeArrayList.add(ioComponentPropertyType);
        }
        return ioComponentPropertyTypeArrayList.toArray(new IoComponentPropertyType[ioComponentPropertyTypeArrayList.size()]);
    }


}
