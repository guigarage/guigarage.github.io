---
title: JavaFX Themes
layout: article
permalink: '/javafx-themes/'
hidden: true
header:
  image: sample
  text: JavaFX Themes
---
Some of the open source projects I'm working on are themes and skins for JavaFX. By using these themes a JavaFX application can be styled without handling CSS or custom controls.

Here is a list of the themes that I'm currently working on:

* [AquaFX]({{ site.baseurl }}{% link pages/projects/aquafx.md %})
* [AeroFX]({{ site.baseurl }}{% link pages/projects/aerofx.md %})
* [Flatter]({{ site.baseurl }}{% link pages/projects/flatter.md %})

All the themes that I'm currently working on are based on the same architecture and structure: The main goal of the themes is to provide a consistent look and feel for all default JavaFX controls. Therefore a theme contains a CSS file that defines the skin for each control type. In addition the themes contains Java skin classes that define some specific behavior or used animations. A developer that wants to use a theme only needs to call a style method in the facade class of the theme. Here is an example how the AquaFX theme can be set for a JavaFX application:

{% highlight java %}
AquaFx.style();
{% endhighlight %}
