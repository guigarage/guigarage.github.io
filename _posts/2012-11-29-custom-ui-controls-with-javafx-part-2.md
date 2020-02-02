---
title: 'Custom UI Controls with JavaFX (Part 2)'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'One thing I often done is Swing was customization of components and the creation of new components types. With the last release of JavaFX you can easily create custom controls with this new UI toolkit, too. This post gives a first overview about the JavaFX APIs to create custom controls.'
featuredImage: java-7
permalink: '2012/11/custom-ui-controls-with-javafx-part-2/'
header:
  text: Custom UI Controls with JavaFX
  image: sample
---
I started a [series of JavaFX tutorials last week]({{ site.baseurl }}{% post_url 2012-11-17-custom-ui-controls-with-javafx-part-1 %}). In this second part I will explain how to layout custom controls and measure their bounds. Like in my last post I will try to show the differences and benefits of JavaFX compared to Swing.

## Floating Point Bounds

JavaFX uses a Scene Graph as the structure for all graphical nodes. This graph supports transformations like scaling or rotation for all its children in a very easy way.

In Swing it is easy to translate a panel with all its children in x or y direction. In JavaFX you can now translate, scale or rotate a parent node with all children according to the x, y an z axes. But before we take a look at this transformations I will show you the simple way of setting bounds in JavaFX. There are three important methodes that every Node in JavaFX provides:

{% highlight Java %}
void relocate(double x, double y)

void resize(double width, double height)

void resizeRelocate(double x, double y, double width, double height)
{% endhighlight %}

This methods are equivalent to the following ones that a provided by JComponent:

{% highlight Java %}
void setLocation(int x, int y)

void setSize(int width, int height)

void setBounds(int x, int y, int width, int height)
{% endhighlight %}

The difference between them is that JavaFX uses "double" as parameter type. The methods of JComponent have it's historical background in AWT. I think at the time of implementation no one thought about rectangles that were arranged between pixels and were drawn with antialiasing. The JavaFX methods provides this functionality and once transformation comes into play everyone should understand why this is essential.

## Let's do a basic layout

When layouting a swing UI you normally do not call the methods mentioned above in your code. Layout managers do all the work for you. In most cases you have a container like the JPanel with LayoutManager like BorderLayout that layouts all children of the container within it's bounds.

JavaFX don't know LayoutManagers. All layouting is done directly by the containers. The basic layout container is called Pane and when looking at the type hierarchy of this class you will find containers with different layout algorithms like the VBox or HBox.

![layout1](/assets/posts/guigarage-legacy/layout1.png)

You can read about the different layout containers and their special scopes [here](http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm). When creation custom panes there are a few more points to take care of. I will talk about this in a later post.

## Preparing custom control for layout

A LayoutManager in Swing computes the bounds of all children by three properties:

{% highlight Java %}
Dimension getMaximumSize()

Dimension getPreferredSize()

Dimension getMinimumSize()
{% endhighlight %}

A LayoutManager can use this properties of every child to compute its bounds inside the layout. When using FlowLayout for example every child has exactly its preferred dimension. So when you created your custom JComponent you needed to override this methods. This mechanism has one big problem: You can not calculate a dynamic aspect ratio of the children. Ever asked yourself why JLabel do not support automatic word wrapping? I think the leak of aspect ratio calculation in swing is the reason for this limitation. You can only calculate the preferred bounds of a component but you can not calculate the preferred width dependent to its height by using the default Swing workflow and APIs.

With JavaFX you can do this calculations. Each Node in JavaFX provides the following methodes:

{% highlight Java %}
double computeMinHeight(double width)

double computeMinWidth(double height)

double computeMaxHeight(double width)

double computeMaxWidth(double height)

double computePrefHeight(double width)

double computePrefWidth(double height)
{% endhighlight %}

By overriding this methods you can control how your custom control will be layouted in a pane. At the first moment everything looks right and easy. You can calculate the components height by its width and vice versa. But to use this calculations JavaFX needs a hint how the bias of a component is working. This is the point where the content bias comes into play. With this property every node can define if its width depends on the height or in opposite way. The current value is defined by this method:

{% highlight Java %}
Orientation getContentBias()
{% endhighlight %}

If the node is resizable and its height depends on its width the method should return Orientation.HORIZONTAL. If its width depends on its height return Orientation.VERTICAL. If your custom component do not need a width/height dependency you can even return null for its content bias. In this case -1 will always be passed to all methodes (computePrefWidth, etc.). Now your calculations will not depend on this value and we will have the same behavior as in Swing. The component do not use aspect ratio.

So it is no problem anymore to provide a word wrap in a Textcomponent when using JavaFX. I will explain the usage of this methodes with a more easy example. Let's assume that we need a component that always has a surface area of 24 pixels.

![layout2](/assets/posts/guigarage-legacy/layout2.png)

