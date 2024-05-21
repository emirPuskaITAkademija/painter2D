package xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import shape.EllipseShape;
import shape.PaintShape;
import shape.RectangleShape;
import xml.XmlPictureParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static xml.sax.SaxParser.CIRCLE;
import static xml.sax.SaxParser.SQUARE;

public class DomParser implements XmlPictureParser {
    @Override
    public List<PaintShape> readPicture(String fileName) {
        try {
            List<PaintShape> paintShapes = new ArrayList<>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(fileName));
            Element documentElement = document.getDocumentElement();
            NodeList nodeList = documentElement.getElementsByTagName("shape");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element shapeElement = (Element) node;
                    Element xElement = (Element) shapeElement.getElementsByTagName("x").item(0);
                    Element yElement = (Element) shapeElement.getElementsByTagName("y").item(0);
                    Element colorElement = (Element) shapeElement.getElementsByTagName("color").item(0);
                    Element typeElement = (Element) shapeElement.getElementsByTagName("type").item(0);

                    int x = Integer.parseInt(xElement.getTextContent());
                    int y = Integer.parseInt(yElement.getTextContent());
                    Color color = colorElement.getTextContent().equals("BLUE") ? Color.BLUE : Color.RED;
                    PaintShape paintShape = typeElement.getTextContent().equals("SQUARE") ?
                            new RectangleShape(x, y, color) :
                            new EllipseShape(x, y, color);
                    paintShapes.add(paintShape);
                }
            }
            return paintShapes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String fileName) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element shapesElement = document.createElement("shapes");
            document.appendChild(shapesElement);
            for (PaintShape paintShape : paintShapes) {
                Element shapeElement = document.createElement("shape");

                Element xElement = document.createElement("x");
                xElement.setTextContent(paintShape.getX() + "");
                shapeElement.appendChild(xElement);

                Element yElement = document.createElement("y");
                yElement.setTextContent(paintShape.getY() + "");
                shapeElement.appendChild(yElement);

                Element colorElement = document.createElement("color");
                colorElement.setTextContent(paintShape.getColor().equals(Color.BLUE) ? "BLUE" : "RED");
                shapeElement.appendChild(colorElement);

                Element typeElement = document.createElement("type");
                typeElement.setTextContent((paintShape instanceof EllipseShape) ? CIRCLE : SQUARE);
                shapeElement.appendChild(typeElement);

                shapesElement.appendChild(shapeElement);
            }
            //Document sam dobio po w3c tree strukturi
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult transformedResult = new StreamResult(new File(fileName));
            transformer.transform(domSource, transformedResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
