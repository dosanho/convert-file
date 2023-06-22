package org.example.service;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;
import java.util.List;

public class XmlService {

    public static String getAttributeOfTagByName(StartElement startElement, String tagName, String attributeName, Iterator<Attribute> attributes) {
        String result = null;
        while (attributes.hasNext()) {
            Attribute attribute = attributes.next();
            if (attribute.getName().toString().equals(attributeName)) {
                result = attribute.getValue();
            }
        }

        return result;
    }
}
