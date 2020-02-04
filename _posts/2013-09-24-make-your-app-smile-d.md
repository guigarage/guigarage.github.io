---
title: 'Make your app smile :D'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: "I think most of you know the emoji icons that are used in WhatsApp and other social apps to send smileys, space invader aliens and a smiling poo around the world. Let's have a look how emojis can be used in JavFX controls."
featuredImage: java-4
permalink: '2013/09/make-your-app-smile-d/'
header:
  text: Make your app smile
  image: sample
---
I think most of you know the emoji icons that are used in WhatsApp and other social apps to send smileys <img class="image-in-text" src="{{ site.baseurl }}/assets/posts/guigarage-legacy/1f603.png" width="24" height="24" />, space invader aliens <img class="image-in-text" src="{{ site.baseurl }}/assets/posts/guigarage-legacy/1f47e.png" width="24" height="24" /> and a smiling poo <img class="image-in-text" src="{{ site.baseurl }}/assets/posts/guigarage-legacy/1f4a9.png" width="24" height="24" /> around the world. With JavaFX 8 TextFlow is added to the set of default JavaFX controls. By using TextFlow, you can enrich text by images and font styles. By doing so, you can easily create rich text views:

![emoji](/assets/posts/guigarage-legacy/emoji.png)

We took this control and added Emoji support to it. All the Emojis are part of the unicode standard and can be defined by simple using a char. The alien emoji <img src="{{ site.baseurl }}/assets/posts/guigarage-legacy/1f47d.png" width="24" height="24" /> is defined by char 0xF47D for example.

You can find out more about the emoji text flow in our [JavaOne slides]({{ site.baseurl }}{% post_url 2013-09-24-lets-get-wet %}). Because this is still in progress we will post a more detailed description later.
