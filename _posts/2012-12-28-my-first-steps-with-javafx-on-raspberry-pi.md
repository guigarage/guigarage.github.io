---
title: 'My first steps with JavaFX on Raspberry Pi'
layout: post
author: hendrik
categories: [DataFX, IoT, JavaFX]
excerpt: 'Today I started playing with my Pi & JavaFX. I created a screensaver as a first demo. The programm loads random pictures (2848 × 4288 pixel) and animates them on the screen.'
featuredImage: java-2
permalink: '2012/12/my-first-steps-with-javafx-on-raspberry-pi/'
header:
  text: My first steps with JavaFX on Raspberry Pi
  image: sample
---
Today I started playing with my Pi & JavaFX. Even after a few hours I can say that this stuff really rocks!

I created a screensaver as a first demo. The programm loads random pictures (2848 × 4288 pixel) and animates them on the screen:

{% include posts/youtube.html id="r0GEm1pEhoE" %}

I used a experimental build of [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) for loading and scaling all the pictures. In my point of view the performance of all animations is awesome. Oracle has really done a great work!

Next will be a [GridFX]({{ site.baseurl }}{% link pages/projects/gridfx.md %}) demo that I currently try to port to JFX8.

For more JFX & Pi infos read [this article by Stephen Chin](http://javafx.steveonjava.com/javafx-on-raspberry-pi-3-easy-steps/).
