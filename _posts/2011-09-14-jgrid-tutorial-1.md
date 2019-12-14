---
title: 'JGrid Tutorial #1'
layout: post
author: hendrik
categories: [Swing]
excerpt: 'TODO'
featuredImage: sample-9
permalink: '2011/09/jgrid-tutorial-1/'
header:
  text: JGrid Tutorial #1
  image: sample
---
At the moment all JGrid demonstrations are very complex and use a lot of Java2D code, web services an so on. So many people asked me to create some simple demos. For this reason I started some bottom-up tutorials for the JGrid.

Here is the first one:
You can integrate a JGrid in every swing application. Just add it to a container:

{% highlight java %}
JGrid grid = new JGrid();
getContentPane().add(new JScrollPane(grid));
{% endhighlight %}

Normally you want to visualize some data in the grid. All data must wrapped in a ListModel:

{% highlight java %}
DefaultListModel model = new DefaultListModel();
for(int i=0; i &lt; 100; i++) {
  model.addElement(new Integer(i));
}
{% endhighlight %}

In a final step you must set the model for the grid:

{% highlight java %}
grid.setModel(model);
{% endhighlight %}

With this few lines of code you can add a JGrid to your code. Because the default renderer of the grid uses a label and renders the `toString()`-result of the data to the grid you will see all Integers in a grid:

![Tutorial1](/assets/posts/guigarage-legacy/Tutorial1.png)

You can download the [source file for the tutorial](/assets/downloads/jgrid/tutorial1.java). To run the program you need the jgrid.jar in your classpath.
