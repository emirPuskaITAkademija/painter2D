package xml.stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileReader;

//PUSH -> StAX podjeća na SAX
//XMLStreamReader - čitati da ali sa XMLStreamReader ne možemo pisati
public class StaxCursorDemo {
    public static void main(String[] args) {
        try{
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader =  xmlInputFactory.createXMLStreamReader(new FileReader("note.xml"));
            while (xmlStreamReader.hasNext()){
                int typeOfElement = xmlStreamReader.next();
                switch (typeOfElement){
                    case XMLStreamReader.START_ELEMENT -> System.out.println("START ELEMENT: " + xmlStreamReader.getName());
                    case XMLStreamReader.CHARACTERS -> System.out.println("CONTENT OF ELEMENT: " + xmlStreamReader.getText());
                    case XMLStreamReader.END_ELEMENT -> System.out.println("END ELEMENT: " + xmlStreamReader.getName());
                }
            }
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }
}
