package hr.fer.zemris.gui;

import hr.fer.zemris.gui.tabs.ImagePreviewTab;

import javax.swing.*;
import java.awt.*;

/**
 * @author Filip Gulan
 */
public class OCRTabbedPane extends JPanel {

    public OCRTabbedPane() {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent header = makeTextPanel("Image preview");
        tabbedPane.addTab("Image preview", new ImagePreviewTab());

        header = makeTextPanel("Process images");
        tabbedPane.addTab("Process images", header);

        header = makeTextPanel("Learn Net");
        tabbedPane.addTab("Learn Net", header);

        header = makeTextPanel("OCR");
        tabbedPane.addTab("OCR", header);

        add(tabbedPane);
    }

    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

}
