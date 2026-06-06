package org.cug.geodt.weibo.sos.codec.encode;

import lombok.extern.slf4j.Slf4j;
import net.opengis.gml.x32.*;
import net.opengis.sensorml.x20.*;
import net.opengis.swe.x101.TextBlockDocument;
import net.opengis.swe.x20.BooleanDocument;
import net.opengis.swe.x20.CountDocument;
import net.opengis.swe.x20.QuantityDocument;
import net.opengis.swe.x20.VectorType;
import net.opengis.swe.x20.*;
import org.cug.geodt.weibo.sos.domain.describeSensor.*;
import org.cug.geodt.weibo.sos.domain.observation.ElementCountEntity;
import org.cug.geodt.weibo.sos.domain.observation.ElementTypeEntity;
import org.cug.geodt.weibo.sos.domain.observation.EncodingEntity;
import org.cug.geodt.weibo.sos.domain.observation.ValuesEntity;
import org.cug.geodt.weibo.sos.utils.XmlOptionHelper;
import org.isotc211.x2005.gco.CharacterStringPropertyType;
import org.isotc211.x2005.gmd.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

//import net.opengis.gml.StringOrRefType;
//import net.opengis.gml.TimeInstantType;
//import net.opengis.gml.TimePeriodType;
//import net.opengis.gml.TimePositionType;
//
//import net.opengis.sensorML.x101.SensorMLDocument;
//import net.opengis.sensorML.x101.*;
//import net.opengis.sensorML.x101.PositionDocument;
//import net.opengis.swe.x101.*;

/**
 * Author WJW
 * Date 2023/6/7 8:51
 * @Description sensorML编码器，输入DescribeSensorResponseEntity对象，
 * 输出PhysicalComponentDocument（sensorML2.0xml编码）
 */
@Slf4j
public class DescribeSensorConverterV2 {

