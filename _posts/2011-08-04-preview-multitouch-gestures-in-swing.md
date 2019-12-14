---
title: 'Preview: Multitouch gestures in swing'
layout: post
author: hendrik
categories: [General]
excerpt: 'TODO'
featuredImage: sample-4
permalink: '2011/08/preview-multitouch-gestures-in-swing/'
header:
  text: Multitouch gestures in swing
  image: sample
---
In my last [post]({{ site.baseurl }}{% post_url 2011-07-28-fun-with-gestures %}) I described Apples gestures API. Up to date I´m developing a wrapper API. With this API you can add multitouch-listeners to any swing component. On any OS unlike Mac OS a `GesturesNotSupportedException` is thrown if you try to register a listener. So you can use the API in every application. If the Applications runs on a Mac it supports gestures.

Here is how:

{% highlight Java %}
try {
  GestureUtilities.add(panel, gestureRotationListener);
} catch (GesturesNotSupportedException e) {
  System.out.println("Gestures-API not Supported!");
}
{% endhighlight %}

Or you can just check if the Apple API is supported:

{% highlight Java %}
if(!GestureUtilities.isSupported()) {
  System.out.println("Gestures-API not Supported!");
}
{% endhighlight %}

I will add javadoc to the source and update the gestures demo next week. You can check out the source @ [https://code.google.com/p/gestures-wrapper/](https://code.google.com/p/gestures-wrapper/).
