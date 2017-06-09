package hr.fer.zemris.gui.tabs;

import hr.fer.zemris.io.ImageFinder;
import hr.fer.zemris.io.ImagesTask;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class ImagesProcessorTab extends JPanel {

    private JLabel inputLabel;
    private JLabel outputLabel;
    private JProgressBar progressBar;
    private List<Path> images;
    private Path inputPath;
    private Path outputPath;

    public ImagesProcessorTab() {
        super(new BorderLayout());
        inputLabel = new JLabel("Choose source folder...");
        outputLabel = new JLabel("Choose destination folder...");
        initGUI();
    }

    private void initGUI() {
        JPanel ioPanel = new JPanel(new FlowLayout());
        ioPanel.setLayout( new GridLayout(2, 2));

//        inputLabel.setText("Choose source folder...");
//        outputLabel.setText("Choose destination folder...");

        // Input folder
        JButton inputButton = new JButton("Choose source");
        inputButton.addActionListener(e -> {
            didSelectChooseSource();
        });
        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.add(inputLabel);
        flowPanel.add(inputButton);
        ioPanel.add(flowPanel);

        // Output folder
        JButton outputButton = new JButton("Choose destination");
        outputButton.addActionListener(e -> {
            didSelectChooseDestination();
        });
        flowPanel = new JPanel(new FlowLayout());
        flowPanel.add(outputLabel);
        flowPanel.add(outputButton);
        ioPanel.add(flowPanel);

        JPanel statusBar = new JPanel(new FlowLayout());
        progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        statusBar.add(progressBar);
        JButton processButton = new JButton("Process");
        processButton.addActionListener(e -> {
            didSelectProcess();
        });
        statusBar.add(processButton);
        add(statusBar, BorderLayout.PAGE_END);

        add(ioPanel, BorderLayout.CENTER);
    }

    private void didSelectChooseSource() {
        JFileChooser chooser = new JFileChooser();
        if (inputPath != null) {
            chooser.setCurrentDirectory(inputPath.toFile());
        }
        chooser.setDialogTitle("Choose source folder...");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            inputLabel.setText(chooser.getSelectedFile().toPath().toString());
            ImageFinder finder = new ImageFinder();
            try {
                inputPath = chooser.getSelectedFile().toPath();
                Files.walkFileTree(inputPath, finder);
                images = finder.getImages();
            } catch (Exception e) {

            }
        } else {
            inputLabel.setText("Choose source folder...");
            images = null;
        }
    }

    private void didSelectChooseDestination() {
        JFileChooser chooser = new JFileChooser();
        if (outputPath != null) {
            chooser.setCurrentDirectory(outputPath.toFile());
        }
        chooser.setDialogTitle("Choose destination folder...");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            outputPath = chooser.getSelectedFile().toPath();
            outputLabel.setText(outputPath.toString());
        } else {
            outputLabel.setText("Choose destination folder...");
            outputPath = null;
        }
    }

    private void didSelectProcess() {
        ImagesTask task = new ImagesTask(images, outputPath);
        task.addPropertyChangeListener(evt -> {
            if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();
                progressBar.setValue(progress);
            }
        });
        task.execute();
    }
}