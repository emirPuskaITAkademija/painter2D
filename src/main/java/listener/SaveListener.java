package listener;

import gui.PaintPanel;
import gui.PaintWindow;
import shape.EllipseShape;
import shape.PaintShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SaveListener implements ActionListener {

    public static final String PICTURE_EXTENSION = ".xml";
    public static final String CIRCLE = "CIRCLE";
    public static final String SQUARE = "SQUARE";

    @Override
    public void actionPerformed(ActionEvent e) {
        String pictureName = JOptionPane.showInputDialog("Unesite ime slike");
        if (pictureName == null || pictureName.isBlank()) {
            JOptionPane.showMessageDialog(null, "Nisi unio ime slike i ne možeš je snimit");
            return;
        }
        if (!pictureName.endsWith(PICTURE_EXTENSION)) {
            pictureName = pictureName + PICTURE_EXTENSION;
        }
        PaintWindow paintWindow = PaintWindow.instance();
        PaintPanel paintPanel = paintWindow.getPaintPanel();

        List<PaintShape> paintShapes = paintPanel.getPaintShapes();
        try (PrintWriter out = new PrintWriter(new FileWriter(pictureName))) {
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
            JOptionPane.showMessageDialog(null, "Sačuvana slika");
            paintPanel.clear();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            JOptionPane.showMessageDialog(null, "Greska: " + exception.getMessage());
        }
    }
}
