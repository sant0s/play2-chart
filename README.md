Play 2 Chart module
===================

The [Play 2 Chart module](https://github.com/sant0s/play2-chart) allows for easy generation of chart images. The following chart types are supported:

-   Bar
-   Line
-   Pie
-   Ring

The format of the images is PNG.

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
`src` attribute is:

1.  A Base64-encoded image:
    -   `<img src="data:image/png;base64,<base64> <attrs> />`
        -   `<base64>` is a Base64-encoded image
        -   `<attrs>` is a list of `img` attributes besides `src`

2.  An image URL:
    -   `<img src="<url>" <attrs> />`
        -   `<url>` is the URL of an image
        -   `<attrs>` is a list of `img` attributes besides `src`

The Chart module supports both of the above scenarios. A chart image can
be generated in both its Base64-encoded and raw forms. These scenarios
are described in the following section.

Usage
-----

### Chart as `img` with `src` set to a Base64-encoded image

In this scenario, a controller creates (or obtains) a `Chart` instance
and passes it to a view. The view then renders it through a method call.

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

1.  Initialise a dataset that is suitable to the chart. Different chart
    types require different dataset types. In this case, a pie chart
    requires a map of keys and values which will be rendered as its
    slices.
2.  Get a builder for a specific type of chart.
3.  Optionally, configure the builder. In this case, one’s setting the
    chart title, informing that the legend should be displayed, and
    setting the width and height of the chart image.
4.  Build the chart according to the previously set dataset and optional
    settings.
5.  Pass the chart to the view.

#### Rendering the `Chart` instance in the view

A `Chart` can be rendered in a view through the following method call:

    @import name.josesantos.play.chart.ChartRenderer._
    @img(pieChart)

This method will create the chart image and generate an `img` tag with
the following attributes:

-   `src` set to the Base64-encoded representation of the chart image
-   `width` set to the width of the chart image (\*)
-   `height` set to the height of the chart image (\*)

(\*) These attributes provide rendering hints to the user agent.

Additional attributes may be supplied using the following method call
which accepts a map of attribute names and values as its second
parameter:

    @import name.josesantos.play.chart.ChartRenderer._
    @img(pieChart, Map("title" -> "Pie chart"))

These attributes are copied verbatim to the HTML `img` tag, so in this
case the result would be an `img` tag similar to the previous one but
having the `title` attribute set.

### Chart as `img` with `src` set to an image URL

In this scenario, a controller creates (or obtains) a `Chart` instance,
creates its corresponding image and returns it in its raw form i.e. as
PNG bytes, once PNG is the format of chart images.

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

In this case, `ChartResults.ok(chart)` will create the chart image and
return it in its raw form i.e. as PNG bytes.

#### Rendering the `Chart` instance in the view

Assuming the code above within an action method named `chart()` of a
controller named `Application`, the view could render the chart image
using:

    <img src="@routes.Application.chart()" />

Additional information
----------------------

The examples above illustrate the idiom used for obtaining a `Chart`
instance and rendering its image using Base64-encoded and raw forms.

`ChartBuilderFactory` is the entry point for chart creation. This factory provides methods for obtaining builders of all supported charts. Once a builder is obtained, different settings can be configured via its API.

Additional information on all supported chart types and builder
configuration options can be found in the sample application.
