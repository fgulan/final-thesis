package hr.fer.zemris.gui.image;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class ImageViewer extends JPanel {

    private String title;
    private ImageComponent imageComponent;

    public ImageViewer(String title) {
        this(title, null);
    }

    public ImageViewer(String title, BufferedImage image) {
        super();
        this.title = title;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        imageComponent = new ImageComponent(image, ImageScale.ASPECT_FIT);
        add(imageComponent);

        // Label constraints
        layout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Imageview constraints
        layout.putConstraint(SpringLayout.EAST, imageComponent, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, imageComponent, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, imageComponent, 0, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.NORTH, imageComponent, 10, SpringLayout.SOUTH, label);

    }

    public void setImage(BufferedImage image) {
        imageComponent.setImage(image);
    }

    public BufferedImage getImage() {
        return imageComponent.getImage();
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
