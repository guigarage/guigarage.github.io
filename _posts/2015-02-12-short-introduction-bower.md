---
title: 'A short introduction to Bower'
redirect_to: https://open-elements.com/posts/2015/02/12/a-short-introduction-to-bower/
layout: post
author: hendrik
categories: [Web Frontends]
excerpt: 'This post gives a short introduction to Bower from a Java developers point of view.'
featuredImage: java-1
permalink: '2015/02/short-introduction-bower/'
header:
  text: A short introduction to Bower
  image: sample
---
Some weeks ago [I blogged about web components]({{ site.baseurl }}{% post_url 2014-11-12-first-steps-webcomponents %}). Since that post I did a lot of research about this topic and had a [talk about web components at JFokus](http://www.jfokus.se/jfokus/talks.jsp#WebComponents). I really like this new technology and therefore I plan to blog about it more often in future. All the JavaFX lovers and readers of my blog shouldn't be afraid. I will continue work with JavaFX ;)

Before going deep in the web components topic I want to introduce a tool that I think is needed when working with this new technology: [bower](http://bower.io).

![bower-logo](/assets/posts/guigarage-legacy/bower-logo-300x263.png)

Bower is an open source tool that is created by Twitter and helps you to manage the dependencies of a web application. By using bower you can define all the dependencies of your application and automatically download them.

## Getting started with bower

Bower can be [installed by using NPM](http://bower.io/#install-bower) and once it's on your system you can create a bower configuration for your project by simply calling `bower init`. By doing so you need to define some metadata for your project and bower will create a json file (`bower.json`) in your project folder. This file contains the definition of your project and might look like this:

{% highlight json %}
{
  "name": "bootstrap-layout",
  "version": "0.0.0",
  "authors": [
    "Hendrik Ebbers <hendrik.ebbers@web.de>"
  ],
  "license": "MIT",
  "ignore": [
    "**/.*",
    "node_modules",
    "bower_components",
    "test",
    "tests"
  ]
}
{% endhighlight %}

Developers who are familiar with [Maven](http://maven.apache.org) might notice some parallels. Once the file is created you can add dependencies to your project by calling a bower command like `bower install --save webcomponentsjs` that installs the web components polyfill (I plan to blog later about that module). When looking at the bower file you will find a dependencies section that contains the added module. In addition a `bower_components` folder was created in your project that contains the defined dependency. Once this is done you can simply use any content of the `bower_components` folder in your application. But don't forget to add the `bower_components` folder to `.gitignore` ;) When checking out the project from git you only need to call `bower install` and the cool little tool will download all the defined dependencies for you and add them to the `bower_components` folder automatically. So from a Java developer point of view this is mostly what Maven does for my Java project.

Here is a quick overview about the commands that is part of my JFokus talk:

![bowe-ovwev](/assets/posts/guigarage-legacy/bowe-ovwev.png)

## How to define dependencies

Some of you might ask yourself how you should know the definition or name of a dependency. Bower is more flexible that Maven in this case. When calling the `install` command there are different ways how a dependency can be specified. Here is a short overview that is taken from the [bower web page](http://bower.io/#getting-started):

![bower install](/assets/posts/guigarage-legacy/bower-install.png)

In addition bower provides a [search frontend](http://bower.io/search/) (like the Maven central frontend) that can be used to search for modules and dependencies.
