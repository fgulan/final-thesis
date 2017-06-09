package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.image.BufferedImage;

public class OtsuBinarize {

    public int[] histogram(BufferedImage input) {
        int[] histogram = new int[256];
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }
        int width = input.getWidth();
        int height = input.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int gray = ImageUtilities.getRed(input.getRGB(i, j));
                histogram[gray]++;
            }
        }
        return histogram;
    }

    private int threshold(int[] histogram, int size) {
        float sum = 0, varMax = 0, sumB = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }
        int wB = 0, wF = 0, threshold = 0;;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) continue;
            wF = size - wB;
            if (wF == 0) break;

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }

    public BufferedImage applyFilter(BufferedImage sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int threshold = threshold(histogram(sourceImage), width * height);
        BufferedImage filteredImage = new BufferedImage(width, height, sourceImage.getType());

        int rgba, pixel;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgba = sourceImage.getRGB(i, j);

                if (ImageUtilities.getRed(rgba) > threshold) {
                    pixel = 255;
                } else {
                    pixel = 0;
                }
                pixel = ImageUtilities.colorToRGB(ImageUtilities.getAlpha(rgba), pixel, pixel, pixel);
                filteredImage.setRGB(i, j, pixel);
            }
        }
        return filteredImage;
    }
}