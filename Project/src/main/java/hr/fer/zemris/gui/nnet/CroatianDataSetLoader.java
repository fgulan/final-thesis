package hr.fer.zemris.gui.nnet;

import hr.fer.zemris.gui.console.JConsole;
import hr.fer.zemris.image.CroatianCharacterMapper;
import hr.fer.zemris.image.ICharacterMapper;
import hr.fer.zemris.learn.InputImage;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class CroatianDataSetLoader extends AbstractDataSetLoader {

    private static final int INPUT_LAYER = 65;
    private static final int OUTPUT_LAYER = 32;

    public CroatianDataSetLoader(JConsole console, boolean useLowercase) {
        super(console, useLowercase);
    }

    @Override
    public DataSet loadDataSet(Path dataSetPath) {
        List<InputImage> images = loadInputImages(dataSetPath);
        DataSet dataSet = new DataSet(INPUT_LAYER, OUTPUT_LAYER);
        ICharacterMapper mapper = new CroatianCharacterMapper();
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
            return "Croatian";
        } else {
            return "Croatian_Without_Lowercase";
        }
    }
}
