---
title: 'Building JavaFX Applications with Maven'
redirect_to: https://open-elements.com/posts/2012/10/13/building-javafx-applications-with-maven/
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'With the preview of JavaFX 2 that is part of Java 6 and 7 you can already build custom JavaFX applications by using Maven'
featuredImage: java-6
permalink: '2012/10/building-javafx-applications-with-maven/'
header:
  text: Building JavaFX Applications with Maven
  image: sample
---
There are already a lot of posts out there that describe a workflow for integrating JavaFX in Maven. Here is one example by Adam Bien: [http://www.adam-bien.com/roller/abien/entry/how_to_compile_java_fx](http://www.adam-bien.com/roller/abien/entry/how_to_compile_java_fx)

For all this solutions you have to specify your system-specific path to the JavaFX installation. You can set the property inside your Maven pom. This is ok for a single user environment but counterproductive when developing in a team. Another way is to set the property on OS Level. But then every developer have to do this on every workstation.

Since Java 7 update 6 JavaFX 2.2 is bundled  in the JRE. You can find JavaFX at `JAVAHOME/lib/jfxrt.jar`. Since Maven 3 you can access the `JAVAHOME` path with the `${java.home}` property. With this informations you are able to create a system independent pom-file:

{% highlight xml %}
<dependency>
  <groupId>com.oracle</groupId>
  <artifactId>javafx</artifactId>
  <version>2.2</version>
  <systemPath>${java.home}/lib/jfxrt.jar</systemPath>
  <scope>system</scope>
</dependency>
{% endhighlight %}

The solution is working since __Java 7u6__. If you have different JDKs / JREs installed on your system you have to start Maven with the right JDK. The pom also works in Eclipse with m2eclipse-plugin. Here you must ensure that Eclipse is started with Java 7u6+.
