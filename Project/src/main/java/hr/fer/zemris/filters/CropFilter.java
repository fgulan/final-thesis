package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class CropFilter implements IFilter {
    private static final int OFFSET = 1;
    private BufferedImage image;
    private int width;
    private int height;

    @Override
    public String getName() {
        return "Crop";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        width = sourceImage.getWidth();
        height = sourceImage.getHeight();
        image = sourceImage;
        int xMin = getMinX();
        int xMax = getMaxX();
        int yMin = getMinY();
        int yMax = getMaxY();

        xMin = xMin < OFFSET ? xMin : xMin - OFFSET;
        xMax = (xMax + 2 * OFFSET) >= width ? xMax : xMax + 2 * OFFSET;
        yMin = yMin < OFFSET ? yMin : yMin - OFFSET;
        yMax = (yMax + 2 * OFFSET) >= height ? yMax : yMax + 2 * OFFSET;

        return fillImage(xMin, yMin, xMax, yMax);
    }

    private int getMinX() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = ImageUtilities.getRed(image.getRGB(i, j));
                if (color == 0) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getMaxX() {
        for (int i = width - 1; i >= 0; i--) {
            for (int j = height - 1; j >= 0; j--) {
                int color = ImageUtilities.getRed(image.getRGB(i, j));
                if (color == 0) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getMinY() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = ImageUtilities.getRed(image.getRGB(j, i));
                if (color == 0) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getMaxY() {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = width - 1; j >= 0; j--) {
                int color = ImageUtilities.getRed(image.getRGB(j, i));
                if (color == 0) {
                    return i;
                }
            }
        }
        return 0;
    }

    private BufferedImage fillImage(int xMin, int yMin, int xMax, int yMax) {
        int newWidth = xMax - xMin ;
        int newHeight = yMax - yMin;

        int dimension = Math.max(newHeight, newWidth) + 2 * OFFSET;
        BufferedImage newImage = new BufferedImage(dimension, dimension, image.getType());

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newImage.setRGB(i, j, ImageUtilities.colorToRGB(255, 255, 255, 255));
            }
        }

        int offset  = 0;
        if (newWidth >= newHeight) {
            offset = (dimension - newHeight) / 2;
            for (int i = 0; i < newWidth; i++) {
                for (int j = 0; j < newHeight; j++) {
                    int rgb = image.getRGB(i + xMin, j + yMin);
                    newImage.setRGB(i + 1, j + offset + 1, rgb);
                }
            }
        } else {
            offset = (dimension - newWidth) / 2;
            for (int i = 0; i < newWidth; i++) {
                for (int j = 0; j < newHeight; j++) {
                    int rgb = image.getRGB(i + xMin, j + yMin);
                    newImage.setRGB(i + offset + 1, j + 1, rgb);
                }
            }
        }

        return newImage;
    }
}
