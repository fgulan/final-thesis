package hr.fer.zemris.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class NearestNeighborScaleFilter implements IFilter {

    private int newWidth;
    private int newHeight;

    public NearestNeighborScaleFilter(int newWidth, int newHeight) {
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public String getName() {
        return "NN scale: " + newWidth + " x " + newHeight;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage scaledImage = new BufferedImage(
                newWidth, newHeight, sourceImage.getType());

        float xRatio = width / (float) newWidth;
        float yRatio = height / (float) newHeight;
        int x, y;
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                x = (int) Math.floor(i * xRatio);
                y = (int) Math.floor(j * yRatio);
                scaledImage.setRGB(i, j, sourceImage.getRGB(x, y));
            }
        }
        return scaledImage;
    }

    @Override
    public String toString() {
        return getName();
    }
}
