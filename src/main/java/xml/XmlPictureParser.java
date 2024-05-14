package xml;

import shape.PaintShape;

import java.util.List;

public interface XmlPictureParser {
    List<PaintShape> readPicture(String fileName);

    void savePicture(List<PaintShape> paintShapes, String fileName);
}
