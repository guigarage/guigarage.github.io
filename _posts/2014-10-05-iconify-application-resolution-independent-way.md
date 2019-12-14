---
title: 'Iconify your application the resolution independent way'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'Often icons are very important for a good UI. They will create a modern and professional look and will help the user to understand the meaning of actions.'
featuredImage: sample-3
permalink: '2014/10/iconify-application-resolution-independent-way/'
header:
  text: Iconify your application
  image: sample
---
Often icons are very important for a good UI. They will create a modern and professional look and will help the user to understand the meaning of actions. Especially on small screens icons are important. For a mobile application you won't create a toolbar with 5 actions that are described by a long text. In the following screenshot 3 actions are defined in the toolbar by using only icon:

![Bildschirmfoto](/assets/posts/guigarage-legacy/Bildschirmfoto-2014-09-16-um-21.21.04-744x1024.png)

I think that mostly all users will understand that these actions defines a back action and 2 actions to change the volume.

Today applications will be developed for different hardware and therefore for different screen resolutions. Let's think about the retina displays. Here you will have a 4x bigger resolution than on normal screens. In an ideal case an applications supports this devices and will fit its size and content. But that means, that all controls will have a 4x bigger size. Often you will use images for your icons. But resizing them will create a pixelated view. For a Mac retina display you can use the ["@2"-syntax](https://developer.apple.com/library/ios/qa/qa1686/_index.html) to provide retina images. But sometimes you want to scale an icon even bigger (Maybe you want to scale it in an animation, for example). Thats why you should use vector based icons:

![pvv-300x141](/assets/posts/guigarage-legacy/pvv-300x141.png)

Ok, that sounds reasonable but where can we find vector based icons and how can we integrate them in JavaFX?

For me the best resource for vector based icons is [Font Awesome](http://fortawesome.github.io/Font-Awesome/) that is a font which contains over 450 vector based icons. Here is a short extract:

![awe](/assets/posts/guigarage-legacy/video-player-icons.png)

Because it is a font it can simply be integrated to any JavaFX application ([see this post]({{ site.baseurl }}{% post_url 2014-10-01-integrate-custom-fonts-javafx-application-using-css %})). Once the font is assigned to a control you can define an icon by setting the text of the control. Here a special unicode character need to be set as the text. The following example describes how to set the pen icon to a button:

{% highlight java %}
button.setText('\uf040' + "");  
// \uf040 is the unicode char for the icon as defines here: http://fortawesome.github.io/Font-Awesome/icon/pencil/
{% endhighlight %}

Once you now this trick you still need to do some steps to display a vector based icon:

* add the font to the resources folder
* define the font in CSS by using `@font-face`
* set the font to the specific control (in CSS)
* define the specific icon in java code

Especially the last point isn't what I want. Icons are part of the style of an application and therefore it would be perfect if we could define them in CSS. There fore I created a new Skin for the JavaFX Button called `IconifiedButtonSkin`. By using the skin the handling of vector based icons in your JavaFX application is much easier. To use the skin you only need one line of Java code:

{% highlight java %}
IconifiedButtonSkin.addStyle(myButton);
{% endhighlight %}

Once this is done the new skin for the button is set. This automatically contains the following steps:

* add the font to the resources folder
* define the font in CSS by using `@font-face`
* set the font to the specific control in CSS by adding the `iconified-button` style class

The last think that need to be done is setting the text of the button to define the wanted icon. Thankfully the new skin provides an additional CSS attribute that can be used. By using the -fx-icon-text attribute you can define the wanted icon directly in CSS:

{% highlight css %}
#myButton {
    -fx-icon-text: "\uf0a9";
}
{% endhighlight %}

The `IconifiedButtonSkin` class is part of the `ui-basics` module that will be found at Maven Central the next days:

{% highlight xml %}
<dependency>
  <groupId>com.guigarage</groupId>
  <artifactId>ui-basics</artifactId>
  <version>X.Y</version>
</dependency>
{% endhighlight %}

## further development

I plan to add a special CSS Converter in Java to provide a better definition of the icons in CSS. Wouldn't it be cool if you could do the following:

{% highlight css %}
#myButton {
    -fx-icon: "fa fa-pencil";
}
{% endhighlight %}

Once this is done it would be cool to support more fonts like [ionicons](http://ionicons.com) by default.
