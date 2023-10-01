---
title: 'Dolphin Platform: A Sneak Peek of the model API'
redirect_to: https://open-elements.com/posts/2015/10/06/dolphin-platform-a-sneak-peek-of-the-model-api/
layout: post
author: hendrik
categories: [Dolphin Platform, JavaFX, Web Frontends]
excerpt: 'This is a short overview about the model API of the Dolphin Platform. This defines a model that will automatically synchronized between client and server.'
featuredImage: dp-2
permalink: '2015/10/dolphin-platform-a-sneak-peek-of-the-model-api/'
header:
  text: Dolphin Platform
  image: sample
---
Before I will talk the first time about the Dolphin Platform today at ["Java Forum Nord"](http://javaforumnord.de/programm_3) I will use the time to post the next preview. Today I will give a short overview about the model API of the Dolphin Platform. The last days I already gave a [general overview]({{ site.baseurl }}{% post_url 2015-10-04-dolphin-platform-a-sneak-peek %}
) and described how the [controller API of Dolphin Platform]({{ site.baseurl }}{% post_url 2015-10-05-dolphin-platform-a-sneak-peek-of-the-controller-api %})  is working.

## The Model API

To describe the API that helps you to define presentation models in Dolphin Platform I will start with an example. Let's say we have the following view that can be part of a photo library app:

![example](/assets/posts/guigarage-legacy/example.png)

In this view we have several elements that need a data model. If the data of this app is stored on a server the data model must be shared between the client and the server. When having a look at the screen we can see 3 different elements that need data from the data model to visualize itself or provide user interaction:

* The title of the screen needs a String as its content. Here we can display the title of a photo album or an internationalized string
* The slider that defines a value. Let's imagine that the interaction with the slider changes the size of the pictures in the main area. Maybe the last value of the slider should be stored on the server to automatically save user preferences
* All pictures in the main area. As you can see each card in this area contains an image and maybe a badge in the top right corner. A badge element in the top right corner visualizes if the photo is flagged.

Based on this definition we would create a presentation model that might look like this one:

![model](/assets/posts/guigarage-legacy/model-1024x732.png)

When defining such a model in JavaFX you can use the cool property API and the observable collections that are part of JavaFX. Modern javaScript frameworks like AngularJS or Polymer provide a similar behavior and therefore we decided to offer the same benefits when defining model with the Dolphin Platform. Since [Michael Heinrichs](https://twitter.com/net0pyr) was the project lead of the property and bindings APIs of JavaFX at Oracle we had a lot of knowledge in this area that helped us creating the model API: In Dolphin Platform you work with properties and observable collections, too. Therefore it really easy to define a hierarchical model for your view. A model for the shown view might look like this:

{% highlight java %}
@DolphinBean
public class PhotoOverviewModel {
  
  private Property<String> title;
  
  private Property<Double> sliderValue;
  
  private ObservableList<PhotoModel> photos;
  
  //getter & setter
  
}

@DolphinBean
public class PhotoModel {
  
  private Property<String> imageUrl;
  
  private Property<Boolean> flagged;
  
  //getter & setter
  
}
{% endhighlight %}

All properties and collections in Dolphin Platform are observable and therefore it's quite easy to observe them on the client and the server:

{% highlight java %}
myModel.getTitleProperty().onChange(e -> System.out.println("New title: " + e.getNewValue()));
{% endhighlight %}

For all client APIs we support first class support for the Dolphin Platform properties. When working with JavaFX for example it's quite easy and intuitive to bind a synchronized Dolphin Platform property to a JavaFX property:

{% highlight java %}
FXBinder.bind(booleanJavaFXProperty).bidirectionalTo(booleanDolphinProperty);
{% endhighlight %}

On JavaScript clients the handling is even more elegant as you can bind the Dolphin Platform model directly in HTML.

The main benefit of this concept is that you can use the same model classes on the server and the client. Because the model will automatically be synchronized between the view and the server controller it feels like you work with the same instance. By doing so you can simply bind a string property to a textfield in the view and observe it's value on the server. The change events will automatically be fired on the server when you start typing in the textfield.

There are a lot of more cool features in the model API but since this should only be a overview I need to stop here :) But there are only a few weeks left till JavaOne where we will release a whole preview with the source, documentation and several samples. As the next step I will post a preview of the view API the next days.
