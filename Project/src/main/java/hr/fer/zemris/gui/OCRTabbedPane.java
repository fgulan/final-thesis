package hr.fer.zemris.gui;

import hr.fer.zemris.gui.tabs.ImagePreviewTab;
import hr.fer.zemris.gui.tabs.ImagesProcessorTab;
import hr.fer.zemris.gui.tabs.NetLearnTab;
import hr.fer.zemris.gui.tabs.RecognizeTab;

import javax.swing.*;
import java.awt.*;

/**
 * @author Filip Gulan
 */
public class OCRTabbedPane extends JPanel {

    public OCRTabbedPane() {
        this.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Image preview", new ImagePreviewTab());

        JComponent header = makeTextPanel("Process images");
        tabbedPane.addTab("Process images", new ImagesProcessorTab());

        header = makeTextPanel("Learn Net");
        tabbedPane.addTab("Learn Net", new NetLearnTab());

        header = makeTextPanel("OCR");
        tabbedPane.addTab("OCR", new RecognizeTab());

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
