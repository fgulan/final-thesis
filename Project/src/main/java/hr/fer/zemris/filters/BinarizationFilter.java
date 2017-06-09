package hr.fer.zemris.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class BinarizationFilter implements IFilter {

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        OtsuBinarize binarization = new OtsuBinarize();
        return binarization.applyFilter(sourceImage);
    }

    @Override
    public String getName() {
        return "Binarization";
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
