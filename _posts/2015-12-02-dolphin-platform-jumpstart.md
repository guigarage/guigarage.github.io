---
title: 'Dolphin Platform Jumpstart'
layout: post
author: hendrik
categories: [Dolphin Platform, JavaFX]
excerpt: 'Starting with a new technology is often hard. Even if the technology is documented and follows the pattern and structures that you already know from other APIs or frameworks the initial start is always a problem. For Dolphin Platform we created a jumpstart project that will help you to get into the framework and create awesome applications based on it.'
featuredImage: dp-2
permalink: '2015/12/dolphin-platform-jumpstart/'
header:
  text: Dolphin Platform Jumpstart
  image: sample
---
Starting with a new technology is often hard. Even if the technology is documented and follows the pattern and structures that you already know from other APIs or frameworks the initial start is always a problem. To simplify this start when working with the [Dolphin Platform](http://www.dolphin-platform.io) I created a first Maven archetype that creates a simple client server application.

![dp](/assets/posts/guigarage-legacy/dp-1024x255.png)

This application is an ideal starting point to play with the Dolphin Platform framework, learn the APIs or directly start your own application based on Dolphin Platform.

The Maven multi module project that is created by using this first archetype contains a Spring Boot based server and a JavaFX client. With this archetype you can create your own project directly in an IDE like IntelliJ or by using the shell. If you want to use the shell you need maven on your system. IDEs like IntelliJ contain a bundled Maven instance and you don't need to install anything on your system (without the IDE).

The following video shows how you can create your first Dolphin Platform application in 1 minute by using IntelliJ:

{% include posts/youtube.html id="e0vdf0coNuc" %}

I will post about other IDE and the commandline usage later. If you have any questions about Dolphin Platform you can now use the ["dolphin-platform" tag at stackoverflow](http://stackoverflow.com/questions/tagged/dolphin-platform). If you are new to Dolphin Platform you can find general information and tutorials [here](http://www.dolphin-platform.io) and [here]({{ site.baseurl }}{% post_url 2015-11-09-dolphin-platform-has-been-released %}).
