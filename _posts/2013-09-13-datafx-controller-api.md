---
title: 'DataFX Controller API'
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'The DataFX team will show some cool new APIs at JavaOne this year. Today I will give a short preview to another part of the new DataFX APIs.'
featuredImage: sample-1
permalink: '2013/09/datafx-controller-api/'
header:
  text: DataFX Controller API
  image: sample
---
The [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) team will show some cool new APIs at [JavaOne](http://www.oracle.com/javaone/index.html) this year. Johan has written [a post about writeback support in the new DataFX](http://www.lodgon.com/dali/blog/entry/Writeback_support_in_DataFX) version some days ago. Today I will give a short preview to another part of the new DataFX APIs.

## The Controller API

The base of the API is the inversion of control for JavaFX controllers. The API offers the annotation `FXMLController` that can be assigned to any controller class. By using this annotation you can define the FXML-View that should be used by this controller. If you don‘t define the name of the FXML-File the API will try to find the view by using a convention over configuration approach. By doing so you do not need to add Java class information in your FXML file. You can use all default FXML annotations that are defined by JavaFX. By doing so you can inject all your nodes that are defined in the fxml-file in the controller by simply adding the `@FXML` annotation. Additionally you can use the `@PostConstruct` annotation. A simple Controller that is created by this API can look like this one:

{% highlight java %}
@FXMLController("Details.fxml")
public class DetailViewController {
        @FXML
        private TextField myTextfield;
        @FXML
        private Button backButton;
        @PostConstruct
        public void init() {
            myTextfield.setText("Hello!");
        }
}
{% endhighlight %}

To load the view and the controller DataFX-Controller offers a simple HelperClass called `ViewFactory`. This is the default entry point to the complete DataFX-Controller API. You can create a view and controller by using this class:

{% highlight java %}
ViewFactory.getInstance().createByController(DetailViewController.class);
{% endhighlight %}

The FXML-file that is used by this controller can look like this one:

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<hbox spacing="10" alignment="bottom_right" xmlns:fx="http://javafx.com/fxml">
    <textfield fx:id="myTextfield" prefwidth="90" />
    <button fx:id="backButton" text="back">
</button></hbox>
{% endhighlight %}

If you want to see more code check out the [DataFX 2.0 repository at bitbucket](https://bitbucket.org/datafx/datafx).

We will provide more samples and tutorials later, and you are very welcome to join us at JavaOne [(CON3202, Wednesday Sep 25, 08:30 am, Hilton - Plaza B)](https://oracleus.activeevents.com/2013/connect/sessionDetail.ww?SESSION_ID=3202).
