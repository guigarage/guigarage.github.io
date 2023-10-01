---
title: 'DataFX 8 Preview 2: The ProcessChain'
redirect_to: https://open-elements.com/posts/2014/01/23/datafx-8-preview-2-the-processchain/
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'This DataFX 8 preview introduces the ProcessChain. This uses Java 8 features like Lambda to provide multi threaded functionality in JavaFX'
featuredImage: java-9
permalink: '2014/01/datafx-8-preview-2-processchain/'
header:
  text: The ProcessChain
  image: sample
---
Some time ago I gave a [first preview of the new APIs and functions]({{ site.baseurl }}{% post_url 2013-12-27-datafx-controller-framework-preview %}) in [DataFX 8]({{ site.baseurl }}{% link pages/projects/datafx.md %}). We are currently plan to release DataFX 8 once JavaFX 8 is released. I plan to blog about the new features in DataFX in the next weeks. By doing so we hope to receive some useful feedback. In the last preview I described the [controller and flow API]({{ site.baseurl }}{% post_url 2013-12-27-datafx-controller-framework-preview %}) of DataFX. Today I will show you only only small class :)

Next to new APIs we added some helpful classes to the DataFX core packages. The class that I want to show you today is one of these new classes: The ProcessChain.

The ProcessChain can be used to create a task chain. A task can be run on the "JavaFX Application Thread" or on a background thread. Some of you may know the SwingWorker class that was a important tool when working with background tasks in Swing. With the ProcessChain you can do similar stuff but thanks to Java 8, Lambdas and functional interfaces this class is more powerful that SwingWorker ever was.

Let's think about a dialog that loads some data from a server. This can be done by clicking a button. Whenever the button is pressed the following workflow should be executed:

* Disable Button
* Communicate with Server
* Enable Button

The button must enabled and disabled on the "JavaFX Application Thread" but you should not communicate with the server on this thread cause it will freeze the application. Because of that the communication must be executed on a extra thread:

* Disable Button (Application Thread)
* Communicate with Server (Background-Thread)
* Enable Button (Application Thread)

The ProcessChain is a fluent API that can be used to create this workflows in JavaFX. Because its a chain you can add as many task as you want. The ProcessChain can use Lambdas and pass task results to the next Task. The following default interfaces can be used here:

* java.util.function.Function
* java.util.function.Supplier
* java.util.function.Consumer
* java.lang.Runnable

Here is a simple example:

{% highlight java %}
Label label = new Label("No data");
Button button = new Button("Press me“);
button.setOnAction(new EventHandler() {
    public void handle(ActionEvent event) {
        new ProcessChain().inPlatformThread(() -> button.setDisable(true))
            .inExecutor(() -> communicateWithServer())
            .inExecutor(() -> {return "Time in Millis: " + System.currentTimeMillis();})
            .inPlatformThread((Consumer) (t) -> label.setText(t.toString()))
            .inPlatformThread(() -> button.setDisable(false))
            .run();
    }
});
{% endhighlight %}

As you can see in the example it is very easy to create a chain with the API. By using the `inExecutor(...)` or `inPlatformThread(...)` method a developer can choose if a task should run on the "JavaFX Application Thread" or in background.

In addition tasks can publish its result and use the result of the previous one. Here the new Java 8 interfaces Function, Supplier and Consumer can be used to define tasks with an input or output value.

The DataReader API is still part of DataFX of course. If you use DataReaders you shouldn't need the shown API or can add all tasks to the Platform thread. But sometimes there are issues where you need to create your own custom background tasks. In these cases the ProcessChain is your friend ;)
