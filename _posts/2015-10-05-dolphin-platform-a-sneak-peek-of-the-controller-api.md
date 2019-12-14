---
title: 'Dolphin Platform: A Sneak Peek of the controller API'
layout: post
author: hendrik
categories: [Dolphin Platform, JavaFX]
excerpt: 'In this sneak peek of the Dolphin Platform, a new open source project by Canoo, I want to show how server side controller can be defined.'
featuredImage: sample-10
permalink: '2015/10/dolphin-platform-a-sneak-peek-of-the-controller-api/'
header:
  text: Dolphin Platform
  image: sample
---
Today I want to add the next sneak peek of the Dolphin Platform, a new __open source__ project on that [Michael Heinrich](https://twitter.com/net0pyr) and I are currently working at Canoo. Yesterday I blogged about the [general concept of the platform]({{ site.baseurl }}{% post_url 2015-10-04-dolphin-platform-a-sneak-peek %}
). Today I want to show server side controllers can be defined by using the Dolphin Platform.

## The Controller API

As already said the controller are defined on the server. If you already used JSF you might know this concept. But don't be afraid, I won't compare Dolphin Platform with JSF ;)

Each controller must be annotated with the `@DolphinController` annotation and therefore the most simple controller might look like this:

{% highlight java %}
@DolphinController
public class MyController {
}
{% endhighlight %}

Even if this class don't make that much sense we created a complete runnable Dolphin Platform server application here. Once this is deployed the underlying platform (currently JavaEE or Spring) will automatically find your container and manage its lifecycle. This is done by the architecture of Dolphin Platform that provides a public API and specific bootstrap implementations for __JavaEE and Spring__. As a developer you will always code against the public API and only need to add the needed bootrap module as a dependency to your server application.

![arch-server](/assets/posts/guigarage-legacy/arch-server.png)

This means that the shown controller will __work in any Spring or JavaEE 7 environment__.

Since the controllers managed by the container you automatically get all the benefits that comes with it. This means that you can use `@Inject` or `@PostContruct` annotations, for example:

{% highlight java %}
@DolphinController
public class MyController {

  @Inject
  private PersistenceService myService;
  
  @PostConstruct
  public void init() {
    myService.loadData();
  }

}
{% endhighlight %}

Next to this Dolphin Platform provides addition features for the controller API. Any method that is annotated by `@DolphinAction` can be called from the client view to trigger some actions on user interaction, for example.

{% highlight java %}
@DolphinController
public class MyController {

  @Inject
  private PersistenceService myService;
  
  @DolphinAction
  public void load() {
    myService.loadData();
  }
  
  @DolphinAction
  public void save() {
    myService.saveData();
  }

}
{% endhighlight %}

By doing so it's very easy to handle the business logic that will be triggered by a user action. Since the methods are defined on the server you benefit of security and transaction support, for example.

Next to the actions a main concept of the Dolphin Platform is the presentation model (or view model). Each view-controller-pair can define it's own model that can simply be injected in the controller by using the `@DolphinModel` annotation. We will see later how such a model can be defined. When injecting a model to the controller its lifecycle is bound to the lifecycle of the controller and will be automatically managed by the underlying platform.

{% highlight java %}
@DolphinController
public class MyController {

  @Inject
  private PersistenceService myService;
  
  @DolphinModel
  private MyModel model;
  
  @DolphinAction
  public void refresh() {
    model.setData(myService.loadData());
  }
  
}
{% endhighlight %}

By doing so the model instance will automatically be created when a new controller instance will be created. If the controller will be destroyed by the container the model will be destroyed, too. In addition the model will automatically be synchronized between the server controller and the view on the client side. How the model can be accessed and handled on the client will part of a future post.

I think the biggest benefit in the shown concept is the perfect integration in a web container like it is provided by __Spring or JavaEE__. All your controller (and model) instances will be managed by the container and it's no problem to inject a __spring service__ in your controller, for example. By doing so you can use an API like [Spring data](http://projects.spring.io/spring-data/) and fill your view model directly with data from your persistence.

In the next preview I will show how a view model can be defined by using Dolphin Platform and how you can simply observe any view model on the server and client.
