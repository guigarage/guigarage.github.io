---
title: 'Update for the native ones'
layout: post
author: claudine
categories: [AquaFX, General, JavaFX]
excerpt: 'TODO'
featuredImage: sample-11
permalink: '2013/03/update-for-the-native-ones/'
header:
  text: Update for the native ones
  image: sample
---
Since [my project]({{ site.baseurl }}{% post_url 2013-01-16-this-is-for-the-native-ones %}) was accepted, I was keen on starting with the first components. Now I want to show you what is the result.

## Remember the shutdown dialog?

As Jonathan suggested, the whole project is realized in JavaFX 8.

In the first step, the proof-of-concept dialog had to be migrated to be up-to-date again. Pretty soon, I could welcome my dialog in JavaFX8:

![shutdown_fx81](/assets/posts/guigarage-legacy/shutdown_fx81.png)

All components look like mountain lion again.

## You want more?

What about those neat components:

TextField:

![textfield-300x79](/assets/posts/guigarage-legacy/textfield-300x79.png)

PasswordField:

![pw-300x67](/assets/posts/guigarage-legacy/pw-300x67.png)

TextArea:

![textarea-300x91](/assets/posts/guigarage-legacy/textarea-300x91.png)

RadioButton:

![radio.png](/assets/posts/guigarage-legacy/radio.png.png)

CheckBox:

![check-242x300](/assets/posts/guigarage-legacy/check-242x300.png)

ChoiceBox:

![choice](/assets/posts/guigarage-legacy/choice.png)

ToggleButton:

![toggle-300x53](/assets/posts/guigarage-legacy/toggle-300x53.png)

ScrollBar:

![scrollbar1-300x51](/assets/posts/guigarage-legacy/scrollbar1-300x51.png)

![scrollbar2-300x49](/assets/posts/guigarage-legacy/scrollbar2-300x49.png)

Just for the moment, everything is crafted in a little dialog, where I have an overview over all results untill now.

The good news in this context is, that all of this stuff works on retina- and non-retina macs and looks the same:

![demodialog1-687x1024](/assets/posts/guigarage-legacy/demodialog1-687x1024.png)

## The development and different struggles

There are some points, which had to be decided before I really could start and which are not absolutely clear.

Currently all CSS-styling is overriding caspian.css, as it is the default style. But we all look forward to modena replacing caspian for JavaFX 8. This may cause some trouble in future concerning new controls, which do not have Mac OS-Styling yet. For this reason, it is not that clear, how to apply mac_os.css to an application. Hendrik describes the different variations using the StyleManager in [his latest blogpost]({{ site.baseurl }}{% post_url 2013-03-02-global-stylesheet-for-your-javafx-application %}). Thoughtless usage of style management also causes trouble with PopUp-components.

The main approach in styling the components is, using CSS as much as possible. Everything, that is not possible in CSS e.g. multiple effects on one component or animations is implemented in Aqua*Skins, which simply override the skin of the affected control. In this way, effects and animations are no problem.

One open struggle is the behavior of the hover in default-buttons. In MacOS default-buttons are blinking from a light blue to a darker blue. If the mouse hovers into the button there is a tiny little flashing until the next repaint, which shows the button in his non-default state. Just some milliseconds, but very distracting.. ;-) Until now, there is no way of disabling the hover-effect in my skin class, which is worth a Jira-Issue.

## What comes next?

Right now I am working on finishing the PopUp of ChoiceBox an ScrollBar-animation. More components will to come up soon.
