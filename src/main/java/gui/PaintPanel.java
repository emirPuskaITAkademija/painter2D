package gui;

import shape.Ellipse;
import shape.PaintShape;
import shape.RectangleShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class PaintPanel extends JPanel {

    private final List<PaintShape> paintShapes = new ArrayList<>();

    public PaintPanel() {
        addMouseListener(new DrawListener());
        addMouseMotionListener(new DrawListener());
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (PaintShape paintShape : paintShapes) {
            graphics2D.setColor(paintShape.getColor());
            graphics2D.fill(paintShape.createShape());
            graphics2D.draw(paintShape.createShape());
        }
    }

    public List<PaintShape> getPaintShapes() {
        return paintShapes;
    }

    private class DrawListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            reactOnMouse(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            reactOnMouse(e);
        }

        private void reactOnMouse(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            PaintWindow paintWindow = PaintWindow.instance();
            Color color = paintWindow.getSelectedColor();
            PaintShape paintShape = paintWindow.isCircleSelected() ? new Ellipse(x, y, color) : new RectangleShape(x, y, color);
            paintShapes.add(paintShape);
            repaint();
        }
    }
}
