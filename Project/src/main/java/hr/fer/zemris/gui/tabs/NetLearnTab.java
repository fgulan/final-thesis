package hr.fer.zemris.gui.tabs;

import hr.fer.zemris.gui.console.JConsole;
import hr.fer.zemris.gui.nnet.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

/**
 * @author Filip Gulan
 */
public class NetLearnTab extends JPanel implements INetTaskListener{

    private Path testSetPath;
    private Path trainingSetPath;
    private JConsole console;

    private JTextField momentumField;
    private JTextField learingRateField;
    private JTextField netNameField;
    private JCheckBox lowercaseBox;
    private JRadioButton croatianRadio;
    private JRadioButton englishRadio;
    private JLabel trainingLabel;
    private JLabel testLabel;

    private JButton startLearningButton;
    private JButton stopLearningButton;

    private LearningNetTask learningNetTask;

    public NetLearnTab() {
        super(new BorderLayout());
        this.console = new JConsole();
        add(console);
        JPanel rightPanel = setupRightPanel();
        JPanel leftPanel = setupLeftPanel();

        JPanel upperPanel = new JPanel(new FlowLayout());
        upperPanel.add(leftPanel);
        upperPanel.add(rightPanel);
        add(upperPanel, BorderLayout.PAGE_START);
    }

    private JPanel setupLeftPanel() {
        JPanel leftPanel = new JPanel(new MigLayout());
        lowercaseBox = new JCheckBox("Lowercase");
        lowercaseBox.setSelected(true);

        croatianRadio = new JRadioButton("Croatian");
        englishRadio = new JRadioButton("English");
        ButtonGroup bg = new ButtonGroup();
        bg.add(englishRadio);
        bg.add(croatianRadio);
        croatianRadio.setSelected(true);

        leftPanel.add(lowercaseBox);
        leftPanel.add(new JPanel(), "wrap");
        leftPanel.add(croatianRadio);
        leftPanel.add(englishRadio);
        leftPanel.add(new JPanel(), "wrap");
        setupLoadingButtons(leftPanel);
        leftPanel.add(new JPanel(), "wrap");
        setupLearningButtons(leftPanel);
        return leftPanel;
    }

    private void setupLearningButtons(JPanel leftPanel) {
        startLearningButton = new JButton("Start learning");
        stopLearningButton = new JButton("Stop learning");
        stopLearningButton.setEnabled(false);

        leftPanel.add(startLearningButton);
        leftPanel.add(stopLearningButton);

        startLearningButton.addActionListener(e -> {
            didSelectStartLearning();
        });

        stopLearningButton.addActionListener(e -> {
            didSelectStopLearning();
        });
    }

    private void didSelectStartLearning() {
        console.clear();
        if (testSetPath == null || trainingSetPath == null) {
            console.addLog("Please provide training and test set path!");
            return;
        }
        IDataSetLoader loader = null;
        if (croatianRadio.isSelected()) {
            loader = new CroatianDataSetLoader(console, lowercaseBox.isSelected());
        } else {
            loader = new EnglishDataSetLoader(console, lowercaseBox.isSelected());
        }

        double momentum = getValueFromTextField(momentumField);
        if (momentum < 0) {
            console.addLog("Invalid momemntum value!");
            return;
        }
        double learningRate = getValueFromTextField(learingRateField);
        if (learningRate < 0) {
            console.addLog("Invalid learning rate value!");
            return;
        }

        if (netNameField.getText() == null || netNameField.getText().length() <= 0) {
            console.addLog("Invalid neural net name!");
            return;
        }

        String nnetName = netNameField.getText() + "_" + loader.toString();
        learningNetTask =
                new LearningNetTask(trainingSetPath, testSetPath, console, loader,
                        learningRate, momentum, nnetName);

        startLearningButton.setEnabled(false);
        stopLearningButton.setEnabled(true);
        console.addLog("Start learning");
        learningNetTask.addListener(this);
        learningNetTask.startLearning();
    }

    private double getValueFromTextField(JTextField field) {
        try {
            return Double.parseDouble(field.getText());
        } catch (Exception e) {
            return -1;
        }
    }

    private void didSelectStopLearning() {
        if (learningNetTask == null) {
            return;
        }
        if (learningNetTask.stopLearning()) {
            startLearningButton.setEnabled(true);
            stopLearningButton.setEnabled(false);
            console.addLog("Stop learning");
        }
    }

    private void setupLoadingButtons(JPanel leftPanel) {
        JButton trainingSetButton = new JButton("Training set");
        JButton testSetButton = new JButton("Test set");
        trainingLabel = new JLabel("Choose training set...");
        testLabel = new JLabel("Choose test set...");
        leftPanel.add(trainingLabel, "w 150:150:150");
        leftPanel.add(trainingSetButton);
        leftPanel.add(new JPanel(), "wrap");
        leftPanel.add(testLabel, "w 150:150:150");
        leftPanel.add(testSetButton);

        trainingSetButton.addActionListener(e -> {
            didSelectChooseTrainingSet();
        });

        testSetButton.addActionListener(e -> {
            didSelectChooseTestSet();
        });
    }

    private void didSelectChooseTestSet() {
        testSetPath = chooseFolder("Choose test set folder");
        if (testSetPath == null) {
            testLabel.setText("Choose test set...");
        } else {
            testLabel.setText(testSetPath.toString());
        }
    }

    private void didSelectChooseTrainingSet() {
        trainingSetPath = chooseFolder("Choose training set folder");
        if (trainingSetPath == null) {
            trainingLabel.setText("Choose training set...");
        } else {
            trainingLabel.setText(trainingSetPath.toString());
        }
    }

    private Path chooseFolder(String title) {
        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        Path selectedPath = null;
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedPath = chooser.getSelectedFile().toPath();
        }
        return selectedPath;
    }

    private JPanel setupRightPanel() {
        JPanel rightPanel = new JPanel(new MigLayout());

        momentumField = new JTextField("0.025");
        learingRateField = new JTextField("0.01");
        netNameField = new JTextField("NeuralNet");


        rightPanel.add(new JLabel("Momentum:"));
        rightPanel.add(momentumField, "width 100:100:100");
        rightPanel.add(new JPanel(), "wrap");
        rightPanel.add(new JLabel("Learning rate:"));
        rightPanel.add(learingRateField, "width 100:100:100");
        rightPanel.add(new JPanel(), "wrap");
        rightPanel.add(new JLabel("Net name"));
        rightPanel.add(netNameField, "width 100:100:100");
        rightPanel.add(new JPanel(), "wrap");
        return rightPanel;
    }

    @Override
    public void learningStopped(LearningNetTask task) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startLearningButton.setEnabled(true);
                stopLearningButton.setEnabled(false);
                console.addLog("Learning finished!");
                learningNetTask.removeListener(NetLearnTab.this);
            }
        });
    }
}
