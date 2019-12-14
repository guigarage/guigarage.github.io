---
title: AeroFX
layout: article
hidden: true
permalink: '/aerofx/'
header:
  image: sample
  text: AeroFX
---

Like [AquaFX]({{ site.baseurl }}{% link pages/projects/aquafx.md %}) and [Flatter]({{ site.baseurl }}{% link pages/projects/flatter.md %}) AeroFX is an open source skin for JavaFX. AeroFX contains skins and style for all controls that are part of JavaFX to provide a native like look and feel on Windows. AeroFX was developed as a bachelor thesis by Matthias Meidinger.

{% include elements/warning-block.html text="AeroFX is not longer in active development. But since it is an open source project you can still find all sources and documentation online. Furher development in a fork as by providing pull request is always welcome." %}

## How to use AeroFX

The AerofFX lib is published to Maven central. Therefore you can easily add it to any Maven (or gradle) project:

{% highlight xml %}
<dependency>
  <groupId>org.aerofx</groupId>
  <artifactId>aerofx</artifactId>
  <version>0.2</version>
</dependency>
{% endhighlight %}

## Links for AeroFX

If you want to know more about AeroFX you should have a look at the following links:

* [AeroFX sources at Github](https://github.com/guigarage/aerofx)
* [German thesis of AeroFX](/assets/downloads/aerofx/thesis.pdf)
* [Introducing AeroFX]({{ site.baseurl }}{% post_url 2014-06-10-sneak-peek-aerofx %})
* [AeroFX - getting closer]({{ site.baseurl }}{% post_url 2014-06-17-aerofx-getting-closer %})
