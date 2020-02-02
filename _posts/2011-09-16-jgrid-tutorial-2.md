---
title: 'JGrid Tutorial #2'
layout: post
author: hendrik
categories: [Swing]
excerpt: 'I created a series of tutorials to get familiar with JGrid.'
featuredImage: java-1
permalink: '2011/09/jgrid-tutorial-2/'
header:
  text: JGrid Tutorial #2
  image: sample
---
After we created a simple JGrid (see [tutorial #1]({{ site.baseurl }}{% post_url 2011-09-14-jgrid-tutorial-1 %}) we want to modify the look now. The JGrid has a lot of getter/setter to change the visualization of the grid. Read the JavaDoc for a complete overview of all properties.

Here is an example of changing colors and dimensions:

{% highlight Java %}
grid.setFont(grid.getFont().deriveFont(40.0f));
grid.setFixedCellDimension(56);
grid.setHorizonztalMargin(4);
grid.setVerticalMargin(4);
grid.setHorizontalAlignment(SwingConstants.LEFT);
grid.setBackground(Color.WHITE);
grid.setSelectionBorderColor(Color.BLUE);
grid.setSelectionBackground(Color.CYAN);
grid.setCellBackground(Color.LIGHT_GRAY);
{% endhighlight %}

After setting all properties the grid looks like this:

![Tutorial2](/assets/posts/guigarage-legacy/Tutorial2.png)

You can download the sources for the tutorial [here](/assets/downloads/jgrid/tutorial2.java).
