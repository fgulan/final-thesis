package hr.fer.zemris.filters;

/**
 * @author Filip Gulan
 */
public class GrayscaleFilter extends AbstractGrayscaleFilter {

    private static final float RED_GRAYSCALE_FACTOR = 0.2126f;
    private static final float GREEN_GRAYSCALE_FACTOR = 0.7152f;
    private static final float BLUE_GRAYSCALE_FACTOR = 0.0722f;

    @Override
    public String getName() {
        return "Grayscale Luma";
    }

    protected int getGray(int red, int green, int blue) {
        return Math.round(RED_GRAYSCALE_FACTOR * red +
                GREEN_GRAYSCALE_FACTOR * green +
                BLUE_GRAYSCALE_FACTOR * blue);
    }

    @Override
    public String toString() {
        return getName();
    }
}
