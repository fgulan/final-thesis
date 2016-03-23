package hr.fer.zemris.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class BinarizationImageFilter implements IImageFilter {

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
