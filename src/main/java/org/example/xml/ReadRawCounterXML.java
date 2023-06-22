package org.example.xml;

import lombok.Data;
import org.example.Main;
import org.example.common.Const;
import org.example.dto.Ericsson3gDTO;
import org.example.dto.StudentDTO;
import org.example.service.XmlService;
import org.example.utils.DateUtils;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.*;
import javax.xml.stream.events.Attribute;

@Data
public class ReadRawCounterXML extends BaseXml {

    //    static String url = "E:/nifi/convert-file/src/main/resources/file/A20230604.1130+0700-1145+0700_SubNetwork=R_34708E_KHA,MeContext=3G_NTG215S_KHA,ManagedElement=3G_NTG215S_KHA_statsfile.xml";
    static String url = "/Users/seu/Documents/telsoft/oss/convert-file/src/main/resources/file/A20230604.1130+0700-1145+0700_SubNetwork=R_34708E_KHA,MeContext=3G_NTG215S_KHA,ManagedElement=3G_NTG215S_KHA_statsfile.xml";
    private String dateTime = "";
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        //write
        Ericsson3gDTO dto = new Ericsson3gDTO();
        Ericsson3gDTO.MeasInfo measInfo = null;
        Ericsson3gDTO.MeasType measType = null;
        Ericsson3gDTO.MeasValue measValue = null;

        Map<String, String> rMap = new HashMap<>();
        String keyMap = null;
        // get file xml
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(url));

        int checkIndex = 0;
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            int index = event.getEventType();
            switch (index) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    // GET TAG NAME
                    String qName = startElement.getName().getLocalPart();
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    //get attribute getFirst
                    if (dto.getBeginTime() == null) {
                        // Check tagname
                        if (Const.EricssonRC.MEASCOLLEC.equals(qName)) {
                            dto.setBeginTime(DateUtils.parseStringToTimestamp(XmlService.getAttributeOfTagByName
                                    (startElement, Const.EricssonRC.MEASCOLLEC, "beginTime", attributes)));
                        }
                    }
                    // MEAS INFO
                    if (Const.EricssonRC.MEAS_INFO.equals(qName)) {
                        measInfo = new Ericsson3gDTO.MeasInfo();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals("measInfoId")) {
                                measInfo.setMeasInfoId(attribute.getValue());
                            }
                        }
                    }
                    // MEAS TYPE
                    if (Const.EricssonRC.MEAS_TYPE.equals(qName)) {
                        measType = new Ericsson3gDTO.MeasType();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals("p")) {
                                measType.setP(Integer.parseInt(attribute.getValue()));
                            }
                        }
                    }

                    // MEAS VALUE
                    if (Const.EricssonRC.MEAS_VALUE.equals(qName)) {
                        measValue = new Ericsson3gDTO.MeasValue();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals("measObjLdn")) {
                                measValue.setMeasObjLdn(attribute.getValue());
                            }
                        }
                    }

                    if (Const.EricssonRC.R.equals(qName)) {
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals("p")) {
                                keyMap = attribute.getValue();
                            }
                        }
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String data = event.asCharacters().getData();
                    if (measType != null) {
                        measType.setText(data);
                    }
                    if (rMap != null && keyMap != null) {
                        rMap.put(keyMap, data);
                        keyMap = null;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    // MEAS INFO
                    if (measInfo != null && Const.EricssonRC.MEAS_INFO.equals(endElement.getName().getLocalPart())) {
                        dto.getMeasInfos().add(measInfo);
                        measInfo = null;
                    }
                    // MEAS INFO
                    if (measType != null && Const.EricssonRC.MEAS_TYPE.equals(endElement.getName().getLocalPart())) {
                        measInfo.getMeasType().add(measType);
                        measType = null;
                    }
                    // MEAS VALUE
                    if (measValue != null && Const.EricssonRC.MEAS_VALUE.equals(endElement.getName().getLocalPart()) && !rMap.isEmpty()) {
                        measValue.setR(rMap);
                        measInfo.getMeasValue().add(measValue);
                        measValue = null;
                        rMap = new HashMap<>();
                    }
                    break;
                default:
                    break;
            }

        }

        System.out.println(dto.toString());
//        dto.getMeasInfos().forEach(System.out::println);
        dto.getMeasInfos().forEach(
                e->{
                    System.out.println(e.getMeasValue().size());
                    e.getMeasValue().forEach(System.out::println);
                }
        );
        System.out.println(dto.getMeasInfos().size());
    }

    @Override
    void startElementEvent(XMLEvent xmlEvent) {

    }

    @Override
    void characterEvent(XMLEvent xmlEvent) {

    }

    @Override
    void endElementEvent(XMLEvent xmlEvent) {

    }


//    public static Ericsson3gDTO redEricsson3gDTO() throws XMLStreamException, FileNotFoundException {
//        Ericsson3gDTO ericsson3gDTO = new Ericsson3gDTO();
//        File inputFile = new File(url);
//        InputStream is = new FileInputStream(inputFile);
//        XMLInputFactory factory = XMLInputFactory.newInstance();
//        XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(is);
//
//        while (xmlStreamReader.hasNext()) {
//            String tagContent = "";
//            String elementName = "";
//            String attribute = "";
//            QName attributeName = null;
//            int event = xmlStreamReader.next();
//            switch (event) {
//                case XMLStreamConstants.START_ELEMENT:
//                    elementName = xmlStreamReader.getLocalName();
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                    tagContent = xmlStreamReader.getText().trim();
//                    break;
//                case XMLStreamConstants.END_ELEMENT:
//                    break;
//                default:
//                    break;
//            }
//
//            switch (elementName) {
//                case Const.EricssonRC.MEASCOLLEC:
//                    attribute = xmlStreamReader.getAttributeValue(0);
//                    attributeName = xmlStreamReader.getAttributeName(0);
//                    break;
//                default:
//                    break;
//            }
//            System.out.println("tag:" + elementName + "- contnet:" + tagContent + "- attribute:"
//                    + attribute + "- attribute name:" + attributeName);
//        }
//        return ericsson3gDTO;
//    }
}
