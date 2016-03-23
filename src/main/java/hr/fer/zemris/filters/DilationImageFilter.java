package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;
import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class DilationImageFilter implements IImageFilter {

    private static final int BLACK = 0;
    private static final int WHITE = 255;

    private BufferedImage filteredImage;

    private int width;
    private int height;

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        width = sourceImage.getWidth();
        height = sourceImage.getHeight();

        filteredImage = new BufferedImage(width, height, sourceImage.getType());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = ImageUtilities.getRedFromRGB(sourceImage.getRGB(i, j));
                if (color == BLACK) {
                    convolve(i, j, sourceImage);
                } else {
                    int alpha = ImageUtilities.getAlphaFromRGB(sourceImage.getRGB(i, j));
                    int rgb = ImageUtilities.colorToRGB(alpha, WHITE, WHITE, WHITE);
                    filteredImage.setRGB(i, j, rgb);
                }
            }
        }
        return filteredImage;
    }

    private void convolve(int i, int j, BufferedImage image) {
        for (int x = i - 2; x <= i + 2; x++) {
            for (int y = j - 2; y <= j + 2; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    int rgb = ImageUtilities.colorToRGB(ImageUtilities.getAlphaFromRGB(image.getRGB(x, y)), 0, 0, 0);
                    filteredImage.setRGB(x, y, rgb);
                }
            }
        }
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