package hr.fer.zemris.gui.nnet;

import hr.fer.zemris.gui.console.JConsole;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;

/**
 * @author Filip Gulan
 */
public class LearningTestSetEvaluator implements LearningEventListener {

    private String nnetName;
    private DataSet testSet;
    private DataSet trainingSet;
    private int testCounter;
    private double maxRate;
    private JConsole console;

    public LearningTestSetEvaluator(String nnetName, DataSet testSet, DataSet trainingSet, JConsole console) {
        this.nnetName = nnetName;
        this.testSet = testSet;
        this.trainingSet = trainingSet;
        this.console = console;
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
        console.addLog("Success rate: " + rate);
        if (rate >= maxRate) {
            maxRate = rate;
            console.addLog("Saving neural net");
            nnet.save(nnetName + ".nnet");
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
            console.addLog("Training set success rate: " + trainingRate);
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
