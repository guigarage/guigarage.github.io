---
title: 'This is for the native ones'
layout: post
author: hendrik
categories: [AquaFX, General, JavaFX]
excerpt: 'JavaFX provides the ability to style controls by CSS or code. We are using this functionallity to create native looking controls.'
featuredImage: java-7
permalink: '2013/01/this-is-for-the-native-ones/'
header:
  text: This is for the native ones
  image: sample
---
As you can read in an [earlier post]({{ site.baseurl }}{% post_url 2012-11-17-custom-ui-controls-with-javafx-part-1 %}), JavaFX provides the ability to style controls by CSS or code. Most of the techniques you can use to style components are described in [this great JavaOne talk by Gerrit Grunwald](https://oracleus.activeevents.com/connect/sessionDetail.ww?SESSION_ID=2425). The following graphic shows the basic relationship between all parts that are involved in component styling:

![nativ1-1](/assets/posts/guigarage-legacy/nativ1-1.png)

As you can see in the graphic, the CSS file is the main entry point for the Control styling. The CSS defines UI related properties like colors or fonts. In addition to this the CSS specifies the Skin class that will be used to style the Control.

## Official JavaFX styles

JavaFX currently offers one system independent style that is named "Caspian". The style is completely defined in one CSS file:

{% highlight Java %}
com.sun.javafx.scene.control.skin.caspian.caspian.css
{% endhighlight %}

Oracle and the JavaFX team did a really great job by designing this cross platform skin. I my opinion it looks great on every OS. Here is a short extract of the Style:

![caspian](/assets/posts/guigarage-legacy/caspian.png)

You can see the complete blueprint [here](http://javafx-jira.kenai.com/secure/attachment/34815/Caspian.png) (you need to be logged in to OpenJFX Jira). The Caspian style offers definitions for all default JavaFX controls.

The following chart describes the style handling for the JavaFX Button with the official caspian CSS file:

![nativ2-1](/assets/posts/guigarage-legacy/nativ2-1.png)

While JavaFX 2.x only offers the caspian style, JavaFX 8 will provide a second one called "Modena".

![modena](/assets/posts/guigarage-legacy/modena.png)

Here you can see a preview of Buttons with Modena style. Compared to Caspian it's brighter and the controls look a little flatter. Modena is still in development and can change all the time until JavaFX 8 will be released.

While the current preview of Modena really looks great (see full preview at OpenJFX Jira [http://javafx-jira.kenai.com/secure/attachment/34814/Modena-v0.1.png](here)) it's still a cross platform Look & Feel. You can compare the "Caspian" and "Modena" styles to the "Metal" and "Nimbus" Look & Feel of Swing. Both are designed for cross platform use, too. But Swing benefit are the system L&Fs. So if you have to develop a application with a native look, you currently have to use Swing because it offers native Look & Feels for Windows, Mac OS and Linux. But since JavaFX 2.x is out we do not want to use Swing anymore :)

## Native JavaFX styles

As described above JavaFX officially only offers cross platform skins. According to a [mail by Richard Bair](http://mail.openjdk.java.net/pipermail/openjfx-dev/2013-January/005281.html) from the JavaFX mailing list oracle won't provide native skins for JavaFX in the near future.

Thanks to the great JavaFX community the first approaches for native styles appeared the last month. Jasper Potts created some [basic CSS styles for buttons](http://fxexperience.com/2011/12/styling-fx-buttons-with-css/) that copy the native style of iOS, Mac OS Lion and Windows 7.

![native-buttons](/assets/posts/guigarage-legacy/native-buttons.png)

The second starting point is [JMetro](http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/), a project maintained by [Pedro Duque Vieira](http://twitter.com/P_Duke), that provides Windows 8 Metro skins for JavaFX controls with the support of a dark and a light theme.

![pushbuttondark-1](/assets/posts/guigarage-legacy/pushbuttondark-1.png)

![contextmenu-metro](/assets/posts/guigarage-legacy/contextmenu-metro.png)

JMetro is part of [JFXtras](http://jfxtras.org) and can be found at [github](https://github.com/JFXtras/jfxtras-styles). While writing this post the following controls are supported by JMetro:

* Button
* ToggleButton
* CheckBox
* RadioButton
* ContextMenu
* MenuItem
* ScrollBar
* ScrollPane
* ComboBox

The next supported Control will be the JFXtras Calender Control. You can find a cool preview [here](http://pixelduke.wordpress.com/2013/01/14/java-calendar-with-a-metro-style/).

The last approach I've found is from [software4java](http://blog.software4java.com). On this blog you can find some pictures and videos of [iOS](http://blog.software4java.com/?p=27), [Mac OS and Windows 7 related styles](http://blog.software4java.com/?p=15) for JavaFX. But there is no activity since April 2012 and the sources were never provided.

## JavaFX goes Aqua

The current community based projects demonstrate that it's possible to create native looking skins for JavaFX controls. According to this conditions a student I'm currently responsible for is planning the bachelor of science thesis based on JavaFX. Currently it is planned to develop a native JavaFX skin for Mac OS. The project should combine skins for JavaFX controls like it's done with JMetro and provide new Mac specific controls on the other hand like [macwidgets](http://code.google.com/p/macwidgets/) does for Java Swing.

We have done a first prove of concept and are happy to share the result with you. Do you find out which shutdown window is the native one and which one is developed in JavaFX?

{% include posts/youtube.html id="GbJDg5wsJ9E" %}

Our first code is hacked in many ways and needs a lot of refactoring for sure. But it was a lot of fun and we learned a lot about CSS and JavaFX skins. Even if it's hacked at some points we do not use any bitmaps. Everything is done with CSS, JavaFX effects and the SVG support. We created some basic concepts how to create this aqua related skins for JavaFX. The following chart shows the class relations and inheritance for a Button:

![nativ3-3](/assets/posts/guigarage-legacy/nativ3-3.png)

As you can see we still use the basic Button class from JavaFX. So every Button can be skinned to a Aqua Button. The Button will be styled by a special Skin class ("AquaButtonSkin") because the usage of CSS isn't enough for some points. As you can see in the video the default button of a dialog has a blinking animation in MacOS. This effect can't be generated by CSS. But because there is only one global CSS file for the Aqua Skin you can style a complete application (once every control type is supported)  by only adding one line to your code. As described at the beginning of this article all Skin classes are defined inside the CSS and will be managed by JavaFX automatically. Here is the code to set the style to your application:

{% highlight java %}
myScene.getStylesheets().add(AquaSkin.getStylesheet());
{% endhighlight %}

## One last hurdle

We currently defining the proposal of the thesis and plan to submit it next week. The topic of the project is not really typical for an exam and it's not supported by a company. But we like the topic and open source development a lot more than a project that is related to our company so we chose this one. It could be difficult to convince the university of this project. Maybe some feedback from the JavaFX community can help. So if you like this idea (or even if you have some critique) please give us a short review or feedback. We hope that we can confirm the benefit of this project this way.
