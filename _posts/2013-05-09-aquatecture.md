---
title: 'Aquatecture'
layout: post
author: hendrik
categories: [AquaFX, General, JavaFX]
excerpt: 'TODO'
featuredImage: sample-6
permalink: '2013/05/aquatecture/'
header:
  text: Aquatecture
  image: sample
---
While Claudine is working on all the needed Skins for AquaFX I will use the time to show you some of the internal API stuff of [AquaFX]({{ site.baseurl }}{% link pages/projects/aquafx.md %}) that Claudine has developed. Once the project is released you can easily style your complete application by just calling

{% highlight Java %}
AquaFx.style();
{% endhighlight %}

This will set the CSS Styles for all controls that are supported by AquaFX for you. Once this is done your application should look like a native one.

While working a lot with Aqua and reading all the Apple style guides, etc. we noticed that simply setting one style for every control type is not enough to provide a great Aqua Look and Feel for JavaFX. Many native applications use the default controls like Buttons in different variations. For example you can have smaller controls or buttons with a more rounded border than the default one. Here is good overview about the different size variations of components:

![sizeVariant](/assets/posts/guigarage-legacy/sizeVariant.png)

This Image is taken from the [Apple documentation of the native Swing Look&Feel for Mac OS](https://developer.apple.com/library/mac/#technotes/tn2007/tn2196.html).

When using the Aqua L&F in Swing you can change the behavior of a component by adding a String as a clientProperty to the component:

{% highlight Java %}
component.putClientProperty("JComponent.sizeVariant", "mini");
{% endhighlight %}

I think with AquaFX Claudine found a better way to provide this different styles by simply providing Enums for all different designs. So you can change the size of the component by simply using this enum:

{% highlight Java %}
public enum ControlSizeVariant implements StyleDefinition {
REGULAR,
SMALL,
MINI;
@Override public String getStyleName() {
...
}
...
}
{% endhighlight %}

Now you can skin a button as a small control with just a method-call:

{% highlight Java %}
AquaFx.resizeControl(myButton, ControlSizeVariant.SMALL);
{% endhighlight %}

For doing so you only need the AquaFX class that is a static [facade](http://en.wikipedia.org/wiki/Facade_pattern) for all features AquaFX will provide. So a normal developer will only need to now this class and its' internal methods to skin and configure an application with AquaFX.

But the rezise as shown above is not the only skin variation you can define. With AquaFX you can easily mix different variations, too. The Facade offers a method for that:

{% highlight Java %}
AquaFx.skin(myButton, ButtonType.ROUND_RECT, ControlSizeVariant.SMALL);
{% endhighlight %}

Cause all Enums implement the StyleDefinition interface you can commit as many as you want for a control. AquaFX will try to create the best look for you. Here is a example of some controls with a custom styling:

![custom](/assets/posts/guigarage-legacy/custom.jpg)

At the moment some combinations of StyleDefinition don't make sense and I gave Claudine the hint to think about a [fluent approach](http://en.wikipedia.org/wiki/Facade_pattern) for skinning this variations. So maybe this is not the final API. But I think it's at a good point to share this ideas and features with you. Hope you like it.
