package hr.fer.zemris.learn;

import java.util.List;

/**
 * @author Filip Gulan
 */
public class HorizontalFetureVector extends AbstractFeatureVector {

    private InputImage inputImage;

    public HorizontalFetureVector(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public HorizontalFetureVector(double[] input, double[] output) {
        super(input, output);
    }

    public HorizontalFetureVector(List<Double> input, List<Double> output) {
        super(input, output);
    }

    public HorizontalFetureVector(InputImage inputImage) {
        super(inputImage.getImage().getHeight()*3, inputImage.getBinarySymbol());
        this.inputImage = inputImage;
        calculateVectors();
    }

    private void calculateVectors() {
        int[][] image = inputImage.getImage().getMatrix();
        int width = inputImage.getImage().getWidth();
        int height = inputImage.getImage().getHeight();
        double[] vector = getInput();
        for (int i = 0, k = 0; i < height; i++, k += 3) {
            int left = width, right = 0, counter = 0;
            for (int j = 0; j < width; j++) {
                if (image[i][j] == 1) {
                    counter++;
                    if (j < left) {
                        left = j;
                    }
                    if (j > right) {
                        right = j;
                    }
                }
            }
            if (left == width) left = 0;
            if (right == 0) right = width;
            vector[k] = counter/(double)height;
            vector[k + 1] = left/(double)height;
            vector[k + 2] = (width - right)/(double)height;
        }
    }
}
