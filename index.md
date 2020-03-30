Play 2 Chart module
===================

The [Play 2 Chart module](https://github.com/sant0s/play2-chart) allows easy generation of chart images. The following chart types are supported:

-   Bar
-   Line
-   Pie
-   Ring

The image format is PNG.

This module makes use of the JFreeChart library.

Quick example
-------------

### Controller

    Map<Comparable<?>, Number> dataset = ImmutableMap.of("Slice 1", 51, "Slice 2", 49);
    Chart pieChart = ChartBuilderFactory.newPieChartBuilder(dataset).build();
    return ok(views.html.index.render(pieChart));

### View

    @img(pieChart)

build.sbt setup
-----

Resolver:

```
@resolvers += Resolver.url("sant0s release repository", url("https://github.com/sant0s/release/raw/master"))(Resolver.ivyStylePatterns)@
```

Dependency:

```
@"name.josesantos" %% "chart" % "{version}"@
```

where `{version}` is taken from the first column of the following table:

| Module | Play  | Description    |
| ------ | ----- | -------------- |
| 0.1.0  | 2.3.X | First version. |


Sample application
------------------

A sample application can be found at [https://github.com/sant0s/play2-chart-sample](https://github.com/sant0s/play2-chart-sample). It provides documentation and examples of all supported charts.

Introduction
------------

For HTML views, an image can be represented as an `img` tag where its
`src` attribute is either:

1.  A Base64-encoded image:
    -   `<img src="data:image/png;base64,<base64> <attrs> />`
        -   `<base64>` is a Base64-encoded image
        -   `<attrs>` is a list of additional attributes

2.  An image URL:
    -   `<img src="<url>" <attrs> />`
        -   `<url>` is the URL of an image
        -   `<attrs>` is a list of `img` attributes besides `src`

The Chart module supports both scenarios i.e. chart images can
be generated in either Base64-encoded or raw forms.

Usage
-----

### Chart as `img` with `src` set to a Base64-encoded image

In this scenario, a controller creates (or obtains) a `Chart` instance
and passes it to a view which renders it through a method call.

#### Creating a `Chart` instance in the controller

A `Chart` can be created using the following idiom (example for a pie
chart):

    Map<Comparable<?>, Number> dataset = ImmutableMap.<Comparable<?>, Number> builder()
        .put("Asia", 29.5D).put("Africa", 20.4D).put("North America", 16.5D)
        .put("South America", 12D).put("Antarctica", 9.2D)
        .put("Europe", 6.8D).put("Australia", 5.9D).build();
    PieChartBuilder builder = ChartBuilderFactory.newPieChartBuilder(dataset);
    builder.title("Pie").legend(true).width(700).height(500);
    Chart pieChart = builder.build();
    return ok(views.html.index.render(pieChart));   // example for index.scala.html

The steps are:

1.  Initialise the chart dataset. Different chart types require different
    dataset types. In this example, the pie chart requires a map of
    key-value pairs, each to be rendered as its slices.
2.  Get a builder for the chart type.
3.  Optionally, configure the builder. In this example, the chart title,
    legend visibility and image dimensions are set.
4.  Build the chart.
5.  Pass the chart to the view.

#### Rendering the `Chart` instance in the view

A `Chart` can be rendered in a view via:

    @import name.josesantos.play.chart.ChartRenderer._
    @img(pieChart)

The @img@ method call generates the chart image and generates an `img` tag
with the following attributes:

-   `src` set to the Base64-encoded representation of the chart image
-   `width` set to the chart image width
-   `height` set to the chart image height

Attributes @width@ and @height@ provide rendering hints to the user agent.

Additional attributes may be supplied via:

    @import name.josesantos.play.chart.ChartRenderer._
    @img(pieChart, Map("title" -> "Pie chart"))

These attributes are copied verbatim to the HTML `img` tag. In this example,
`img` will include attributes `src`, `width`, `height` and `title`.

### Chart as `img` with `src` set to a URL

In this scenario, a controller creates (or obtains) a `Chart` instance,
generates its image and returns it in its raw form i.e. as
PNG bytes.

#### Creating a `Chart` instance in the controller

The steps are similar to the previous scenario, except for the render
part:

    import name.josesantos.play.chart.ChartResults;
    Map<Comparable<?>, Number> dataset = ImmutableMap.<Comparable<?>, Number> builder()
        .put("Asia", 29.5D).put("Africa", 20.4D).put("North America", 16.5D)
        .put("South America", 12D).put("Antarctica", 9.2D)
        .put("Europe", 6.8D).put("Australia", 5.9D).build();
    PieChartBuilder builder = ChartBuilderFactory.newPieChartBuilder(dataset);
    builder.title("Pie").legend(true).width(700).height(500);
    Chart pieChart = builder.build();
    return ChartResults.ok(chart);

`ChartResults.ok(chart)` will generate the chart image and return it
as PNG bytes.

#### Rendering the `Chart` instance in the view

Assuming that the code above belongs to an action method named
`chart()` within a controller named `Application`, the view can render
the chart image via:

    <img src="@routes.Application.chart()" />

Additional information
----------------------

The examples herein illustrate the idiom to obtain a `Chart` instance
and render its image using Base64-encoded and raw forms.

`ChartBuilderFactory` is the entry point for chart generation. This factory provides methods for obtaining configurable builders of all supported chart types.

Additional information on supported chart types and builder
configuration options can be found in the sample application.
