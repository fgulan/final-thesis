package hr.fer.zemris.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public interface IFilter {

    String getName();
    BufferedImage applyFilter(BufferedImage sourceImage);
}
