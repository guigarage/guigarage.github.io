---
title: 'Fun with gestures'
layout: post
author: hendrik
categories: [General]
excerpt: 'Apple added a listener based API for multitouch gestures to their eawt package. So you can use pinching and rotation on a multitouch trackpad.'
featuredImage: java-3
permalink: '2011/07/fun-with-gestures/'
header:
  text: Fun with gestures
  image: sample
---
While working on the next JGrid release I found the cool gestures API from Apple. Since "Java 6 Update 2" Apple added a listener based API for multitouch gestures to their eawt package. So you can use pinching and rotation on a multitouch trackpad. I wrote a short java webstart demo:

![gestures](/assets/posts/guigarage-legacy/gestures.png)

Currently you can start this demo only on a Mac with Java 6 Update 2 or later. You can find the sources in the JGrid repository @ [https://code.google.com/p/jgrid/](https://code.google.com/p/jgrid/). I will try to write a wrapper API around the Apple classes. Then you can add the listeners to your code and they will automatically be activated on a Mac.
