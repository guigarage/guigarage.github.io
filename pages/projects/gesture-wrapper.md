---
title: GestureWrapper
layout: article
hidden: true
header:
  image: sample
  text: GestureWrapper
---

The GestureWrapper lib add the functionallity of gestures to Swing applications on a Mac. Since several years Apple supports gesture on the trackpads of Macs and Macbooks. By using the GestureWrapper you can react on such gestures in your swing application.

{% include elements/warning-block.html text="The GestureWrapper project is not longer in active development. But since it is an open source project you can still find all sources and documentation online. Furher development in a fork as by providing pull request is always welcome." %}

## How to use the GestureWrapper

The GestureWrapper lib is published to Maven central. Therefore you can easily add it to any Maven (or gradle) project:

{% highlight xml %}
<dependency>
  <groupId>com.guigarage</groupId>
  <artifactId>gestures-wrapper</artifactId>
  <version>0.2</version>
</dependency>
{% endhighlight %}

## Links for the GestureWrapper

If you want to know more about the GestureWrapper you should have a look at the following links:

* [Fun with gestures]({{ site.baseurl }}{% post_url 2011-07-28-fun-with-gestures %})
* [Multitouch gestures in Swing]({{ site.baseurl }}{% post_url 2011-08-04-preview-multitouch-gestures-in-swing %})
* [Gesture wrapper released]({{ site.baseurl }}{% post_url 2011-09-01-gesture-wrapper-0-1-released %})
* [New gesture wrapper release]({{ site.baseurl }}{% post_url 2011-10-04-release-update %})
