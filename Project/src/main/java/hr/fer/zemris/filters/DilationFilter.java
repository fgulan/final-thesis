package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class DilationFilter implements IFilter {

    private static final int BLACK = 0;
    private static final int WHITE = 255;

    private BufferedImage filteredImage;

    private int width;
    private int height;

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        width = sourceImage.getWidth();
        height = sourceImage.getHeight();

        filteredImage = clone(sourceImage);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = ImageUtilities.getRed(sourceImage.getRGB(i, j));
                if (color == BLACK) {
                    convolve(i, j, sourceImage);
                }
            }
        }
        return filteredImage;
    }

    private void convolve(int i, int j, BufferedImage image) {
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    int rgb = ImageUtilities.
                            colorToRGB(ImageUtilities.getAlpha(image.getRGB(x, y)), 0, 0, 0);
                    filteredImage.setRGB(x, y, rgb);
                }
            }
        }
    }

    private final BufferedImage clone(BufferedImage image) {
        BufferedImage clone = new BufferedImage(image.getWidth(),
                image.getHeight(), image.getType());
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return clone;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return "Dilation";
    }
}