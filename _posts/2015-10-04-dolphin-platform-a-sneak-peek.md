---
title: 'Dolphin Platform: A Sneak Peek'
redirect_to: https://open-elements.com/posts/2015/10/04/dolphin-platform-a-sneak-peek/
layout: post
author: hendrik
categories: [Desktop Application Framework (JSR 377), Dolphin Platform, JavaFX]
excerpt: 'This is an overview of the Dolphin Platform, a new open source project that provides MVC architecture based on presentation models for several clients'
featuredImage: dp-3
permalink: '2015/10/dolphin-platform-a-sneak-peek/'
header:
  text: Dolphin Platform
  image: sample
---
Today I want to give you a first overview of the Dolphin Platform, a new __open source__ project on that [Michael Heinrichs](https://twitter.com/net0pyr) and I are currently working at Canoo.

![platform-logo](/assets/posts/guigarage-legacy/platform-logo-1024x255.png)

The Dolphin Platform is a framework that implements the presentation model pattern and provides a modern way to create enterprise applications. As you might know there are several of this frameworks out there. So let me start this post by describing what makes the Dolphin Platform special and why we decided to create it.

## Architecture overview

Since most of you know Michael and me as part of the JavaFX community, we don't stop here when talking about the Dolphin Platform. The Platform will provide several client implementations. Currently it's our main goal to provide a first preview at JavaOne with __JavaFX__, __AngularJS__ and __Polymer__ client support. Based on this you can simply create desktop, web or mobile clients with the Dolphin Platform. The client APIs are pure Java or JavaScript APIs and you don't need to learn any new language to use them.

![clients](/assets/posts/guigarage-legacy/clients.png)

What we've started on the client side will be continued on the server. Here the Dolphin Platform provides an API to create server side controllers that will automatically be managed by the underlying platform. Therefore we create a __Spring__ and __JavaEE__ implementation of the public APIs on server side. As you will see later you can simply inject all your business services in a controller and don't even think about any lifecycle because the web container will handle that for you. But before I go into the details I want to give a short overview about presentation models and the patterns that are implemented and provided by Dolphin Platform.

## Synchronized Presentation Models

In the Dolphin Platform all models will automatically be synchronized between client and server. By doing so you don't need to think about any specific endpoints or requests.

![pm1](/assets/posts/guigarage-legacy/pm1.png)

Based on this the Dolphin Platform defines server side controllers that contains all the controller logic for a specific view. The lifecycle of these controllers is automatically synchronized with the view lifecycle. By doing so you have a MVC group for each client view with a synchronized model and a managed controller.

Ok, I think this was enough description and I hope that you want to see some code ;) I will post a preview of the controller API, the model API and the view API the next days.

Here are the links to additional DOlphin Platform sneak peeks:

* I blogged about the [Dolphin Platform controller API]({{ site.baseurl }}{% post_url 2015-10-05-dolphin-platform-a-sneak-peek-of-the-controller-api %}).
* I blogged about the [Dolphin Platform model API]({{ site.baseurl }}{% post_url 2015-10-06-dolphin-platform-a-sneak-peek-of-the-model-api %}).
* I blogged about the [Dolphin Platform view API]({{ site.baseurl }}{% post_url 2015-10-07-dolphin-platform-a-sneak-peek-of-the-view-api %}).
* I created [a first "getting started" tutorial]({{ site.baseurl }}{% post_url 2015-10-18-dolphin-platform-how-to-create-an-application %}) for the Dolphin Platform.
* I blogged about [Dolphin Platform Web Frontends with Polymer]({{ site.baseurl }}{% post_url 2015-10-23-dolphin-platform-web-frontends-with-polymer %}).
* I blogged about [the first release of the Dolphin Platform]({{ site.baseurl }}{% post_url 2015-11-09-dolphin-platform-has-been-released %}).
* I blogged about how to use [Dolphin Platform with kumuluzEE]({{ site.baseurl }}{% post_url 2015-11-11-dolphin-platform-kumuluzee-javaee-microservices-with-dynamic-and-rich-frontends %}).
* I was interviewed for the [Nighthacking YouTube channel]({{ site.baseurl }}{% post_url 2015-11-12-dolphin-platform-in-15-minutes-nighthacking %}).
