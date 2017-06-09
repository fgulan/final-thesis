package hr.fer.zemris.learn;

import java.util.List;

/**
 * @author Filip Gulan
 */
public class DiagonalStructuralFeatureVector extends AbstractFeatureVector {

    private static final int ZONE_SIZE = 5;

    private InputImage inputImage;

    public DiagonalStructuralFeatureVector(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public DiagonalStructuralFeatureVector(int inputSize, double[] output) {
        super(inputSize, output);
    }

    public DiagonalStructuralFeatureVector(double[] input, double[] output) {
        super(input, output);
    }

    public DiagonalStructuralFeatureVector(List<Double> input, List<Double> output) {
        super(input, output);
    }

    public DiagonalStructuralFeatureVector(InputImage inputImage) {
        super(66, inputImage.getBinarySymbol());
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

        int hCount = width/ZONE_SIZE;
        int vCount = height/ZONE_SIZE;
        double[] hMean = new double[hCount];
        for (int i = 0; i < hCount; i++) {
            double hSum = 0;
            for (int j = 0; j < vCount; j++) {
                hSum += vector[i*hCount + j];
            }
            hMean[i] = hSum/hCount;
        }

        double[] vMean = new double[vCount];
        for (int i = 0; i < vCount; i++) {
            double vSum = 0;
            for (int j = 0; j < hCount; j++) {
                vSum += vector[j*hCount + i];
            }
            vMean[i] = vSum/hCount;
        }

        int offset = hCount*vCount;
        for (int i = 0; i < hCount; i++) {
            vector[offset + i] = hMean[i];
        }
        offset += hCount;
        for (int i = 0; i < vCount; i++) {
            vector[offset + i] = vMean[i];
        }

        int size = 15;
        double[] crossVector = new double[size];
        int step = height/size;
        for (int i = 0; i < size; i++) {
            int firstIndex = i*step;
            for (int j = 0; j < width; j++) {
                if (image[firstIndex][j] == 1) {
                    crossVector[i] += 1/(double)width;
                }
            }
        }

        offset = hCount*vCount + hCount + vCount;
        for (int i = 0; i < size; i++) {
            vector[offset + i] = crossVector[i];
        }

        offset += size;
        StructuralPoints points = countPoints(image, width, height);
        if (points.endPoints == 0) {
            vector[offset] = 0;
        } else {
            vector[offset] = 1 - 1/(double)points.endPoints;
        }
        if (points.branchPoints == 0) {
            vector[offset + 1] = 0;
        } else {
            vector[offset + 1] = 1 - 1/(double)points.branchPoints;
        }
        if (points.crossPoints == 0) {
            vector[offset + 2] = 0;
        } else {
            vector[offset + 2] = 1 - 1/(double)points.crossPoints;
        }
        System.out.println(inputImage.getSymbol() + " " + points.endPoints + " " + points.crossPoints + " " +points.branchPoints);
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
        int count = 2*ZONE_SIZE - 1;
        double sum = 0;
        for (int i = 0; i < count ; i++) {
            int z = i < ZONE_SIZE ? 0 : i - ZONE_SIZE + 1;
            int sliceSize = i - z + 1;
            int sliceSum = 0;
            for (int j = z; j < sliceSize; j++) {
                if (zone[j][i - j] == 1) {
                    sliceSum++;
                }
                //System.out.println(zone[j][i - j]);
            }
            sum += sliceSum/(double)sliceSize;
        }
        return sum/count;
    }

    private StructuralPoints countPoints(int[][] image, int width, int height) {
        int endPoints = 0;
        int branchPoints = 0;
        int crossPoints = 0;

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (image[i][j] == 0) {
                    continue;
                }

                int p2 = image[i - 1][j];
                int p3 = image[i - 1][j + 1];
                int p4 = image[i][j + 1];
                int p5 = image[i + 1][j + 1];
                int p6 = image[i + 1][j];
                int p7 = image[i + 1][j - 1];
                int p8 = image[i][j - 1];
                int p9 = image[i - 1][j - 1];

                int diagonalSum = p3 + p5 + p7 + p9;
                int crossSum = p2 + p4 + p6 + p8;
                int sum = crossSum + diagonalSum;
                if (sum == 0 || sum == 1) {
                    endPoints++;
                } else if (crossSum > 3 || diagonalSum > 3) {
                    crossPoints++;
                } else if (p2 + p4 + p8 == 3 || p2 + p4 + p6 == 3 || p4 + p6 + p8 == 3 ||
                        p6 + p8 + p2 == 3 || p3 + p5 + p7 == 3 || p5 + p7 + p9 == 3 ||
                        p7 + p9 + p3 == 3 || p9 + p3 + p5 == 3) {
                    branchPoints++;
                }
            }
        }
        return new StructuralPoints(endPoints, branchPoints, crossPoints);
    }

    private class StructuralPoints {
        public int endPoints;
        public int branchPoints;
        public int crossPoints;

        public StructuralPoints(int endPoints, int branchPoints, int crossPoints) {
            this.endPoints = endPoints;
            this.branchPoints = branchPoints;
            this.crossPoints = crossPoints;
        }
    }
}
