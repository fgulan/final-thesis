package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class GrayscaleImageFilter implements IImageFilter {

    private static final float RED_GRAYSCALE_FACTOR = 0.2126f;
    private static final float GREEN_GRAYSCALE_FACTOR = 0.7152f;
    private static final float BLUE_GRAYSCALE_FACTOR = 0.0722f;

    @Override
    public String getName() {
        return "Grayscale";
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, sourceImage.getType());

        int gray, rgb;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = sourceImage.getRGB(i, j);
                gray = getGray(ImageUtilities.getRedFromRGB(rgb),
                        ImageUtilities.getGreenFromRGB(rgb),
                        ImageUtilities.getBlueFromRGB(rgb));

                gray = ImageUtilities.colorToRGB(ImageUtilities.getAlphaFromRGB(rgb), gray, gray, gray);
                filteredImage.setRGB(i, j, gray);
            }
        }
        return filteredImage;
    }

    private int getGray(int red, int green, int blue) {
        return (int) (RED_GRAYSCALE_FACTOR * red +
                GREEN_GRAYSCALE_FACTOR * green +
                BLUE_GRAYSCALE_FACTOR * blue);
    }

    @Override
    public String toString() {
        return getName();
    }
}
