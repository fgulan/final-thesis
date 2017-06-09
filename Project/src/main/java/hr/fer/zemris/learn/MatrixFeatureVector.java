package hr.fer.zemris.learn;

import java.util.List;

/**
 * @author Filip Gulan
 */
public class MatrixFeatureVector extends AbstractFeatureVector {

    public MatrixFeatureVector(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public MatrixFeatureVector(double[] input, double[] output) {
        super(input, output);
    }

    public MatrixFeatureVector(List<Double> input, List<Double> output) {
        super(input, output);
    }

    public MatrixFeatureVector(InputImage inputImage) {
        super(inputImage.getImage().getVector(), inputImage.getBinarySymbol());
    }
}