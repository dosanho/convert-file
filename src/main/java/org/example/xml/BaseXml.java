package org.example.xml;

import javax.xml.stream.events.XMLEvent;

public abstract class BaseXml {
    abstract void  startElementEvent(XMLEvent xmlEvent);
    abstract void  characterEvent(XMLEvent xmlEvent);
    abstract void  endElementEvent(XMLEvent xmlEvent);
}