With swing we would only have a few different ways/dimensions to create such a component:

{% highlight Java %}
Dimension getPreferredSize() {
   return new Dimension(24,1);
   //All other different versions
   //return new Dimension(1,24);
   //return new Dimension(2,12);
   //return new Dimension(12,2);
   //return new Dimension(8,3);
   //return new Dimension(3,8);
   //return new Dimension(6,4);
   //return new Dimension(4,6);
}
{% endhighlight %}

In reality there is a unlimited count of rectangles that have a area of 24 pixels. For example a rectangle with a width of 4,7 and a height of 5,105... has exact this area.

![layout3](/assets/posts/guigarage-legacy/layout3.png)

With JavaFX and the extended ways to calculate the dimension of components and the use of double values we can create all of this rectangles (this is only limited by the range of double values). First of all we need to implement all this different methods for dimension calculation:

{% highlight Java %}
@Override
protected double computeMaxHeight(double width) {
   if (width > 0) {
      return Double.MAX_VALUE;
   } else {
      return 24.0 / width;
   }
}

@Override
protected double computeMaxWidth(double height) {
   if (height > 0) {
      return Double.MAX_VALUE;
   } else {
      return 24.0 / height;
   }
}

@Override
protected double computeMinHeight(double width) {
   if (width > 0) {
      return Double.MIN_VALUE;
   } else {
      return 24.0 / width;
   }
}

@Override
protected double computeMinWidth(double height) {
   if (height > 0) {
      return Double.MIN_VALUE;
   } else {
      return 24.0 / height;
   }
}

@Override
protected double computePrefHeight(double width) {
   if (width > 0) {
      return 4;
   } else {
      return 24.0 / width;
   }
}

@Override
protected double computePrefWidth(double height) {
   if (height > 0) {
      return 6;
   } else {
      return 24.0 / height;
   }
}
{% endhighlight %}

All methodes can handle -1 as parameter and returns a default value in that case.

Here is a movie showing the layout with Orientation.HORIZONTAL. Because a 24 pixel area would be very small. So I changed it to 240.000 for this movie:

{% include posts/vimeo.html id="54478790" %}

And here is the movie with Orientation.VERTICAL:

{% include posts/vimeo.html id="54479737" %}

As mentioned in my last post each Custom Control needs a Skin. To do things right you have to override all compute...-methods in your Skin and not in the Control class. Only getContentBias() needs to be overridden in the Control.

## Useful hints

If your component should has a constant dimension you can easily set all properties instead of overriding all the methods:

{% highlight Java %}
myControl.setPrefWidth(4);
myControl.setPrefHeight(6);

//myControl.setPrefSize(4, 6);
{% endhighlight %}

By default Control.USE_COMPUTED_SIZE is set for this properties. This indicates JavaFX to calculate the dimension by using mecanisms mentioned above.

Another hint is to set Control.USE_PREF_SIZE to max/min size instead of overriding all methods. This will use the preferred size for min/max size:

{% highlight Java %}
myControl.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
{% endhighlight %}

Once a component is layouted you can access it's current bounds with:

{% highlight Java %}
myControl.getBoundsInLocal()
{% endhighlight %}

You can find more information about JavaFX layout [here](http://amyfowlersblog.wordpress.com/2011/06/02/javafx2-0-layout-a-class-tour/).

### But I need a control that is not resizable

The JavaFX layout mechanism even supports this feature. Every Node has this method:

{% highlight Java %}
boolean isResizable()
{% endhighlight %}

When this method returns false all (official) layout panes will not resize your control. In this case the layout only handles the location of your control.

## Transformation

As I mentioned before Nodes support transformation. In special translation, rotation and scaling are currently supported. Very important is to not equalize transformation with layouting. A transform in JavaFX changes the visual bounds of a layouted component. Every component needs to be layouted as descripted above. Once this is done the control can be transformed by a mouse event, for example. Here is a short example that rotates a node by mouse over event:

{% highlight Java %}
myControl.setOnMouseEntered(new EventHandler() {

   @Override
   public void handle(MouseEvent arg0) {
      setRotate(15.0);
   }
});

myControl.setOnMouseExited(new EventHandler() {

   @Override
   public void handle(MouseEvent arg0) {
      setRotate(0);
   }
});
{% endhighlight %}

To measure the current visual bounds of a components once it is transformed we have the additional method getBoundsInParent(). Other than getBoundsInLocal() this method returns the bounds which include the transforms of the component.

![layout4](/assets/posts/guigarage-legacy/layout4.png)

You can find more informations about transformations in JavaFX [here](http://docs.oracle.com/javafx/2/transformations/jfxpub-transformations.htm).

You can download the example I used for this post [here](https://github.com/downloads/guigarage/gridfx-demos/layout-demo.zip).
