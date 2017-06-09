package hr.fer.zemris.filters;

/**
 * @author Filip Gulan
 */
public class GrayscaleBT601Filter extends AbstractGrayscaleFilter {

    private static final float RED_GRAYSCALE_FACTOR = 0.299f;
    private static final float GREEN_GRAYSCALE_FACTOR = 0.587f;
    private static final float BLUE_GRAYSCALE_FACTOR = 0.114f;

    @Override
    public String getName() {
        return "Grayscale BT601";
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
