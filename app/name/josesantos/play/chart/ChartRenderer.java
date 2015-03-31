package name.josesantos.play.chart;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import play.twirl.api.Html;

/**
 * Chart renderer.
 * 
 * @author Jose Santos
 * @version 0.1
 * @since 0.1
 */
public class ChartRenderer {

    private static final String HTML_IMG = "<img src=\"data:image/png;base64,%s\" %s/>";
    private static final String[] EXCLUDE_IMG_ATTRS = {"src", "width", "height"};
    private static final String HTML_IMG_WIDTH_HEIGHT = "width=\"%s\" height=\"%s\"";
    private static final String HTML_IMG_ATTR = " %s=\"%s\"";

    /**
     * Render chart as an HTML <code>img</code> tag with the following attributes:
     * <ul>
     * <li><code>src</code> set to <code>data:image/png;base64,&lt;base64&gt;</code> where <code>&lt;base64&gt;</code>
     * is the Base64-encoded chart image</li>
     * <li><code>width</code> set to the chart image width</li>
     * <li><code>height</code> set to the chart image height</li>
     * </ul>
     * 
     * @param chart The chart to be rendered
     * @return Chart as an HTML <code>img</code> tag
     */
    public static Html img(Chart chart) {
	return img(chart, null);
    }

    /**
     * Render chart as an HTML <code>img</code> tag with the following attributes:
     * <ul>
     * <li><code>src</code> set to <code>data:image/png;base64,&lt;base64&gt;</code> where <code>&lt;base64&gt;</code>
     * is the Base64-encoded chart image</li>
     * <li><code>width</code> set to the chart image width</li>
     * <li><code>height</code> set to the chart image height</li>
     * <li><code>args</code></li>
     * </ul>
     * 
     * @param chart The chart to be rendered
     * @param attributes Attributes copied verbatim as HTML attributes of the HTML <code>img</code> tag
     * @return Chart as an HTML <code>img</code> tag
     */
    public static Html img(Chart chart, Map<String, String> attributes) {
	return new Html(getBase64IMG(chart, attributes));
    }

    private static String getBase64IMG(Chart chart, Map<String, String> args) {

	String base64IMG = null;

	if (chart != null) {
	    // build HTML img attributes src, width, height and args (excluding src, width and args themselves from it)
	    StringBuilder attributes = new StringBuilder(String.format(HTML_IMG_WIDTH_HEIGHT, chart.getWidth(),
		    chart.getHeight()));
	    if (args != null) {
		Map<String, String> arguments = new LinkedHashMap<String, String>(args);
		for (String key : EXCLUDE_IMG_ATTRS) {
		    arguments.remove(key);
		}
		for (Entry<String, String> arg : arguments.entrySet()) {
		    attributes.append(String.format(HTML_IMG_ATTR, arg.getKey(), arg.getValue()));
		}
	    }

	    // build the HTML img tag with the Base64-encoded chart image and the attributes
	    base64IMG = String.format(HTML_IMG, chart.asBase64(), attributes);
	}

	return base64IMG;

    }

}