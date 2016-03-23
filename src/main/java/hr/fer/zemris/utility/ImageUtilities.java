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

    public static int getAlphaFromRGB(int rgb) {
        return (rgb >> 24) & 0xff;
    }

    public static int getRedFromRGB(int rgb) { return (rgb >> 16) & 0xff; }

    public static int getGreenFromRGB(int rgb) {
        return (rgb >> 8) & 0xff;
    }

    public static int getBlueFromRGB(int rgb) {
        return (rgb >> 0) & 0xff;
    }
}
