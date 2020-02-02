---
title: 'JavaFX and CSS'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'One of the cool features of JavaFX is the CSS support. By using CSS you can simply style a single control or a complete application. This post gives a first introduction to the CSS usage and API in JavaFX'
featuredImage: java-4
permalink: '2016/02/javafx-and-css/'
header:
  text: JavaFX and CSS
  image: sample
---
One of the cool features of JavaFX is the CSS support. By using CSS you can simply style a single control or a complete application. The CSS support in JavaFX is based on the W3C CSS version 2.1. There are some minor differences that can be found in the [JavaFX CSS documentation](http://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html) that is the best source to check a specific property or value type when using CSS in JavaFX.

![css](/assets/posts/guigarage-legacy/css-1024x570.png)

## How to define CSS rules in JavaFX

Normally CSS rules are defined in a CSS file called stylesheet. A CSS file normally uses the `*.css` extension like `style.css`. A stylesheet can contain one or several CSS rules. Here is an content for a stylesheet that contains only one CSS rule:

{% highlight css %}
.button {
  -fx-background-color: blue;
}
{% endhighlight %}

If you are familiar with the general CSS syntax you can see that defining a CSS rules in JavaFX is exactly the same as for the web. Only the name of the CSS property looks different.

In JavaFX a stylesheet can be applied to the scene graph or to any node that is part of a scene graph. A stylesheet that is applied to the scene graph will affect all nodes that are part of the scene graph. If the stylesheet is applied to only a specific node it will affect this node and all its children (recursively). Several stylesheets can be applied to the scene graph or a single node. Internally the applied stylesheets are organized in a list. The following code snippet shows how you can set a stylesheet for a scene graph or a node:

{% highlight java %}
// load the stylesheet
String style = getClass().getResource("style.css").toExternalForm();

// apply stylesheet to the scene graph
myScene.getStylesheets().addAll(style);

// apply stylesheet to a node
parentPanel.getStylesheets().addAll(style);
{% endhighlight %}

Next to stylesheets that are defined in CSS files JavaFX supports inline stylesheets. With this feature you can define a CSS rules directly in your Java code as a String. In this case you don't need to define a CSS selector.

{% highlight java %}
button.setStyle("-fx-background-color: green;");
{% endhighlight %}

Using inline styles isn't best practice and destroys the benefit of separation between code and style. I would recommend to use this feature only for visual debugging and testing.

## CSS selectors in JavaFX

To apply a CSS rule to a JavaFX node the selector of the rule must match to the node. JavaFX provides the possibility to define an `id`, `style classes` and `pseudo classes` for any node. All this metadata can be used to select a specific or several nodes based on a CSS selector at runtime. JavaFX provides some methods to define the metadata. Let's start with the `id`:

As defined in the specification a node can have one id that is specified by a string value:

{% highlight java %}
mySaveButton.setId("my-save-button");
{% endhighlight %}

A CSS rule with a selector that matches to the button can look like this:

{% highlight css %}
/* The # sign in the selector defines an id string */
#my-save-button {
-fx-background-color: blue;
}
{% endhighlight %}

Next to the id a JavaFX can contain several style classes. Like the id all style classes are defined by strings. Internally all nodes contains a list for the style classes. A new style class can easily be added to a node like shown in this code snippet:

{% highlight java %}
button.getStyleClass().add("toolbar-button");
{% endhighlight %}

Most of the basic JavaFX controls define one or several style classes by default. For example each Button has already defined the `.button` style class. By doing so the style of all buttons can easily be changed by this CSS rule:

{% highlight css %}
.button {
  -fx-background-color: red;
  -fx-text-fill: white;
  -fx-background-radius: 16px;
}
{% endhighlight %}

Once the stylesheet is added to the scene the buttons in your application will look like this:

![css](/assets/posts/guigarage-legacy/styled-button.png)

As you can see in the CSS definition all properties that are used until know are defined by the "-fx-" prefix. This is specific to JavaFX and defines a change against the CSS how you use it for styling in the web. Since most of the properties that you know from the web are supported all of them start with a `-fx-`. By doing so you could create a global stylesheet that styles your web frontend and JavaFX frontend.

Based on this post I added a [second post]({{ site.baseurl }}{% post_url 2016-02-09-javafx-and-css-pseudo-classes %}) the shows how you can use pseudo classes in JavaFX.
