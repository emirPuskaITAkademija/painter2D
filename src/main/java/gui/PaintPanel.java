package gui;

import shape.EllipseShape;
import shape.PaintShape;
import shape.RectangleShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Platno za slikanje sličica.
 * <p>
 *     Naša sličica nije ništa drugo nego List<PaintShape>.
 *     Kada neko povlači mišem po tom platnu za slikanje ili kako mi programeri
 *     volimo kazati PaintPanel instanci on puni tu našu listu i od mnoštva
 *     PaintShape instanci kreira jednu ili više linija od kojih je satkana naša slika.
 * </p>
 *<p>
 *     <li>MouseListener -> puni paintShapes listu</li>
 *     <li>paintComponent funkcija -> uzima iz liste "friško" kreirane objekte i prebacuje
 *     ih na platno kako bi korisnik vidio vizuelno tačkice od kojih je satkana slika</li>
 *</p>
 */
public class PaintPanel extends JPanel {

    private final List<PaintShape> paintShapes = new ArrayList<>();

    public PaintPanel() {
        addMouseListener(new DrawListener());
        addMouseMotionListener(new DrawListener());
        setBackground(Color.WHITE);
    }

    public void clear(){
        paintShapes.clear();
        repaint();
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
            PaintShape paintShape = paintWindow.isCircleSelected() ? new EllipseShape(x, y, color) : new RectangleShape(x, y, color);
            paintShapes.add(paintShape);
            System.out.println(paintShapes.size());
            repaint();
        }
    }
}
