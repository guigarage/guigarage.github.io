---
title: 'gridfx & pagination'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'TODO'
featuredImage: sample-5
permalink: '2012/11/gridfx-pagination/'
header:
  text: gridfx & pagination
  image: sample
---
The [gridfx]({{ site.baseurl }}{% post_url 2012-11-14-gridfx-is-hosted-at-github %}) component has currently one big problem. For every item inside the model a new cell is created inside the scene graph. So if you have 100.000 items in the item list of a the grid it will contain 100.000 cells. If every cell contains 4 nodes the scene graph will contain 400.000 nodes. In swing you used [renderer classes](http://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html#renderer) to avoid this problem. But JavaFX work different. Instead of using only one renderer instance and painting this again and again JavaFX uses cell factories to create new instances for every cell. The trick is, that only the cells you currently see on screen are added to the scene graph. Old cells will be dropped from the scene graph when the view is scrolling and recycled or reused later. This logic is implemented in com.sun.javafx.scene.control.skin.VirtualFlow and used by the JavaFX [ListView](http://docs.oracle.com/javafx/2/ui_controls/list-view.htm) for example. I will implement this behavior to gridfx as soon as possible.

![pagination1](/assets/posts/guigarage-legacy/pagination1.png)

While playing with other JavaFX features and APIs I found another solution for this problem. With the [Pagination](http://docs.oracle.com/javafx/2/ui_controls/pagination.htm) class you can split your UI in different pages. An example for this method is the iOS home screen. Here all your apps are split in different pages and you can flip through them.

![Pagnation2](/assets/posts/guigarage-legacy/Pagnation2.png)

I tried to imitate this behavior for the GridView and will share my experience here.

A list of items should be cut in little peaces so that each part fits exactly into one page of the Pagination Control and the page count depends on the item count. Thanks to the [JavaFX property binding](http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm) this plan wasn't to hard to implement. I created a helper class ([GridPaginationHelper.java](https://github.com/guigarage/gridfx/blob/master/src/main/java/com/guigarage/fx/grid/util/GridPaginationHelper.java)) that takes care of all the bindings and calculations. You only need a cell factory for the GridView. There are some problems with Pagination so that not everything is working as it should. Will later talk about this. First a short movie that shows the current state:

{% include posts/vimeo.html id="54046675" %}

You can see how the Pagination animates through all pages and the page count is computed every time the size of the cells is changing. You can see the problems that the implementation has at this stage, too. Sometimes the navigation bar flickers or disappear for a moment. This a very strange behavior and I can't find the cause. Then I can not access the size of the page inside the Pagination Control. At the moment I'm working with the following hack:

{% highlight Java %}
pageHeight = pagination.getHeight() - 64;
{% endhighlight %}

There is another limitation of the Pagination Control. You can not deactivate the animation. When the cell size changes the current page index may change while showing the same cells. At this moment the animation should be deactivated.

Maybe I have done something wrong or don't understand the complete usage of Pagination but I think that behavior I want to copy is used in a lot of modern applications:

![pagination-demo1-150x150](/assets/posts/guigarage-legacy/pagination-demo1.jpg)

![pagination-demo2-150x150](/assets/posts/guigarage-legacy/pagination-demo2.png)

![pagination-demo3-150x150](/assets/posts/guigarage-legacy/pagination-demo3.png)

I will open some tickets at Jira for this issues. Maybe someone has I idea how to do this a better way in the meantime. I'm open for better and new ideas.
