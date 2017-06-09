package hr.fer.zemris.utility;

/**
 * @author Filip Gulan
 */
public class ImageUtilities {

    public static int colorToRGB(int alpha, int red, int green, int blue) {
        int code = 0;

        code += alpha;
        code = code << 8;
        code += red;
        code = code << 8;
        code += green;
        code = code << 8;
        code += blue;
        return code;
    }

    public static int getAlpha(int rgb) {
        return (rgb >> 24) & 0xff;
    }

    public static int getRed(int rgb) { return (rgb >> 16) & 0xff; }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xff;
    }

    public static int getBlue(int rgb) {
        return (rgb >> 0) & 0xff;
    }
}
