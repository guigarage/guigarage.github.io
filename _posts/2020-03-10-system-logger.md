---
layout: post
title:  'TODO'
author: hendrik
featuredImage: java-2
excerpt: 'TODO.'
categories: [Java]
header:
  text: TODO
  image: sample
---

With the release of Java 9 a new Logging API has been added to Java. While this API is one of the Java features that was missed in a lot of "What's new in Java" articles I will have a deeper look at the API and how it can be used.

TODO: Noch bessere einführung: Bisher java.util.logging und viele externe LOgging APIs. Nun gibt es was neues, kleines, schlankes im JDK. Warum und wo macht es sinn?

## About the API

The main functionallity of the new logger API can be found in the `System.Logger` interface. This interface defines a logger that provides several methods to log messages. Like you might already know from other logging framework a level must be defined for each log message. The new Logger API provides it's own log levels. Here the API does not simply reuse the Levels that are part of the `java.util.logging` API but contains a new enum with custom levels (see `System.Logger.Level`). This levels are inspired by the level definitions of modern Java logging frameworks like slf4J or Log4J. While this levels are new all of the can easily mapped to the level definition of the `java.util.logging` API. The following table gives an overview of the mapping:

 {:.table}
 | System.Logger Level | java.util.logging Level |
 | ------------------- | ----------------------- |
 | ALL                 | ALL                     |
 | TRACE               | FINER                   |
 | DEBUG               | FINE                    |
 | INFO                | INFO                    |
 | WARNING             | WARNING                 |
 | ERROR               | SEVERE                  |
 | OFF                 | OFF                     |

Here is an example:

{% highlight java %}
System.Logger LOG = ...
LOG.log(Level.INFO, "Hello World");
{% endhighlight %}

Für zusammengesetzte Strings liefert das Logging Framework auch verschiedene Methoden mit. Wenn immer eine Log-Message zusammengebait wurd sollten diese Methoden aus performance-gründen genutzt werden:

{% highlight java %}
final String user = ...

// Supplier
LOG.log(Level.INFO, () ->"Hello " + user);

//format
LOG.log(Level.INFO, "Hello {0}", user);
{% endhighlight %}

Für alle die schon mal mit einem Logging API gearbeitet hat ist das alles alter Kram.
 Interessant ist aber natürlich, wo das Logging denn jetzt überhaupt landet. Hierfür ist die Klasse System.LoggerFinder
 System.LoggerFinder werden über SPI geladen. Wenn kein spezifischer System.LoggerFinder über SPI definiert ist, wird der defualt genommen. Dieser leitet alle LOG-Nachrichten an das `java.util.logging` API weiter. Somit handelt es sich bei dem neuen Logging API um einen ähnlichen Ansatzt wie bei SLF4J.

## Interne nutzung im JDK

Vorteil: Man kann das JDK Logging einfach nach LOG4J etc. umleiten.
Hier mal prüfen wie viel es genutzt wird.

## Warum sollte ich es nun nutzen?

- Dependencies vermeiden
- Interface und man kann die eigentihe Logging-Impl super einfach austauschen
- Im Grunde SLF4J aber ohne die benötigte dependency.
- Ideal wär es, wenn alle dependencies es nutzen würden

- Aber: gegenüber z.B. SLF4J fehlen ein paar APIs. Wenn man erweiterte Features nutzen will, muss man doch in teilen gegen konkretes Logging API programmieren.