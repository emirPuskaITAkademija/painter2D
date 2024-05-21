package listener;

import gui.PaintPanel;
import gui.PaintWindow;
import shape.PaintShape;
import xml.XmlPictureParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SaveListener implements ActionListener {

    public static final String PICTURE_EXTENSION = ".xml";
    public static final String CIRCLE = "CIRCLE";
    public static final String SQUARE = "SQUARE";

    //Depend upon abstraction(interface or abstract class) not upon concrete implementation
    //Loose coupled logic/ NOT TIGHT coupled logic
    private final XmlPictureParser xmlPictureParser;

    public SaveListener(XmlPictureParser xmlPictureParser) {
        this.xmlPictureParser = xmlPictureParser;
    }

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
        try  {
            xmlPictureParser.savePicture(paintShapes, pictureName);
            JOptionPane.showMessageDialog(null, "Sačuvana slika");
            paintPanel.clear();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            JOptionPane.showMessageDialog(null, "Greska: " + exception.getMessage());
        }
    }
}
