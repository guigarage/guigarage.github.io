---
title: 'Designing JavaFX Business Applications (Part 1)'
layout: post
author: hendrik
categories: [General]
excerpt: 'TODO'
featuredImage: sample-7
permalink: '2013/05/designing-javafx-business-applications-part-1/'
header:
  text: Designing JavaFX Business Applications
  image: sample
---
The JavaFX community is really growing and more and more open source frameworks and first real business applications appear on the internet.

While most of the open source frameworks are just at the beginning I think that many of them demonstrate what could be done with JavaFX. While tracking all this APIs and Open Source Frameworks (thanks to [Jonathan Giles and his weekly link overview](http://jonathangiles.net/blog/)) I'm trying to prepare some best practice pattern for JavaFX application development. I thought a lot about this topic and think that it's the right time to share my opinion with you. Sure everything that comes now is only my way of looking at this stuff and no whitepaper. If you think that my thoughts are wrong or you have some advancements please let me know!

While the architecture of applications is a so big topic I will break this down and only take a deeper look at classic business applications. But I think that most of the practices that I will describe can be used in other kinds of applications, too.

## The classic business application

Ok, first of all I will define what a classic business app is. I think that most of this applications or systems based on a client server architecture. In this case it is not important if the client is a rich desktop client or a JSF based web client. I a normal scenario you will have a [3 tier architecture](https://en.wikipedia.org/wiki/Multitier_architecture) that is splitted is a persitence, businesslogic and view layer.

![3tier](/assets/posts/guigarage-legacy/3tier.png)

If you have a [JSF](http://en.wikipedia.org/wiki/JavaServer_Faces) based web client your view can directly access the [EJB](http://en.wikipedia.org/wiki/EJB) beans out of the view by using the [Expression Language (EL)](http://en.wikipedia.org/wiki/Unified_Expression_Language). By doing so you can handle the properties of the EJB bean or call methods on it. Here is a short example:

![jsf](/assets/posts/guigarage-legacy/jsf.png)

If you have a desktop client (or maybe a webclient that is written in another language / framework like [AngularJS](http://angularjs.org)) you need a middleware to interact with the server. Here you will hopefully depend on a standard like [SOAP](http://en.wikipedia.org/wiki/SOAP), [REST](http://en.wikipedia.org/wiki/REST) or [WebSocket](http://en.wikipedia.org/wiki/Websocket). By doing so you can achieve the same features in your application that you will have while using JSF and EL.

## JavaFX middleware

As you can see there is no technical barrier that will stop you to connect a JavaFX client with a JEE Backend. A good example for this is [CaptainCasa](http://www.captaincasa.com) that provides a JSP and JavaFX based client for business applications that are created with this framework.

If you connect your JavaFX app to a backend server you need to pay attention to multi threading. A request to the server can take some time and if you execute this inside the JavaFX application thread your complete client will freeze. To avoid this problems you can fall back on one of the great open source frameworks that are currently out. In my opinion you should take a deeper look at

* [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %})
* [RedFX](http://www.redfx.org)
* [OpenDolphin](http://open-dolphin.org/dolphin_website/Home.html)

If you only need REST Requests as the middleware layer DataFX is in my eyes the best API at the moment. You can define your complete server infrastructure by only using JEE defaults as mentioned before. DataFX will only be needed at client site and handle all the REST calls for you.

![dfx](/assets/posts/guigarage-legacy/dfx.png)

While using DataFX all requested business values will be stored in JavaFX properties and you can simple use them in the JavaFX application thread. You can find a simple demo that access iTunes REST web services [here](https://github.com/guigarage/DataFX-iTunes-Demo).

For a more complex communication I would prefer to use RedFX or Open Dolphin. This two frameworks need a API on server site too. RedFX uses the default JEE WebSocket API for communication and Open Dolphin has no default communication layer (as far as I know) but can easily be used with REST. If you use one of this framework your application stack may look like this:

![redfx](/assets/posts/guigarage-legacy/redfx.png)

Until now I haven't worked with one of this two framework. Cause of this I can't tell you what are the fine differences between this two framework and in which specific usecase you should use the one or the other.

Once the middelware is created and you can access all your business objects on client side you need to form a concept for the client.

Hope you liked my short overview of JavaFX related middleware. The next post in this series will cover the client site. Here I will cover dependency injection in JavaFX applications.
