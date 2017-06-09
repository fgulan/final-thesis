package hr.fer.zemris.gui.nnet;

import hr.fer.zemris.gui.console.JConsole;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class LearningNetTask {

    private Path trainingSetPath;
    private Path testSetPath;
    private JConsole console;
    private IDataSetLoader loader;
    private double learningRate;
    private double momentum;
    private String nnetName;

    private DataSet testSet;
    private DataSet trainingSet;
    private MultiLayerPerceptron nnet;

    private List<INetTaskListener> listeners;

    public LearningNetTask(Path trainingSetPath, Path testSetPath, JConsole console,
                           IDataSetLoader loader, double learningRate, double momentum, String nnetName) {
        this.trainingSetPath = trainingSetPath;
        this.testSetPath = testSetPath;
        this.console = console;
        this.loader = loader;
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.nnetName = nnetName;
        this.listeners = new ArrayList<>();
    }

    public void startLearning() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                console.addLog("Loading test set");
                testSet = loader.loadDataSet(testSetPath);
                console.addLog("Test set loaded");

                console.addLog("Loading training set");
                trainingSet = loader.loadDataSet(trainingSetPath);
                console.addLog("Training set loaded. Input size: " + trainingSet.getInputSize() +
                        " Output size: " + trainingSet.getOutputSize());

                nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                        trainingSet.getInputSize(), 86, 86, trainingSet.getOutputSize());

                MomentumBackpropagation backPropagation = new MomentumBackpropagation();
                backPropagation.setLearningRate(learningRate);
                backPropagation.setMomentum(momentum);

                LearningTestSetEvaluator evaluator =
                        new LearningTestSetEvaluator(nnetName, testSet, trainingSet, console);
                backPropagation.addListener(evaluator);
                backPropagation.addListener(new LearningEventListener() {
                    @Override
                    public void handleLearningEvent(LearningEvent event) {
                        if (event.getEventType() == LearningEvent.Type.LEARNING_STOPPED) {
                            listeners.forEach((listener) -> listener.learningStopped(LearningNetTask.this));
                        }
                    }
                });
                nnet.setLearningRule(backPropagation);
                console.addLog("Started neural net learning with momentum: "
                        + momentum + ", learning rate: " + learningRate);
                nnet.learnInNewThread(trainingSet);
            }
        });
        t1.start();
    }

    public boolean stopLearning() {
        if (nnet == null) {
            return false;
        }
        nnet.stopLearning();
        nnet.save(nnetName + "_stop.nnet");
        return true;
    }

    public void addListener(INetTaskListener listener) {
        listeners.add(listener);
    }

    public void removeListener(INetTaskListener listener) {
        listeners.remove(listener);
    }
}
