package shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends PaintShape{
    public RectangleShape(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public Shape createShape() {
        return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
    }
}
