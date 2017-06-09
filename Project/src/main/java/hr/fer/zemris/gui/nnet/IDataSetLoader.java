package hr.fer.zemris.gui.nnet;

import org.neuroph.core.data.DataSet;

import java.nio.file.Path;

/**
 * @author Filip Gulan
 */
public interface IDataSetLoader {
    public DataSet loadDataSet(Path dataSetPath);
}
