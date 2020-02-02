---
title: 'Concurrency in UI Toolkits (Part 2)'
layout: post
author: hendrik
categories: [Desktop Application Framework (JSR 377), JavaFX]
excerpt: 'This post describes how the Concurrency in UI Toolkits can be defined in a unified way.'
featuredImage: java-8
permalink: '2015/02/concurrency-ui-toolkits-part-2/'
header:
  text: Concurrency in UI Toolkits
  image: sample
---
In the [first post]({{ site.baseurl }}{% post_url 2015-01-19-concurrency-ui-toolkits-part-1 %}) of this series I showed how Concurrency is handled in UI Toolkits and how a generic approach to work with the toolkit specific thread may look like. This ends in the following interface:

{% highlight java %}
public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);

    default <T> Future<T> runOnUiToolkitThread(Callable<T> callable) {
        FutureTask<T> future = new FutureTask<>(callable);
        runOnUiToolkitThread(future);
        return future;
    }

    default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        runOnUiToolkitThread((Callable<Void>)() -> {
            runnable.run();
            return null;
        }).get();
    }

    default <T> T runOnUiToolkitThreadAndWait(Callable<T> callable) throws InterruptedException, ExecutionException {
        return runOnUiToolkitThread(callable).get();
    }
}
{% endhighlight %}

But there are still some problems with this interface:

* What happens if the `runOnUiToolkitThreadAndWait(..)` method is called on the UI-Thread?
* One method returns a `Future<>` that defines the `boolean cancel(boolean mayInterruptIfRunning)` method. By definition thy method will interrupt the thread in that the task is running. But we never want to interrupt the UI Toolkit thread.

Let's start with the first problem. Before we can solve this another question must be answered: I it's a problem to call this methods on the toolkit thread we need a way to check if the current thread is the toolkit thread. To do so most toolkits provide a helper method that checks if the current thread is the toolkit Thread. Examples are shown in the following code snippet.

{% highlight java %}
//JavaFX Helper Method
Platform.isFxApplicationThread();

//Swing Helper Method
SwingUtilities.isEventDispatchThread()
{% endhighlight %}

Because most Toolkits support this method we can simply add it to our interface:

{% highlight java %}
public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);

 boolean isUIToolkitThread();

    default <T> Future<T> runOnUiToolkitThread(Callable<T> callable) {
        FutureTask<T> future = new FutureTask<>(callable);
        runOnUiToolkitThread(future);
        return future;
    }
 
    default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        runOnUiToolkitThread((Callable<Void>)() -> {
            runnable.run();
            return null;
        }).get();
    }
 
    default <T> T runOnUiToolkitThreadAndWait(Callable<T> callable) throws InterruptedException, ExecutionException {
        return runOnUiToolkitThread(callable).get();
    }
}
{% endhighlight %}

Once this is done we can have a deeper look at the methods that will block until a task was executed on the ui toolkit. In the defined interface the two methods that are named `runOnUiToolkitThreadAndWait` defines this behavior. Once the method is called a new task is created and added to the ui thread. Because the thread has a lot of work to do normally a queue will handle this tasks and execute them by using a first in first out approach. The following image shows an example.

![queue](/assets/posts/guigarage-legacy/queue.png)

By doing so our task will be added to the queue and executed once all task that has been added earlier to the queue were executed. If we call this method from the ui thread the created task can't be executed before the task that is currently running is finished. But because the `runOnUiToolkitThreadAndWait` methods will wait for the execution of the new task we will end in an deadlock that is definitely the worst think that can happen. By doing so nothing can be handled on the UI thread: No user interaction or rendering can be done and the application is frozen. Because no Exception will be thrown the application just hangs we will receive no information what has triggered the error.

With the help of the new `isUIToolkitThread()` method we can avoid this behavior and refactor the methods to an more fail-safe version. With a simple if-statement we can add a special behavior if the `runOnUiToolkitThreadAndWait` method is called from the ui thread:

{% highlight java %}
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
  if(isUIToolkitThread()) {
    //what should we do now?? 😰
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}
{% endhighlight %}

Once this is done we need to decide what we want to do if the method was called join the ui thread. In general there are two different ways how this is handled by ui toolkits:

* throw an `Exception` (checked or unchecked)
* directly execute the runnable by calling the `run()` method

Here are the implementations for this approaches:

{% highlight java %}
//unchecked exception
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
  if(isUIToolkitThread()) {
    throw new RuntimeException("This method should not be called on the UI Thread")
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}

//unchecked exception
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException, ToolkitException {
  if(isUIToolkitThread()) {
    throw new ToolkitException("This method should not be called on the UI Thread")
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}

//Call the runnable directly
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
  if(isUIToolkitThread()) {
    try {
      runnable.run();
    } catch(Exception e) {
      throw new ExecutionException(e);
    }
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}
{% endhighlight %}

The first 2 methods looks mostly the same. Only the exception type is different. The first method uses an unchecked exception that will end in a more readable code when using the method because you don't need to catch the new exception type all the time. But developers need to know that an unchecked exception will be thrown whenever the method is called on the ui thread to avoid errors at runtime.

The third method can be called on any thread. A developer doesn't need to think about it. If you know what you do, this is the most flexibel way how such a method can be defined. But on the other hand this can cause problems. I have seen a lot of projects where developers used this type of method way to often. Because they ad no idea how to handle the ui thread `invokeAndWait(..)` methods were called all over the code. By doing so your stack trace ends in something like this:

![stack](/assets/posts/guigarage-legacy/stack.png)

This will end in code that is unperformant, unstable and can't be maintained anymore. Therefore I would choose one of the first 2 implementations. But that's only how I see this things and maybe you have a complete different opinion. Therefore it would be great if you can leave a comment here about your favorite way how to handle this problems. [JSR-377]({{ site.baseurl }}{% post_url 2014-12-30-desktopembedded-application-api-jsr %}) will contain such a interface and we want to resolve all the shown problems in an ui toolkit independent way. If you are interested in the JSR or want to share your opinion about this topic you should have a look at the [JSR Mailing List](http://jsr377-api.40747.n7.nabble.com). In the next post I will have a deeper look at the `Future<>` interface in combination with ui threads.
