package hr.fer.zemris.learn;

import java.util.List;

/**
 * @author Filip Gulan
 */
public class VerticalHorizontalProjection extends AbstractFeatureVector {

    private InputImage inputImage;

    public VerticalHorizontalProjection(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public VerticalHorizontalProjection(int inputSize, double[] output) {
        super(inputSize, output);
    }

    public VerticalHorizontalProjection(double[] input, double[] output) {
        super(input, output);
    }

    public VerticalHorizontalProjection(List<Double> input, List<Double> output) {
        super(input, output);
    }

    public VerticalHorizontalProjection(InputImage inputImage) {
        super(45, inputImage.getBinarySymbol());
        this.inputImage = inputImage;
        calculateVectors();
    }

    private void calculateVectors() {
        int[][] image = inputImage.getImage().getMatrix();
        int width = inputImage.getImage().getWidth();
        int height = inputImage.getImage().getHeight();
        double[] vector = getInput();
//        for (int i = 0, k = 0; i < height; i+=2, k += 3) {
//            int left = width, right = 0, counter = 0;
//            for (int j = 0; j < width; j +=2) {
//                if (image[i][j] == 1) {
//                    counter++;
//                    if (j < left) {
//                        left = j;
//                    }
//                    if (j > right) {
//                        right = j;
//                    }
//                }
//            }
//            if (left == width) left = 0;
//            if (right == 0) right = width;
//            vector[k] = counter/(double)width;
//            vector[k + 1] = left/(double)width;
//            vector[k + 2] = (width - right)/(double)width;
//        }

        for (int i = 0, k = 0; i < width; i+=2, k += 3) {
            int left = height, right = 0, counter = 0;
            for (int j = 0; j < height; j +=2) {
                if (image[j][i] == 1) {
                    counter++;
                    if (j < left) {
                        left = j;
                    }
                    if (j > right) {
                        right = j;
                    }
                }
            }
            if (left == height) left = 0;
            if (right == 0) right = height;
            vector[k] = counter/(double)height;
            vector[k + 1] = left/(double)height;
            vector[k + 2] = (height - right)/(double)height;
        }
    }
}
