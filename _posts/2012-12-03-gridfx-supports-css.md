---
title: 'GridFX supports CSS'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'GridFX has no CSS support. Several CSS properties were added to GridFX and let you style the control'
featuredImage: sample-10
permalink: '2012/12/gridfx-supports-css/'
header:
  text: GridFX supports CSS
  image: sample
---
I added CSS support to [GridFX]({{ site.baseurl }}{% post_url 2012-11-29-gridfx-is-moving-forward %}). There are five properties that can be styled by CSS at the moment:

* `-fx-vertical-cell-spacing` - vertical spacing between two cells (double)
* `-fx-horizontal-cell-spacing` - horizontal spacing between two cells (double)
* `-fx-cell-height` - height of a cell (double)
* `-fx-cell-width` - width of a cell (double)
* `-fx-horizontal-alignment` - horizontal alignment of all cells (LEFT,CENTER, or RIGHT)

Stay tuned for the upcoming "Custom Controls" post that will handle CSS support.
