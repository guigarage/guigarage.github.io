---
title: 'Maven support for DataFX'
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'TODO'
featuredImage: sample-6
permalink: '2012/11/maven-support-for-datafx/'
header:
  text: Maven support for DataFX
  image: sample
---
At [Devoxx](http://www.devoxx.com/display/DV12/Home) I met [Johan Vos](https://twitter.com/johanvos) and we talked about [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) and Maven support. Now two weeks later we released DataFX 1.0 with Maven support and I am a official contributor of this great project. Thanks to Johan and Jonathan!

With this post I will give a short "Getting Started" documentation for everyone who wants to use DataFX in a Maven based project.

You can easily add a dependency to DataFX 1.0 to any Maven project. With doing so a JavaFX 2.2 dependency is added to your project, too. Just add the following dependency to your Maven pom:

{% highlight xml %}
<dependency>
   <groupId>org.javafxdata</groupId>
   <artifactId>datafx-core</artifactId>
   <version>1.0</version>
</dependency>
{% endhighlight %}

Since all datafx-core artifacts are available at [Maven Central Repository](http://search.maven.org) the provision of DataFX will work automatically.

If you are interested in how we added JavaFX as a dependency to DataFX you can read [here]({{ site.baseurl }}{% post_url 2012-10-13-building-javafx-applications-with-maven %}) about it.

This description shows how to add DataFX to your dependency hierarchy. If you want to run or distribute your application we recommend this [Maven plugin](https://github.com/zonski/javafx-maven-plugin) that provides a lot of functionality to a JavaFX Maven project. You can find a tutorial about this plugin [here](http://www.zenjava.com/2012/11/24/from-zero-to-javafx-in-5-minutes/).

I hope you like the way how we integrated Maven support to DataFX and it this will help you creating cool JavaFX apps.
