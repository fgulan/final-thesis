package hr.fer.zemris.learn;

import java.util.List;

/**
 * @author Filip Gulan
 */
public class DiagonalFeatureVector extends AbstractFeatureVector {

    private static final int ZONE_SIZE = 5;

    private InputImage inputImage;

    public DiagonalFeatureVector(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public DiagonalFeatureVector(int inputSize, double[] output) {
        super(inputSize, output);
    }

    public DiagonalFeatureVector(double[] input, double[] output) {
        super(input, output);
    }

    public DiagonalFeatureVector(List<Double> input, List<Double> output) {
        super(input, output);
    }

    public DiagonalFeatureVector(InputImage inputImage) {
        super(48, inputImage.getBinarySymbol());
        this.inputImage = inputImage;
        calculateVectors();
    }

    private void calculateVectors() {
        int[][] image = inputImage.getImage().getMatrix();
        int width = inputImage.getImage().getWidth();
        int height = inputImage.getImage().getHeight();
        int counter = 0;
        double[] vector = getInput();
        for (int i = 0; i < height; i += ZONE_SIZE) {
            for (int j = 0; j < width; j += ZONE_SIZE) {
                vector[counter++] = calculateZone(getZone(i, j, image));
            }
        }

        int hCount = width / ZONE_SIZE;
        int vCount = height / ZONE_SIZE;
        double[] hMean = new double[hCount];
        for (int i = 0; i < hCount; i++) {
            double hSum = 0;
            for (int j = 0; j < vCount; j++) {
                hSum += vector[i * hCount + j];
            }
            hMean[i] = hSum / hCount;
        }

        double[] vMean = new double[vCount];
        for (int i = 0; i < vCount; i++) {
            double vSum = 0;
            for (int j = 0; j < hCount; j++) {
                vSum += vector[j * hCount + i];
            }
            vMean[i] = vSum / hCount;
        }

        int offset = hCount * vCount;
        for (int i = 0; i < hCount; i++) {
            vector[offset + i] = hMean[i];
        }
        offset += hCount;
        for (int i = 0; i < vCount; i++) {
            vector[offset + i] = vMean[i];
        }
    }

    private int[][] getZone(int iOffset, int jOffset, int[][] input) {
        int[][] zone = new int[ZONE_SIZE][ZONE_SIZE];
        for (int i = 0; i < ZONE_SIZE; i++) {
            for (int j = 0; j < ZONE_SIZE; j++) {
                zone[i][j] = input[iOffset + i][jOffset + j];
            }
        }
        return zone;
    }

    private double calculateZone(int[][] zone) {
        int count = 2 * ZONE_SIZE - 1;
        double sum = 0;
        for (int i = 0; i < count; i++) {
            int z = i < ZONE_SIZE ? 0 : i - ZONE_SIZE + 1;
            int sliceSize = i - z + 1;
            int sliceSum = 0;
            for (int j = z; j < sliceSize; j++) {
                if (zone[j][i - j] == 1) {
                    sliceSum++;
                }
            }
            sum += sliceSum / (double) sliceSize;
        }
        return sum / count;
    }
}
