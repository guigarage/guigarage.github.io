---
title: 'JGrid Tutorial #4'
layout: post
author: hendrik
categories: [Swing]
excerpt: 'TODO'
featuredImage: sample-2
permalink: '2011/09/jgrid-tutorial-4/'
header:
  text: JGrid Tutorial #4
  image: sample
---
In this tutorial we want to add zoom functionality to the JGrid. You can set the dimension of the grid cells be the property `fixedCellDimension`. Here is a example for two different dimensions:

![tutorial4-1](/assets/posts/guigarage-legacy/tutorial4-1.png)

![tutorial4-2](/assets/posts/guigarage-legacy/tutorial4-2.png)

To add a zoom functionality to the grid you can set the dimension by using a JSlider. Here is the code:

{% highlight Java %}
final JSlider slider = new JSlider(32, 256);
slider.setValue(grid.getFixedCellDimension());

slider.addChangeListener(new ChangeListener() {
  @Override
  public void stateChanged(ChangeEvent arg0) {
    grid.setFixedCellDimension(slider.getValue());
  }
});
{% endhighlight %}

Now you can edit the dimension dynamically. Here is the result:

{% include posts/youtube.html id="Zyqf-P2ftFs" %}

You can download the source file [here](/assets/downloads/jgrid/tutorial4.java).
