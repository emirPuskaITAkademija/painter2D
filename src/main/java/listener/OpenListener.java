package listener;

import gui.PaintWindow;
import shape.PaintShape;
import xml.XmlPictureParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static listener.SaveListener.PICTURE_EXTENSION;

public class OpenListener implements ActionListener {
    private final XmlPictureParser xmlPictureParser;

    public OpenListener(XmlPictureParser xmlPictureParser) {
        this.xmlPictureParser = xmlPictureParser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pictureName = JOptionPane.showInputDialog("Ime vaše slike");
        if (pictureName == null || pictureName.isBlank()) {
            return;
        }
        if (!pictureName.endsWith(PICTURE_EXTENSION)) {
            pictureName += PICTURE_EXTENSION;
        }
        PaintWindow.instance().getPaintPanel().getPaintShapes().clear();
        List<PaintShape> paintShapes = xmlPictureParser.readPicture(pictureName);
        PaintWindow.instance().getPaintPanel().getPaintShapes().addAll(paintShapes);
    }
}