    public static PhysicalComponentDocument convert(DescribeSensorResponseEntity describeSensorResponse) {
        PhysicalComponentDocument physicalComponentDocument = PhysicalComponentDocument.Factory.newInstance();
        PhysicalComponentType physicalComponentType = PhysicalComponentType.Factory.newInstance();
        StringOrRefType stringOrRefType = StringOrRefType.Factory.newInstance();
        CodeWithAuthorityType codeWithAuthorityType = CodeWithAuthorityType.Factory.newInstance();
        if(describeSensorResponse.getDescription() != null) {
            stringOrRefType.setStringValue(describeSensorResponse.getDescription());
            physicalComponentType.setDescription(stringOrRefType);
        } else {
            stringOrRefType.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(describeSensorResponse.getId() != null) {
            physicalComponentType.setId(describeSensorResponse.getId());
        } else {
            physicalComponentType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(describeSensorResponse.getIdentifier() != null) {
            codeWithAuthorityType.setCodeSpace(describeSensorResponse.getIdentifier());
        } else {
            codeWithAuthorityType.setCodeSpace(XmlOptionHelper.NullPointHandler("string",false));
            physicalComponentType.setIdentifier(codeWithAuthorityType);
        }
        if(describeSensorResponse.getKeyWordEntityList() != null) {
            KeywordListPropertyType[] keywordListPropertyTypes = convertKeywordList(describeSensorResponse.getKeyWordEntityList());
            physicalComponentType.setKeywordsArray(keywordListPropertyTypes);
        }
        if(describeSensorResponse.getIdentifierEntityList() != null) {
            IdentifierListPropertyType[] identifierListPropertyTypes = convertIdentifierList(describeSensorResponse.getIdentifierEntityList());
            physicalComponentType.setIdentificationArray(identifierListPropertyTypes);
        }
        if(describeSensorResponse.getValidTimeEntity() != null) {
            DescribedObjectType.ValidTime[] validTimes = convertValidTimeList(describeSensorResponse.getValidTimeEntity());
            physicalComponentType.setValidTimeArray(validTimes);
        }
        if(describeSensorResponse.getCharacteristicsEntity() != null) {
            DescribedObjectType.Characteristics[] characteristics = convertCharacteristics(describeSensorResponse.getCharacteristicsEntity());
            physicalComponentType.setCharacteristicsArray(characteristics);
        }
        if(describeSensorResponse.getCapabilitiesEntity() != null) {
            DescribedObjectType.Capabilities[] capabilities = convertCapabilities(describeSensorResponse.getCapabilitiesEntity());
            physicalComponentType.setCapabilitiesArray(capabilities);
        }
        if(describeSensorResponse.getContactsEntity() != null) {
            ContactListPropertyType[] contactListPropertyTypes = convertContactList(describeSensorResponse.getContactsEntity());
            physicalComponentType.setContactsArray(contactListPropertyTypes);
        }
        if(describeSensorResponse.getDocumentsEntity() != null) {
            DocumentListPropertyType[] documentListPropertyTypes = convertDocumentListPropertyType(describeSensorResponse.getDocumentsEntity());
            physicalComponentType.setDocumentationArray(documentListPropertyTypes);
        }
        if(describeSensorResponse.getPositionEntityList() != null) {
            PositionUnionPropertyType[] positionUnionPropertyTypes = convertPosition(describeSensorResponse.getPositionEntityList());
            physicalComponentType.setPositionArray(positionUnionPropertyTypes);
        }
        if(describeSensorResponse.getHistoryEntity() != null) {
            EventListPropertyType[] eventListPropertyTypes = convertEvent(describeSensorResponse.getHistoryEntity());
            physicalComponentType.setHistoryArray(eventListPropertyTypes);
        }

//        EventListPropertyType[] eventListPropertyTypes = convertEventListPropertyType(describeSensorResponse.getHistoryEntity());
        if(describeSensorResponse.getParameterEntity() != null) {
            AbstractProcessType.Parameters parameters = convert(describeSensorResponse.getParameterEntity());
            physicalComponentType.setParameters(parameters);
        }
        if(describeSensorResponse.getInputEntities() != null) {
            AbstractProcessType.Inputs inputs = convertInputs(describeSensorResponse.getInputEntities());
            physicalComponentType.setInputs(inputs);
        }
        if(describeSensorResponse.getOutputEntities() != null) {
            AbstractProcessType.Outputs outputs = convertOutputs(describeSensorResponse.getOutputEntities());
            physicalComponentType.setOutputs(outputs);
        }

//        physicalComponentType.setHistoryArray(eventListPropertyTypes);
//        physicalComponentType.setMethod();
//        physicalComponentType.setPositionArray();
//        physicalComponentType.setLocation();
//        physicalComponentType.setClassificationArray();

        physicalComponentDocument.setPhysicalComponent(physicalComponentType);
        return physicalComponentDocument;
    }

    public static EventListPropertyType[] convertEvent(List<HistoryEntity> historyEntity) {
        ArrayList<EventListPropertyType> eventListPropertyTypes = new ArrayList<>();
        EventListPropertyType eventListPropertyType = EventListPropertyType.Factory.newInstance();
        for (HistoryEntity entity : historyEntity) {
            EventListType eventListType = EventListType.Factory.newInstance();
            if(entity.getEventEntityList() != null) {
                EventPropertyType[] eventPropertyTypes = convertEventPropertyType(entity.getEventEntityList());
                eventListType.setEventArray(eventPropertyTypes);
            }
            eventListPropertyType.setEventList(eventListType);
            eventListPropertyTypes.add(eventListPropertyType);
        }
        return eventListPropertyTypes.toArray(new EventListPropertyType[eventListPropertyTypes.size()]);
    }

    public static EventPropertyType[] convertEventPropertyType(List<EventEntity> eventEntityList) {
        ArrayList<EventPropertyType> eventPropertyTypes = new ArrayList<>();
        EventPropertyType eventPropertyType = EventPropertyType.Factory.newInstance();
        EventType eventType = EventType.Factory.newInstance();
        for (EventEntity eventEntity : eventEntityList) {
            if(eventEntity.getLabel() != null) {
                eventType.setLabel(eventEntity.getLabel());
            } else {
                eventType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(eventEntity.getDocumentEntity() != null) {
                DocumentListPropertyType[] documentListPropertyTypes = convertDocumentListPropertyType(eventEntity.getDocumentEntity());
                eventType.setDocumentationArray(documentListPropertyTypes);
            }
            if(eventEntity.getTimeEntity() != null) {
                EventType.Time time = convert(eventEntity.getTimeEntity());
                eventType.setTime(time);
            }
            eventPropertyType.setEvent(eventType);
            eventPropertyTypes.add(eventPropertyType);
        }
        return eventPropertyTypes.toArray(new EventPropertyType[eventPropertyTypes.size()]);
    }

    private static EventType.Time convert(TimeEntity timeEntity) {
        EventType.Time time = EventType.Time.Factory.newInstance();
        TimeInstantType timeInstantType = TimeInstantType.Factory.newInstance();
        TimePositionType timePositionType = TimePositionType.Factory.newInstance();
        if(timeEntity.getTimeInstantEntity() != null && timeEntity.getTimeInstantEntity().getTimePosition() != null) {
            timePositionType.setStringValue(timeEntity.getTimeInstantEntity().getTimePosition());
        } else {
            timePositionType.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(timeEntity.getTimeInstantEntity() != null && timeEntity.getTimeInstantEntity().getGmlId() != null) {
            timeInstantType.setId(timeEntity.getTimeInstantEntity().getGmlId());
        } else {
            timeInstantType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }
        timeInstantType.setTimePosition(timePositionType);
        time.setTimeInstant(timeInstantType);
        return time;
    }

    public static PositionUnionPropertyType[] convertPosition(List<PositionEntity> positionEntityList) {
        ArrayList<PositionUnionPropertyType> positionUnionPropertyTypes = new ArrayList<>();
        for(PositionEntity positionEntity : positionEntityList) {
            PositionUnionPropertyType positionUnionPropertyType = PositionUnionPropertyType.Factory.newInstance();
            VectorType vectorType = VectorType.Factory.newInstance();
            if (positionEntity.getCoordinateEntity() != null) {
                VectorType.Coordinate[] coordinateArray = convertCoordinate(positionEntity.getCoordinateEntity());
                vectorType.setCoordinateArray(coordinateArray);
                positionUnionPropertyType.setVector(vectorType);
            }
            if(positionEntity.getDefinition() != null) {
                vectorType.setDefinition(positionEntity.getDefinition());
            } else {
                vectorType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(positionEntity.getReferenceFrame() != null) {
                vectorType.setReferenceFrame(positionEntity.getReferenceFrame());
            } else {
                vectorType.setReferenceFrame(XmlOptionHelper.NullPointHandler("string",false));
            }
            positionUnionPropertyTypes.add(positionUnionPropertyType);
        }
        return positionUnionPropertyTypes.toArray(new PositionUnionPropertyType[positionUnionPropertyTypes.size()]);
    }

    public static VectorType.Coordinate[] convertCoordinate(List<CoordinateEntity> coordinateEntityList) {
        ArrayList<VectorType.Coordinate> coordinates = new ArrayList<>();
        for (CoordinateEntity coordinateEntity : coordinateEntityList) {
            VectorType.Coordinate coordinate = VectorType.Coordinate.Factory.newInstance();
            QuantityType quantityType = QuantityType.Factory.newInstance();
            UnitReference unitReference = UnitReference.Factory.newInstance();
            if(coordinateEntity.getQuantityEntity() != null && coordinateEntity.getQuantityEntity().getDefinition() != null) {
                quantityType.setDefinition(coordinateEntity.getQuantityEntity().getDefinition());
            } else {
                quantityType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(coordinateEntity.getQuantityEntity() != null && coordinateEntity.getQuantityEntity().getUom() != null) {
                unitReference.setCode(coordinateEntity.getQuantityEntity().getUom());
            } else {
                unitReference.setCode(XmlOptionHelper.NullPointHandler("string",false));
            }
            quantityType.setUom(unitReference);
            if(coordinateEntity.getQuantityEntity() != null && coordinateEntity.getQuantityEntity().getValue() != null) {
                quantityType.setValue(coordinateEntity.getQuantityEntity().getValue());
            } else {
                quantityType.setValue(Double.parseDouble(XmlOptionHelper.NullPointHandler("num",false)));
            }
            coordinate.setQuantity(quantityType);
            if(coordinateEntity.getName() != null) {
                coordinate.setName(coordinateEntity.getName());
            } else {
                coordinate.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            coordinates.add(coordinate);
        }
        return coordinates.toArray(new VectorType.Coordinate[coordinates.size()]);
    }

    public static AbstractProcessType.Parameters convert(ParametersEntity parameterEntity) {
        AbstractProcessType.Parameters parameters = AbstractProcessType.Parameters.Factory.newInstance();
        ParameterListType parameterListType = ParameterListType.Factory.newInstance();
        if(parameterEntity.getParameterEntityList() != null) {
            ParameterListType.Parameter[] parameterArray = convertParameter(parameterEntity.getParameterEntityList());
            parameterListType.setParameterArray(parameterArray);
        }
        parameters.setParameterList(parameterListType);
        return parameters;
    }

    public static ParameterListType.Parameter[] convertParameter(List<ParameterEntities> parameterEntityList) {
        ArrayList<ParameterListType.Parameter> parameters = new ArrayList<>();
        for (ParameterEntities parameterEntity : parameterEntityList){
            ParameterListType parameterListType = ParameterListType.Factory.newInstance();
            ParameterListType.Parameter parameter = parameterListType.addNewParameter();
            if (parameterEntity.getQuantityEntity() != null) {
                QuantityDocument quantityDocument = QuantityDocument.Factory.newInstance();
                QuantityType quantityType = GetObservationConverterV2.convert(parameterEntity.getQuantityEntity());
                quantityDocument.setQuantity(quantityType);
                parameter.set(quantityDocument);
            }else if (parameterEntity.getBooleanEntity() != null ) {
                BooleanDocument booleanDocument = BooleanDocument.Factory.newInstance();
                BooleanType booleanType = convert(parameterEntity.getBooleanEntity());
                booleanDocument.setBoolean(booleanType);
                parameter.set(booleanDocument);
            }else if (parameterEntity.getCountEntity() != null){
                CountDocument countDocument = CountDocument.Factory.newInstance();
                CountType countType = convert(parameterEntity.getCountEntity());
                countDocument.setCount(countType);
                parameter.set(countDocument);
            }else {

            }
            if(parameterEntity.getName() != null) {
                parameter.setName(parameterEntity.getName());
            } else {
                parameter.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            parameters.add(parameter);
        }
        return parameters.toArray(new ParameterListType.Parameter[parameters.size()]);
    }

    public static CountType convert(CountEntity countEntity) {
        CountType countType = CountType.Factory.newInstance();
        if(countEntity.getDefinition() != null) {
            countType.setDefinition(countEntity.getDefinition());
        } else {
            countType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(countEntity.getLabel() != null) {
            countType.setLabel(countEntity.getLabel());
        } else {
            countType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(countEntity.getDescription() != null) {
            countType.setDescription(countEntity.getDescription());
        } else {
            countType.setDescription(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(countType.getValue() != null) {
            countType.setValue(countType.getValue());
        } else {
            countType.setValue(BigInteger.valueOf(Long.parseLong(XmlOptionHelper.NullPointHandler("num",false))));
        }
        return countType;
    }

    public static BooleanType convert(BooleanEntity booleanEntity) {
        BooleanType booleanType = BooleanType.Factory.newInstance();
        if(booleanEntity.getDefinition() != null) {
            booleanType.setDefinition(booleanEntity.getDefinition());
        } else {
            booleanType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(booleanEntity.getDefinition() != null) {
            booleanType.setLabel(booleanEntity.getLabel());
        } else {
            booleanType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(booleanEntity.getDescription() != null) {
            booleanType.setDescription(booleanEntity.getDescription());
        } else {
            booleanType.setDescription(XmlOptionHelper.NullPointHandler("string",false));
        }
        if(booleanEntity.getValue() != null) {
            booleanType.setValue(booleanEntity.getValue());
        } else {
            booleanType.setValue(XmlOptionHelper.NullPointHandler("string",false));
        }
        return booleanType;
    }

    public static AbstractProcessType.Outputs convertOutputs(OutputEntity outputEntity) {
        AbstractProcessType.Outputs outputs = AbstractProcessType.Outputs.Factory.newInstance();
        OutputListType outputListType = OutputListType.Factory.newInstance();
        if(outputEntity.getIOEntityList() != null) {
            OutputListType.Output[] outPutArray = convertOutputs(outputEntity.getIOEntityList());
            outputListType.setOutputArray(outPutArray);
        }
        outputs.setOutputList(outputListType);
        return outputs;
    }

    public static OutputListType.Output[] convertOutputs(List<IOEntity> ioEntityList) {
        ArrayList<OutputListType.Output> outputs = new ArrayList<>();
        for (IOEntity ioEntity : ioEntityList) {
            OutputListType.Output output = OutputListType.Output.Factory.newInstance();
            if(ioEntity.getName() != null) {
                output.setName(ioEntity.getName());
            } else {
                output.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(ioEntity.getDataInterfaceEntity() != null) {
                DataInterfaceType dataInterfaceType = convertDataInterfaceType(ioEntity.getDataInterfaceEntity());
                output.setDataInterface(dataInterfaceType);
            }
            if(ioEntity.getObservablePropertyEntity() != null) {
                ObservablePropertyType observablePropertyType = convertObservablePropertyType(ioEntity.getObservablePropertyEntity());
                output.setObservableProperty(observablePropertyType);
            }
            outputs.add(output);
        }
        return outputs.toArray(new OutputListType.Output[outputs.size()]);
    }


    public static AbstractProcessType.Inputs convertInputs(InputsEntity inputEntity) {
        AbstractProcessType.Inputs inputs = AbstractProcessType.Inputs.Factory.newInstance();
        InputListType inputListType = InputListType.Factory.newInstance();
        if(inputEntity.getInputEntityList() != null) {
            InputListType.Input[] inputsArray = convertInputListType(inputEntity.getInputEntityList());
            inputListType.setInputArray(inputsArray);
        }
        inputs.setInputList(inputListType);
        return inputs;
    }

    public static InputListType.Input[] convertInputListType(List<IOEntity> iOEntityList) {
        ArrayList<InputListType.Input> inputs = new ArrayList<>();
        for (IOEntity iOEntity : iOEntityList) {
            InputListType.Input input = InputListType.Input.Factory.newInstance();
            if (iOEntity.getName() != null) {
                input.setName(iOEntity.getName());
            } else {
                input.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
//            DataInterfaceType dataInterfaceType = convertDataInterfaceType(iOEntity.getDataInterfaceEntity());
            if (iOEntity.getObservablePropertyEntity() != null) {
                ObservablePropertyType observablePropertyType = convertObservablePropertyType(iOEntity.getObservablePropertyEntity());
                input.setObservableProperty(observablePropertyType);
            }
//            input.setDataInterface(dataInterfaceType);
            inputs.add(input);
        }
        return inputs.toArray(new InputListType.Input[inputs.size()]);
    }



    public static DataInterfaceType convertDataInterfaceType(DataInterfaceEntity dataInterfaceEntity) {
        DataInterfaceType dataInterfaceType = DataInterfaceType.Factory.newInstance();
        DataStreamPropertyType dataStreamPropertyType = DataStreamPropertyType.Factory.newInstance();
        DataStreamType dataStreamType = DataStreamType.Factory.newInstance();
        DataRecordPropertyType dataRecordPropertyType = DataRecordPropertyType.Factory.newInstance();
        DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
        if(dataInterfaceEntity.getDataArrayEntity() != null && dataInterfaceEntity.getDataArrayEntity().getElementTypeEntity() != null) {
            DataStreamType.ElementType elementType = convertElementType(dataInterfaceEntity.getDataArrayEntity().getElementTypeEntity());
            dataStreamType.setElementType(elementType);
        }
        if(dataInterfaceEntity.getDataArrayEntity() != null && dataInterfaceEntity.getDataArrayEntity().getEncodingEntity() != null) {
            DataStreamType.Encoding encoding = convertEncoding(dataInterfaceEntity.getDataArrayEntity().getEncodingEntity());
            dataStreamType.setEncoding(encoding);
        }
        if(dataInterfaceEntity.getDataArrayEntity() != null && dataInterfaceEntity.getDataArrayEntity().getValuesEntity() != null) {
            EncodedValuesPropertyType encodedValuesPropertyType = convertValue(dataInterfaceEntity.getDataArrayEntity().getValuesEntity());
            dataStreamType.setValues(encodedValuesPropertyType);
        }
        if(dataInterfaceEntity.getDataArrayEntity() != null && dataInterfaceEntity.getDataArrayEntity().getId() != null) {
            dataStreamType.setId(dataInterfaceEntity.getDataArrayEntity().getId());
        } else {
            dataStreamType.setId(XmlOptionHelper.NullPointHandler("string",false));
        }

        dataStreamPropertyType.setDataStream(dataStreamType);
        dataInterfaceType.setData(dataStreamPropertyType);
        if (dataInterfaceEntity.getFieldEntityList() != null) {
            DataRecordType.Field[] fields = convertField(dataInterfaceEntity.getFieldEntityList());
            dataRecordType.setFieldArray(fields);
        }

        dataRecordPropertyType.setDataRecord(dataRecordType);
        dataInterfaceType.setInterfaceParameters(dataRecordPropertyType);
        return dataInterfaceType;
    }

    public static EncodedValuesPropertyType convertValue(ValuesEntity valuesEntity) {
        EncodedValuesPropertyType encodedValuesPropertyType = EncodedValuesPropertyType.Factory.newInstance();
        if (valuesEntity.getValue() != null) {
            encodedValuesPropertyType.setTitle(valuesEntity.getValue());
        }else {
            encodedValuesPropertyType.setTitle(XmlOptionHelper.NullPointHandler("string",false));
        }
        return encodedValuesPropertyType;
    }

    public static DataStreamType.Encoding convertEncoding(EncodingEntity encodingEntity) {
        DataStreamType dataStreamType = DataStreamType.Factory.newInstance();
        DataStreamType.Encoding encoding = dataStreamType.addNewEncoding();
        TextBlockDocument textBlockDocument = TextBlockDocument.Factory.newInstance();
        TextBlockDocument.TextBlock textBlock = TextBlockDocument.TextBlock.Factory.newInstance();
        textBlock.setBlockSeparator(encodingEntity.getBlockSeparator());
        textBlock.setDecimalSeparator(encodingEntity.getDecimalSeparator());
        textBlock.setTokenSeparator(encodingEntity.getTokenSeparator());
        textBlockDocument.setTextBlock(textBlock);
        encoding.set(textBlockDocument);
        return encoding;
    }

    public static DataStreamType.ElementType convertElementType(ElementTypeEntity elementTypeEntity) {
        DataStreamType dataStreamType = DataStreamType.Factory.newInstance();
        DataStreamType.ElementType elementType = dataStreamType.addNewElementType();
        DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
        if (elementTypeEntity.getFieldEntityList() != null) {
            DataRecordType.Field[] fieldArray = GetObservationConverterV2.convertField(elementTypeEntity.getFieldEntityList());
            dataRecordType.setFieldArray(fieldArray);
        }
        elementType.set(dataRecordType);
        return elementType;
    }

    public static DataStreamType.ElementCount convertElementCount(ElementCountEntity elementCountEntity) {
        DataStreamType.ElementCount elementCount = DataStreamType.ElementCount.Factory.newInstance();
        CountType countType = CountType.Factory.newInstance();
        countType.setValue(BigInteger.valueOf(elementCountEntity.getCountValues()));
        elementCount.setCount(countType);
        return elementCount;
    }

    public static DataRecordType.Field[] convertField(List<FieldsEntity>  fieldEntityList) {
        ArrayList<DataRecordType.Field> fieldList = new ArrayList<>();
        for (FieldsEntity fieldEntity : fieldEntityList) {
            DataRecordType dataRecordType = DataRecordType.Factory.newInstance();
            DataRecordType.Field field = dataRecordType.addNewField();
            TextDocument textDocument = TextDocument.Factory.newInstance();
            TextType textType = TextType.Factory.newInstance();
            if(fieldEntity.getTextEntity() != null && fieldEntity.getTextEntity().getDefinition() != null) {
                textType.setDefinition(fieldEntity.getTextEntity().getDefinition());
            } else {
                textType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(fieldEntity.getTextEntity() != null && fieldEntity.getTextEntity().getLabel() != null) {
                textType.setLabel(fieldEntity.getTextEntity().getLabel());
            } else {
                textType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(fieldEntity.getTextEntity() != null && fieldEntity.getTextEntity().getValue() != null) {
                textType.setValue(fieldEntity.getTextEntity().getValue());
            } else {
                textType.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            field.set(textType);
            if(fieldEntity.getName() != null) {
                field.setName(fieldEntity.getName());
            } else {
                field.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
        }
        return fieldList.toArray(new DataRecordType.Field[fieldList.size()]);
    }

    public static ObservablePropertyType convertObservablePropertyType(ObservablePropertyEntity observablePropertyEntity) {
        ObservablePropertyType observablePropertyType = ObservablePropertyType.Factory.newInstance();
        if (observablePropertyEntity.getDefinition() != null) {
            observablePropertyType.setDefinition(observablePropertyEntity.getDefinition());
        } else {
            observablePropertyType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
        }
        return observablePropertyType;
    }

//    public static EventListPropertyType[] convertEventListPropertyType(List<HistoryEntity> historyEntityList) {
//        ArrayList<EventListPropertyType> eventListPropertyTypes = new ArrayList<>();
//        EventListPropertyType eventListPropertyType = EventListPropertyType.Factory.newInstance();
//        EventListType eventListType = EventListType.Factory.newInstance();
//        for (HistoryEntity historyEntity : historyEntityList) {
//            EventPropertyType[] eventPropertyTypes = convertEventPropertyType(historyEntity.getEventEntityList());
//            eventListType.setEventArray(eventPropertyTypes);
//            eventListPropertyType.setEventList(eventListType);
//            eventListPropertyTypes.add(eventListPropertyType);
//        }
//        return eventListPropertyTypes.toArray(new EventListPropertyType[eventListPropertyTypes.size()]);
//    }

//    public static EventPropertyType[] convertEventPropertyType(List<EventEntity> eventEntityList) {
//        ArrayList<EventPropertyType> eventPropertyTypes = new ArrayList<>();
//        EventPropertyType eventPropertyType = EventPropertyType.Factory.newInstance();
//        EventType eventType = EventType.Factory.newInstance();
//        for (EventEntity eventEntity : eventEntityList) {
//            eventType
//            eventPropertyType.setEvent(eventType);
//        }
//    }

    public static  DocumentListPropertyType[] convertDocumentListPropertyType(List<DocumentsEntity> documentsEntityList) {
        ArrayList<DocumentListPropertyType> documentListPropertyTypes = new ArrayList<>();
        for (DocumentsEntity documentsEntity : documentsEntityList) {
            DocumentListPropertyType documentListPropertyType = DocumentListPropertyType.Factory.newInstance();
            DocumentListType documentListType = DocumentListType.Factory.newInstance();
            if (documentsEntity.getDocumentEntity() != null) {
                CIOnlineResourcePropertyType[] ciOnlineResourcePropertyTypes = convertCIOnlineResourcePropertyType(documentsEntity.getDocumentEntity());
                documentListType.setDocumentArray(ciOnlineResourcePropertyTypes);
            }
            documentListPropertyType.setDocumentList(documentListType);
            documentListPropertyTypes.add(documentListPropertyType);
        }
        return documentListPropertyTypes.toArray(new DocumentListPropertyType[documentListPropertyTypes.size()]);
    }

    public static CIOnlineResourcePropertyType[] convertCIOnlineResourcePropertyType(List<DocumentEntity> documentEntityList) {
        ArrayList<CIOnlineResourcePropertyType> cIOnlineResourcePropertyTypeList = new ArrayList<>();
        for (DocumentEntity documentEntity : documentEntityList) {
            CIOnlineResourcePropertyType cIOnlineResourcePropertyType = CIOnlineResourcePropertyType.Factory.newInstance();
            CIOnlineResourceType ciOnlineResourceType = CIOnlineResourceType.Factory.newInstance();
            URLPropertyType urlPropertyType = URLPropertyType.Factory.newInstance();
            CharacterStringPropertyType characterStringPropertyType = CharacterStringPropertyType.Factory.newInstance();
            if (documentEntity.getLinkageEntity() != null && documentEntity.getLinkageEntity().getUrl() != null) {
                urlPropertyType.setURL(documentEntity.getLinkageEntity().getUrl());
            } else {
                urlPropertyType.setURL(XmlOptionHelper.NullPointHandler("href",false));
            }
            ciOnlineResourceType.setLinkage(urlPropertyType);
            if (documentEntity.getDescriptionEntity() != null && documentEntity.getDescriptionEntity().getDescription() != null){
                characterStringPropertyType.setCharacterString(documentEntity.getDescriptionEntity().getDescription());
            } else {
                characterStringPropertyType.setCharacterString(XmlOptionHelper.NullPointHandler("string",false));
            }
            ciOnlineResourceType.setDescription(characterStringPropertyType);
            cIOnlineResourcePropertyType.setCIOnlineResource(ciOnlineResourceType);
            cIOnlineResourcePropertyTypeList.add(cIOnlineResourcePropertyType);
        }
        return cIOnlineResourcePropertyTypeList.toArray(new CIOnlineResourcePropertyType[cIOnlineResourcePropertyTypeList.size()]);
    }

    public static ContactListPropertyType[] convertContactList(List<ContactsEntity> contactsEntityList) {
        ArrayList<ContactListPropertyType> contactListPropertyTypes = new ArrayList<>();
        for (ContactsEntity contactsEntity : contactsEntityList) {
            ContactListPropertyType contactListPropertyType = ContactListPropertyType.Factory.newInstance();
            ContactListType contactListType = ContactListType.Factory.newInstance();
            if(contactsEntity.getContactEntities() != null) {
                CIResponsiblePartyPropertyType[] contactListPropertyTypeArray = convertCIResponsiblePartyPropertyType(contactsEntity.getContactEntities());
                contactListType.setContactArray(contactListPropertyTypeArray);
            }
            contactListPropertyType.setContactList(contactListType);
            contactListPropertyTypes.add(contactListPropertyType);
        }
        return contactListPropertyTypes.toArray(new ContactListPropertyType[contactListPropertyTypes.size()]);
    }

    public static CIResponsiblePartyPropertyType[] convertCIResponsiblePartyPropertyType(List<ContactEntity> contactEntityList) {
        ArrayList<CIResponsiblePartyPropertyType> ciResponsiblePartyPropertyTypes = new ArrayList<>();
        for (ContactEntity contactEntity : contactEntityList) {
            CIResponsiblePartyPropertyType ciResponsiblePartyPropertyType = CIResponsiblePartyPropertyType.Factory.newInstance();
            CIResponsiblePartyType ciResponsiblePartyType = CIResponsiblePartyType.Factory.newInstance();
            CIContactPropertyType ciContactPropertyType = CIContactPropertyType.Factory.newInstance();
            CharacterStringPropertyType characterStringPropertyType = CharacterStringPropertyType.Factory.newInstance();
            CIContactType ciContactType = CIContactType.Factory.newInstance();
            CIAddressPropertyType ciAddressPropertyType = CIAddressPropertyType.Factory.newInstance();
            CIAddressType ciAddressType = CIAddressType.Factory.newInstance();
            CITelephonePropertyType ciTelephonePropertyType = CITelephonePropertyType.Factory.newInstance();
            CITelephoneType ciTelephoneType = CITelephoneType.Factory.newInstance();
            if (contactEntity.getContactInfoEntity() != null && contactEntity.getContactInfoEntity().getAddressEntity() != null && contactEntity.getContactInfoEntity().getAddressEntity().getPostalCode() != null) {
                CharacterStringPropertyType characterStringPropertyType1 = CharacterStringPropertyType.Factory.newInstance();
                characterStringPropertyType1.setCharacterString(contactEntity.getContactInfoEntity().getAddressEntity().getPostalCode());
                ciAddressType.setPostalCode(characterStringPropertyType1);
            }
            if(contactEntity.getContactInfoEntity() != null && contactEntity.getContactInfoEntity().getAddressEntity() != null && contactEntity.getContactInfoEntity().getAddressEntity().getCity() != null) {
                characterStringPropertyType.setCharacterString(contactEntity.getContactInfoEntity().getAddressEntity().getCity());
            } else {
                characterStringPropertyType.setCharacterString(XmlOptionHelper.NullPointHandler("string",false));
            }
            ciAddressType.setCity(characterStringPropertyType);
            if (contactEntity.getContactInfoEntity() != null && contactEntity.getContactInfoEntity().getAddressEntity() != null && contactEntity.getContactInfoEntity().getAddressEntity().getCountry() != null) {
                characterStringPropertyType.setCharacterString(contactEntity.getContactInfoEntity().getAddressEntity().getCountry());
            } else {
                characterStringPropertyType.setCharacterString(XmlOptionHelper.NullPointHandler("string",false));
            }
            ciAddressType.setCountry(characterStringPropertyType);
            ciAddressPropertyType.setCIAddress(ciAddressType);
            if(contactEntity.getContactInfoEntity() != null && contactEntity.getContactInfoEntity().getPhoneEntity() !=null && contactEntity.getContactInfoEntity().getPhoneEntity().getVoiceList() != null) {
                CharacterStringPropertyType[] voiceArray = convert(contactEntity.getContactInfoEntity().getPhoneEntity().getVoiceList());
                ciTelephoneType.setVoiceArray(voiceArray);
            }
            if(contactEntity.getContactInfoEntity() != null && contactEntity.getContactInfoEntity().getPhoneEntity() !=null && contactEntity.getContactInfoEntity().getPhoneEntity().getFacsimile() != null) {
                CharacterStringPropertyType[] facsimileArray = convert(contactEntity.getContactInfoEntity().getPhoneEntity().getFacsimile());
                ciTelephoneType.setFacsimileArray(facsimileArray);
            }
            ciTelephonePropertyType.setCITelephone(ciTelephoneType);
            ciContactType.setPhone(ciTelephonePropertyType);
            ciContactType.setAddress(ciAddressPropertyType);
            ciContactPropertyType.setCIContact(ciContactType);
            ciResponsiblePartyType.setContactInfo(ciContactPropertyType);
            if(contactEntity.getOrganisationNameEntity() != null && contactEntity.getOrganisationNameEntity().getOrganisationName() != null) {
                characterStringPropertyType.setCharacterString(contactEntity.getOrganisationNameEntity().getOrganisationName());
            } else {
                characterStringPropertyType.setCharacterString(XmlOptionHelper.NullPointHandler("string",false));
            }
            ciResponsiblePartyType.setOrganisationName(characterStringPropertyType);
//            characterStringPropertyType.setCharacterString(contactEntity.getPositionNameEntity().);
//            ciResponsiblePartyType.setPositionName();
//            ciResponsiblePartyType.setIndividualName();
            ciResponsiblePartyPropertyType.setCIResponsibleParty(ciResponsiblePartyType);
            ciResponsiblePartyPropertyType.setTitle(contactEntity.getTitle());
            ciResponsiblePartyPropertyTypes.add(ciResponsiblePartyPropertyType);
        }
        return ciResponsiblePartyPropertyTypes.toArray(new CIResponsiblePartyPropertyType[ciResponsiblePartyPropertyTypes.size()]);
    }

    private static CharacterStringPropertyType[] convert(List<String> phoneList) {
        ArrayList<CharacterStringPropertyType> characterStringPropertyTypes = new ArrayList<>();
        for (String phone : phoneList) {
            CharacterStringPropertyType characterStringPropertyType = CharacterStringPropertyType.Factory.newInstance();
            characterStringPropertyType.setCharacterString(phone);
            characterStringPropertyTypes.add(characterStringPropertyType);
        }
        CharacterStringPropertyType[] characterStringPropertyTypesList = characterStringPropertyTypes.toArray(new CharacterStringPropertyType[characterStringPropertyTypes.size()]);
        return characterStringPropertyTypesList;
    }



    public static DescribedObjectType.Capabilities[] convertCapabilities(List<CapabilitiesEntity> capabilitiesEntityList) {
        ArrayList<DescribedObjectType.Capabilities> capabilities = new ArrayList<>();
        for (CapabilitiesEntity capabilitiesEntity : capabilitiesEntityList) {
            DescribedObjectType.Capabilities capabilities1 = DescribedObjectType.Capabilities.Factory.newInstance();
            CapabilityListType capabilityListType = CapabilityListType.Factory.newInstance();
            if(capabilitiesEntity.getCapabilityEntity() != null) {
                CapabilityListType.Capability[] capabilityArray = convertCapabilityListType(capabilitiesEntity.getCapabilityEntity());
                capabilityListType.setCapabilityArray(capabilityArray);
            }
            if(capabilitiesEntity.getName() != null) {
                capabilities1.setName(capabilitiesEntity.getName());
            } else {
                capabilities1.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            capabilities1.setCapabilityList(capabilityListType);
            capabilities.add(capabilities1);
        }
        return capabilities.toArray(new DescribedObjectType.Capabilities[capabilities.size()]);
    }

    public static CapabilityListType.Capability[] convertCapabilityListType(List<CapabilityEntity> capabilityEntityList) {
        ArrayList<CapabilityListType.Capability> capabilities = new ArrayList<>();
        for (CapabilityEntity capabilityEntity : capabilityEntityList) {
            CapabilityListType capabilityListType = CapabilityListType.Factory.newInstance();
            CapabilityListType.Capability capability = capabilityListType.addNewCapability();
            TextDocument textDocument = TextDocument.Factory.newInstance();
            TextType textType = textDocument.addNewText();
            if(capabilityEntity.getTextEntity() !=null && capabilityEntity.getTextEntity().getDefinition() != null) {
                textType.setDefinition(capabilityEntity.getTextEntity().getDefinition());
            } else {
                textType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(capabilityEntity.getTextEntity() != null && capabilityEntity.getTextEntity().getLabel() != null) {
                textType.setLabel(capabilityEntity.getTextEntity().getLabel());
            } else {
                textType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(capabilityEntity.getTextEntity() != null && capabilityEntity.getTextEntity().getValue() != null) {
                textType.setValue(capabilityEntity.getTextEntity().getValue());
            } else {
                textType.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            textDocument.setText(textType);
            capability.set(textDocument);
            if(capabilityEntity.getName() != null) {
                capability.setName(capabilityEntity.getName());
            } else {
                capability.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            capabilities.add(capability);
        }
        return capabilities.toArray(new CapabilityListType.Capability[capabilities.size()]);
    }


    public static DescribedObjectType.Characteristics[] convertCharacteristics(List<CharacteristicsEntity> characteristicsEntities) {
        ArrayList<DescribedObjectType.Characteristics> characteristics = new ArrayList<>();
        for (CharacteristicsEntity characteristicsEntity : characteristicsEntities) {
            DescribedObjectType.Characteristics characteristics1 = DescribedObjectType.Characteristics.Factory.newInstance();
            CharacteristicListType characteristicListType = CharacteristicListType.Factory.newInstance();
            if(characteristicsEntity.getCharacteristicEntityList() != null) {
                CharacteristicListType.Characteristic[] characteristicArray = convertCharacteristicListType(characteristicsEntity.getCharacteristicEntityList());
                characteristicListType.setCharacteristicArray(characteristicArray);
            }
            if(characteristicsEntity.getName() != null) {
                characteristics1.setName(characteristicsEntity.getName());
            } else  {
                characteristics1.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            characteristics1.setCharacteristicList(characteristicListType);
            characteristics.add(characteristics1);
        }
        return characteristics.toArray(new DescribedObjectType.Characteristics[characteristics.size()]);
    }

    public static CharacteristicListType.Characteristic[] convertCharacteristicListType(List<CharacteristicEntity> characteristicEntityList) {
        ArrayList<CharacteristicListType.Characteristic> characteristics = new ArrayList<>();
        for (CharacteristicEntity characteristicEntity : characteristicEntityList) {
            CharacteristicListType characteristicListType = CharacteristicListType.Factory.newInstance();
            CharacteristicListType.Characteristic characteristic = characteristicListType.addNewCharacteristic();
            TextDocument textDocument = TextDocument.Factory.newInstance();
            TextType textType = TextType.Factory.newInstance();
            if(characteristicEntity.getName() != null) {
                characteristic.setName(characteristicEntity.getName());
            } else {
                characteristic.setName(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(characteristicEntity.getTextEntity() != null && characteristicEntity.getTextEntity().getDefinition() != null) {
                textType.setDefinition(characteristicEntity.getTextEntity().getDefinition());
            } else {
                textType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(characteristicEntity.getTextEntity() != null && characteristicEntity.getTextEntity().getValue() != null) {
                textType.setValue(characteristicEntity.getTextEntity().getValue());
            } else {
                textType.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(characteristicEntity.getTextEntity() != null && characteristicEntity.getTextEntity().getLabel() != null) {
                textType.setLabel(characteristicEntity.getTextEntity().getLabel());
            } else {
                textType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
            }
            textDocument.setText(textType);
            characteristic.set(textDocument);
            characteristics.add(characteristic);
        }
        return characteristics.toArray(new CharacteristicListType.Characteristic[characteristics.size()]);
    }

    public static DescribedObjectType.ValidTime[] convertValidTimeList(List<ValidTimeEntity> validTimeEntityList) {
        ArrayList<DescribedObjectType.ValidTime> validTimes = new ArrayList<>();
        for (ValidTimeEntity validTimeEntity : validTimeEntityList) {
            DescribedObjectType.ValidTime validTime = DescribedObjectType.ValidTime.Factory.newInstance();
            TimePeriodType timePeriodType = TimePeriodType.Factory.newInstance();
//        TimeInstantType timeInstantType = TimeInstantType.Factory.newInstance();
            TimePositionType timePositionTypeStart = TimePositionType.Factory.newInstance();
            TimePositionType timePositionTypeEnd = TimePositionType.Factory.newInstance();
            if(validTimeEntity.getTimePeriodEntity() != null && validTimeEntity.getTimePeriodEntity().getBeginTime() != null) {
                timePositionTypeStart.setStringValue(validTimeEntity.getTimePeriodEntity().getBeginTime());
            } else {
                timePositionTypeStart.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(validTimeEntity.getTimePeriodEntity() != null && validTimeEntity.getTimePeriodEntity().getEndTime() != null) {
                timePositionTypeEnd.setStringValue(validTimeEntity.getTimePeriodEntity().getEndTime());
            } else {
                timePositionTypeEnd.setStringValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(validTimeEntity.getTimePeriodEntity() != null && validTimeEntity.getTimePeriodEntity().getId() != null) {
                timePeriodType.setId(validTimeEntity.getTimePeriodEntity().getId());
            } else {
                timePeriodType.setId(XmlOptionHelper.NullPointHandler("string",false));
            }
            timePeriodType.setBeginPosition(timePositionTypeStart);
            timePeriodType.setEndPosition(timePositionTypeEnd);
            validTime.setTimePeriod(timePeriodType);
            validTimes.add(validTime);
        }
        return validTimes.toArray(new DescribedObjectType.ValidTime[validTimes.size()]);
    }

    public static KeywordListPropertyType[] convertKeywordList(List<KeyWordEntity> keywords) {
        ArrayList<KeywordListPropertyType> keywordListPropertyTypes = new ArrayList<>();
        for (KeyWordEntity keyword : keywords) {
            KeywordListPropertyType keywordListPropertyType = KeywordListPropertyType.Factory.newInstance();
            KeywordListType keywordListType = KeywordListType.Factory.newInstance();
            if(keyword.getKeywords() != null) {
                keywordListType.setKeywordArray(keyword.getKeywords().toArray(new String[keyword.getKeywords().size()]));
            }
            keywordListPropertyType.setKeywordList(keywordListType);
            keywordListPropertyTypes.add(keywordListPropertyType);
        }
        return keywordListPropertyTypes.toArray(new KeywordListPropertyType[keywordListPropertyTypes.size()]);
    }

    public static IdentifierListPropertyType[] convertIdentifierList(List<IdentifierEntity> identifierEntityList) {
        ArrayList<IdentifierListPropertyType> identifierListPropertyTypes = new ArrayList<>();
        for (IdentifierEntity identifierEntity : identifierEntityList) {
            IdentifierListPropertyType identifierListPropertyType = IdentifierListPropertyType.Factory.newInstance();
            IdentifierListType identifierListType = IdentifierListType.Factory.newInstance();
            if(identifierEntity.getTermEntityList() != null) {
                IdentifierListType.Identifier[] identifierArray = convertIdentifier(identifierEntity.getTermEntityList());
                identifierListType.setIdentifier2Array(identifierArray);
            }
            identifierListPropertyType.setIdentifierList(identifierListType);
            identifierListPropertyTypes.add(identifierListPropertyType);
        }
        return identifierListPropertyTypes.toArray(new IdentifierListPropertyType[identifierListPropertyTypes.size()]);
    }

    public static IdentifierListType.Identifier[] convertIdentifier(List<TermEntity> termEntityList) {
        ArrayList<IdentifierListType.Identifier> identifiers = new ArrayList<>();
        for (TermEntity termEntity : termEntityList) {
            IdentifierListType.Identifier identifier = IdentifierListType.Identifier.Factory.newInstance();
            TermType termType = TermType.Factory.newInstance();
            if(termEntity.getDefinition() != null ) {
                termType.setDefinition(termEntity.getDefinition());
            }else {
                termType.setDefinition(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(termEntity.getLabel() != null) {
                termType.setLabel(termEntity.getLabel());
            }else {
                termType.setLabel(XmlOptionHelper.NullPointHandler("string",false));
            }
            if(termEntity.getValue() != null) {
                termType.setValue(termEntity.getValue());
            }else {
                termType.setValue(XmlOptionHelper.NullPointHandler("string",false));
            }
            termType.setValue(termEntity.getValue());
            identifier.setTerm(termType);
            identifiers.add(identifier);
        }
        return identifiers.toArray(new IdentifierListType.Identifier[identifiers.size()]);
    }


}