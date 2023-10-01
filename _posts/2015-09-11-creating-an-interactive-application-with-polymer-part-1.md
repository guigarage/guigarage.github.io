---
title: 'Creating an interactive application with Polymer (Part 1)'
redirect_to: https://open-elements.com/posts/2015/09/11/creating-an-interactive-application-with-polymer-part-1/
layout: post
author: hendrik
categories: [Polymer, Web Frontends, WebComponents]
excerpt: 'In this post I will create an interactive web application based on Polymer 1.0 to show how data binding can be used and how a basic app might look like.'
featuredImage: java-6
permalink: '2015/09/creating-an-interactive-application-with-polymer-part-1/'
header:
  text: Interactive applications with Polymer
  image: sample
---
Some time ago I created [my first post about web components]({{ site.baseurl }}{% post_url 2014-11-12-first-steps-webcomponents %}) and today I want to continue this series. In the last month I played a lot with this new technology since at Canoo we think that this will be very important for web development in the future. Therefore [Michael Heinrichs](https://twitter.com/net0pyr) and I will give [a university session at Devoxx](http://cfp.devoxx.be/2015/talk/OVY-1576/Building_modern_web_UIs_with_Web_Components) this year and plan to offer web component and Polymer training at Canoo.

In this blog post I will create an interactive web application based on Google Polymer 1.0 to show how data binding can be used and how a basic Polymer application might look like. In this case I don't want to dive deep into the creation of custom components using Polymer but give a first introduction how you can design applications by using the Polymer paper elements.

## What is Polymer?

[Google Polymer](https://www.polymer-project.org/1.0/) is an open source toolkit that is build based on the [web component specification]({{ site.baseurl }}{% post_url 2015-02-15-use-webcomponents-today %}) and provides some additional features. In addition Polymer provides it's own UI component suite that is based on the [Material Design style by Google](https://www.google.com/design/spec/material-design/introduction.html).

![pol-arch](/assets/posts/guigarage-legacy/pol-arch-1024x348.png)

Based on Polymer and web components it's very easy to create a web application that is based on custom components instead falling in the `<div>` hell.

Here is a short example that shows how a Google maps web component can be integrated in any web page:

{% highlight html %}
<!-- Polyfill Web Components support for older browsers -->
<script src="components/webcomponentsjs/webcomponents-lite.min.js"></script>

<!-- Import element -->
<link rel="import" href="components/google-map/google-map.html">

<!-- Use element -->
<google-map latitude="37.790" longitude="-122.390"></google-map>
{% endhighlight %}

![map](/assets/posts/guigarage-legacy/map-300x287.png)

Polymer provides an [element catalog](https://elements.polymer-project.org) with documentation and many examples of all components that are part of the lib.

## Project setup

Let's start with a first project that uses Polymer and the Polymer paper components to create an application. This project only needs 2 file:

* a `index.html` that contains our application
* a `bower.json` that defines all external dependencies

Our final application should be a picture viewer that support some user interaction. I want to change the size of all images by using a slider. Here is a first sketch:

![app-design](/assets/posts/guigarage-legacy/app-design-1024x760.png)

To use the Polymer paper components we only need 1 dependency to `PolymerElements/paper-elements` in the bower file. If you are not familiar with [Bower](http://bower.io) you can find a short introduction [here]({{ site.baseurl }}{% post_url 2015-02-12-short-introduction-bower %}).

Once you have added the dependency your bower.json file like look like this:

{% highlight json %}
{
  "name": "polymer-interaction",
  "version": "1.0.0",
  "authors": [
    "Hendrik Ebbers <hendrik.ebbers@web.de>"
  ],
  "license": "MIT",
  "ignore": [
    "**/.*",
    "node_modules",
    "bower_components",
    "test",
    "tests"
  ],
  "dependencies": {
    "paper-elements": "PolymerElements/paper-elements#1.0.5"
  }
}
{% endhighlight %}

After calling `bower install` all dependencies are downloaded to the `bower_components` folder. This includes:

* `webcomponentsjs` - A [polyfill](https://en.wikipedia.org/wiki/Polyfill) that provides the features of the web components spec for browser version that doesn't support native support for the features
* `polymer` - the basic api of polymer.
* `paper-*` - all Polymer paper components like controls and layouts
* `iron-*` - basic web components like an icon view. The Polymer paper components are based on this basic components

Once this is done we can create the initial `index.html` file. Here we will start to provide the functionality of the web components spec by adding the polypill. To do so we include the script in our page:

{% highlight html %}
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="">

    <script src="bower_components/webcomponentsjs/webcomponents.js"></script>
</head>
<body>
</body>
</html>
{% endhighlight %}

Now we can start using the new features. We want to start with the import. In our example we need the Polymer API and some paper components. Therefore we add imports to all these dependencies:

{% highlight html %}
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="">

    <script src="bower_components/webcomponentsjs/webcomponents.js"></script>

    <link rel="import" href="bower_components/paper-header-panel/paper-header-panel.html">
    <link rel="import" href="bower_components/paper-toolbar/paper-toolbar.html">
    <link rel="import" href="bower_components/paper-slider/paper-slider.html">

</head>
<body>
</body>
</html>
{% endhighlight %}

The paper elements already depends on the Polymer API and therefore we don't need to add it explicitly.

## Starting with Polymer

Since Polymer is a framework for web components we will create a component that wraps our complete application view. To do so we need a html template and some JavaScript for the Polymer boostrap / configuration. A first simple example of such a component looks like this:

{% highlight html %}
<dom-module id="app-view">
    <template>
        CONTENT
    </template>
</dom-module>

<script>
    Polymer({
        is: "app-view"
    });
</script>
{% endhighlight %}

This code snippet defines a Polymer based web component that can be added to a html page by using the `<app-view>` tag (as defined in the polymer id). This web component only contains the static text "Content". Let's add this code snippet and the custom tag to our `index.html`:

{% highlight html %}
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="">

    <script src="bower_components/webcomponentsjs/webcomponents.js"></script>


    <link rel="import" href="bower_components/paper-header-panel/paper-header-panel.html">
    <link rel="import" href="bower_components/paper-toolbar/paper-toolbar.html">
    <link rel="import" href="bower_components/paper-slider/paper-slider.html">

</head>
<body>
<app-view></app-view>
</body>

<dom-module id="app-view">
    <template>
        CONTENT
    </template>
</dom-module>

<script>
    Polymer({
        is: "app-view"
    });
</script>

</html>
{% endhighlight %}

Here is a pic of the current state in chrome:

![content](/assets/posts/guigarage-legacy/content-1024x814.png)

As a Java developer I simply use IntelliJ to test my app. By opening the page from IntelliJ the IDE starts automatically a web server to provide the web app.

![intellij](/assets/posts/guigarage-legacy/intellij-1024x771.png)

If you don't use IntelliJ there are [several](https://developers.google.com/web/tools/polymer-starter-kit/) [other](https://www.npmjs.com/package/gulp-webserver) [ways](http://www.linuxjournal.com/content/tech-tip-really-simple-http-server-python) how you can test your app.

Let's add some first Polymer paper elements. Here I want to start with a [`paper-header-panel`](https://elements.polymer-project.org/elements/paper-header-panel) that defines a header and a content area. The header area is always on top and you can use it perfectly to display a toolbar. The content are will fill the rest of the page size and is scrollable by default.

![content-panel](/assets/posts/guigarage-legacy/content-panel.png)

In this header panel we want to add a toolbar with a title and a slider. For all these components Polymer paper offers ready to use web components. How this components can be used is documented in the [Polymer elements catalog](https://elements.polymer-project.org). Once this is done our code look like this:

{% highlight html %}
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="">

    <script src="bower_components/webcomponentsjs/webcomponents.js"></script>


    <link rel="import" href="bower_components/paper-header-panel/paper-header-panel.html">
    <link rel="import" href="bower_components/paper-toolbar/paper-toolbar.html">
    <link rel="import" href="bower_components/paper-slider/paper-slider.html">

</head>
<body>
<app-view></app-view>
</body>

<dom-module id="app-view">
    <template>
        <paper-header-panel class="main-wrapper">
            <paper-toolbar >
                <span class="title">Polymer interaction</span>
                <paper-slider></paper-slider>
            </paper-toolbar>
            <div>CONTENT</div>
        </paper-header-panel>
    </template>
</dom-module>

<script>
    Polymer({
        is: "app-view"
    });
</script>

</html>
{% endhighlight %}

In chrome we can already see the toolbar but the layout doesn't look that perfect:

![preview2](/assets/posts/guigarage-legacy/preview2-1024x814.png)

Polymer provides a [flexbox](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) based layout that can be simply modified by using custom attributes that are [provided by Polymer](https://elements.polymer-project.org/guides/flex-layout). I don't want to dive deep here in flexbox and Polymer layout (since this post is already very long) and simply show the final code:

{% highlight html %}
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="">

    <script src="bower_components/webcomponentsjs/webcomponents.js"></script>


    <link rel="import" href="bower_components/paper-header-panel/paper-header-panel.html">
    <link rel="import" href="bower_components/paper-toolbar/paper-toolbar.html">
    <link rel="import" href="bower_components/paper-slider/paper-slider.html">

</head>
<body unresolved class="fullbleed">
<app-view></app-view>
</body>

<dom-module id="app-view">
    <template>
        <paper-header-panel class="main-wrapper">

            <paper-toolbar >
                <span class="title">Polymer interaction</span>
                <paper-slider></paper-slider>
            </paper-toolbar>
            <div class="horizontal layout center-justified wrap">
               Content
            </div>
        </paper-header-panel>
    </template>
</dom-module>

<script>
    Polymer({
        is: "app-view"
    });
</script>

</html>
{% endhighlight %}

![preview3](/assets/posts/guigarage-legacy/preview3-1024x814.png)

Ok, as a next step I want to add some static images. Therefore I will use [lorempixel.com](http://lorempixel.com) that is a perfect service to get some random images. After adding some images to the content as shown in the following code snippet our application already look like some kind of image viewer:

{% highlight html %}
<div class="horizontal layout center-justified wrap">
  <img src="http://lorempixel.com/320/320/animals/1/">
  <img src="http://lorempixel.com/320/320/animals/2/">
  <img src="http://lorempixel.com/320/320/animals/3/">
  ...
</div>
{% endhighlight %}

![with-images](/assets/posts/guigarage-legacy/with-images-1024x656.png)

Thanks to the flex box layout that is used in Polymer the images are aligned in a responsive grid. When resizing the browser windows the column count in each row will fit to the width of the browser. In addition a scrollbar automatically appears if all images can't be shown on screen.

As a next step I want to finalize the styling of our application by adding some CSS. Here is the final html file:

{% highlight html %}
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="">

    <script src="bower_components/webcomponentsjs/webcomponents.js"></script>

    <link rel="import" href="bower_components/paper-header-panel/paper-header-panel.html">
    <link rel="import" href="bower_components/paper-toolbar/paper-toolbar.html">
    <link rel="import" href="bower_components/paper-slider/paper-slider.html">

</head>
<body>
<body unresolved class="fullbleed">
<app-view></app-view>
</body>
</body>

<dom-module id="app-view">
    <style>
        #toolbar {
            background: orange;
        }
        #slider {
            --paper-slider-pin-color: white;
            --paper-slider-knob-color: white;
        }
        .image {
            margin: 24px;
            border-color: white;
            border-width: 8px;
            border-style: solid;
        }
        .content-wrapper {
            background: darkslategray;
        }
    </style>
    <template>
        <paper-header-panel class="main-wrapper">

            <paper-toolbar id="toolbar">
                <span class="title">Polymer interaction</span>
                <paper-slider id="slider"></paper-slider>
            </paper-toolbar>
            <div class="horizontal layout center-justified wrap content-wrapper">
                <img class="image" src="http://lorempixel.com/320/320/animals/1/">
                <img class="image" src="http://lorempixel.com/320/320/animals/2/">
                <img class="image" src="http://lorempixel.com/320/320/animals/3/">
                <img class="image" src="http://lorempixel.com/320/320/animals/4/">
                <img class="image" src="http://lorempixel.com/320/320/animals/5/">
                <img class="image" src="http://lorempixel.com/320/320/animals/6/">
                <img class="image" src="http://lorempixel.com/320/320/animals/7/">
                <img class="image" src="http://lorempixel.com/320/320/animals/8/">
                <img class="image" src="http://lorempixel.com/320/320/animals/9/">
                <img class="image" src="http://lorempixel.com/320/320/animals/10/">
            </div>
        </paper-header-panel>
    </template>
</dom-module>

<script>
    Polymer({
        is: "app-view"
    });
</script>

</html>
{% endhighlight %}

![preview5](/assets/posts/guigarage-legacy/preview5-1024x577.png)

Ok, this will be enough for today :)

In the next post I will show how the Polymer properties and data binding can be used to create the needed interaction.
