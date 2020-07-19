---
title: 'GridFX on Raspberry Pi & JavaFX 8'
layout: post
author: hendrik
categories: [IoT, JavaFX]
excerpt: 'For my first JFX demo I created a experimental GridFX version for JavaFX 8.'
featuredImage: java-3
permalink: '2012/12/gridfx-on-raspberry-pi-javafx-8/'
header:
  text: GridFX on Raspberry Pi
  image: sample
---
For my [first JFX demo]({{ site.baseurl }}{% post_url 2012-12-28-my-first-steps-with-javafx-on-raspberry-pi %}) I tried to create a experimental [GridFX]({{ site.baseurl }}{% link pages/projects/gridfx.md %}) version for JavaFX 8. As I mentioned in an [earlier post]({{ site.baseurl }}{% post_url 2012-11-17-custom-ui-controls-with-javafx-part-1 %}) there are some API changed between JavaFX 2 and 8. For GridFX I had to change two things:

* The BaseSkin class is now public
* The CSS support that I used in GridFX seams to work completely different in JavaFX 8.

Both issues were fixed very fast and an GridFX demo was running on my Mac where I have JDK "build 1.8.0-ea-b69" installed ([download it here](http://jdk8.java.net/download.html)). But after deploying the demo on my Pi nothing happened...

So I created a log file for the java output by adding some parameters to the shell command:

{% highlight shell %}
java -cp myApp.jar com.guigarage.Demo >log 2>&1
{% endhighlight %}

The logging showed me the cause of my problems:

{% highlight shell %}
java.lang.NoSuchMethodError: javafx.scene.control.SkinBase.(Ljavafx/scene/control/Control;)V at com.guigarage.fx.grid.skin.GridViewSkin.(GridViewSkin.java:24)
{% endhighlight %}

While everything compiled perfectly on my Mac, the Pi has a different JavaFX compilation / version ("build 1.8.0-ea-b36e"). As a first solution I downloaded the "jfxrt.jar" file from my Pi and included it to the GridFX project on my Mac. After doing so I saw all the compilation problems in Eclipse and had a change to resolve them. It would be very nice to have a real 1.8.0-ea-b36 build installed on my Mac. Any idea where to get it?

But after fixing all this problems the GridFX demo is running on my Pi:

{% include posts/youtube.html id="OZv6WUpEzS8" %}
