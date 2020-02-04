---
title: 'BoxFX (JavaOne Preview 1)'
layout: post
author: hendrik
categories: [IoT, JavaFX]
excerpt: "After a long time without a post I will show you a first preview of the stuff I'm currently working on. I (and Claudine) don't sleep the last month. We worked hard on some projects especially for our JavaOne presentations"
featuredImage: java-10
permalink: '2013/08/boxfx-javaone-preview-1/'
header:
  text: BoxFX
  image: sample
---
After a long time without a post I will show you a first preview of the stuff I'm currently working on. I (and Claudine) don't sleep the last month. We worked hard on some projects especially for our [JavaOne](http://www.oracle.com/javaone/index.html) presentations:

* [JavaFX, Widgets, and Apps, Oh My! Launching Frameworks for Platforms Large and Small](https://oracleus.activeevents.com/2013/connect/sessionDetail.ww?SESSION_ID=2605)
* [DataFX: The Best Way to Get Real-World Data into Your JavaFX Application](https://oracleus.activeevents.com/2013/connect/sessionDetail.ww?SESSION_ID=3202)
* [Let’s Get Wet! AquaFX and Best Practices for Skinning JavaFX Controls](https://oracleus.activeevents.com/2013/connect/sessionDetail.ww?SESSION_ID=3839)

Today I will give a sneak peak at one of the projects that will be mentioned in the talks: BoxFX

![box1](/assets/posts/guigarage-legacy/box1.png)

BoxFX is an application container for JavaFX applications that will run on the Raspberry Pi. It is optimized for HD resolution and can be controlled by a remote. You can simply convert any JavaFX application to BoxFX and simple install applications on a running instance. The main goal of this project is to offer a Set-Top-Box for your TV (like AppleTV or Google TV). But BoxFX will be open source and gives you the chance to develop your own applications for the TV.

Each application will run in a sandbox with a special classloader and security manager. This will be very similar to JEE web applications that are running in a web container. BoxFX will provide an application profile that you can simply add as a provided dependency to your application. By using the BoxFX Maven plugin you can easily install a application on any remote BoxFX. Once an app is installed you can will see it on the BoxFX home screen:

![homescreen](/assets/posts/guigarage-legacy/homescreen.png)

You can control BoxFX with a remote. BoxFX will provide a open API for remotes so that you can easily create a iOS or Android app as a remote for BoxFX. I already created a JavaFX based remote:

![remote](/assets/posts/guigarage-legacy/remote.png)

I created a short movie about BoxFX. Hope you like it:

{% include posts/youtube.html id="w3r3hzJs2W0" %}
