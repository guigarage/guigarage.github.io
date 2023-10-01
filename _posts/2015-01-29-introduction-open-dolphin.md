---
title: 'An introduction to Open Dolphin'
redirect_to: https://open-elements.com/posts/2015/01/29/an-introduction-to-open-dolphin/
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'The post gives a short introduction about the architecture and features of Open Dolphin'
featuredImage: java-7
permalink: '2015/01/introduction-open-dolphin/'
header:
  text: An introduction to Open Dolphin
  image: sample
---
Last year I started to work at Canoo and some of you might know that Canoo provides the open source remoting middleware [Open Dolphin](http://open-dolphin.org/dolphin_website/Home.html). At the end of the last year I had the pleasure to work with Open Dolphin in a customer project. Based on the experience of this project I started to contribute to Open Dolphin because I think that it provides an interesting way to handle the remoting aspects of an application. In addition I will blog about Open Dolphin and how you can use it in (JavaFX) applications.

![dheadt](/assets/posts/guigarage-legacy/dheadt.png)

If I need to describe Open Dolphin in just a few words I would say that it is a library that syncs presentation models between a server and one or many clients. If you haven't seen such an approach before it's maybe hard to understand. Therefore I will start with a basic introduction of the core concepts with this post.

![newA](/assets/posts/guigarage-legacy/newA.png)

As you can see in the image the client and the server can access the same presentation model. Let's think about the presentation model as any data model. There are some best practices what kind of data should be defined in a presentation model but first of all let us define this as a model with any data in it. Both the client and the server can read data from the model or edit data in the model. Open Dolphin will automatically sync the model between the server and the client. In addition Open Dolphin supports multiple clients and therefore a more correct diagram would look something like this:

![sync](/assets/posts/guigarage-legacy/sync.png)

As you can see in the picture all the different clients don't know each other and the sync between the presentation models in centralized by the server. If client A edits a value in the presentation model it will be synchronized with the server. Once the server has updated it's own presentation model with the new value the server synchronizes this change with all other clients. By doing you can create applications that share exactly the same presentation model. The following video shows a short example:

{% include posts/youtube.html id="fdTdo7G_hZY" %}

While the sharing of the presentation model between several clients is a very cool feature and big benefit most of the time you need to synchronize a model only between the server and one client. Therefore the examples that will be shown in the following post will all use this approach. Once this workflow is completely described I will start to introduce the sync of several clients in future posts.

## Benefits of using Open Dolphin and presentation models

One big benefit of Open Dolphin is its independence to UI Toolkits. By default Open Dolphin doesn't depend on any toolkit like JavaFX, Swing or GWT. By using Open Dolphin you can use the same presentation model for JavaFX and Java Script clients, for example. When using some of the best practices that I will show in future posts a migration from one UI technology to another one will be more easy by using Open Dolphin. In addition the creation of an application that supports different client implementations (for example mobile, desktop and web) can be based on Open Dolphin. By doing so only the view of the clients will be different because Open Dolphin capsulate the controller logic and the data model.

Next to the support of different client technologies Open Dolphin is based on the presentation model pattern. By doing so it combines many best practices to create MVC, MVP or MVVM applications. The following graphic shows how the MVC can be implemented by using Open Dolphin:

![dolphin-mvc](/assets/posts/guigarage-legacy/dolphin-mvc.png)

## The presentation model

Let's have a deeper look at the presentation models and how such a model is structured and managed in Open Dolphin. Each presentation model is defined by an unique id. In addition a presentation model can be specified by a type. Both values are defined as a string. Open Dolphin provides methods to find a specific presentation model by its id or all presentation models of a given type.

{% highlight java %}
PresentationModel model = dolphin.findPresentationModelById("myPresentationModel");
List<PresentationModel> allErrorModels = dolphin.findAllPresentationModelsByType("errorModel");
{% endhighlight %}

A presentation model contains a set of attributes and each attribute defines a value in the presentation model. Let's have a look at the following example to describe how attributes can be defined:

![attributes](/assets/posts/guigarage-legacy/attributes.png)

In this example we have two different input fields and therefore we will define 2 different attributes:

* An attributes that defines the name value
* An attributes that defines the description value

To create Attributes the Dolphin interface contains some factory methods. The following code snippet shows how the Attributes can be created:

{% highlight java %}
ClientAttribute nameAttribute = clientDolphin.createAttribute("nameValue", "initial value");
ClientAttribute descriptionAttribute = clientDolphin.createAttribute("descriptionValue", "initial value");
{% endhighlight %}

In the code a both attributes are created by defining the name of the attribute and an initial value. Once the attributes are created we can create a presentation model that will automatically synced between client and server:

{% highlight java %}
ClientPresentationModel presentationModel = dolphin.presentationModel("myPresentationModel", nameAttribute, descriptionAttribute);
{% endhighlight %}

Once this is done the presentation model is created an can will automatically be synced between client and server whenever anything in it will be changed. This will happen if the value of an attribute will be changed, for example:

{% highlight java %}
nameAttribute.setValue("Hendrik Ebbers");
{% endhighlight %}

Next to the name an Open Dolphin attribute is defined by a tag and a qualifier. As shown with the type of  presentation models attributes can be found by using its tag, for example. Since these powerful features aren't needed for a first simple application I will show them in a later post.

## Server commands

Another important feature of Open Dolphin is the support of server commands. On server side command handlers can be registered for a specific command. Each command is defined by a unique name and can be called from client side.

![command](/assets/posts/guigarage-legacy/command.png)

By doing so you can trigger server commands from client side. In a server command the presentation models can be accessed and modified. By doing so you can define a save command for example. Once the command is triggered you can convert the current content of the presentation model into business entities and persist them in a database.

On server side the command handler can be registered by using a method of the ServerDolphin interface:

{% highlight java %}
dolphin.action("action", (command, response) -> {
    System.out.println("action command was triggered");
}
{% endhighlight %}

Once the command handler is registered it can be called from client side:

{% highlight java %}
clientDolphin.send("action");
{% endhighlight %}

By doing so the command will be called on server side.

## Basic interfaces

The 3 most important interfaces of Open Dolphin are Dolphin, PresentationModel and Attribute. All three interfaces are defined in the dolphin-core module. In addition to the core model Open Dolphin provides a module for the client and the server side. Each of this modules contains a specialization of the interfaces. The dolphin-client module contains the ClientDolphin, ClientPresentationModel and CLientAttribute interfaces that extend the base ones for example.

![interfaces](/assets/posts/guigarage-legacy/interfaces.png)

By doing so you need only one dependency for the client and one dependency for the server to use open dolphin:

{% highlight xml %}
<!-- Maven dependency for the server -->
<dependency>
    <groupId>org.open-dolphin</groupId>
    <artifactId>dolphin-server</artifactId>
    <version>0.11</version>
</dependency>

<!-- Maven dependency for the client -->
<dependency>
    <groupId>org.open-dolphin</groupId>
    <artifactId>dolphin-client</artifactId>
    <version>0.11</version>
</dependency>
{% endhighlight %}

## Conclusion

This post shows some of the core concepts of Open Dolphin. For developers that used REST or EJB as the removing layer of applications this is a complete new approach. Therefore it's important to understand how Open Dolphin works before creating a first simple application based on this concepts. In my next Open Dolphin post I will show how a first "Hello World" application can be created.
