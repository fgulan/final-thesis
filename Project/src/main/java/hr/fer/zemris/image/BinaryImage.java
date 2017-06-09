package hr.fer.zemris.image;

import hr.fer.zemris.filters.IFilter;
import hr.fer.zemris.utility.ImageUtilities;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class BinaryImage {

    private int[][] image;
    private BufferedImage imageRepresentation;

    /**
     * Image needs to be a binary.
     * @param image
     */
    public BinaryImage(BufferedImage image) {
        this(image, null);
    }

    public BinaryImage(BufferedImage image, List<IFilter> filters) {
        BufferedImage filteredImage = image;
        if (filters != null) {
            for (IFilter filter : filters) {
                filteredImage = filter.applyFilter(filteredImage);
            }
        }
        int width = filteredImage.getWidth();
        int height = filteredImage.getHeight();
        this.imageRepresentation = filteredImage;
        this.image = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = ImageUtilities.getRed(filteredImage.getRGB(j, i));
                if (pixel != 255 && pixel != 0) {
                    throw new BinaryImageException("Given image is not binary image!");
                }
                this.image[i][j] = 1 - pixel/255;
               // System.out.print(this.image[i][j]);
            }
            //System.out.println();
        }
    }

    public BufferedImage getImageRepresentation() {
        if (imageRepresentation == null) {
            int width = image.length;
            int height = image[0].length;
            imageRepresentation = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = image[i][j] == 1 ? 0 : 255;
                    int rgb = ImageUtilities.colorToRGB(1, pixel, pixel, pixel);
                    imageRepresentation.setRGB(i, j, rgb);
                }
            }
        }
        return imageRepresentation;
    }
    
    public String dumpToJBIC() {
        StringBuilder builder = new StringBuilder();
        int width = image.length;
        int height = image[0].length;
        builder.append("w " + width + "\n");
        builder.append("h " + height + "\n");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (image[i][j] == 0) {
                    builder.append("p " + i + " " + j + "\n");
                }
            }
        }
        return builder.toString();
    }

    public double[] getVector() {
        int width = image.length;
        int height = image[0].length;
        double[] vector = new double[width*height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                vector[i*width + j] = image[i][j];
            }
        }
        return vector;
    }

    public int[][] getMatrix() {
        return image;
    }

    public int getWidth() {
        return image.length;
    }

    public int getHeight() {
        return image[0].length;
    }
}
