package xml.sax;

import shape.PaintShape;
import xml.XmlPictureParser;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Collections;
import java.util.List;

public class SaxParser implements XmlPictureParser {
    @Override
    public List<PaintShape> readPicture(String fileName) {
        //SAX -> zakači se na ovu sliku i pushaj mi sadržaj
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            PictureHandler pictureHandler = new PictureHandler();
            saxParser.parse(fileName, pictureHandler);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String fileName) {

    }
}
