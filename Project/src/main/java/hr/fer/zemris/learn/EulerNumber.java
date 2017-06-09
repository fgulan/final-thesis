package hr.fer.zemris.learn;

import hr.fer.zemris.image.BinaryImage;

/**
 * @author Filip Gulan
 */
public class EulerNumber {

    private BinaryImage inputImage;
    private int[][] image;

    public EulerNumber(BinaryImage image) {
        this.inputImage = image;
        this.image = image.getMatrix();
    }

    public int getEulerNumber() {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[][] matrix = inputImage.getMatrix();

        int x = 0;
        int v = 0;

        for (int i = 0; i < width - 1; i += 2) {
            for (int j = 0; j < height - 1; j += 2) {
                boolean same = matrix[i][j] == 0 && matrix[i + 1][j + 1] == 1;
                boolean xPattern = same && matrix[i][j + 1] == 0 && matrix[i + 1][j] == 0;
                boolean yPattern = same && matrix[i][j + 1] == 1 && matrix[i + 1][j] == 1;
                if (xPattern) {
                    x++;
                } else if (yPattern) {
                    v++;
                }
            }
        }
        return x - v;
    }
}
