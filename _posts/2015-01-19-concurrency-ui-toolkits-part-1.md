---
title: 'Concurrency in UI Toolkits (Part 1)'
layout: post
author: hendrik
categories: [Desktop Application Framework (JSR 377), JavaFX]
excerpt: 'This post describes how the Concurrency in UI Toolkits can be defined in a unified way.'
featuredImage: sample-3
permalink: '2015/01/concurrency-ui-toolkits-part-1/'
header:
  text: Concurrency in UI Toolkits
  image: sample
---
Today every UI toolkit that is not running in a browser needs an UI Thread the handle the repainting and the event handling of the UI. Examples for this kinds of UI Toolkits are iOS, Android, SWT, JavaFX or Swing. Each of this toolkits defines a thread that will handle all the ui specific calls. Let's call this thread "UI Thread".

![ui-thread](/assets/posts/guigarage-legacy/ui-thread.png)

By definition all calls that will affect the UI or get data of the UI must be called on this thread. Accessing the UI from another thread than the UI Thread will result in a lot of different problems.

Normally all user events that are handled by the toolkit will be called on the UI thread. Therefore an event handler will be called on the thread, too. If a developer wants to interact with UI components as a result of an user event he can do this directly in the handler. The following code shows some pseudo code how this might look. Normally each UI Toolkits provide it's own mechanism for event handling.

{% highlight java %}
button.setOnAction(event -> button.setEnabled(false));
{% endhighlight %}

If you want to interact with the UI from outside of an event you need to invoke your code on the "UI Thread".

![invokeLater](/assets/posts/guigarage-legacy/invokeLater.png)

Each UI Toolkit provides a helper method to handle this invocation. In a UI Toolkit independent and unified way this method might look like this:

{% highlight java %}
void runOnUiToolkitThread(Runnable runnable);
{% endhighlight %}

By doing so any runnable can be called on the UI Thread. This is ok for some general use cases but it's definitely not enough to create an big application. One of the big problems is that you don't know when the code will be called and when the call is finished. The definition of this method only says that the code will be called in some future on the UI Thread. Therefore we need a second method that blocks until the call is finished.

![invokeAndWait](/assets/posts/guigarage-legacy/invokeAndWait.png)

In most cases this method will have the same signature as the previous one. Let's define the method in a unified way:

{% highlight java %}
void runOnUiToolkitThreadAndWait(Runnable runnable);
{% endhighlight %}

Thanks to Java 8 we can define this method as a default method based on the other one:

{% highlight java %}
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        FutureTask<Void> future = new FutureTask<>(runnable, null);
        runOnUiToolkitThread(future);
        future.get();
}
{% endhighlight %}

This looks good so far but there is still a problem. As said the UI must only be accessed by using the UI Thread. Let's think about a background thread that want's to call a web service based on some user input. To do so the thread needs to know the input of a textfield, for example. Because we can't access the text from the background thread we need to invoke the call to the UI Thread:

![access](/assets/posts/guigarage-legacy/access.png)

The following code shows how such a call might look like:

{% highlight java %}
public void runningOnBackgroundThread() {
  String userInput = runOnUiToolkitThreadAndWait(() -> textfield.getText());
  callWebservice(userInput);
}
{% endhighlight %}

To do so we need another helper method:

{% highlight java %}
<T> T runOnUiToolkitThreadAndWait(Callable<T> callable);
{% endhighlight %}

In addition we can provide a method that won't block until the call is finished:

{% highlight java %}
<T> Provider<T> runOnUiToolkitThread(Callable<T> callable);
{% endhighlight %}

Now we have a set of methods that can be used to interact with the UI Thread:

{% highlight java %}

public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);
  
  <T> Provider<T> runOnUiToolkitThread(Callable<T> callable);
  
  void runOnUiToolkitThreadAndWait(Runnable runnable);
  
  <T> T runOnUiToolkitThreadAndWait(Callable<T> callable);
  
}
{% endhighlight %}

Let's have a deeper look how we can implement this methods by using default methods:

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

As you can see there are 2 differences to the basic interface definition. First we need to throw some exceptions because calling the get() method of a Future instance will throw exceptions. These exceptions are needed. Let's think your runnable call that accesses the UI will contain an error and throws an exception. In this case you want to know about the error when checking the result of the call. As a next change the Provider result type of one method is changed to Future. Internally a Future is used that can't be casted to the Provider interface. In addition a Provider won't define the needed Exceptions as described earlier.

## Conclusion

The defined interface contains only one method that needs to be implemented in a UI Toolkit specific way to create some helper methods. This is a good start but some of you might know, that there are still some problems in this methods. Maybe you call a *AndWait(..) method from the UI Thread. This will maybe end in a deadlock. In addition the Future interface defines the method "boolean cancel(boolean mayInterruptIfRunning)". What happens if we call this on a task that is executed by the UI Thread? This issues will be discussed in the next post.
