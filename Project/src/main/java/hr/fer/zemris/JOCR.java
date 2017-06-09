package hr.fer.zemris;

import hr.fer.zemris.gui.OCRTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Filip Gulan
 */
public class JOCR extends JFrame {


    public JOCR() {
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

       getContentPane().add(new OCRTabbedPane(), BorderLayout.CENTER);
    }

    public static void main(String [] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JOCR();
            frame.setVisible(true);
        });
    }
}
