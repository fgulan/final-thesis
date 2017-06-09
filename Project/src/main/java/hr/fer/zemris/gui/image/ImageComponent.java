package hr.fer.zemris.gui.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class ImageComponent extends JComponent {

    private BufferedImage image;
    private ImageScale scale;

    public ImageComponent(BufferedImage image, ImageScale scale) {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.scale = scale;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public ImageScale getScale() {
        return scale;
    }

    public void setScale(ImageScale scale) {
        this.scale = scale;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null)  {
            Image scaledImage = null;
            Dimension imageDimension = new Dimension(image.getWidth(this), image.getHeight(this));
            Dimension boundaries = new Dimension(getWidth(), getHeight());
            Dimension scaled = null;

            switch (scale) {
                case SCALE_TO_FILL:
                    scaled = boundaries;
                    break;
                case ASPECT_FIT:
                    scaled = getScaledDimension(imageDimension, boundaries);
                    break;
            }
            scaledImage = image.getScaledInstance(scaled.width, scaled.height, Image.SCALE_FAST);
            Point upperLeft = getStartPoint(scaled, boundaries);
            g.drawImage(scaledImage, upperLeft.x, upperLeft.y, null);
        }
    }

    private Point getStartPoint(Dimension imageDimensoin, Dimension boundaries) {
        int widthOffset = boundaries.width - imageDimensoin.width;
        int heightOffset = boundaries.height - imageDimensoin.height;
        return new Point(widthOffset/2, heightOffset/2);
    }

    private Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
