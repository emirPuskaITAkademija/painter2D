package xml.stax;

import shape.EllipseShape;
import shape.PaintShape;
import shape.RectangleShape;
import xml.XmlPictureParser;

import javax.swing.*;
import javax.xml.stream.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * StAX parser:
 * <mod>1. PUSH(Kursor)</mod>
 * <mod>2. PULL(Iterator)</mod>
 * <p>
 * čitati i pisati
 */
public class StaxParser implements XmlPictureParser {

    public static final String BLUE = "BLUE";
    public static final String RED = "RED";

    @Override
    public List<PaintShape> readPicture(String fileName) {
        try {
            List<PaintShape> paintShapes = new ArrayList<>();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileReader(fileName));
            int x = 0;
            boolean xOpen = false;
            int y = 0;
            boolean yOpen = false;
            String color = null;
            boolean colorOpen = false;
            String type = null;
            boolean typeOpen = false;
            while (xmlStreamReader.hasNext()) {
                int typeOfElement = xmlStreamReader.next();
                switch (typeOfElement) {
                    case XMLStreamReader.START_ELEMENT:
                        String xmlElementName = xmlStreamReader.getName().toString();
                        if ("x".equals(xmlElementName)) {
                            xOpen = true;
                        } else if (xmlElementName.equals("y")) {
                            yOpen = true;
                        } else if ("color".equals(xmlElementName)) {
                            colorOpen = true;
                        } else if ("type".equals(xmlElementName)) {
                            typeOpen = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (xOpen) {
                            x = Integer.parseInt(xmlStreamReader.getText());
                        } else if (yOpen) {
                            y = Integer.parseInt(xmlStreamReader.getText());
                        } else if (colorOpen) {
                            color = xmlStreamReader.getText();
                        } else if (typeOpen) {
                            type = xmlStreamReader.getText();
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        String xmlEndElementName = xmlStreamReader.getName().toString();
                        if ("x".equals(xmlEndElementName)) {
                            xOpen = false;
                        } else if (xmlEndElementName.equals("y")) {
                            yOpen = false;
                        } else if ("color".equals(xmlEndElementName)) {
                            colorOpen = false;
                        } else if ("type".equals(xmlEndElementName)) {
                            typeOpen = false;
                        } else if ("shape".equals(xmlEndElementName)) {
                            Color colorAwt = color.equals("BLUE") ? Color.BLUE : Color.RED;
                            PaintShape paintShape = "CIRCLE".equals(type) ? new EllipseShape(x, y, colorAwt) : new RectangleShape(x, y, colorAwt);
                            paintShapes.add(paintShape);
                        }
                        break;
                }
            }
            return paintShapes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String fileName) {
        try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileWriter(fileName));
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement("shapes");
            for (PaintShape paintShape : paintShapes) {
                xmlStreamWriter.writeStartElement("shape");
                xmlStreamWriter.writeStartElement("x");
                xmlStreamWriter.writeCharacters(String.valueOf(paintShape.getX()));
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("y");
                xmlStreamWriter.writeCharacters(Integer.toString(paintShape.getY()));
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("color");
                xmlStreamWriter.writeCharacters(paintShape.getColor().equals(Color.BLUE) ? BLUE : RED);
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("type");
                xmlStreamWriter.writeCharacters((paintShape instanceof EllipseShape) ? "CIRCLE" : "SQUARE");
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
