---
title: 'How to support Emojis (Part1)'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'The post shows how emojis can be supported in (JavaFX) applications'
featuredImage: sample-4
permalink: '2015/01/support-emojis/'
header:
  text: How to support Emojis
  image: sample
---
Mostly all mobile application support __emojis__ since some years. Later browsers and applications like twitter added global support for emojis. I think it's time to have a deeper look at this funny icons and how a software can make use of them.

![emoji_small](/assets/posts/guigarage-legacy/emoji_small.png)

## Emojis and unicode

Let's start with a fact that is a big benefit when working with emojis: Since 2010 all the emoji icons that you know from your favorite chat application are __standardized in the unicode standard__. The unicode standard defines more than 700 emojis. This means that each emoji is defined as a unicode character and has its unique unicode code:

![emoji](/assets/posts/guigarage-legacy/emoji-def-1024x558.png)

In the picture above the icons that you might know from apple devices are used to visualize the emojis. But unicode doesn't define the look of the icons. The unicode standard only defines the expression of the emoji as you can see in the official unicode documentation ([example](http://www.unicode.org/charts/PDF/U1F600.pdf)):

![uni-expression](/assets/posts/guigarage-legacy/uni-expression.png)

Because the emojis are defined as unicode chars the can be part of any String or character array that supports unicode. For example in Java the String object supports unicode and therefore a String can contain emoji chars. Now the big question is: How can we add a emoji character to a String? On a normal desktop PC we don't have emojis on the keyboard :(

There are different ways how you can type any unicode character with a keyboard ([windows tutorial](http://www.fileformat.info/tip/microsoft/enter_unicode.htm)). If you are a mac user there is a very fast and easy way:

The "Messages" app supports emojis like you know it from an iPhone. Here you can simple add emojis and copy&paste them in any other application. Here is a short video that shows the handling:

{% include posts/youtube.html id="ihVJn4C3SzI" %}

## Emojis in JavaFX

This successful test shows that the operation system supports emojis. Let's try Java as a next step. To do so I created a small and easy JavaFX application that contains only a simple TextField:

{% highlight java %}
public class EmojiTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textField = new TextField();
        primaryStage.setScene(new Scene(new StackPane(textField)));
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
{% endhighlight %}

Once the application is running we can do the same as before: Create a text with emoji chars, copy the text and past it into the JavaFX textfield:

![jfx-bad](/assets/posts/guigarage-legacy/jfx-bad.png)

As you can see in the image something went terrible wrong. We don't see any smileys in the textfield. Instead of the emojis some strange characters appeared. Does this mean that JavaFX doesn't support emojis?

![shocked](/assets/posts/guigarage-legacy/shocked.png)

## Emojis and fonts

You shouldn't be scared ;) JavaFX isn't the problem. The strange issue is based on the font of the textfield. Let's think about the functionality of a font: it interprets a character and draws a (vector based) icon for the char on the screen. In the example no specify font is defined for the textfield. Therefore the default font will be used that is "Lucida Grande" on a Mac ([see this post for more details](http://mail.openjdk.java.net/pipermail/openjfx-dev/2013-August/009912.html)). Lucida Grande doesn't contain a visual representation for the emoji unicode characters and therefore the strange icons will be shown. Th show emojis on the screen we need a font that supports them. One open source font that supports emojis is OpenSansEmoji. The font can be found at [github](https://github.com/MorbZ/OpenSansEmoji). Once you downloaded the font you can define it for your textfield [as described in an earlier post]({{ site.baseurl }}{% post_url 2014-10-01-integrate-custom-fonts-javafx-application-using-css %}):

{% highlight java %}
@font-face {
    font-family: 'OpenSansEmoji';
    src: url('OpenSansEmoji.ttf');
}

.text-field {
    -fx-font-family: OpenSansEmoji;
    -fx-font-size: 32;
}
{% endhighlight %}

Once this is done we can use emojis in JavaFX applications - but only on windows without problems. On Mac OS there is a bug in the os depended clipboard code. Therefore you can't paste any text with emojis in a JavaFX textfield. I will describe this problem in detail later. Let's add a emoji character directly in code to the textfield. This will work on any OS.

## Emojis and UTF-8

As you can see [on this page](http://apps.timwhitlock.info/emoji/tables/unicode) a emoji is defined by 4 bytes in UTF-8. In Java all Strings are defined in UTF-8 by default and therefore we need to define a emoji this way:

{% highlight java %}
byte[] emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
String emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
{% endhighlight %}

This code will create a string that contains the grinning emoji as you can see in the overview:

![utf-emoji](/assets/posts/guigarage-legacy/utf-emoji.png)

Once this is done the emoji will be shown in the textfield and can simply be copied to any other application that supports emojis:

![emoji-working](/assets/posts/guigarage-legacy/emoji-working.png)

The last posts ([like this one]({{ site.baseurl }}{% post_url 2015-01-19-concurrency-ui-toolkits-part-1 %})) I'm working on are very long and therefore I decided to split them in several smaller ones. In the next post I will discuss the problem on Mac. In addition I will show how emojis can be simply added to a text.
