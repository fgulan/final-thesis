package hr.fer.zemris.filters;

import hr.fer.zemris.utility.ImageUtilities;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class BilinearScaleFilter implements IFilter {

    private int newWidth;
    private int newHeight;

    public BilinearScaleFilter(int newWidth, int newHeight) {
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public String getName() {
        return "Bilinear scale filter: " + newWidth + " x " + newHeight;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        float xRatio = (width - 1) / (float) newWidth;
        float yRatio = (height - 1) / (float) newHeight;
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, sourceImage.getType());

        int x, y, A, B, C, D, grey;
        float xDiff, yDiff;
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                x = (int) (xRatio * i);
                y = (int) (yRatio * j);

                xDiff = xRatio * i - x;
                yDiff = yRatio * j - y;

                A = ImageUtilities.getRed(sourceImage.getRGB(x, y));
                B = ImageUtilities.getRed(sourceImage.getRGB(x + 1, y));
                C = ImageUtilities.getRed(sourceImage.getRGB(x, y + 1));
                D = ImageUtilities.getRed(sourceImage.getRGB(x + 1, y + 1));

                // Y = A(1-w)(1-h) + B(w)(1-h) + C(h)(1-w) + Dwh
                grey = (int) (
                        A * (1 - xDiff) * (1 - yDiff) +
                        B * xDiff * (1 - yDiff) +
                        C * yDiff * (1 - xDiff) +
                        D * xDiff * yDiff);
                scaledImage.setRGB(i, j, ImageUtilities.colorToRGB(255, grey, grey, grey));
            }
        }
        return scaledImage;
    }

    @Override
    public String toString() {
        return getName();
    }
}
