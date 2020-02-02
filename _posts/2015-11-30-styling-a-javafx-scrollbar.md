---
title: 'Styling a JavaFX Scrollbar'
layout: post
author: hendrik
categories: [General, JavaFX]
excerpt: "Styling a scrollbar in JavaFX isn't that easy since it it composed of several internal nodes. Thanks to CSS all the internal nodes can be styled by using the defined style classes of the nodes."
featuredImage: java-2
permalink: '2015/11/styling-a-javafx-scrollbar/'
header:
  text: Styling a JavaFX Scrollbar
  image: sample
---
Styling a scrollbar in JavaFX isn't that easy since it it composed of several internal nodes. Thanks to CSS all the internal nodes can be styled by using the defined style classes of the nodes.

When adding a scrollbar to a scene it will contain the following internal / private nodes:

* A StackPane that defines the background of the track area that can be styled by using the  `track-background` style class
* A Button that defines the increment button of the scrollbar. The button can be styled by using the `increment-button` style class
* The increment arrow is a subnode of the button and it's defined as Region. It can be styled by using the `increment-arrow` style class
* A Button that defines the decrement button of the scrollbar. The button can be styled by using the `decrement-button` style class
* The decrement arrow is a subnode of the button and it's defined as Region. It can be styled by using the `decrement-arrow` style class
* A StackPane that defines the track area of the scrollbar. It that can be styled by using the  `track` style class
* A StackPane that defines the thumb of the scrollbar. It that can be styled by using the `thumb` style class

In addition the scrollbar supports 2 pseudo classes that can be used to define specific styles for a vertical and horizontal scrollbar. This pseudo classes are named "vertical" and "horizontal".

If you want to use a scrollpane there is this empty little area in the bottom right corner. You can style that area by using the `corner` style class.

![scroll](/assets/posts/guigarage-legacy/scroll-1024x595.png)
