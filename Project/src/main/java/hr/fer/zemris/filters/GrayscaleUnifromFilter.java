package hr.fer.zemris.filters;

/**
 * @author Filip Gulan
 */
public class GrayscaleUnifromFilter extends AbstractGrayscaleFilter {

    private static final float RED_GRAYSCALE_FACTOR = 1.0f/3.0f;
    private static final float GREEN_GRAYSCALE_FACTOR = 1.0f/3.0f;
    private static final float BLUE_GRAYSCALE_FACTOR = 1.0f/3.0f;

    @Override
    public String getName() {
        return "Grayscale Uniform";
    }

    protected int getGray(int red, int green, int blue) {
        return (int) (RED_GRAYSCALE_FACTOR * red +
                GREEN_GRAYSCALE_FACTOR * green +
                BLUE_GRAYSCALE_FACTOR * blue);
    }

    @Override
    public String toString() {
        return getName();
    }
}
