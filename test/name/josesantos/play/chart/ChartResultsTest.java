package name.josesantos.play.chart;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.api.mvc.ResponseHeader;
import play.mvc.Results.Status;

import com.google.common.collect.ImmutableMap;

/**
 * Chart results test.
 * 
 * @author Jose Santos
 * @version 0.1
 * @since 0.1
 */
public class ChartResultsTest {

    private Map<Comparable<?>, Map<Comparable<?>, Number>> seriesCategories;

    @Before
    public void setup() {
	this.seriesCategories = ImmutableMap.<Comparable<?>, Map<Comparable<?>, Number>> of("S1",
		ImmutableMap.of("C1", 1, "C2", 2));
    }

    @Test
    public void ok() {
	Status status = ChartResults.ok(ChartBuilderFactory.newBarChartBuilder(this.seriesCategories).build());
	ResponseHeader header = status.toScala().header();
	assertThat(header.status()).isEqualTo(200);
	assertThat(header.headers().get("Content-Type").get()).isEqualTo("image/png");
    }

}