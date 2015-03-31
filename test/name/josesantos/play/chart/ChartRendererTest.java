package name.josesantos.play.chart;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.twirl.api.Html;

import com.google.common.collect.ImmutableMap;

/**
 * Chart renderer test.
 * 
 * @author Jose Santos
 * @version 0.1
 * @since 0.1
 */
public class ChartRendererTest {

    private Map<Comparable<?>, Map<Comparable<?>, Number>> seriesCategories;

    @Before
    public void setup() {
	this.seriesCategories = ImmutableMap.<Comparable<?>, Map<Comparable<?>, Number>> of("S1",
		ImmutableMap.of("C1", 1, "C2", 2));
    }

    @Test
    public void imgDefault() {
	Chart chart = ChartBuilderFactory.newBarChartBuilder(this.seriesCategories).build();
	Html html = ChartRenderer.img(chart);
	assertImg(html.text(), AbstractChart.DEFAULT_WIDTH, AbstractChart.DEFAULT_HEIGHT, "");
    }

    @Test
    public void imgCustom() {
	int width = 100, height = 100;
	Chart chart = ChartBuilderFactory.newBarChartBuilder(this.seriesCategories).width(width).height(height).build();
	Html html = ChartRenderer.img(chart);
	assertImg(html.text(), width, height, "");
    }

    @Test
    public void imgDefaultWithAttributes() {
	Map<String, String> attributes = ImmutableMap.of("title", "Title");
	Chart chart = ChartBuilderFactory.newBarChartBuilder(this.seriesCategories).build();
	Html html = ChartRenderer.img(chart, attributes);
	assertImg(html.text(), AbstractChart.DEFAULT_WIDTH, AbstractChart.DEFAULT_HEIGHT, " title=\"Title\"");
    }

    @Test
    public void imgCustomWithAttributes() {
	int width = 100, height = 100;
	Map<String, String> attributes = ImmutableMap.of("title", "Title");
	Chart chart = ChartBuilderFactory.newBarChartBuilder(this.seriesCategories).width(width).height(height).build();
	Html html = ChartRenderer.img(chart, attributes);
	assertImg(html.text(), width, height, " title=\"Title\"");
    }

    private void assertImg(String text, int width, int height, String attributes) {
	String w = String.valueOf(width);
	String h = String.valueOf(height);
	assertThat(text).matches(
		"<img src=\"data:image/png;base64,[^\"]+\" width=\"" + w + "\" height=\"" + h + "\"" + attributes
			+ "/>");
    }

}