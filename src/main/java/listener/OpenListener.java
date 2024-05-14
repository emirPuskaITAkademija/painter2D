package listener;

import org.xml.sax.SAXException;
import xml.XmlPictureParser;
import xml.sax.PictureHandler;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static listener.SaveListener.PICTURE_EXTENSION;

public class OpenListener implements ActionListener {
    private final XmlPictureParser xmlPictureParser;

    public OpenListener(XmlPictureParser xmlPictureParser){
        this.xmlPictureParser = xmlPictureParser;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String pictureName = JOptionPane.showInputDialog("Ime vaše slike");
        if(pictureName == null || pictureName.isBlank()){
            return;
        }
        if(!pictureName.endsWith(PICTURE_EXTENSION)){
            pictureName+=PICTURE_EXTENSION;
        }
        xmlPictureParser.readPicture(pictureName);
    }
}
