package hr.fer.zemris.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class ResizeFilter implements IFilter {

    private int dimension;

    public ResizeFilter(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public String getName() {
        return "Resize " + dimension;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        Image scaled = sourceImage.getScaledInstance(dimension, dimension, Image.SCALE_FAST);
        BufferedImage newBuffered = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
        newBuffered.getGraphics().drawImage(scaled, 0, 0, null);
        return newBuffered;
    }
}
