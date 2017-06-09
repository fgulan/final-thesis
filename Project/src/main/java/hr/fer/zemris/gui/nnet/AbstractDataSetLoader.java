package hr.fer.zemris.gui.nnet;

import hr.fer.zemris.filters.FiltersFactory;
import hr.fer.zemris.gui.console.JConsole;
import hr.fer.zemris.image.BinaryImage;
import hr.fer.zemris.image.ICharacterMapper;
import hr.fer.zemris.io.ImageFinder;
import hr.fer.zemris.learn.DiagonalCrossFeatureVector;
import hr.fer.zemris.learn.IFeatureVector;
import hr.fer.zemris.learn.InputImage;
import org.neuroph.core.data.DataSetRow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public abstract class AbstractDataSetLoader implements IDataSetLoader {
    protected JConsole console;
    protected boolean useLowercase;

    public AbstractDataSetLoader(JConsole console, boolean useLowercase) {
        this.console = console;
        this.useLowercase = useLowercase;
    }

    protected List<InputImage> loadInputImages(Path inputPath) {
        ImageFinder finder = new ImageFinder();
        List<InputImage> inputImages = new ArrayList<>();

        try {
            Files.walkFileTree(inputPath, finder);
            List<Path> images = finder.getImages();
            for (Path path : images) {
                char name = path.getFileName().toString().charAt(0);
                if (!useLowercase && Character.isLowerCase(name)) {
                    continue;
                }
                BufferedImage image = ImageIO.read(path.toFile());
                BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
                inputImages.add(new InputImage(binImage, name));
            }
        } catch (Exception e) {
            console.addLog("Unable to load images from given path!");
        }
        return inputImages;
    }

    protected DataSetRow getDataSetRow(InputImage image, ICharacterMapper mapper) {
        int index = mapper.mapCharacter(image.getSymbol());
        if (index == -1) {
            return null;
        }
        IFeatureVector vector = new DiagonalCrossFeatureVector(image);
        double[] output = new double[mapper.getSize()];
        output[index] = 1.0;
        return new DataSetRow(vector.getInput(), output);
    }
}
