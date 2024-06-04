package xml.stax;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import java.io.FileReader;

import static javax.xml.stream.XMLStreamConstants.*;

public class StaxIteratorDemo {
    public static void main(String[] args) {
        try{
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileReader("note.xml"));
            while (xmlEventReader.hasNext()){
                //Ovdje je sada razlika
                //Ovaj xmlEvent u sevbi ima sve atribute dok je kod kursora potrebno pozvati nextElement
                //dovuče cijeli element a DOM cijeli dokument
                XMLEvent xmlEvent =  xmlEventReader.nextEvent();
                int eventType = xmlEvent.getEventType();
                switch (eventType){
                    case START_ELEMENT://ali ovdje možemo čitati i atribute
                        System.out.println("START element:" + xmlEvent.asStartElement().getName());
                        break;
                    case CHARACTERS:
                        System.out.println("CONTENT of element: " + xmlEvent.asCharacters().getData());
                        break;
                    case END_ELEMENT:
                        System.out.println("END element: " + xmlEvent.asEndElement().getName());
                        break;
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
