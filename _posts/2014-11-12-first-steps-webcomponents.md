---
title: 'First steps with WebComponents'
layout: post
author: hendrik
categories: [Polymer, Web Frontends, WebComponents]
excerpt: 'WebComponents are custom components for the web. I tried the new spec and created my first simple WebComponent with HTML, JavaScript and CSS'
featuredImage: sample-10
permalink: '2014/11/first-steps-webcomponents/'
header:
  text: First steps with WebComponents
  image: sample
---
I think one of the big new HTML features in the next years will be WebComponents. By using WebComponents developers can create fully-featured DOM elements as defined in the [web component spec](http://w3c.github.io/webcomponents/spec/custom/). In short this means that you can create your own HTML tags. If you want to add an avatar view to your web app you and use web components you can define a avatar component and then use the `<avatar>` tag in your HTML file. To use web components today you can use one of the following libraries:

* [bosonic](http://bosonic.github.io)
* [polymer](https://www.polymer-project.org)
* [x-tag](http://x-tags.org)

I chose polymer to create my first web component and in this post I want to show you what I did to create a reusable custom HTML tag. Most likely this isn't a best practice tutorial because I currently starting to learn about this topic :)

## Project setup

As a first step I downloaded the polymer lib. To do so I used [bower](http://bower.io) that is a package manager for web development. With bower you can define all the library dependencies of a web application like you can do with Maven for Java apps.

After you have installed bower on your system you need to execute the following commands in the folder of your web project:

{% highlight shell %}
bower init
bower install --save Polymer/polymer
{% endhighlight %}

This will create a `bower.json` file in your project and adds polymer as dependency. My file looks like this:

{% highlight json %}
{
    "name": "polymer-test",
    "version": "0.0.0",
    "description": "A polymer playground",
    "license": "MIT",
    "keywords": [
        "polymer",
        "web-components"
    ],
    "ignore": [
        "**/.*",
        "node_modules",
        "bower_components"
    ],
    "dependencies": {
        "polymer": "Polymer/polymer#^0.4.1"
    }
}
{% endhighlight %}

To download all dependencies you need to call

{% highlight shell %}
bower install
{% endhighlight %}

This command will download all defined dependencies and install the in the `bower_components` folder in your project. When committing your project to git you can simply ignore this folder.

If you don't want to use bower you can download and integrate the polymer lib by hand.

As a next step you can create a `index.html` to test your custom components and a folder that will contain the components. Once this is done your application folder might look like this:

![folder-structure](/assets/posts/guigarage-legacy/folder-structure.png)

## General structure of a WebComponent

As said a web component is a custom control for web pages. Top define a component we only need one HTML file. As in other [languages or UT toolkits]({{ site.baseurl }}{% post_url 2012-11-17-custom-ui-controls-with-javafx-part-1 %}) a web component is composed of a layout definition, a style and controller logic. In the web world this means HTML (layout), CSS (style) and JavaScript (controller). By using polymer all 3 parts can combined in one file. Here is a skeleton for the file:

{% highlight html %}
<link rel="import" href="../bower_components/polymer/polymer.html">

<polymer-element name="lorem-ipsum" attributes="paragraphs">

    <template>
    // contains the layout of the component in HTML
    </template>

    <script>
    // contains the logic / control of the component in JavaScript
    </script>


    <style>
    // contains the style of the component in CSS
    </style>

</polymer-element>
{% endhighlight %}

As you can see the code starts with an import. Each custom component must import the `polymer.html` file. Thanks to bower this is already in the `bower_components` folder and can simply be imported. The `polymer-element` tag describes the web component. In the tag there are 3 more tags that describes the layout (template), the style (style) and the controller of the component (script). The `polymer-element` tag has some attributes that describes the name of our component and its attributes.

To create a small web component you don't need to define all 3 inner tags but some basics are needed to display the component in a browser.

## Defining the first component

As a start we want to create a minimal web component that will only print an "A" on screen. To do so we create the "Simple-A.html" file in the components folder and add the following content:

{% highlight html %}
<link rel="import" href="../bower_components/polymer/polymer.html">

<polymer-element name="simple-a">

    <template>
        <p>A</p>
    </template>

    <script>
        Polymer({});
    </script>

</polymer-element>
{% endhighlight %}

As far as I know is this the minimum definition that you need to define a component. In the template a paragraph that contains an "A" is defined. This is default HTML and if we don't want to create a reusable component we could write this directly in an HTML file. In the script section the Polymer({}); call registers the component so it's recognized by the browser. Once this is done the component can be used in any HTML file to render the "A" paragraph on screen:

![rendered-936x1024](/assets/posts/guigarage-legacy/rendered-936x1024.png)

## Use the component

To include the custom web component in a HTML page you need to import it. In addition the polymer platform lib must included to the page. Here is the code of a HTML page that includes everything and adds the "A" paragraph component several times to the body of the page:

{% highlight html %}
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <script src="bower_components/platform/platform.js"></script>

    <link rel="import" href="components/SimpleA.html">
</head>
<body>
<div>
    <simple-a></simple-a>
    <simple-a></simple-a>
    <simple-a></simple-a>
    <simple-a></simple-a>
</div>
</body>
</html>
{% endhighlight %}

As you can see in the code the html files that defines the custom component is imported in the web page. Once this is done the custom tag `<simple-a>` can be used in the web page. When having a look at the web developer tools of safari you can see that the A tags are now part of the page:

![view-in-console](/assets/posts/guigarage-legacy/view-in-console.png)

## Browser support

I tested my HTML file on Safari, Chrome and Firefox. Safari and Firefox work fine but on Chrome I don't see anything. The web console shows a "Cross-Origin Resource Sharing" so maybe it will work when running the page on a web server instead of just opening the file from the filesystem. I'm not a web expert so maybe Chrome has the correct behavior here. I will check this later and blog about it in my next web component post. After all by doing the most simply try and opening the HTML file with the browsers that re installed on my system this is the result of the browser check:

![browser-check](/assets/posts/guigarage-legacy/browser-check.png)

## Conclusion

It was very easy to create the first web component. As a next step I will try attributes for the components and some JavaScript logic. I will block about it as fast as possible.
