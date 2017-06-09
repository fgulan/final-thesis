package hr.fer.zemris.gui.nnet;

import hr.fer.zemris.gui.console.JConsole;
import hr.fer.zemris.image.EnglishCharacterMapper;
import hr.fer.zemris.image.ICharacterMapper;
import hr.fer.zemris.learn.InputImage;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class EnglishDataSetLoader extends AbstractDataSetLoader {

    private static final int INPUT_LAYER = 65;
    private static final int OUTPUT_LAYER = 27;

    public EnglishDataSetLoader(JConsole console, boolean useLowercase) {
        super(console, useLowercase);
    }

    @Override
    public DataSet loadDataSet(Path dataSetPath) {
        List<InputImage> images = loadInputImages(dataSetPath);
        DataSet dataSet = new DataSet(INPUT_LAYER, OUTPUT_LAYER);
        ICharacterMapper mapper = new EnglishCharacterMapper();
        for (InputImage image : images) {
            DataSetRow row = getDataSetRow(image, mapper);
            if (row == null) {
                continue;
            }
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public String toString() {
        if (useLowercase) {
            return "English";
        } else {
            return "English_Without_Lowercase";
        }
    }
}
