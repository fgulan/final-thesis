package hr.fer.zemris.io;

import hr.fer.zemris.filters.FiltersFactory;
import hr.fer.zemris.image.BinaryImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

;

public class ImagesTask extends SwingWorker<Void, Void> {

    private List<Path> images;
    private Path outputPath;

    public ImagesTask(List<Path> images, Path outputPath) {
        this.images = images;
        this.outputPath = outputPath;
    }

    @Override
    public Void doInBackground() {
        if (images == null || outputPath == null) {
            return null;
        }

        int max = images.size();
        int counter = 0;
        for (Path path : images) {
            try {
                counter++;
                BufferedImage image = ImageIO.read(path.toFile());
                BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
                File newImage = new File(outputPath + "/" + path.getFileName());
                System.out.println(newImage.getPath());
                ImageIO.write(binImage.getImageRepresentation(), "png", newImage);
                setProgress((int) (100 * counter/(double) max));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Greska");
            }
        }
        setProgress(0);
        return null;
    }

    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
    }
}
