---
title: 'Desktop & Embedded Application JSR'
layout: post
author: hendrik
categories: [DataFX, Desktop Application Framework (JSR 377), JavaFX]
excerpt: 'Today the Desktop & Embedded Application JSR has been started. Its goal is to define a spec for common behaviors that are shared by desktop apps.'
featuredImage: java-1
permalink: '2014/12/desktopembedded-application-api-jsr/'
header:
  text: Desktop & Embedded Application JSR
  image: sample
---
Today the JSR for Desktop & Embedded Applications has been started and I'm proud to be part of it. The main goal of this JSR is to define a specification for common issues and behaviors that are shared between most desktop applications. This contains the following topics:

* dependency injection via JSR330
* common application structure
* application life-cycle
* localized resources
* resource injection
* localized configuration
* decouple state from UI (binding)
* persistence session state (preferences)
* action management
* component life-cycle
* light-weight event bus
* honor threading concerns (specific to UI toolkit)
* application extensibility via plugins (implies modularity)

Until now there isn't any line of code but I think that parts of the JSR will be influenced by some Application Frameworks like [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) or [Griffon](http://new.griffon-framework.org). In addition Eclipse RCP and Netbeans have solved some of these problems and will be used as inspiration.

The website of the JSR can be found [here](https://jcp.org/en/jsr/detail?id=377). If you want to discuss about the JSR you should join the [mailing list](http://jsr377-api.40747.n7.nabble.com) and star / watch the [github repositories](https://github.com/jsr377).

Oh, and because it's a JSR it has an official number: JSR 377
