---
title: 'DataFX 8 has been released & DataFX core overview'
redirect_to: https://open-elements.com/posts/2014/10/22/datafx-8-has-been-released-datafx-core-overview/
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'I''m proud to announce that we have released DataFX 8.0 last week. This post will give you an overview of all the cool new features.'
featuredImage: java-6
permalink: '2014/10/datafx-8-released/'
header:
  text: DataFX 8 has been released
  image: sample
---
I'm proud to announce that we have released DataFX 8.0 last week. With DataFX 2.0 we created an API to get real world data in your JavaFX 2 application by using data readers for REST, WebSocket, SSE and many more endpoints. With DataFX 8.0 we introduce a lot of more content for your JavaFX 8 applications. Next to the data reader APIs we included flow and injection API to DataFX to create MVC based views and complex workflows. By doing so we lifted DataFX from a data reader API to a (small) application framework. DataFX 8.0 contains 5 modules:

* core
* datasources
* websocket
* flow
* injection

I think one of the big benefits of DataFX is that it has hardly any external dependency. The following graph shows the internal and external dependencies of DataFX 8:

![datafx-dep.016](/assets/posts/guigarage-legacy/datafx-dep.016.png)

As you can see in the picture next to the javassist dependencies all other dependencies are Java specs.

Ok let's talk about the content of the modules. As a first step of the DataFX 8 development we extracted all APIs that provide general support for multithreading and added them to the core module. Next to this some cool new APIs are part of the core module that will help you to define background tasks and solve concurrent problems the easy ways. Today I want to introduce two of these features. If you are interested in all features of DataFX 8  you should read the [tutorials]({{ site.baseurl }}{% post_url 2014-05-19-datafx-8-0-tutorials %}) and have a look in our [JavaOne slides](http://de.slideshare.net/HendrikEbbers/datafx-8-javaone-2014).

## The Observable Executor

When working with background tasks you will need a thread pool to manage all the concurrent operations. JavaSE provides several different of them with the Executors class. In DataFX 8 we introduce the [ObservableExecutor]({{ site.baseurl }}{% post_url 2013-02-09-datafx-observableexecutor-preview %}) that is an implementation of the [Executor](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html) interface and provides some JavaFX specific additional functionality. By using the ObservableExecutor you can bind the capacity of the executor to any JavaFX property. In addition all the task interfaces and classes of JavaSE, JavaFX and DataFX are supported by the ObservableExecutor. By doing so it is very easy to define titles, messages or progress updates for all your background tasks and show them on screen. A demo of the ObservableExecutor can be found [here]({{ site.baseurl }}{% post_url 2013-02-09-datafx-observableexecutor-preview %}). As a next step we will combine the ObservableExecutor with the cool Task Progress View by Dirk Lemmermann. It looks like this one is made for the ObservableExecutor ;)

{% include posts/youtube.html id="As-ahnLR_Dw" %}

## The ProcessChain

When developing an enterprise application with JavaFX you will need to define background tasks to call some server endpoints or start batch processes. Normally you will react to the answer of the tasks and update the UI. For example if you call a REST endpoint to receive some data you want to display the data on screen once the call is done. Doing this in the JavaFX Application thread isn't the best idea. You don't know how long the task will need to execute and therefore the application can't be repainted while the call is executing. This will end in a frozen application and frustrated users.

![frozen](/assets/posts/guigarage-legacy/frozen.png)

It's import to execute the server call (as any long running action) to a background thread. Doing this with the basic JavaSE concurrency tools will blow up your code and create methods that aren't readable. Here is a simple example of a function that will call a background task and show it's result on screen:

{% highlight java %}
Runnable backgroundRunnable = () -> {
	try {
		data = loadFromServer();
		Platform.runLater(() -> {
			updateUI(data);
		});
	} catch(Exception e) {
		Platform.runLater(() -> {
			handleException(e);	
		});	
	} finally {
		Platform.runLater(() -> {
			unblockUI();
		});
	}
}
{% endhighlight %}

I hope you are with me when saying that this code isn't as readable as it should be. In Swing Java contains a good helper class called the [SwingWorker](http://docs.oracle.com/javase/tutorial/uiswing/concurrency/simple.html). By using this class it was easier to create background tasks that provide data for the fronted.

![background-thread](/assets/posts/guigarage-legacy/background-thread.png)

It's still a lot of code that is needed to create a working SwingWorker because anonymous classes are needed. But today we have Lambdas, functional interfaces and all this cool language features and therefore you wouldn't code a background tasks this way. In DataFX 8 we introduce the ProcessChain class that is like a SwingWorker on steroids. Here is a small example that shows how the top code can be refactored by using the ProcessChain:

{% highlight java %}
ProcessChain.create().
addRunnableInPlatformThread(() -> blockUI()).
addSupplierInExecutor(() -> loadFromServer()).
addConsumerInPlatformThread(d -> updateUI(d)).
onException(e -> handleException(e)).
withFinal(() -> unblockUI()).
run();
{% endhighlight %}

Cool, isn't it. Now we can read the code and understand what's going on here. The ProcessChain uses all the new functional interfaces like Supplier or Consumer to define a chain of tasks that can be called on a background thread or on the JavaFX Application Thread. In addition the exception handling is directly included in the ProcessChain API. If you want to learn more about the ProcessChain you should check out our [slides](http://de.slideshare.net/HendrikEbbers/datafx-8-javaone-2014) or my [JavaFX Enterprise talk](http://de.slideshare.net/HendrikEbbers/javafx-enterprise-javaone-2014?related=1).

I hope you like these features. In the next posts I will introduce the other DataFX 8 modules.

{% include posts/slideshare.html id="39687394" %}
