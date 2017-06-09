package hr.fer.zemris.gui.tabs;

import hr.fer.zemris.filters.FiltersFactory;
import hr.fer.zemris.filters.IFilter;
import hr.fer.zemris.gui.console.JConsole;
import hr.fer.zemris.gui.image.ImageViewer;
import hr.fer.zemris.image.*;
import hr.fer.zemris.io.ValueComparator;
import hr.fer.zemris.learn.DiagonalCrossFeatureVector;
import hr.fer.zemris.learn.HorizontalFetureVector;
import hr.fer.zemris.learn.IFeatureVector;
import hr.fer.zemris.learn.InputImage;
import net.miginfocom.swing.MigLayout;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by filipgulan on 15/06/16.
 */
public class RecognizeTab extends JPanel {

    private static final int ENG_OUTPUT = 27;
    private static final int INPUT_LAYER = 65;
    private static final int CRO_OUTPUT = 32;

    private ImageViewer letterViewer;
    private JConsole console;
    private NeuralNetwork nnet;
    private Path imagePath;

    public RecognizeTab() {
        this.letterViewer = new ImageViewer("Letter");
        this.console = new JConsole();
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        add(letterViewer, BorderLayout.CENTER);
        console.setPreferredSize(new Dimension(250, 0));
        add(console, BorderLayout.LINE_START);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        JButton loadNetworkButton = new JButton("Load network");
        loadNetworkButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open neural net file");
            if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path path = fc.getSelectedFile().toPath();
            if (!Files.isReadable(path)) {
                return;
            }
            try {
                nnet = NeuralNetwork.createFromFile(path.toString());
                console.addLog("Loaded neural net:");
                console.addLog(path.getFileName().toString());
            } catch (Exception exception) {
                console.addLog("Unable to load neural net:");
                console.addLog(path.getFileName().toString());
            }
        });
        buttonsPanel.add(loadNetworkButton);

        JButton loadImageButton = new JButton("Load letter");
        loadImageButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open letter image");
            if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path path = fc.getSelectedFile().toPath();
            if (!Files.isReadable(path)) {
                return;
            }
            imagePath = path;
            try {
                letterViewer.setImage(ImageIO.read(path.toFile()));
            } catch (IOException exception) {
                console.addLog("Unable to open image!");
            }
        });
        buttonsPanel.add(loadImageButton);

        JButton recognizeButton = new JButton("Recognize");
        recognizeButton.addActionListener(e -> {
            recognizeLetter(letterViewer.getImage(), imagePath.getFileName().toString().charAt(0));
        });
        buttonsPanel.add(recognizeButton);
        add(buttonsPanel, BorderLayout.LINE_END);
    }

    private void recognizeLetter(BufferedImage image, char letter) {
        if (nnet == null || image == null) {
            return;
        }
        BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
        InputImage inputImage = new InputImage(binImage, letter);

        ICharacterMapper mapper;
        int outputs = nnet.getOutputsCount();
        if (outputs == CRO_OUTPUT) {
            mapper = new CroatianCharacterMapper();
        } else if (outputs == ENG_OUTPUT) {
            mapper = new EnglishCharacterMapper();
        } else {
            return;
        }

        int index = mapper.mapCharacter(letter);
        if (index == - 1) return;

        IFeatureVector featureVector = new DiagonalCrossFeatureVector(inputImage);;
        nnet.setInput(featureVector.getInput());
        nnet.calculate();
        double[] networkOutput = nnet.getOutput();
        HashMap<Character, Double> results = new HashMap<>();
        for (int i = 0; i < networkOutput.length; i++) {
            results.put(mapper.mapInteger(i), networkOutput[i]);
        }

        Comparator<Character> comparator = new ValueComparator<>(results);
        TreeMap<Character, Double> sortedResults = new TreeMap<>(comparator);
        sortedResults.putAll(results);
        console.addLog("Izlazi mreze:");
        int counter = 0;
        for (Map.Entry<Character, Double> entry : sortedResults.entrySet()) {
            if (entry.getValue() <= 0.02 && counter > 9) break;
            console.addLog(entry.getKey() + " => " + Math.round(entry.getValue() * 1000)/1000.0);
            counter++;
        }
    }
}


