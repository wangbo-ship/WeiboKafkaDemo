package org.cug.geodt.weibo.sos.utils.parser.insertSensorRequest;

import net.opengis.gml.x32.CodeWithAuthorityType;
import net.opengis.sensorml.x20.PhysicalComponentDocument;
import net.opengis.sensorml.x20.PhysicalComponentType;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.pojo.sensor.info.GroundStationFixSensor;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.utils.StringUtils;
import org.geotools.temporal.object.DefaultInstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Author WJW
 * Date 2023/7/7 15:07
 */
@Repository
@Component
public class InsertSensorParser {

    @Autowired
    SensorInfo newSensorInfo;

    @Autowired
    GroundStationFixSensor groundStationFixSensor;


    public static String apply(String xml) throws XmlException {
        PhysicalComponentDocument physicalComponentDocument = PhysicalComponentDocument.Factory.parse(xml);
        PhysicalComponentType physicalComponentType = physicalComponentDocument.getPhysicalComponent();
        CodeWithAuthorityType identifier = physicalComponentType.getIdentifier();
        String sensorType = identifier.getStringValue();
        return sensorType;
    }

    /**
     * 用于解析describeSensorXMl(SensorML)
     * @param xml
     * @return
     * @throws XmlException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public SensorInfo SensorMLConvertToObject(String xml) throws IOException, ParserConfigurationException, SAXException {

//        FESConfiguration configuration = new FESConfiguration();
//        Parser parser = new Parser(configuration);
//        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
//        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(inputStream);
        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        Set<String> strings = map.keySet();
        strings.forEach(s -> System.out.println(s));

        if (map.containsKey("identifier")) {
            newSensorInfo.setSensorId(map.get("identifier").toString());
            newSensorInfo.setSensorType(map.get("identifier").toString());
        }
        if (map.containsKey("description")) {
            newSensorInfo.setDescription(map.get("description").toString());
        }
        if (map.containsKey("identification")) {
            HashMap<String, ?> identification = (HashMap<String, ?>) map.get("identification");
            if (identification.containsKey("identifier")) {
                List<HashMap<String,?>> identifiers = (List<HashMap<String,?>>)identification.get("identifier");
                for (HashMap<String,?> identifier: identifiers) {
                    if (identifier.get("label").equals("Sensor Long Name")) {
                        newSensorInfo.setSensorLongName(identifier.get("value").toString());
                    }
                    if (identifier.get("label").equals("Platform Id")) {
                        newSensorInfo.setFkPlatform(identifier.get("value").toString());
                    }
                    if (identifier.get("label").equals("Sensor Short Name")) {
                        newSensorInfo.setSensorShortName(identifier.get("value").toString());
                    }
                    if (identifier.get("label").equals("Create User Id")) {
                        newSensorInfo.setCreateUserId(identifier.get("value").toString());
                    }
                }
            }
        }
        if (map.containsKey("validTime")){

        }
        if (map.containsKey("characteristics")) {
            HashMap<String, ?> characteristics = (HashMap<String, ?>) map.get("characteristics");
            if (characteristics.containsKey("CharacteristicList")) {
                HashMap<String, ?> characteristicList = (HashMap<String, ?>) characteristics.get("CharacteristicList");
                if (characteristicList.containsKey("characteristic")){
                    for (HashMap<String, ?> characteristic : (List<HashMap<String, ?>>) characteristicList.get("characteristic")) {
                        if(characteristic.containsKey("Text")) {
                            HashMap<String, ?> text = (HashMap<String, ?>) characteristic.get("Text");
                            if (text.get("label").equals("Sensor Length")) {
                                newSensorInfo.setSensorLength(Float.parseFloat(text.get("value").toString()));
                            }
                            if (text.get("label").equals("Sensor Width")) {
                                newSensorInfo.setSensorWidth(Float.parseFloat(text.get("value").toString()));
                            }
                            if (text.get("label").equals("Arcrole")) {
                                newSensorInfo.setArcrole(text.get("value").toString());
                            }
                            if (text.get("label").equals("Sensor Weight")) {
                                newSensorInfo.setSensorWeight(Float.parseFloat(text.get("value").toString()));
                            }
                            if (text.get("label").equals("Sensor Height")) {
                                newSensorInfo.setSensorHeight(Float.parseFloat(text.get("value").toString()));
                            }
                        }
                    }
                }
            }
        }

        if(map.containsKey("capabilities")) {
            HashMap<String, ?> capabilities = (HashMap<String, ?>) map.get("capabilities");
            if (capabilities.containsKey("CapabilityList")) {
                HashMap<String, ?> capabilityList = (HashMap<String, ?>) capabilities.get("CapabilityList");
                if (capabilityList.containsKey("capability")) {
                    for (HashMap<String, ?> capability : (List<HashMap<String, ?>>) capabilityList.get("capability")) {
                        if (capability.containsKey("Quantity") && capability.containsKey("name")) {
                            HashMap<String, ?> quantity = (HashMap<String, ?>) capability.get("Quantity");
                            if (capability.get("name").equals("Obs Theme")) {
                                newSensorInfo.setObsTheme(quantity.get("value").toString());
                            }
                            if (capability.get("name").equals("Obs Radius")) {
                                newSensorInfo.setObsRadius(Float.parseFloat(quantity.get("value").toString()));
                            }
                        }
                    }

                }
            }
        }

        if(map.containsKey("contacts")) {
            HashMap<String, ?> contacts = (HashMap<String, ?>) map.get("contacts");
            if(contacts.containsKey("contact")) {
                for (HashMap<String, ?> contact : (List<HashMap<String, ?>>) contacts.get("contact")) {
                    if(contact.containsKey("title") && contact.get("title").equals("pointOfContact")) {
                        if(contact.containsKey("CI_ResponsibleParty")) {
                            HashMap<String,?> ci_responsibleParty = (HashMap<String,?>)contact.get("CI_ResponsibleParty");
                            if (ci_responsibleParty.containsKey("organisationName")) {
                                newSensorInfo.setOrganisationName(ci_responsibleParty.get("organisationName").toString());
                            }
                            if (ci_responsibleParty.containsKey("contactInfo")) {
                                HashMap<String, ?> contactInfo = (HashMap<String, ?>) ci_responsibleParty.get("contactInfo");
                                if (contactInfo.containsKey("country")) {
                                    newSensorInfo.setCountry(contactInfo.get("country").toString());
                                }
                                if (contactInfo.containsKey("city")) {
                                    newSensorInfo.setCity(contactInfo.get("city").toString());
                                }
                            }
                        }
                    }
                    if(contact.containsKey("title") && contact.get("title").equals("owner")) {
                        HashMap<String,?> ci_responsibleParty= (HashMap<String,?>)contact.get("CI_ResponsibleParty");
                        if (ci_responsibleParty.containsKey("contactInfo")) {
                            HashMap<String,?> contactInfo = (HashMap<String,?>)ci_responsibleParty.get("contactInfo");
                            if (contactInfo.containsKey("address")) {
                                HashMap<String,?> address = (HashMap<String,?>)contactInfo.get("address");
                                if(address.containsKey("postalCode")) {
                                    newSensorInfo.setPostalCode(address.get("postalCode").toString());
                                }
                                if (address.containsKey("electronicMailAddress")) {
                                    newSensorInfo.setEmail("electronicMailAddress");
                                }
                            }
                            if (contactInfo.containsKey("phone")) {
                                HashMap<String, ?> phone = (HashMap<String, ?>) contactInfo.get("phone");
                                if(phone.containsKey("voice")){
                                    newSensorInfo.setTelephone(phone.get("voice").toString());
                                }
                            }
                        }
                    }
//                    if(contact.containsKey("title") && contact.get("title") == "operator"){
////                        contact.get("")
//                    }
//                    if(contact.containsKey("title") && contact.get("title") == "principalInvestigator") {
//
//                    }
//                    if(contact.containsKey("title") && contact.get("title") == "manufacturer") {
//
//                    }
                }
            }
        }

        if (map.containsKey("history")) {
            HashMap<String, ?> history = (HashMap<String, ?>) map.get("history");
            if (history.containsKey("event")) {
                for (HashMap<String, ?> event : (List<HashMap<String, ?>>) history.get("event")) {
                    if (event.containsKey("label") && event.get("label").equals("Installation")) {
                        if (event.containsKey("time")) {
                            DefaultInstant time = (DefaultInstant)event.get("time");
                            if (time.getPosition() != null ) {
                                newSensorInfo.setSensorInstallTime(time.getPosition().getDate().getTime());
                            }
                        }
                    }
                    if (event.containsKey("label") && event.get("label").equals("Deployment")) {
                        if (event.containsKey("time")) {
                            DefaultInstant time = (DefaultInstant)event.get("time");
                            if (time.getPosition() != null ) {
                                newSensorInfo.setSensorDeployTime(time.getPosition().getDate().getTime());
                            }
                        }
                    }
                }
            }
        }
        //组装metric需要数据的类型！！！
//        if (map.containsKey("inputs")) {
//            HashMap<String, ?> inputs = (HashMap<String, ?>) map.get("inputs");
//            if (inputs.containsKey("input")) {
//                for (HashMap<String, ?> input : (List<HashMap<String, ?>>) inputs.get("inputs")) {
//                    if (input.get("name") != null) {
////                        newSensorInfo.setMetrics();
//                    }
//                }
//
//            }
//        }
        if (map.containsKey("parameters")) {
            HashMap<String, ?> parameters = (HashMap<String, ?>) map.get("parameters");
            if (parameters.containsKey("parameter")) {
                for (HashMap<String, ?> parameter : (List<HashMap<String, ?>>) parameters.get("parameter")) {
                    if (parameter.containsKey("name") && parameter.containsKey("Quantity")) {
                        HashMap<String, ?> quantity = (HashMap<String, ?>) parameter.get("Quantity");
                        if (parameter.get("name").equals("Sample Duration") ){
                            newSensorInfo.setSampleDuration(Long.valueOf(quantity.get("value").toString()));
                        }
                        if (parameter.get("name").equals("Sample Interval") ){
                            newSensorInfo.setSampleInterval(Long.valueOf(quantity.get("value").toString()));
                        }
                    }
                }

            }
        }

        if(map.containsKey("position")) {
            HashMap<String, ?> position = (HashMap<String, ?>) map.get("position");
            if (position.containsKey("referenceFrame")) {
                newSensorInfo.setSrid(position.get("referenceFrame").toString());
            }
            if(position.containsKey("coordinate")) {
                for (HashMap<String, ?> coordinate : (List<HashMap<String, ?>>) position.get("coordinate")) {
                    HashMap<String, ?> quantity = (HashMap<String, ?>) coordinate.get("Quantity");
                    if (quantity.containsKey("axisID") && quantity.get("axisID").equals("Lat")) {
                        newSensorInfo.setSensorLatitude(Float.parseFloat(quantity.get("value").toString()));
                    }
                    if (quantity.containsKey("axisID") && quantity.get("axisID").equals("Long")) {
                        newSensorInfo.setSensorLongitude(Float.parseFloat(quantity.get("value").toString()));
                    }
                    if (quantity.containsKey("axisID") && quantity.get("axisID").equals("Alt")) {
                        newSensorInfo.setSensorAltitude(Float.parseFloat(quantity.get("value").toString()));
                    }
                }

            }
        }


        return newSensorInfo;
    }



}
