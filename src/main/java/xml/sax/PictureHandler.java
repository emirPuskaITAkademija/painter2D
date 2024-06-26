package xml.sax;

import commons.Refreshable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import shape.EllipseShape;
import shape.PaintShape;
import shape.RectangleShape;

import java.awt.*;
import java.util.List;

public class PictureHandler extends DefaultHandler {

    private int x;
    private boolean xOpen = false;
    private int y;
    private boolean yOpen = false;
    private String color ;
    private boolean colorOpen = false;
    private String type;
    private boolean typeOpen;

    private final Refreshable refreshable;
    private final List<PaintShape> paintShapes;


    public PictureHandler(List<PaintShape> paintShapes){
        this(paintShapes, Refreshable.EMPTY);
    }

    public PictureHandler(List<PaintShape> paintShapes, Refreshable refreshable){
        this.paintShapes = paintShapes;
        this.refreshable = refreshable;
    }


    @Override
    public void startElement(String uri, String localName, String xmlElementName, Attributes attributes) throws SAXException {
        if("x".equals(xmlElementName)){
            xOpen = true;
        }else if ("y".equals(xmlElementName)){
            yOpen = true;
        }else if ("color".equals(xmlElementName)){
            colorOpen = true;
        }else if ("type".equals(xmlElementName)){
            typeOpen = true;
        }
    }

    @Override
    public void characters(char[] contentOfXmlElement, int start, int length) throws SAXException {
        String xmlContent = new String(contentOfXmlElement, start, length);
        if(xOpen){
            xOpen = false;
            x = Integer.parseInt(xmlContent);
        }else if (yOpen){
            yOpen = false;
            y = Integer.parseInt(xmlContent);
        }else if (colorOpen){
            colorOpen = false;
            color = xmlContent;
        }else if (typeOpen){
            typeOpen = false;
            type = xmlContent;
        }
    }

    @Override
    public void endElement(String uri, String localName, String xmlElementName) throws SAXException {
        if("shape".equals(xmlElementName)){
            Color paintColor = color.equals("RED") ? Color.RED: Color.BLUE;
            PaintShape paintShape = type.equals("CIRCLE") ? new EllipseShape(x, y, paintColor): new RectangleShape(x, y, paintColor);
            paintShapes.add(paintShape);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        refreshable.refresh();
    }
}
