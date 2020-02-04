---
title: 'JavaFX and CSS: Pseudo Classes'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'Pseudo classes are a really cool CSS feature that you can use to define styles for different states of a component. This post gives an overview how pseudo classes can be used in JavaFX'
featuredImage: css-1
permalink: '2016/02/javafx-and-css-pseudo-classes/'
header:
  text: Pseudo Classes
  image: sample
---
In [my last post]({{ site.baseurl }}{% post_url 2016-02-07-javafx-and-css %}) I gave a quick overview of the how CSS can be used in JavaFX by styling nodes based on an id or style classes. Today I want to explain what a pseudo class is and how you can use it to style dynamic controls.

## CSS Pseudo Classes

In CSS a pseudo class is used to define a specific state of a node. In general a node can have `0-n` pseudo classes. Let's think about a regular JavaFX Button and how it is rendered. When thinking about this the first visualization that comes into mind is this one:

![Button](/assets/posts/guigarage-legacy/Button.png)

The button that is shown on the picture has a defined state:

* The button is not disabled
* The button is not armed
* The button is focused
* The mouse cursor is not over the button (no hover)

Based on this definition the state of the button instance is defined what ends in a specific visualization. Next to the visualization that is shown on the image a button can be rendered in several different ways that reflect the current state of the button. Here is an overview of some buttons that are rendered in different states:

![button state](/assets/posts/guigarage-legacy/button-state-1024x88.png)

Since all JavaFX controls are styled by CSS all the different states must be specified in CSS. To do so you need pseudo classes to define specific styles for states of the control.

## How to use a pseudo class in CSS

Since all JavaFX controls already support several pseudo classes you can simply define a CSS rule for a specific pseudo class. Let's say you want to change the font color of a button whenever the mouse cursor is over the button. For this special state of a node the `hover` pseudo class is defined in JavaFX. Therefore you can simply define the following CSS rule:

{% highlight css %}
#my-button:hover {
    -fx-text-fill: orange;
}
{% endhighlight %}

As you can see a pseudo class can simply be added to a CSS selector by using the ":" as a prefix for the pseudo class. In this example the rule is defined for a specific button that is defined by an id (`my-button`) but you can use a pseudo class in combination with every CSS selector. In the last post I described how a selection can be done by using a style class. By doing so you can simply define a specific CSS rule for all buttons by using the following selector:

{% highlight css %}
.button:hover {
    -fx-text-fill: orange;
}
{% endhighlight %}

Next to the `hover` pseudo class JavaFX controls support several other pseudo classes. An overview can be found in the [JavaFX CSS documentation](http://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html).

## How to define new pseudo classes

As you have seen the basic JavaFX controls already support several pseudo classes to style specific states of a control. But maybe that is not enough for your current project. If you extend a control and add new states or create a custom control you need to define your own pseudo classes if you want to support styling with CSS. To define a pseudo class JavaFX contains the class `PseudoClass` that can be used to create a new pseudo class definition for your control. Here it's best practice to use a static instance directly in your control class:

{% highlight java %}
public class MyCustomButton extends Button {
  
  private static PseudoClass EXPLODING_PSEUDO_CLASS = PseudoClass.getPseudoClass("exploding");
  
}
{% endhighlight %}

The pseudo class that is created in the code snippet can be used in CSS selectors by its given name ("exploding"). As a next step we need to active and deactivate the pseudo class. To do so it's best practice to create a boolean property in you control that reflects the state of the pseudo class. By doing so you can simply mutate and check the state in Java by using the property. Internally the state of the pseudo class changes whenever the value of the property changes. To set the state of a pseudo class the JavaFX `Node` class provides the method `pseudoClassStateChanged(PseudoClass pseudoClass, boolean active)`. The following code snippet shows how you can create a control that defines a new state by using a property and automatically updates the pseudo class based on the property value:

{% highlight java %}
public class MyCustomButton extends Button {
  
  private static PseudoClass EXPLODING_PSEUDO_CLASS = PseudoClass.getPseudoClass("exploding");
  
  BooleanProperty exploding = new BooleanPropertyBase(false) {
    public void invalidated() {
      pseudoClassStateChanged(EXPLODING_PSEUDO_CLASS, get());
    }

    @Override public Object getBean() {
      return MyControl.this;
    }
    
    @Override public String getName() {
      return "exploding";
    }
  };
  
  public void setExploding(boolean exploding) {
    this.exploding.set(exploding);
  }
}
{% endhighlight %}

The code snippet shows the best practice how it's defined in the JavaFX documentation. If this code is too long for you or you don't like to override the property class you could get the same result by using a listener:

{% highlight java %}
public class MyCustomButton extends Button {
  
  private static PseudoClass EXPLODING_PSEUDO_CLASS = PseudoClass.getPseudoClass("exploding");
  
  BooleanProperty exploding;
  
  public MyCustomButton() {
    exploding = new SimpleBooleanProperty(false);
    exploding.addListener(e -> pseudoClassStateChanged(EXPLODING_PSEUDO_CLASS, exploding.get()));

    getStyleClasses().add("exploding-button");
  }
  
  public void setExploding(boolean exploding) {
    this.exploding.set(exploding);
  }
  
  public boolean isExploding() {
    return this.exploding.get();
  }
}
{% endhighlight %}

As you can see in the last code snippet I added the style class `exploding-button` to the costume control. By doing so I can now define the following CSS Rule that will effect my new control type if the `exploding` style class is active:

{% highlight java %}
.exploding-button:exploding {
  -fx-background-color: red;
}
{% endhighlight %}

## Conclusion

As you can see pseudo classes adds an important feature to JavaFX and CSS. By using pseudo classes you can define custom styles for specific states of a control and simply reflect the state of a control in it's visualization.

As a next step I will blog about the different property types that are supported by CSS in JavaFX.
