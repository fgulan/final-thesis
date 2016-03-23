package hr.fer.zemris.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class FiltersFactory {

    public static List<IImageFilter> filters;

    public static List<IImageFilter> getAvailableFilters() {
        filters = new ArrayList<>();
        filters.add(new GrayscaleImageFilter());
        filters.add(new BinarizationImageFilter());
        filters.add(new DilationImageFilter());
        filters.add(new ThinningImageFilter());
        return filters;
    }
}
