package name.josesantos.play.chart;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Chart builder factory test.
 * 
 * @author Jose Santos
 * @version 0.1
 * @since 0.1
 */
public class ChartBuilderFactoryTest {

    private Map<Comparable<?>, Map<Comparable<?>, Number>> seriesCategories;
    private Map<Comparable<?>, Number> keysValues;

    @Before
    public void setup() {
	this.seriesCategories = ImmutableMap.<Comparable<?>, Map<Comparable<?>, Number>> of("S1",
		ImmutableMap.of("C1", 1, "C2", 2));
	this.keysValues = ImmutableMap.<Comparable<?>, Number> of("K1", 1, "K2", 2);
    }

    @Test
    public void newBarChartBuilder() {
	assertChart(ChartBuilderFactory.newBarChartBuilder(this.seriesCategories).build());
    }

    @Test
    public void newLineChartBuilder() {
	assertChart(ChartBuilderFactory.newLineChartBuilder(this.seriesCategories).build());
    }

    @Test
    public void newPieChartBuilder() {
	assertChart(ChartBuilderFactory.newPieChartBuilder(this.keysValues).build());
    }

    @Test
    public void newRingChartBuilder() {
	assertChart(ChartBuilderFactory.newRingChartBuilder(this.keysValues).build());
    }

    private void assertChart(Chart chart) {
	assertThat(chart.getWidth()).isEqualTo(AbstractChart.DEFAULT_WIDTH);
	assertThat(chart.getHeight()).isEqualTo(AbstractChart.DEFAULT_HEIGHT);
	assertThat(chart.asBase64()).isNotNull();
	assertThat(chart.asRaw()).isNotNull();
    }

}