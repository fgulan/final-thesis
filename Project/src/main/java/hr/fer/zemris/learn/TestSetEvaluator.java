package hr.fer.zemris.learn;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;

/**
 * @author Filip Gulan
 */
public class TestSetEvaluator implements LearningEventListener {

    private String saveNetName;
    private DataSet testSet;
    private DataSet trainingSet;
    private int testCounter;
    private double maxRate;

    public TestSetEvaluator(String saveNetName, DataSet testSet, DataSet trainingSet) {
        this.saveNetName = saveNetName;
        this.testSet = testSet;
        this.trainingSet = trainingSet;
        testCounter = 0;
        maxRate = 0;
    }

    @Override
    public void handleLearningEvent(LearningEvent event) {
        testCounter++;
        if (testCounter < 5) return;;
        testCounter = 0;

        NeuralNetwork nnet = ((LearningRule)event.getSource()).getNeuralNetwork();

        int passCounter = 0;
        for (DataSetRow row : testSet.getRows()) {
            nnet.setInput(row.getInput());
            nnet.calculate();

            double[] networkOutput = nnet.getOutput();
            if (isOutputSame(networkOutput, row.getDesiredOutput())) {
                passCounter++;
            }
        }
        double rate = passCounter / (double) testSet.size();
        System.out.println("Success rate: " + rate);
        if (rate > maxRate) {
            maxRate = rate;
            System.out.println("Spremam mrezu");
            nnet.save(saveNetName);
        } else {
            passCounter = 0;
            for (DataSetRow row : trainingSet.getRows()) {
                nnet.setInput(row.getInput());
                nnet.calculate();

                double[] networkOutput = nnet.getOutput();
                if (isOutputSame(networkOutput, row.getDesiredOutput())) {
                    passCounter++;
                }
            }
            double trainingRate = passCounter / (double) trainingSet.size();
            System.out.println("Training success rate: " + trainingRate);
        }
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
        return maxIndex == index;
    }
}
