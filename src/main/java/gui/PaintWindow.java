package gui;

import listener.ExitListener;
import listener.OpenListener;
import listener.SaveListener;
import xml.sax.SaxParser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PaintWindow extends JFrame {
    private static PaintWindow paintWindow = null;

    private final JRadioButton circleRadioButton = new JRadioButton("Krug");
    private final JRadioButton squareRadioButton = new JRadioButton("Kvadrat");

    private final JRadioButton blueRadioButton = new JRadioButton("Plava");
    private final JRadioButton redRadioButton = new JRadioButton("Crvena");

    private final PaintPanel paintPanel = new PaintPanel();

    private PaintWindow(){
        setTitle("Painter 2D");
        setSize(500, 300);
        add(settingsPanel(), BorderLayout.NORTH);
        add(paintPanel, BorderLayout.CENTER);

        //Prozor ima 3 meni: opcije(otvori, sačuvaj, izlaz)
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fajl");

        JMenuItem saveMenuItem = new JMenuItem("Sačuvaj");
        saveMenuItem.addActionListener(new SaveListener());

        JMenuItem openMenuItem = new JMenuItem("Otvori");
        openMenuItem.addActionListener(new OpenListener(new SaxParser()));

        JMenuItem exitMenuItem = new JMenuItem("Izlaz");
        exitMenuItem.addActionListener(new ExitListener());

        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }

    private JPanel settingsPanel(){
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


    public Color getSelectedColor(){
        return blueRadioButton.isSelected() ? Color.BLUE : Color.RED;
    }

    public boolean isCircleSelected(){
        return circleRadioButton.isSelected();
    }

    public static PaintWindow instance() {
        if (paintWindow == null) {
            paintWindow = new PaintWindow();
        }
        return paintWindow;
    }
}
