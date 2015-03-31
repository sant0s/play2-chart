package name.josesantos.play.chart;

import static name.josesantos.play.chart.AbstractChart.DEFAULT_IMAGE_CONTENT_TYPE;
import play.mvc.Results;
import play.mvc.Results.Status;

/**
 * Chart results.
 *
 * @author Jose Santos
 * @version 0.1
 * @since 0.1
 */
public class ChartResults {

    /**
     * Generate an HTTP 200 chart result. This method is meant to be used within controller actions rendering charts in
     * raw form.
     * 
     * @param chart Chart
     */
    public static Status ok(Chart chart) {
	return Results.ok(chart.asRaw()).as(DEFAULT_IMAGE_CONTENT_TYPE);
    }

}