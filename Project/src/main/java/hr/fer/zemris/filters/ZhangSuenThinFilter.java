package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;

enum Iteration {
    ITERATION_FIRST, ITERATION_SECOND
}

public class ZhangSuenThinFilter {

    private int width;
    private int height;

    public BufferedImage processImage(BufferedImage originalImage) {
        width = originalImage.getWidth();
        height = originalImage.getHeight();
        int[][] image = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = ImageUtilities.getRed(originalImage.getRGB(i, j));
                image[i][j] = 1 - (color / 255);
            }
        }

        for (;;) {
            int[][] start = new int[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    start[i][j] = image[i][j];
                }
            }

            image = thinningIteration(Iteration.ITERATION_FIRST, image);
            image = thinningIteration(Iteration.ITERATION_SECOND, image);

            boolean equalImages = checkEqualityForImages(start, image);
            if (equalImages) {
                break;
            }
        }

        return thinnedImage(originalImage, image);
    }

    private int[][] thinningIteration(Iteration iteration, int[][] inputImage) {
        int[][] marker = new int[width][height];

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int p2 = inputImage[i - 1][j];
                int p3 = inputImage[i - 1][j + 1];
                int p4 = inputImage[i][j + 1];
                int p5 = inputImage[i + 1][j + 1];
                int p6 = inputImage[i + 1][j];
                int p7 = inputImage[i + 1][j - 1];
                int p8 = inputImage[i][j - 1];
                int p9 = inputImage[i - 1][j - 1];

                int c1 = p2 == 0 && p3 == 1 ? 1 : 0;
                int c2 = p3 == 0 && p4 == 1 ? 1 : 0;
                int c3 = p4 == 0 && p5 == 1 ? 1 : 0;
                int c4 = p5 == 0 && p6 == 1 ? 1 : 0;
                int c5 = p6 == 0 && p7 == 1 ? 1 : 0;
                int c6 = p7 == 0 && p8 == 1 ? 1 : 0;
                int c7 = p8 == 0 && p9 == 1 ? 1 : 0;
                int c8 = p9 == 0 && p2 == 1 ? 1 : 0;

                int A = c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8;
                int B = p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;

                int m1 = 0; int m2 = 0;
                switch (iteration) {
                    case ITERATION_FIRST:
                        m1 = (p2 * p4 * p6);
                        m2 = (p4 * p6 * p8);
                        break;
                    case ITERATION_SECOND:
                        m1 = (p2 * p4 * p8);
                        m2 = (p2 * p6 * p8);
                        break;
                }
                if (A == 1 && (B > 1 && B < 7) && m1 == 0 && m2 == 0) {
                    marker[i][j] = 1;
                }
            }
        }
        return outputImageFromMarker(inputImage, marker);
    }

    private int[][] outputImageFromMarker(int[][] inputImage, int[][] marker) {
        int[][] outputImage = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int tmp = 1 - marker[i][j];
                if (inputImage[i][j] == tmp && inputImage[i][j] == 1) {
                    outputImage[i][j] = 1;
                } else {
                    outputImage[i][j] = 0;
                }
            }
        }
        return outputImage;
    }

    private boolean checkEqualityForImages(int[][] firstImage, int[][] secondImage) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (firstImage[i][j] != secondImage[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private BufferedImage thinnedImage(BufferedImage originalImage, int[][] image) {
        int color, alpha, rgb;
        BufferedImage filteredImage = new BufferedImage(width, height, originalImage.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                alpha = new Color(originalImage.getRGB(i, j)).getAlpha();
                color = 255 - image[i][j] * 255;
                rgb = ImageUtilities.colorToRGB(alpha, color, color, color);
                filteredImage.setRGB(i, j, rgb);
            }
        }
        return filteredImage;
    }
}
