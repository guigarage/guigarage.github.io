---
title: 'AquaFX dressed in Elements'
layout: post
author: claudine
categories: [AquaFX, JavaFX]
excerpt: "As you know, Apples Aqua-UI is held completely in blue. But what about UI-Controls, that need the users' special attention? Or what about Apps, that should fit into OS X with their look, but still need a corporate touch? During JavaOne we have introduced an approach to this matter."
featuredImage: java-6
permalink: '2013/10/aquafx-dressed-in-elements/'
header:
  text: AquaFX dressed in Elements
  image: sample
---
As you know, Apples Aqua-UI is held completely in blue. But what about UI-Controls, that need the users' special attention? Or what about Apps, that should fit into OS X with their look, but still need a corporate touch? During JavaOne we have introduced an approach to this matter.

With AquaFX Elements, we will provide a variation to usual AquaFX in three additional (more or less useful ;-) ) colors.

Elements enables the controls to be styled in red, green and a white variation of Aqua, representing the four classical elements water, fire, earth and air. Any colored highlight of the AquaFX Skin is changed by Elements in the chosen color-variation, so that e.g. a ProgressBar becomes pretty eye-catchy

![progress_elements-300x81](/assets/posts/guigarage-legacy/progress_elements-300x81.png)

and the focus-highlighting adapts to the element of choice.

![buttons_elements](/assets/posts/guigarage-legacy/buttons_elements.png)

Those variations are achieved by a simple extension of the AquaFX default stylesheet. The main styling remained in the aquafx.css and in addition to that, the coloring of all components are overridden by the element CSS files. So the elements are just an extension to the main stylesheet and do not cause structural changes. With this approach it is pretty easy to produce further color-variations.

![elements](/assets/posts/guigarage-legacy/elements.png)

Since AquaFX Elements is sitll in progress, it is not released yet. The information about availability will be posted here on GuiGarage.

## WANT SOME MORE ABOUT CSS?

I want to share my ideas and experiences about CSS in JavaFX beyond the [past talk on JavaOne 2013](http://de.slideshare.net/ClaudineZillmann/lets-get-wetbestpracticesforskinningjavafxcontrols). So, if you have seen something, you'd want me to write about, feel free to mail, twitter or comment :-)
