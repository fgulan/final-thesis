package hr.fer.zemris.gui.tabs;

import hr.fer.zemris.filters.IFilter;
import hr.fer.zemris.gui.filter.JFilterSelection;
import hr.fer.zemris.gui.image.ImageViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Filip Gulan
 */
public class ImagePreviewTab extends JPanel {

    private ImageViewer beforeImageViewer;
    private ImageViewer afterImageViewer;
    private JFilterSelection filterSelection;

    public ImagePreviewTab() {
        super(new BorderLayout());
        initGUI();
    }

    private void initGUI() {

        // Image preview panel
        JPanel imagePanel = new JPanel();
        GridLayout layout = new GridLayout(2, 1);
        imagePanel.setLayout(layout);
        beforeImageViewer = new ImageViewer("Before:");
        afterImageViewer = new ImageViewer("After:");
        imagePanel.add(beforeImageViewer);
        imagePanel.add(afterImageViewer);
        add(imagePanel, BorderLayout.CENTER);

        // Filter selection panel
        filterSelection = new JFilterSelection();
        add(filterSelection, BorderLayout.LINE_START);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open file");
            if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path path = fc.getSelectedFile().toPath();
            if (!Files.isReadable(path)) {
                return;
            }
            try {
                beforeImageViewer.setImage(ImageIO.read(path.toFile()));
            } catch (IOException exception) {
            }
        });
        buttonsPanel.add(loadButton);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                File outputfile = new File("saved.png");
                ImageIO.write(afterImageViewer.getImage(), "png", outputfile);
            } catch (IOException exception) {
            }
        });
        buttonsPanel.add(saveButton);

        JButton applyFilterButton = new JButton("Apply filter");
        applyFilterButton.addActionListener(e -> {
            BufferedImage sourceImage = beforeImageViewer.getImage();
            for (IFilter filter : filterSelection.getSelectedFilters()) {
                sourceImage = filter.applyFilter(sourceImage);
            }
            afterImageViewer.setImage(sourceImage);
        });
        buttonsPanel.add(applyFilterButton);
        add(buttonsPanel, BorderLayout.LINE_END);
    }
}
