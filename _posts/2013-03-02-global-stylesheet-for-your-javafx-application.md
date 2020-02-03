---
title: 'Global Stylesheet for your JavaFX Application'
layout: post
author: hendrik
categories: [General, JavaFX]
excerpt: 'There is a way to set a global Stylesheet to all JavaFX Scenes in your app. By using the JavaFX 8 class StyleManager you can define the default CSS files.'
featuredImage: css-3
permalink: '2013/03/global-stylesheet-for-your-javafx-application/'
header:
  text: Global Stylesheet for JavaFX
  image: sample
---
You can style your JavaFX [Scene](http://docs.oracle.com/javafx/2/api/javafx/scene/Scene.html) by CSS as you can read [here]({{ site.baseurl }}{% post_url 2013-01-16-this-is-for-the-native-ones %}) and [here](http://docs.oracle.com/javafx/2/css_tutorial/jfxpub-css_tutorial.htm). All this examples show how to apply a specific Stylesheet to one Scene by using

{% highlight java %}
myScene.getStylesheets().add("path/to/custom.css");
{% endhighlight %}

Inside the SceneGraph of this Scene all Nodes will use the defined Stylesheets. But while your Application grows you will normally have more than one Scene. Each [Window](http://docs.oracle.com/javafx/2/api/javafx/stage/Window.html) in JavaFX holds its own Scene. Even if your Application only uses one [Stage](http://docs.oracle.com/javafx/2/api/javafx/stage/Stage.html) you will normally have more than one window while every [ContextMenu](http://docs.oracle.com/javafx/2/api/javafx/scene/control/ContextMenu.html) is Window. So if you use custom ContextMenus or a [ChoiceBox](http://docs.oracle.com/javafx/2/api/javafx/scene/control/ChoiceBox.html) inside your application this Components will technically be displayed in a separate Window with a new Scene and SceneGraph. But with the code mentioned above only "myScene" will use the custom Stylesheet. The Scene of the ContextMenu will not be affected by this. One trick here is to set the Stylesheet manually to the Scene of the Window:

{% highlight java %}
myContextMenu.getScene().getStylesheets().add("path/to/custom.css");
{% endhighlight %}

But this is only a bad workaround and you won't do this for every ContextMenu. When you use a ChoiceBox you even can't access the Scene of the popup because this is defined in private methods of the ChoiceBoxSkin.

But there is a way to set a global Stylesheet to all Scenes. By using the JavaFX 8 class StyleManager you can define the default CSS files. The class uses the singleton pattern and can easily accepted. The following code will add a CSS file to all Stylesheets of all Scenes:

{% highlight java %}
StyleManager.getInstance().addUserAgentStylesheet(AQUA_CSS_NAME);
{% endhighlight %}

Currently there is one bug with this. The default Stylesheet (currently [caspian]({{ site.baseurl }}{% post_url 2013-01-16-this-is-for-the-native-ones %})) is defined inside the StyleManger, too. But the default will not be set until a first Node is created. When adding a additional user defined Stylesheet a Exception is thrown. So to avoid problems you have to set the default CSS before adding a custom one. This can currently only done by calling a private API:

{% highlight java %}
PlatformImpl.setDefaultPlatformUserAgentStylesheet();
StyleManager.getInstance().addUserAgentStylesheet(AQUA_CSS_NAME);
{% endhighlight %}

I will open a issue at [http://javafx-jira.kenai.com](http://javafx-jira.kenai.com) about this behavior.

## Addition

With the help of Jonathan Giles I found a better way without using private APIs. You can easily set the default stylesheet by using "Application.setUserAgentStylesheet(String url)". If you use null as parameter value the default stylesheet (currently caspian) will be used. So here is the code without using a private API:

{% highlight java %}
Application.setUserAgentStylesheet(null);
StyleManager.getInstance().addUserAgentStylesheet(AQUA_CSS_NAME);
{% endhighlight %}
