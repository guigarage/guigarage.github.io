---
title: 'Material Design in JavaFX'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'The last weeks I worked on a prototype to create a like Material Design UI with JavaFX. This post introduces the theme.'
featuredImage: java-3
permalink: '2015/07/material-design-in-javafx/'
header:
  text: Material Design in JavaFX
  image: sample
---
The last weeks I worked on a prototype to create a [Material Design](https://www.google.com/design/spec/material-design/introduction.html) like UI with JavaFX. Google provides an awesome documentation about the Material Design and its guidelines. You can find an example [here](https://www.google.com/design/spec/layout/metrics-keylines.html#metrics-keylines-keylines-spacing).

I tried to recreate some of the Material Design layouts and components in JavaFX. Here I don't won't to recreate everything but create a theme that is inspired by material design. By doing so I can feel free to change any point of the Material Design definition. I think the project is at a point were I can show it to you to receive some feedback and maybe contribution. Here are some pictures of a demo application that is based on the theme:

![mat2](/assets/posts/guigarage-legacy/mat2-1024x819.png)

![mat1](/assets/posts/guigarage-legacy/mat1-711x1024.png)

The source of the project [can be found at GitHub](https://github.com/guigarage/sdkfx/tree/master). Currently this project provides more than the theme (and the name will change in future). The complete demo application is build without using any specific or complex layouts. By doing so it's very easy for a developer that isn't that good in layout and UI related stuff to create a cool looking application. How this can be done will be discussed in a future post.

The entry point of the demo application can be found at [`com.guigarage.sdk.demos.SimpleViewAppDemo1`](https://github.com/guigarage/sdkfx/blob/master/src/main/java/com/guigarage/sdk/demos/SimpleViewAppDemo1.java) and by starting this application you can play with the first demo. An overview of this application can be found in this video:

{% include posts/youtube.html id="3hnYnEm6sHA" %}
