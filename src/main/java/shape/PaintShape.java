package shape;

import java.awt.*;

public abstract class PaintShape {
    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_HEIGHT = 5;

    private int x;
    private int y;
    private Color color;
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    public PaintShape(int x, int y, Color color) {
        this(x, y, color, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PaintShape(int x, int y, Color color, int width, int height) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public abstract Shape createShape();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
