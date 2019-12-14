---
title: 'How to set up a DataFX application'
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'This tutorial describes how you can add DataFX to your JavaFX application or start a new application that is based on DataFX.'
featuredImage: sample-6
permalink: '2015/01/set-datafx-application/'
header:
  text: How to set up a DataFX application
  image: sample
---
A lot of people start using DataFX in small projects without a build file and by doing so they sometimes use old builds. Because of that I will give you some hints how to setup a project that is using DataFX.

## DataFX builds

Every stable DataFX build can be found at Maven Central. If you are new to Maven or Maven Central just open this [link](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.datafx%22). On the page you will find all artifacts of DataFX. If you for example want to use DataFX core you can download the jar, sources and javadoc [here](http://search.maven.org/#artifactdetails%7Cio.datafx%7Ccore%7C8.0%7Cjar).

![maven central](/assets/posts/guigarage-legacy/maven-central.png)

Each DataFX artifact is defined by the group-id, artifact-id and version. The group-id is always "io.datafx" and the artifact-id depends on the DataFX-Module. If you for example want to create an application that is suing the DataFX Flow you need the datafx-flow module and all it's dependencies. At the moment the last stable version is 8.0.

If you want to download the DataFX jars by hand and add them to your application you need to download all dependencies of the module, too. Thankfully DataFX doesn't have much dependencies. Here is an overview:

![datafx-dep.016](/assets/posts/guigarage-legacy/datafx-dep.016.png)

Once you have downloaded the last version of DataFX and added the jars to the class path of your application you can start coding.

## Using builds tools

Normally you won't download the dependencies of your application manually. Build tools like [Maven](http://maven.apache.org) or [Gradle](https://gradle.org) can do this job for you. By using one of the tools you only need to define the dependencies in your build file and the build tool will automatically download all needed jars. In addition the tools support transitive dependencies. This allows you to avoid needing to discover and specify the libraries that your own dependencies require, and including them automatically. If you Maven as build tool and you only want to add the DataFX Flow API to your application you only need to add one dependency:

{% highlight xml %}
<dependency>
    <groupId>io.datafx</groupId>
    <artifactId>flow</artifactId>
    <version>8.0</version>
</dependency>
{% endhighlight %}

## Use DataFX in your application

Once you added DataFX as dependency to your application you can using it as described in the [tutorials]({{ site.baseurl }}{% post_url 2014-05-19-datafx-8-0-tutorials %}).
