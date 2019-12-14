---
title: 'DataFX: ObservableExecutor Preview'
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'TODO'
featuredImage: sample-9
permalink: '2013/02/datafx-observableexecutor-preview/'
header:
  text: ObservableExecutor Preview
  image: sample
---
Since December we are working on a new [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) version. DataFX will provide a new low level API for multithreading and background tasks in JavaFX. With this API everyone can create new and custom DataSources that will fetch data in background and publish it to JavaFX properties (like you can do with the SwingWorker in Swing). Additionally we will provide some basic concurrency features like the [invokeAndWait-method]({{ site.baseurl }}{% post_url 2013-01-01-invokeandwait-for-javafx %}).

Next to this features we created a new [Executor](http://docs.oracle.com/javase/tutorial/essential/concurrency/exinter.html) class that offers some additional features for the use with JavaFX. The `ObservableExecutor` holds a ListProperty with all currently scheduled or running tasks. By using this Property you can easily observe all tasks in your UI. We will provide a ListCell class to visualize the running tasks of the executor with only a few lines of code:

{% highlight java %}
Executor executor = new ObservableExecutor();
ListView<Service<?>> list = new ListView<>();
list.setCellFactory(new ServiceListCellFactory());
list.itemsProperty().bind(executor.currentServicesProperty());
{% endhighlight %}

The ObservableExecutor uses the wrapper pattern to hold any Executor. Because all task are wrapped into [Services](http://docs.oracle.com/javafx/2/api/javafx/concurrent/Service.html) you can easily access the title, message or progress of any task. A short example that shows the current state of the API can be found here:

{% include posts/youtube.html id="eQaVNQKy1U0" %}

Because Runnable & Callable normally do not provide title, message and progress properties we created extended interfaces (`DataFXRunnable` & `DataFXCallable`) where all this functions are injected while using them with the ObservableExecutor.

At the current state tasks can be passed to an `ObservableExecutor` by the following methods:

{% highlight java %}
public <T> Worker<T> submit(Service<T> service);

public <T> Worker<T> submit(Task<T> task);

public <T> Worker<T> submit(Callable<T> callable);

public Worker<Void> submit(Runnable runnable);

public void execute(Runnable runnable);
{% endhighlight %}

There are some additional features, too. You can mark a task as not cancelable for example.

Hope you like this stuff!
