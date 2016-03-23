package hr.fer.zemris.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class ThinningImageFilter implements IImageFilter {

    @Override
    public String getName() {
        return "Thinning";
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        ZhangSuenThinFilter filter = new ZhangSuenThinFilter();

        return filter.processImage(sourceImage);
    }

    @Override
    public String toString() {
        return getName();
    }
}
