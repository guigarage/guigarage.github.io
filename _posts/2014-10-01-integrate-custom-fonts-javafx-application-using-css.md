---
title: 'How to integrate custom fonts in your JavaFX application by using CSS'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'This CSS trick will show how you can change to font for a complete application or only a specific control by using CSS.'
featuredImage: java-2
permalink: '2014/10/integrate-custom-fonts-javafx-application-using-css/'
header:
  text: Custom fonts in your JavaFX application
  image: sample
---
This is one of the CSS tips that were part of my "Extreme Guimaker" talk at JavaOne this year and will show how you can change the font for a complete application or only a specific control by using CSS.

Before explaining the CSS solution I want to show a short example in Java code:

{% highlight java %}
Button b = new Button("Text");
b.setFont(new Font("Arial", 24));
{% endhighlight %}

In the code, the font of a button is set to "Arial" with a size of 24. All basic nodes in JavaFX that contains a text provide a font property that can be simply used to define a new font for the node. I don't think that this is a best practice because the font is an attribute that styles the application and therefore it should be separated from the application code.

## Set fonts by CSS

Fortunately JavaFX supports CSS and therefore we can extract the font specification from the Java code and add it to CSS. I won't discuss how IDs and style classes in CSS work in this post (If you not familiar with CSS you should have a look in [my book]({{ site.baseurl }}{% link pages/mastering-javafx-controls.md %})). The font of a node can be defined by using the `-fx-font-*` attributes in CSS. You can find a documentation of these attributes [here](http://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html#typefont). Here is an example that defines the font for a button:

{% highlight css %}
#my-button {
  -fx-font-family: "Arial";
  -fx-font-size: 24;
}{% endhighlight %}

If you want to define a global font for all controls in your application you can simply set the font to the `.text` style class. The Text shape in JavaFX contains this pseudo class by default and all nodes that render a text on the screen use the Text shape internally. Here is the css rule that will set the font for a complete application:

{% highlight css %}
.text {
    -fx-font-family: "Asap";
}
{% endhighlight %}

## Adding custom fonts

In the examples "Arial" is used as the custom font. Normally you can assume that this font will be installed on a client system. But sometimes you want to use a custom font to create a unique UI. In the following example, I want to use the Roboto font that is the official font of [Googles Material Design](http://www.google.com/design/spec/style/typography.html#typography-roboto). Normally this font won't be installed on a client system. So if you define the font by CSS and a customer will run the application without installing the specific font on the OS JavaFX will select a system font as a fallback and the cool UI of the app is broken. But thankfully there is a good solution for this problem. Since Java 8 JavaFX supports the `@font-face` rule that can be used to add fonts. As a first step, the font file must be added to the application. As a best practice the file should be added to the resources folder:

![font](/assets/posts/guigarage-legacy/font.png)

Once this is done the font can be defined in CSS by using the `@font-face` rule:

{% highlight css %}
@font-face {
    font-family: 'Roboto';
    src: url('Roboto-Medium.ttf');
}

.text {
    -fx-font-family: "Roboto";
}
{% endhighlight %}

Now the font will be used in our application even if it isn't installed on the OS:

![font-loaded](/assets/posts/guigarage-legacy/font-loaded.png){:class="image-two-third-width"}

## Update

As I learned today the shown code isn't working in Java versions >= 1.8u60. Starting with this version the attribute “font-family” is ignored and you have to use the real name of the TTF.

If you want to use the font “Birds of Paradis” that is contained in the file `demo.ttf`, for example, you have to use this CSS file:

{% highlight css %}
@font-face {
  src: url(“/ui/font/demo.ttf”);
}

.label {
  -fx-font-family: “Birds of Paradise”;
}
{% endhighlight %}
