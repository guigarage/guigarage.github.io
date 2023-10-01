---
title: 'Cool JavaFX UIs in less time? Use projection!'
redirect_to: https://open-elements.com/posts/2015/09/06/cool-javafx-uis-in-less-time-use-projection/
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'This post shows how you can create UIs by using metadata and the projection pattern in JavaFX. By doing so all developers can create cool UIs.'
featuredImage: java-4
permalink: '2015/09/cool-javafx-uis-in-less-time-use-projection/'
header:
  text: Projection with JavaFX
  image: sample
---
Some weeks ago I blogged about [my first Material Design approach in JavaFX]({{ site.baseurl }}{% post_url 2015-07-10-material-design-in-javafx %}). But the project contains more than just some new styles for JavaFX. In the project I tried some technics to create good looking application without spending much time in styling. Therefore I created some basic Java layout and styles that will automatically be used for specific data types. It's like projecting the raw data in a nice UI. The basic idea of this approach evolved after some discussions with [Dierk König](https://twitter.com/mittie). In addition Twitter Bootstrap already offers some styles and layouts that I reused here.

## Project your data to the view

Let's have a deeper look how this small approach is working. In the Material Design demo I created a basic interface called [Media](https://github.com/guigarage/sdkfx/blob/master/src/main/java/com/guigarage/sdk/util/Media.java). This interface defines a basic data structures that can hold an image, a title and a description:

{% highlight java %}
public interface Media {

    StringProperty titleProperty();

    StringProperty descriptionProperty();

    ObjectProperty<Image> imageProperty();
}
{% endhighlight %}

When creating your own custom data model you can simply create complete classes that implement the interface and export some basic metadata based of the class by using the interface. The project includes a basic implementation of the interface called [DefaultMedia](https://github.com/guigarage/sdkfx/blob/master/src/main/java/com/guigarage/sdk/util/DefaultMedia.java).

Let's think about a possible visual presentation of this interface. When having a look at Twitter Bootstrap you can find an example. Bootstrap provides the [media object](http://getbootstrap.com/components/#media) that can simply be used to render media data:

![bootstrap-media](/assets/posts/guigarage-legacy/bootstrap-media-1024x403.png)

I already created a JavaFX list cell that specifies a layout that can be used for media objects. You can find a detailed description [here]({{ site.baseurl }}{% post_url 2014-09-30-enrich-list-ui-using-medialistcell %}). Based on this I created the MediaList that is completely ready to render media and don't need to be configured anymore.

{% highlight java %}
MediaList<Media> list = new MediaList<>();
list.getItems().add(new DefaultMedia("Test01", "This is the description", image1));
list.getItems().add(new DefaultMedia("Test02", "This is the description", image2));
list.getItems().add(new DefaultMedia("Test03", "This is the description", image3));
list.getItems().add(new DefaultMedia("Test04", "This is the description", image4));
list.getItems().add(new DefaultMedia("Test05", "This is the description", image5));
{% endhighlight %}

By using the MediaList class you simply create a list like this one:

![media-list](/assets/posts/guigarage-legacy/media-list-1024x432.png)

But this isn't the only view in the material design example that is based on the Media definition. Next to the list the user info panel uses a media object internally:

![user-info](/assets/posts/guigarage-legacy/user-info-1024x393.png)

The next one is the image view that contains a description of the shown image:

![image-view](/assets/posts/guigarage-legacy/image-view-1024x938.png)

As you can see there are several examples how a simple data definition like the shown Media interface can be rendered on screen. Currently I'm working on a table that support the Media object.

![table-view](/assets/posts/guigarage-legacy/table-view-1024x804.png)

In the table implementation I experiment how such a control can easily be configured. So stay tuned ;)

## Why should we use such a concept?

I think there are several benefits in the media example. If you want to visualize some data that will match in the given format you don't need to think about the visualization. If you want to show a list, a table or just one element there are several ready to use containers / controls. Each of this controls defines a different projection of the data to the screen. Developers that aren't familiar with custom controls or styling of application can use this concepts to create good looking applications from scratch.

The Media interface is just a beginning here and is still very limited in the way how it can be used and displayed. In addition it must fit to your data model. But as said I think that this is only the start. You can create a form layout based on the same concepts. Let's think you have a FormData interface that defines the data that should be displayed in a form and adds some metadate like the label texts and so on. I'm currently thinking about concepts for complex scenarios and how such interfaces might look like. If you want to have a first look you can check the [form classes](https://github.com/guigarage/sdkfx/tree/master/src/main/java/com/guigarage/sdk/form) in my material design example.
