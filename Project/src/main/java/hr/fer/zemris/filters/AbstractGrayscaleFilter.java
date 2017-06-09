package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public abstract class AbstractGrayscaleFilter implements IFilter {

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, sourceImage.getType());

        int gray, rgb;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = sourceImage.getRGB(i, j);
                gray = getGray(ImageUtilities.getRed(rgb),
                        ImageUtilities.getGreen(rgb),
                        ImageUtilities.getBlue(rgb));

                gray = ImageUtilities.
                        colorToRGB(ImageUtilities.getAlpha(rgb), gray, gray, gray);
                filteredImage.setRGB(i, j, gray);
            }
        }
        return filteredImage;
    }

    protected abstract int getGray(int red, int green, int blue);
}
