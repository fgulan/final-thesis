package hr.fer.zemris.test.croatian;

import hr.fer.zemris.filters.FiltersFactory;
import hr.fer.zemris.image.BinaryImage;
import hr.fer.zemris.image.UniqueLetterMapper;
import hr.fer.zemris.io.ImageFinder;
import hr.fer.zemris.learn.DiagonalCrossFeatureVector;
import hr.fer.zemris.learn.IFeatureVector;
import hr.fer.zemris.learn.InputImage;
import hr.fer.zemris.learn.TestSetEvaluator;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Filip Gulan
 */
public class UppercaseOneToOneDiagonalCrossTest {

    private static final int INPUT_LAYER = 65;
    private static final int OUTPUT_LAYER = 32;
    private static final double MOMENTUM = 0.025;
    private static final double LEARINING_RATE = 0.01;
    private static final String NNET_NAME = "OneToOne_Cross_5_Max_Index_Unique_Uppercase_Cro_Points_small_momentum.nnet";
    private static final String LEARNING_PATH = "/Users/Filip/FER/Zavrsni rad/Obrada/";
    private static final String TEST_PATH = "/Users/Filip/FER/Zavrsni rad/Test/";
    private static boolean learn = false;
    private static Map<Integer, Integer> errorMap = new TreeMap<>();

    public static void main(String[] args) {
        System.out.println("Loading images...");
        DataSet trainingSet = loadDataSet(LEARNING_PATH);
        DataSet testSet = loadDataSet(TEST_PATH);

        System.out.println("Images loaded!" + testSet.size());
        if (learn) {
            learnNeuralNet(trainingSet, testSet);
        }
        testLearnedNeuralNet(trainingSet, testSet);
    }

    private static void testLearnedNeuralNet(DataSet trainingSet, DataSet testSet) {
        NeuralNetwork nnet = NeuralNetwork.createFromFile(NNET_NAME);
        System.out.println("Testing loaded neural network");
        //testNeuralNet(nnet, trainingSet, "Training set");
        testNeuralNet(nnet, testSet, "Test set");
        for(Map.Entry<Integer, Integer> entry : errorMap.entrySet()) {
            //System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private static void testNeuralNet(NeuralNetwork nnet, DataSet dataSet, String setName) {
        int counter = 0;
        for (DataSetRow row : dataSet.getRows()) {
            nnet.setInput(row.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            if (isOutputSame(networkOutput, row.getDesiredOutput())) {
                counter++;
            } else {
                for (int i = 0; i < row.getDesiredOutput().length; i++) {
                    if (row.getDesiredOutput()[i] == 1) {
                        Integer d = errorMap.get(i);
                        if (d == null) {
                            errorMap.put(i, 1);
                        } else {
                            errorMap.put(i, ++d);
                        }
                        break;
                    }
                }
            }
        }

        System.out.println(setName + " success rate: " + (counter / (double) dataSet.size()));
    }

    private static void learnNeuralNet(DataSet trainingSet, DataSet testSet) {
        TestSetEvaluator testEvaluator = new TestSetEvaluator(NNET_NAME, testSet, trainingSet);
        MultiLayerPerceptron nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, INPUT_LAYER, 86, 86, OUTPUT_LAYER);

        MomentumBackpropagation bp = new MomentumBackpropagation();
        bp.setLearningRate(LEARINING_RATE);
        bp.setMomentum(MOMENTUM);
        bp.addListener(testEvaluator);

        nnet.setLearningRule(bp);
        nnet.learn(trainingSet);
        nnet.save(NNET_NAME + "last");
    }

    private static DataSet loadDataSet(String path) {
        List<InputImage> images = loadInputImages(path);
        DataSet dataSet = new DataSet(INPUT_LAYER, OUTPUT_LAYER);

        for (InputImage image : images) {
            int index = UniqueLetterMapper.mapLetter(image.getSymbol());
            if (index == - 1) continue;

            IFeatureVector vector = new DiagonalCrossFeatureVector(image);
            double[] output = new double[OUTPUT_LAYER];
            output[index] = 1;
            dataSet.addRow(vector.getInput(), output);
        }
        return dataSet;
    }

    private static List<InputImage> loadInputImages(String stringPath) {
        ImageFinder finder = new ImageFinder();
        List<InputImage> inputImages = new ArrayList<>();

        try {
            Path inputPath = Paths.get(stringPath);
            Files.walkFileTree(inputPath, finder);
            List<Path> images = finder.getImages();
            for (Path path : images) {
                char name = path.getFileName().toString().charAt(0);
                if (Character.isLowerCase(name)) {
                    continue;
                }
                BufferedImage image = ImageIO.read(path.toFile());
                BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
                inputImages.add(new InputImage(binImage, name));
            }
        } catch (Exception e) {
            System.out.println("Greska");
        }
        return inputImages;
    }

    private static boolean isVectorSame(double[] first, double[] second) {
        for (int i = 0; i < first.length; i++) {
            if (first[i] > 0.5 && second[i] < 0.5) {
                return false;
            } else if (first[i] < 0.5 && second[i] > 0.5) {
                return false;
            }
        }
        return true;
    }

    private static boolean isOutputSame(double[] netOutput, double[] desiredOutput) {
        int index = -1;
        for (int i = 0; i < desiredOutput.length; i++) {
            if (desiredOutput[i] > 0.5) {
                index = i;
                break;
            }
        }

        int maxIndex = 0;
        double maxValue = 0;
        for (int i = 0; i < netOutput.length; i++) {
            if (netOutput[i] > maxValue) {
                maxValue = netOutput[i];
                maxIndex = i;
            }
        }
        if (!learn) {
            if (maxIndex != index) {
                System.out.println(index + " as " + maxIndex);
            }
        }
        return maxIndex == index;
    }
}
