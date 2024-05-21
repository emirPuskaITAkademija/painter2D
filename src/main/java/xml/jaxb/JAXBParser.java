package xml.jaxb;

import shape.PaintShape;
import xml.XmlPictureParser;

import java.util.List;

public class JAXBParser implements XmlPictureParser {
    @Override
    public List<PaintShape> readPicture(String fileName) {
        return null;
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String fileName) {

    }
}
