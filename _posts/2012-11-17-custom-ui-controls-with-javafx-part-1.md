---
title: 'Custom UI Controls with JavaFX - Part 1'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'One thing I often done is Swing was customization of components and the creation of new components types. With the last release of JavaFX you can easily create custom controls with this new UI toolkit, too. This post gives a first overview about the JavaFX APIs to create custom controls.'
featuredImage: java-3
permalink: '2012/11/custom-ui-controls-with-javafx-part-1/'
header:
  text: Custom UI Controls with JavaFX
  image: sample
---
One thing I often done is Swing was customization of components and the creation of new components types. One example for this is the [JGrid]({{ site.baseurl }}{% post_url 2011-07-15-jgrid %}). Since JavaFX was out I wanted to [port the JGrid to it]({{ site.baseurl }}{% post_url 2012-11-14-gridfx-is-hosted-at-github %}). After some experiments and bad prototyps I think I found the right way to do it. The talks from [Gerrit Grunwald](http://harmoniccode.blogspot.de) and [Jonathan Giles](http://jonathangiles.net/blog/) at [JavaOne](http://www.oracle.com/javaone/index.html) helped me really a lot to do so. The records of this talks is online ([link](https://oracleus.activeevents.com/connect/sessionDetail.ww?SESSION_ID=2425&amp;tclass=popup), [link](https://oracleus.activeevents.com/connect/sessionDetail.ww?SESSION_ID=4726&amp;tclass=popup)) so I would advise everybody who is interest in this topic to spend some time and watch them.

## Getting started

Every UI component in JavaFX is composed by a __control__, a __skin__ and a __behavior__. In an ideal case there is a css part to.

![custom-components1](/assets/posts/guigarage-legacy/custom-components1.png)

Best way to start is by creating a new control class that extends `javafx.scene.control.Control`. This class is basically comparable to `JComponent`. It should hold the properties of the component and acts as the main class for it because instances of this class will later created in your application code and added to the UI tree.

{% highlight Java %}
MyCustomControl myControl = new MyCustomControl();
panel.getChildren().add(myControl);
{% endhighlight %}

When programming swing components the right way you put everything that depends on the visualization or user interaction into a UI class (see `LabelUI` for example). JavaFX goes one step further and provides the skin class for all visualization and layout related code and the behavior class for all user interaction.

![custom-components2](/assets/posts/guigarage-legacy/custom-components2.png)

To do so in JavaFX you need to know how the classes depends on each other. Here is a short chart that shows the relations between them:

![custom-componens3](/assets/posts/guigarage-legacy/custom-componens3.png)

## Creating the Behavior

If your component only visualizes data and has no interaction it 's quite simple to create a behavior. Therefore you only need to extend the `com.sun.javafx.scene.control.behavior.BehaviorBase`.

{% highlight Java %}
public class MyCustomControlBehavior extends BehaviorBase {

   public MyCustomControlBehavior(MyCustomControl control) {
      super(control);
   }
}
{% endhighlight %}

Some of you may be confused when seeing the package of BehaviorBase. At the moment this is a __private API__ and normally you should not use this classes in your code but the guys at Oracle know about this problem and will provide the __BehaviorBase as a public API with JavaFX 8__. So best practice is to use the private class now and refactor the import once Java 8 is out.

## Creating the Skin

After the behavior class is created we can take a look at the skin. Your skin class will mostly extend `com.sun.javafx.scene.control.skin.BaseSkin` and create a new behavior for your control. Your code normally should look like this:

{% highlight Java %}
public class MyCustomControlSkin extends SkinBase{

   public MyCustomControlSkin(MyCustomControl control) {
      super(control, new MyCustomControlBehavior(control));
   }
}
{% endhighlight %}

As you can see the BaseSkin is a private API as well. It will also changed to public with Java 8.

## Creating the Control

The last class we will need is the control. First we create a simple empty class:

{% highlight Java %}
public class MyCustomControl extends Control {

   public MyCustomControl() {
   }
}
{% endhighlight %}

At this point we have a leak in the dependencies of our three classes. The skin knows about the behavior and control. Here everything looks right. However in application code you will simply create a new control and use it as I showed earlier. The problem is that the control class do not know anything about the skin or behavior. This was one of the biggest pitfalls I was confronted with while learning JavaFX.

## Putting it together

What first looks as a great problem is part of the potency JavaFX provides. With JavaFX it should be very easy to create different visualisation (skins) for controls. For this part you can customize the look of components by css. Because the skin is the main part of this look it has to defined by css, too. So instead of creating a skin object for the control by your own you only define the skin class that should be used for your control. The instanciation and everything else is automatically done by the JavaFX APIs. To do so you have to bind your control to a css class.

Firts off all you have to create a new css file in your project. I think best practice is to use the same package as the controls has and but a css file under src/main/resource:

![custom-components4](/assets/posts/guigarage-legacy/custom-components4.png)

Inside the css you have to specify a new selector for your component and add the skin as a property to it. This will for example look like this:

{% highlight css %}
.custom-control {
   -fx-skin: "com.guigarage.customcontrol.MyCustomControlSkin";
}
{% endhighlight %}

Once you have created the css you have to define it in your control. Therefore you have to configure the path to the css file and the selector of your component:

{% highlight Java %}
public class MyCustomControl extends Control {

   public MyCustomControl() {
      getStyleClass().add("custom-control");
   }

   @Override
   protected String getUserAgentStylesheet() {
      return MyCustomControl.class.getResource("customcontrol.css").toExternalForm();
   }
}
{% endhighlight %}

After all this stuff is done correctly JavaFX will create a skin instance for your control. You do not need to take care about this instantiation or the dependency mechanism. At this point I want to thank [Jonathan Giles](http://jonathangiles.net/blog/) who taked some time to code the css integration for gridfx together with me and explained me all the mechanisms and benefits.

## Access the Skin and Behavior

Normally there is no need to access the skin or the behavior from within the controller. But if you have the need to do you can access them this way:

![custom-controls5](/assets/posts/guigarage-legacy/custom-controls5.png)

Because controler.getSkin() receives a javafx.scene.control.Skin and not a SkinBase you have to cast it if you need the Behavior:

{% highlight Java %}
((SkinBase)getSkin()).getBehavior();
{% endhighlight %}

## Workaround for css haters

For some of you this mechanism seems to be a little to oversized. Maybe you only need a specific control once in your application and you do not plan to skin it with css and doing all this stuff. For this use case there is a nice workaround in the JavaFX API. You can ignore all the css stuff and set the skin class to your control in code:

{% highlight Java %}
public class MyCustomControl extends Control {
   public MyCustomControl() {
      setSkinClassName(MyCustomControlSkin.class.getName());
   }
}
{% endhighlight %}

The benefit of this workflow is that refactoring of packages or classnames don't break your code and you don't need a extra css file. On the other side there is a great handicap. You can't use css defined skins in any extension of this control. __I think that every public API like gridfx should use the css way__. In some internal use cases the hard coded way could be faster.

## Conclusion

Now we created a control, a skin and a behavior that are working fine and can be added to your UI tree. But like in swing when simply extending the JComponent you don't see anything on screen. So the next step is to style and layout your component. I will handle this topic in my next post.

If you want to look at some code of existing custom components check out [jgridfx](https://github.com/guigarage/gridfx) or [JFXtras](https://github.com/JFXtras/jfxtras-labs). At jgridfx the following files match with this article:

* `com.guigarage.fx.grid.GridView` (control)
* `com.guigarage.fx.grid.skin.GridViewSkin` (skin)
* `com.guigarage.fx.grid.behavior.GridViewBehavior` (behavior)
* `/src/main/resources/com/guigarage/fx/grid/gridview.css` (css)
