package hr.fer.zemris.learn;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class AbstractFeatureVector implements IFeatureVector {

    private double[] input;
    private double[] output;

    public AbstractFeatureVector(int inputSize, int outputSize) {
        this.input = new double[inputSize];
        this.output = new double[outputSize];
    }

    public AbstractFeatureVector(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    public AbstractFeatureVector(int inputSize, double[] output) {
        this.input = new double[inputSize];
        this.output = output;
    }

    public AbstractFeatureVector(List<Double> input, List<Double> output) {
        this.input = input.stream().mapToDouble(Double::doubleValue).toArray();
        this.output = output.stream().mapToDouble(Double::doubleValue).toArray();
    }

    @Override
    public double[] getInput() {
        return input;
    }

    @Override
    public double[] getOutput() {
        return output;
    }

    public void saveToFile(Path path) {
        // TODO : save to file feature vector

    }
}
