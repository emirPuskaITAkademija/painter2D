package xml.jaxb;

import shape.EllipseShape;
import shape.PaintShape;
import shape.RectangleShape;
import xml.XmlPictureParser;
import xml.jaxb.generated.ObjectFactory;
import xml.jaxb.generated.Shape;
import xml.jaxb.generated.Shapes;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Potreban nam je:
 * <li>XSD</li>
 * <li>generisane klase</li>
 *
 * <p>
 * JAXB - XSD šemu i XJC
 * </p>
 */
public class JAXBParser implements XmlPictureParser {
    @Override
    public List<PaintShape> readPicture(String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("xml.jaxb.generated");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Shapes shapes = (Shapes) unmarshaller.unmarshal(new FileReader(fileName));
            return shapes
                    .getShape()
                    .stream()
                    .map(this::toPaintShape)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private PaintShape toPaintShape(Shape shape) {
        Color color = shape.getColor().equals("BLUE") ? Color.BLUE : Color.RED;
        int x = shape.getX().intValue();
        int y = shape.getY().intValue();
        PaintShape paintShape = shape.getType().equals("CIRCLE") ?
                new EllipseShape(x, y, color)
                : new RectangleShape(x, y, color);
        return paintShape;
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String fileName) {
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            Shapes shapes = objectFactory.createShapes();
            for (PaintShape paintShape : paintShapes) {
                Shape shape = fromPaintShape(paintShape);
                shapes.getShape().add(shape);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance("xml.jaxb.generated");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(shapes, new FileWriter(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private Shape fromPaintShape(PaintShape paintShape) {
        ObjectFactory objectFactory = new ObjectFactory();
        Shape shape = objectFactory.createShape();
        shape.setX(BigInteger.valueOf(paintShape.getX()));
        shape.setY(BigInteger.valueOf(paintShape.getY()));
        shape.setColor(paintShape.getColor().equals(Color.BLUE) ? "BLUE" : "RED");
        shape.setType((paintShape instanceof EllipseShape) ? "CIRCLE" : "SQUARE");
        return shape;
    }
}
