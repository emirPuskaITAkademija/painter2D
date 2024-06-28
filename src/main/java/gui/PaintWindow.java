package gui;

import listener.ExitListener;
import listener.OpenListener;
import listener.SaveListener;
import xml.dom.DomParser;
//import xml.jaxb.JAXBParser;
import xml.sax.SaxParser;
import xml.stax.StaxParser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaintWindow extends JFrame {
    private static PaintWindow paintWindow = null;

    private final JRadioButton circleRadioButton = new JRadioButton("Krug");
    private final JRadioButton squareRadioButton = new JRadioButton("Kvadrat");

    private final JRadioButton blueRadioButton = new JRadioButton("Plava");
    private final JRadioButton redRadioButton = new JRadioButton("Crvena");

    private final PaintPanel paintPanel = new PaintPanel();

    private PaintWindow() {
        setTitle("Painter 2D");
        setSize(500, 300);
        add(settingsPanel(), BorderLayout.NORTH);
        add(paintPanel, BorderLayout.CENTER);
        createMenuBar();
    }

    private void createMenuBar() {
        //Prozor ima 3 meni: opcije(otvori, sačuvaj, izlaz)
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fajl");

        SaxParser saxParser = new SaxParser();
        DomParser domParser = new DomParser();
        StaxParser staxParser = new StaxParser();
//        JAXBParser jaxbParser = new JAXBParser();

        JMenu saveMenu = new JMenu("Sačuvaj");
        saveMenu.add(createMenuItem("SAX", new SaveListener(saxParser)));
        saveMenu.add(createMenuItem("DOM", new SaveListener(domParser)));
        saveMenu.add(createMenuItem("StAX", new SaveListener(staxParser)));
//        saveMenu.add(createMenuItem("JAXB", new SaveListener(jaxbParser)));


        JMenu openMenu = new JMenu("Otvori");
        openMenu.add(createMenuItem("SAX", new OpenListener(saxParser)));
        openMenu.add(createMenuItem("DOM", new OpenListener(domParser)));
        openMenu.add(createMenuItem("StAX", new OpenListener(staxParser)));
//        openMenu.add(createMenuItem("JAXB", new OpenListener(jaxbParser)));

        JMenuItem exitMenuItem = new JMenuItem("Izlaz");
        exitMenuItem.addActionListener(new ExitListener());

        fileMenu.add(saveMenu);
        fileMenu.add(openMenu);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String label, ActionListener actionListener) {
        JMenuItem jMenuItem = new JMenuItem(label);
        jMenuItem.addActionListener(actionListener);
        return jMenuItem;
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }

    private JPanel settingsPanel() {
        JPanel paintSettingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel shapePanel = new JPanel();
        shapePanel.add(circleRadioButton);
        shapePanel.add(squareRadioButton);
        TitledBorder shapeTitledBorder = new TitledBorder("Oblik");
        shapePanel.setBorder(shapeTitledBorder);
        circleRadioButton.setSelected(true);
        ButtonGroup shapeButtonGroup = new ButtonGroup();
        shapeButtonGroup.add(circleRadioButton);
        shapeButtonGroup.add(squareRadioButton);


        JPanel colorPanel = new JPanel();
        colorPanel.add(redRadioButton);
        colorPanel.add(blueRadioButton);
        TitledBorder colorTitledBorder = new TitledBorder("Boja");
        colorPanel.setBorder(colorTitledBorder);
        blueRadioButton.setSelected(true);
        ButtonGroup colorButtonGroup = new ButtonGroup();
        colorButtonGroup.add(blueRadioButton);
        colorButtonGroup.add(redRadioButton);

        paintSettingPanel.add(shapePanel);
        paintSettingPanel.add(colorPanel);
        return paintSettingPanel;
    }


    public Color getSelectedColor() {
        return blueRadioButton.isSelected() ? Color.BLUE : Color.RED;
    }

    public boolean isCircleSelected() {
        return circleRadioButton.isSelected();
    }

    public static PaintWindow instance() {
        if (paintWindow == null) {
            paintWindow = new PaintWindow();
        }
        return paintWindow;
    }
}
