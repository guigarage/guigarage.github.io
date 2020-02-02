---
title: 'Reactive Programming with JavaFX'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'Because the JavaFX API was designed for Java 8 it provides a lot of Lambda support and callbacks are used a lot. But next to the default JavaFX APIs there are currently an open source projects that adds a lot of reactive design and architecture to the JavaFX basics: ReactFX.'
featuredImage: java-2
permalink: '2014/03/reactive-programming-javafx/'
header:
  text: Reactive Programming with JavaFX
  image: sample
---
Java 8 is finally released and Lambda expression are an official part of Java. Thanks to this it's much easier to write applications in a reactive way. One of the main concepts in reactive architecture is an __event driven design__. [The reactive manifesto](http://www.reactivemanifesto.org/) contains the following about event driven design:

> In an event-driven application, the components interact with each other through the production and consumption of events—discrete pieces of information describing facts. These events are sent and received in an asynchronous and non-blocking fashion.

## Reactive Programming with Java

By using Lambdas it's very easy to define callbacks that can react on specific events. Here is a short example about an event driven design without the usage of Lambda expressions:

{% highlight java %}
public static void hello(String... names) {
    Observable.from(names).subscribe(new Action<String>() {
        @Override
        public void call(String s) {
            System.out.println("Hello " + s + "!");
        }
    });
}
{% endhighlight %}

Thanks to Lambda expressions the same functionallity can be coded in Java 8 this way:

{% highlight java %}
public static void hello(String... names) {
    Observable.from(names).subscribe((s) -> System.out.println("Hello " + s + "!"));
}
{% endhighlight %}

The example is part of the [RxJava tutorial](https://github.com/Netflix/RxJava/wiki/Getting-Started).

## Reactive Programming with JavaFX

Let's take a look at JavaFX. Because the JavaFX API was designed for Java 8 it provides a lot of Lambda support and callbacks are used a lot. But next to the default JavaFX APIs there are currently an open source projects that adds a lot of reactive design and architecture to the JavaFX basics: [ReactFX](https://github.com/TomasMikula/ReactFX).

By using ReactFX you can do a lot of cool event driven stuff with only a few lines of code. Here is an example how event handlers can be designed to react on user inputs:

{% highlight java %}
EventStream<MouseEvent> clicks = EventStreams.eventsOf(node, MouseEvent.MOUSE_CLICKED);
clicks.subscribe(click -> System.out.println("Click!"));
{% endhighlight %}

I think the API provides a lot of cool functionallity that let you design JavaFX applications that are more reactive and I hope to see a lot of more code like shown in the example above.

## Summery

The are currently 2 cool reactive APIs for Java out there: [RxJava](https://github.com/Netflix/RxJava/wiki/Getting-Started) for a basic use in Java and [ReactFX](https://github.com/TomasMikula/ReactFX) that is specialized for JavaFX. Theoretically you can do everything (or most of the stuff) you can do with ReactFXwith the help of RxJava, too. But here you need to concern about the JavaFX Application Thread. Because ReactFX is implementated for JavaFX (or a single threaded environment) you don't need to handle this. A first comparison of this two libraries can be found [here](https://gist.github.com/timyates/fd6904dcca366d50729c#comment-1198536).
