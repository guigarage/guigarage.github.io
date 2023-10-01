---
title: 'Dolphin Platform has been released'
redirect_to: https://open-elements.com/posts/2015/11/09/dolphin-platform-has-been-released/
layout: post
author: hendrik
categories: [Dolphin Platform, JavaFX, Polymer, Web Frontends]
excerpt: "Since JavaOne is over for more than a week we found some time to finish all the steps and I'm proud to announce the first Dolphin Platform release"
featuredImage: dp-4
permalink: '2015/11/dolphin-platform-has-been-released/'
header:
  text: Dolphin Platform
  image: sample
---
We planed to release the [Dolphin Platform]({{ site.baseurl }}{% post_url 2015-10-04-dolphin-platform-a-sneak-peek %}) before JavaOne but the preparation of the
[JavaOne Voting Machines]({{ site.baseurl }}{% post_url 2015-10-23-a-short-preview-of-the-javaone-voting-machine %}) took more time then expected and we can't finish all the todos that we had to fully open source the Dolphin Platform. Since JavaOne is over for more than a week we found some time to finish all the steps and I'm proud to announce the first Dolphin Platform release :)

![Dolphin Platform](/assets/posts/guigarage-legacy/dp1-1024x255.png)

As a first step we uploaded the [Dolphin Platform website](http://www.dolphin-platform.io) and added some descriptions and information about the framework. In addition we created [a first tutorial](http://www.dolphin-platform.io/documentation/tutorial.html) and a [getting started" guide](http://www.dolphin-platform.io/documentation/getting-started.html). All the Java sources (client and server) are uploaded to [GitHub](https://github.com/canoo/dolphin-platform), a first release has been created and was uploaded to [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cdolphin-platform) & [JCenter](https://bintray.com/canoo/dolphin-platform/dolphin-platform/0.6.1/view).

If you want to try Dolphin Platform you can create your first application by simply adding the following dependencies:

For a Spring Boot based server you only need to add the following dependency:
{% highlight xml %}
<dependency>
    <groupId>com.canoo.dolphin-platform</groupId>
    <artifactId>dolphin-platform-server-spring</artifactId>
    <version>0.6.1</version>
</dependency>
{% endhighlight %}

For a JavaFX based client you need to add the following dependency:
{% highlight xml %}
<dependency>
    <groupId>com.canoo.dolphin-platform</groupId>
    <artifactId>dolphin-platform-client-javafx</artifactId>
    <version>0.6.1</version>
</dependency>
{% endhighlight %}

If you are new to Dolphin Platform you can find some post about the API [in my blog]({{ site.baseurl }}{% post_url 2015-10-04-dolphin-platform-a-sneak-peek %}). In addition we spend some time to create the [JavaDoc of the public API](http://www.dolphin-platform.io/javadoc/index.html). If you have any questions please leave a comment or [ask us on twitter](https://twitter.com/DolphinPlatform). For news about Dolphin Platform you should follow the official twitter account [@DolphinPlatform](https://twitter.com/DolphinPlatform).

## Java Script client libraries

As I [mentioned before]({{ site.baseurl }}{% post_url 2015-10-23-dolphin-platform-web-frontends-with-polymer %}) Dolphin Platform supports several client technologies. Next to the JavaFX library we hosted the sources for the basic [JavaScript client library](https://github.com/canoo/dolphin-platform-js) and the [Polymer client library](https://github.com/canoo/dolphin-platform-polymer) on github. We will add releases of the libraries this week. Once this is done I will blog how you can simply add Dolphin Platform to your web project by using [bower](http://bower.io).

## Links

Here is an overview of all important links:

* [Dolphin Platform website](http://www.dolphin-platform.io)
* [Getting started](http://www.dolphin-platform.io/documentation/getting-started.html)
* [Tutorial](http://www.dolphin-platform.io/documentation/tutorial.html)
* [JavaDoc](http://www.dolphin-platform.io/javadoc/index.html)
* [StackOverflow](http://stackoverflow.com/questions/tagged/dolphin-platform)
* [Blog Posts]({{ site.baseurl }}{% post_url 2015-10-04-dolphin-platform-a-sneak-peek %})
* [Java Github Repo](https://github.com/canoo/dolphin-platform)
* [JavaScript Github Repo](https://github.com/canoo/dolphin-platform-js)
* [Polymer Github Repo](https://github.com/canoo/dolphin-platform-polymer)
* [Dolphin Platform @ Twitter](https://twitter.com/DolphinPlatform)
