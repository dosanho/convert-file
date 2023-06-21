package org.example.xml;

import org.example.Main;
import org.example.dto.Ericsson3gDTO;
import org.example.dto.StudentDTO;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ReadRawCounterXML {

    static String url = "/Users/seu/Documents/telsoft/oss/convert-file/src/main/resources/file/A20230604.1130+0700-1145+0700_SubNetwork=R_34708E_KHA,MeContext=3G_NTG215S_KHA,ManagedElement=3G_NTG215S_KHA_statsfile.xml";

    public static void main(String[] args) {
        Ericsson3gDTO ericsson3gDTO = new Ericsson3gDTO();
        try {
            ericsson3gDTO = ReadRawCounterXML.redEricsson3gDTO();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static Ericsson3gDTO redEricsson3gDTO() throws XMLStreamException, FileNotFoundException {
        Ericsson3gDTO ericsson3gDTO = new Ericsson3gDTO();
        File inputFile = new File(url);
        InputStream is = new FileInputStream(inputFile);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(is);

        String elementName;
        while (xmlStreamReader.hasNext()) {
            int event = xmlStreamReader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    System.out.println(xmlStreamReader.getLocalName());
                    break;
                default:
                    break;
            }
        }
        return ericsson3gDTO;
    }
}
