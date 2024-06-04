package xml.sax;

import commons.Refreshable;
import gui.PaintWindow;
import shape.EllipseShape;
import shape.PaintShape;
import xml.XmlPictureParser;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SaxParser implements XmlPictureParser {

    public static final String PICTURE_EXTENSION = ".xml";
    public static final String CIRCLE = "CIRCLE";
    public static final String SQUARE = "SQUARE";

    @Override
    public List<PaintShape> readPicture(String fileName) {
        List<PaintShape> paintShapes = new ArrayList<>();
        //SAX -> zakači se na ovu sliku i pushaj mi sadržaj
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            PictureHandler pictureHandler = new PictureHandler(paintShapes);
            saxParser.parse(fileName, pictureHandler);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return paintShapes;
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String fileName) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<shapes>");
            for (PaintShape paintShape : paintShapes) {
                out.println("<shape>");
                out.println("<x>" + paintShape.getX() + "</x>");
                out.println("<y>" + paintShape.getY() + "</y>");
                String color = paintShape.getColor().equals(Color.BLUE) ? "BLUE" : "RED";
                out.println("<color>" + color + "</color>");
                String type = (paintShape instanceof EllipseShape) ? CIRCLE : SQUARE;
                out.println("<type>" + type + "</type>");
                out.println("</shape>");
            }
            out.println("</shapes>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
