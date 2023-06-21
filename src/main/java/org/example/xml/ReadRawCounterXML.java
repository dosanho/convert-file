package org.example.xml;

import lombok.Data;
import org.example.Main;
import org.example.common.Const;
import org.example.dto.Ericsson3gDTO;
import org.example.dto.StudentDTO;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.events.Attribute;

@Data
public class ReadRawCounterXML {

    static String url = "E:\\nifi\\convert-file\\src\\main\\resources\\file\\A20230604.1130+0700-1145+0700_SubNetwork=R_34708E_KHA,MeContext=3G_NTG215S_KHA,ManagedElement=3G_NTG215S_KHA_statsfile.xml";

    private String dateTime = "";


    public static void main(String[] args) {
        Ericsson3gDTO ericsson3gDTO = new Ericsson3gDTO();
//            ericsson3gDTO = ReadRawCounterXML.redEricsson3gDTO();
        ReadRawCounterXML xml = new ReadRawCounterXML();

        xml.getDateTime(url);
        System.out.println(xml.dateTime);

    }

    public static Ericsson3gDTO redEricsson3gDTO() throws XMLStreamException, FileNotFoundException {
        Ericsson3gDTO ericsson3gDTO = new Ericsson3gDTO();
        File inputFile = new File(url);
        InputStream is = new FileInputStream(inputFile);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(is);

        while (xmlStreamReader.hasNext()) {
            String tagContent = "";
            String elementName = "";
            String attribute = "";
            QName attributeName = null;
            int event = xmlStreamReader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    elementName = xmlStreamReader.getLocalName();
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = xmlStreamReader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
                default:
                    break;
            }

            switch (elementName) {
                case Const.EricssonRC.MEASCOLLEC:
                    attribute = xmlStreamReader.getAttributeValue(0);
                    attributeName = xmlStreamReader.getAttributeName(0);
                    break;
                default:
                    break;
            }
            System.out.println("tag:" + elementName + "- contnet:" + tagContent + "- attribute:"
                    + attribute + "- attribute name:" + attributeName);
        }
        return ericsson3gDTO;
    }

    public void getDateTime(String inputFilename) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLEventReader eventReader = factory.createXMLEventReader(
                    new FileReader(inputFilename));
//            baseFileName = bulkCMXMLFileBasename = getFileBasename(inputFilename);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        if (qName.equals("fileFooter")) {
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals("dateTime")) {
                                    dateTime = attribute.getValue();
                                }
                            }
                        }

                        break;
                }


            }
        } catch (Exception e) {

        }
    }
}
