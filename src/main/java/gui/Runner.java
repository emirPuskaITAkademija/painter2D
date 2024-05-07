package gui;

import javax.swing.*;

public class Runner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Runner::createAndShowGUI);
    }

    private static void createAndShowGUI(){
        PaintWindow paintWindow = PaintWindow.instance();
        paintWindow.setVisible(true);
    }
}
