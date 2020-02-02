---
title: 'JavaFX meets JavaEE'
layout: post
author: hendrik
categories: [DataFX]
excerpt: 'The post shows all the main specifications of Java EE and describes how the are supported by DataFX and how a JavaFX application can use them'
featuredImage: sample-10
permalink: '2014/01/javafx-meets-javaee/'
header:
  text: JavaFX meets JavaEE
  image: sample
---
At [JavaOne](http://www.oracle.com/javaone/) [Arun Gupta](https://www.java.net//blogs/arungupta) gave me a copy of his ["Java EE 7 Essentials" book](http://www.amazon.com/Java-EE-Essentials-Arun-Gupta/dp/1449370179). I don't want to write a complete review of the book, but if you are interested in the Java EE platform this book is perfect. It covers all the different specifications that are new or polished in Java EE 7 in several chapters and gives a very good overview to all the technologies and how to use them.

![book](/assets/posts/guigarage-legacy/javaee-book.jpg)

After reading the book you are ready to develop a web application that is based on JEE 7. Most of this applications will have a web frontend that is based on JSF as it is shown in the book, too. But as I mentioned in a [earlier post]({{ site.baseurl }}{% post_url 2013-05-11-designing-javafx-business-applications-part-1 %}), a modern business applications often needs support for mobile or desktop applications:

![3tier](/assets/posts/guigarage-legacy/3tier.png)

Java EE supports different middelware standards for this purpose. In this article I will show you the different Java EE specifications and how you can combine them with JavaFX. If you have seen [oracles technical keynote at JavaOne](http://medianetwork.oracle.com/video/player/2685720528001) this year you will know what I'm talking about. I think that  multi client support by using a standardize middelware was one of the key conclusions of the keynote.

To do so I will mainly use [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) APIs at client site because DataFX is made for exactly the described goal: Connect your desktop application with a server and use all the best practice methods that are originated by enterprise development in your desktop app. In Aruns book you find all JavaEE specifications separated by chapters. I will do the same and handle the following technologies:

* RESTful Web Services
* JSON Processing
* WebSocket
* SOAP-Based Web Services
* JavaServer Faces
* Contexts and Dependency Injection
* Concurrency Utilities
* Bean Validation
* Java Persistence
* Java Message Service
* Enterprise JavaBeans
* Servlets

Aruns book covers the topics "Java Transaction" and "Batch Processing", too. This two topics are extremely JEE related and currently don't fit in the topic of this article.

## RESTful Web Services

As described in Aruns book JavaEE has a specification for REST based webservices. This specification isn't new in Java EE 7 and is surely used in a lot of applications. To create a simple communication between a client and a server REST is of course a good choice. By using REST the client can call URLs to get, create or delete data on server site, for example. The access to REST endpoints was one of the first DataFX features. By using the API you can simple access REST endpoints. Because a lot of applications support REST (Twitter, Facebook, Google Maps, ...) you can easily retrieve data from this applications. With DataFX 2.0 we added write support. Now you can get data from a server and post new or edited data back by simply using REST. If you use DataFX for this approach you even don't need to handle multithreading issues. DataFX will handle all this stuff for you. You will find more informations about REST based access in our [JavaOne slides](http://de.slideshare.net/HendrikEbbers/datafx-javaone-2013) or in this [tutorial](http://jaxenter.com/getting-real-world-data-into-java-ui-controls-with-datafx-46600.html).

## JSON Processing

With version 7 JSON support is added to JavaEE. By using JSON you can convert your data object to a human and machine readable format. I think, that JSON is the best format to provide data to a client. With JEE 7 can convert your data object to a JSON string and vice versa. Because this is a new part of JavaEE 7 it is treated in Arun Guptas book with an own chapter. After reading you are ready to create endpoints that send and receive JSON based data. By using DataFX it is really simply to handle the JSON data. The API provides a JSONConverter that can create Java objects from JSON strings. This can be easily done by using [Jackson](http://wiki.fasterxml.com/JacksonInFiveMinutes) annotations. DataFX will convert the JSON data automatically.

## WebSocket

DataFX provides special DataSources that can be used to connect to a WebSocket server endpoint and send or receive custom data. DataFX uses the default websocket specification here that is part of JEE 7.

## SOAP-Based Web Services

SOAP webservice are already part of Java EE since version X. Because most modern application use REST or websocket in combination with JSON there is currently no SOAP support in DataFX. But we discussed this topic and hope to provide a SOAP API in a later version of DataFX. Because JavaSE already supports SOAP you can easily create your own client API until DataFX will support it. You can find a example [here](https://weblogs.java.net/blog/vivekp/archive/2006/12/webservices_in.html).

## JavaServer Faces

For most web applications the user interface is defined as a HTML based view. With JEE you can use JSF to define this views and bind your business date to a page. If you develop a JavaFX client you will normally use FXML to define your views. With Java EE 7 JSF is extended by the flow API. With this API you can define flows in your application. A flow is a linkage of some pages. A good example for a flow is the checkout process in a webshop. You have some pages that are bind by some navigation rules and share data like the credit card informations. Here is an example how a (master-detail) flow can look:

![m-d](/assets/posts/guigarage-legacy/m-d.png)

With DataFX 2.0 we will introduce the flow API. By using this API you can simply define FXML based flows. Each view is defined by a controller class that has a binding to the FXML based view. You can simply connect views and add actions to the views. The flow API uses the DataFX controller API that was shown in our [JavaOne session](http://de.slideshare.net/HendrikEbbers/datafx-javaone-2013). But because the API is still in development it has already changed in some parts. You can check out the latest version in our [DataFX source repository](https://bitbucket.org/datafx/datafx).

## Contexts and Dependency Injection

As shown in [one of my last posts]({{ site.baseurl }}{% post_url 2013-12-27-datafx-controller-framework-preview %}) DataFX supports context dependency injection in controllers. Currently Providers, etc. are not supported. This will be one of the next features that we want to add. DataFX currently support the following scopes:

* viewScope
* flowScope
* applicationScope

## Concurrency Utilities

With JEE 7 you can use Executor instances in your business app. In DataFX we provide different classes and utility functions to provide a great support for concurrency. Next to invokeLater(...) method that is part of JavaFX you can find [invokeAndWait(...) utility methods in DataFX-core]({{ site.baseurl }}{% post_url 2013-01-01-invokeandwait-for-javafx %}). These can be used to create your own background task API. In addition DataFX provides a [new Executor class]({{ site.baseurl }}{% post_url 2013-02-09-datafx-observableexecutor-preview %}) that supports JavaFX properties. In most cases you can use the DataReader API of DataFX to handle background tasks. In some special cases you can use the [ProcessChain class]({{ site.baseurl }}{% post_url 2014-01-23-datafx-8-preview-2-processchain %}
) that will be new in DataFX 8.

## Bean Validation

Thanks to JEE we have a [default specification](http://docs.oracle.com/javaee/6/tutorial/doc/gircz.html) for bean validation. DataFX uses this specification, too. By doing so a developer doesn't need to learn new APIs. All the annotations and interfaces that are part of [JSR 303](http://beanvalidation.org/1.0/spec/) can be used in DataFX. A first example how bean validation works in DataFX can be found [here]({{ site.baseurl }}{% post_url 2013-12-27-datafx-controller-framework-preview %}).

## Java Persistence

JPA is the specification for accessing relational databases in JEE. The API can simply be used in JavaFX, too. With the help of DataReaders and the ProcessChain JPA queries can simply be called as background tasks. Today a developer need to implement custom data sources here. We plan to add a better support with DataFX 8 :)

## Java Message Service

Ok, what should I say: This issue is completely open in DataFX. Currently there is no support for JMS.

## Enterprise JavaBeans

EJBs are still a very important part of JEE applications. A EJB can be defined as a local or remote bean. A local bean can only be accessed from the same enterprise application and a remote EJB can theoretically accessed from everywhere. The JEE application containers provide different functions how a remote application can access these EJB. The support for EJBs will come with DataFX 8. A first version is finished and nosy developers can find the sources and examples in the [DataFX sources at Bitbucket](https://bitbucket.org/datafx/datafx/). I plan to blog about EJB support in the next days ;)

## Servlets

A Servlet is a very general class in JEE and for example REST services can be implemented by using Servlets. REST his supported by DataFX. Servlets provide some additional functionality. Currently there is no deeper support in DataFX.

## Summery

Hope you like this overview of DataFX. If you want to know more about these technologies on server side buy [Arun Gupta's](https://www.java.net//blogs/arungupta) ["Java EE 7 Essentials" book](http://www.amazon.com/Java-EE-Essentials-Arun-Gupta/dp/1449370179). For the client part you should take a look at [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}). If you have any comments, additional ideas or critic about the features of DataFX please add a post to our [Google Group](https://groups.google.com/forum/#!forum/datafx-dev).
