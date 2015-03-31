package name.josesantos.play.chart;

import java.util.Map;

import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Dataset utilities.
 * 
 * @author Jose Santos
 * @version 0.1
 * @since 0.1
 */
class Datasets {

    private Datasets() {
    }

    static DefaultCategoryDataset asCategoryDataset(Map<Comparable<?>, Map<Comparable<?>, Number>> seriesCategories) {

	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	for (Map.Entry<Comparable<?>, Map<Comparable<?>, Number>> series : seriesCategories.entrySet()) {
	    for (Map.Entry<Comparable<?>, Number> category : series.getValue().entrySet()) {
		dataset.addValue(category.getValue(), series.getKey(), category.getKey());
	    }
	}

	return dataset;

    }

    static KeyedValues asKeyedValues(Map<Comparable<?>, Number> dataset) {

	DefaultKeyedValues keyedValues = new DefaultKeyedValues();

	for (Map.Entry<Comparable<?>, Number> entry : dataset.entrySet()) {
	    keyedValues.addValue(entry.getKey(), (Number) entry.getValue());
	}

	return keyedValues;

    }

}