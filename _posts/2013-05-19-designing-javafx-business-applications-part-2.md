---
title: 'Designing JavaFX Business Applications (Part 2)'
layout: post
author: hendrik
categories: [General, JavaFX]
excerpt: 'This is the second part of my series about JavaFX architecture and best practice for (business) application.'
featuredImage: java-arch-4
permalink: '2013/05/designing-javafx-business-applications-part-2/'
header:
  text: Designing JavaFX Business Applications
  image: sample
---
This is the second part of my series about JavaFX architecture and best practice for (business) application. You can find the first part about middleware [here]({{ site.baseurl }}{% post_url 2013-05-11-designing-javafx-business-applications-part-1 %}).

In this post I will show you a simply and modern way to bind your data model to your view by using [Context Dependency Injection (CDI)](http://en.wikipedia.org/wiki/Dependency_injection).

## CDI?? What the hell??

CDI is a modern design pattern that is for example part of JEE. By using dependency injection you can remove hard coded dependencies  in your code. Here is a short example:

{% highlight Java %}
public class Controller {
    @Inject
    Model model;
    public void action() {...}
}
{% endhighlight %}

If the Controller will be created by a CDI-Container the model field will be injected. This means that the CDI-Container will fill the field with a suitable instance of Model. How this model is created is not part of the Controller class. The controller only knows that he will get a injected instance of Model and can work with it.

This is only a very short introduction to CDI. If you want to use it please read a [better documentation](http://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html) :)

## How a JavaFX application is mostly designed until now

In a normal business application you will have views and workflows. For example you will have a overview of some important business values and a form dialog with [CRUD](http://en.wikipedia.org/wiki/Create,_read,_update_and_delete) functions.

Normally you will define your views by code or with the [Scene Builder](http://docs.oracle.com/javafx/scenebuilder/1/get_started/jsbpub-get_started.htm). If you use the Scene Builder you can transfer all your UI code into the [fxml](http://docs.oracle.com/javafx/2/fxml_get_started/jfxpub-fxml_get_started.htm) file and handle the logic inside a controller class. The controller can now access the business data by calling data stores or any other of your classes.

![cdi1](/assets/posts/guigarage-legacy/cdi1.png)

This pattern is good and is working much better that your old Swing code. You have a clean separation between the view and the business logic. But there are still some problems here:

* The controller has hard dependencies to you business data classes.
* The controller class is defined inside the fxml file.
* Providing special business object instances only for one view workflow isn't easy.

I will try to solve this problems with a new approach of JavaFX client development.

## Let's pimp my JavaFX application

To create a real modern and modular version of this we will do some inversion of control. First of all I don't like that the controller class for a fxml-view is defined inside the fxml. This is a refactoring killer. Cause if you rename the class or move it to another package this binding is broken. In addition I like it more to define the used view in the controller. Maybe this is not 100% compatible with the MVC pattern where different views can easily share one controller but we can define all dependencies in java code. To inverse this dependency I created a FXMLController annotation:

{% highlight Java %}
@FXMLController("/com/guigarage/application/views/MyView.fxml")
public class MyController {
...
}
{% endhighlight %}

The internal path to the fxml file is defined by the annotation. By doing so I can simply create a view:

{% highlight Java %}
Node myView FXMLHelper.create(MyController.class);
{% endhighlight %}

FXMLHelper.create(..) is a Method that I created to do some magic (Don't be afraid, I will release a code for this design pattern later). The method creates the controller instance and the view and bind them.

We can now easily add the node to our scene graph.

![cdi2](/assets/posts/guigarage-legacy/cdi2.png)

But wait! Where is the controller instance? We don't have access to this object...

Right, cause we don't need it :) Cause now comes the CDI magic.

## Using the CDI pattern in JavaFX

A first project that shows the use of CDI in JavaFX is [afterburner.fx](http://afterburner.adam-bien.com) by [Adam Bien](http://about.adam-bien.com). With this framework you can easily inject objects in your JavaFX controller. But this framework is very lightweight and don't support all default CDI features. There are no [producers](http://docs.oracle.com/javaee/6/tutorial/doc/gkgkv.html#gmgjt), [scopes](http://docs.oracle.com/javaee/6/tutorial/doc/gjbbk.html), etc.

I created a new API that is currently based on [Weld](http://seamframework.org/Weld) (the JEE default implementation for CDI) and provides all the default CDI features. By using it you can use the @Inject annotation in you view controllers:

{% highlight Java %}
public class MyController {
   @Inject
   Model myModel;
}
{% endhighlight %}

The model will be injected to your controller as mentioned above. Other CDI Annotations will work, too. Here is an example of a more complex version of a controller:

{% highlight Java %}
public class MyController {
   @Inject
   @ModelQualifier
   Model myModel;
   @PostConstruct
   public void init() {....}
}
public class ModelFactory {
    @Produces
    @ModelQualifier
    public Model getModel() {
        ....
    }
}
{% endhighlight %}

In this example the model has a custom [Qualifier](https://blogs.oracle.com/arungupta/entry/totd_161_java_ee_6) annotation (@ModelQualifier). This will define a special Model type. This type is created by the ModelFactory. The getModel() method has a @Producer annotation that defines this method as a producer for the model class. Additionally the init() method of the controller has a [PostConstruct](http://docs.oracle.com/javaee/6/tutorial/doc/gmgkd.html) annotation. This method will automatically called once a controller is created.

## Putting it all together

After I added the @FXMLController annotation and CDI to my basic application it is really easy to create new views. The UI can be easily created by using the [Scene Builder](http://docs.oracle.com/javafx/scenebuilder/1/get_started/jsbpub-get_started.htm). Once this is done you can create the controller class and use CDI inside it. Cause you can inject everything that is needed it will be very easy to capsulate a view-controller-union as a single module. To create this I only need a line of code (as mentioned above):

{% highlight Java %}
Node myView FXMLCDI.create(MyController.class);
{% endhighlight %}

The FXMLCDI util class (my custom one) handles all the FXML and CDI stuff.

![cdi3](/assets/posts/guigarage-legacy/cdi3.png)

## Thinking about Scopes

Cause CDI is mostly used in JEE context at the moment most of the [Scopes](http://docs.oracle.com/javaee/6/tutorial/doc/gjbbk.html) don't fit to a JFX application. But we will need Scopes here, too.

In the default CDI Scope a new instance of a class is created for every injection. Secondly you can use a singleton scope. By doing so only one instance of the class will be used in your application. All other default scopes are related to web applications (RequestScope, SessionScope, ...) and won't fit in a JavaFX application. While looking at other frameworks I found some interesting scopes that are part of [ADF](http://www.oracle.com/technetwork/developer-tools/adf/overview/index.html). Here you can use a ViewScope and a ViewFlowScope. I think this scopes are very useful for client applications and one of my next steps is the integration of this scopes in my FXML/CDI Framework.

## A simple example

Let's think about a dialog with some very important business values. This values should only be accessed by this dialog. But your application offers a twitter integration to post some stuff. The Twitter configuration is defined globally for the whole application. If you want to add a function to twitter the business values by just clicking a button in your dialog the controller could look like this:

{% highlight Java %}
@FXMLController("/com/guigarage/application/views/ImportantDialog.fxml")
public class ImportantDialogController {
   @Inject
   @DialogScope
   Model importantModel;
   @Inject
   @ApplicationScope
   Twitter twitter;
   @PostConstruct
   public void init() {
      importantModel.loadAllImportantValues();
   }
   @FXML
   public void twitter() {
      twitter.post(importantModel.getImportantBusinessValues());
   }
}
{% endhighlight %}

## And how can I use this stuff?

At the moment my complete code is experimental but I plan to release it once it is more clean. Maybe you have some cool additional ideas for this pattern of JavaFX application development that I can add to the framework before it will be released ;)
