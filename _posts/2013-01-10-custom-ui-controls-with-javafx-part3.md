---
title: 'Custom UI Controls with JavaFX (Part3)'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'TODO'
featuredImage: sample-6
permalink: '2013/01/custom-ui-controls-with-javafx-part3/'
header:
  text: Custom UI Controls with JavaFX
  image: sample
---
In this post I will explain the basic JavaFX Property API because most of the planed future tutorials will depend on Properties. I will first explain the old Swing way to better understand the idea of the new API and all problems that are solved by it.

## Properties in Swing

In Swing every Component is a [JavaBean](http://docs.oracle.com/javase/tutorial/javabeans/) and its fields (bean properties) are accessible by getters and setters. If you want to change the text of a button you have to call the setter method:

{% highlight Java %}
jbutton.setText("click me");

You can receive the current text by calling the getter method:

String currentText = jbutton.getText();
{% endhighlight %}

With this methods you can easily modify and control your user interface in a static way. For example you can receive and check all entered data by calling the required getter methods on your components once the save button is pressed.

But in the last years user experience has changed. Data should be validated in the moment it has been entered by the user and the background color of a application should change on the fly while the user is dropping the mouse over the colorchooser. All this things can be done with Swing and [PropertyChangeSupport](http://docs.oracle.com/javase/6/docs/api/java/beans/PropertyChangeSupport.html). In a well coded Swing Control every change of a bean property fires a PropertyChangeEvent to all its listeners. The official Swing components offer this feature for all properties. Here is a short example that shows `PropertyChangeSupport` in a custom JComponent:

{% highlight Java %}
public class JCustomComponent extends JComponent {

   private int volume;

   public int getVolume() {
      return volume;
   }

   public void setVolume(int volume) {
      int oldValue = this.volume;
      this.volume = volume;
      firePropertyChange("volume", oldValue, this.volume);
   }
}
{% endhighlight %}

It's quite simple to access this feature outside of the component:

{% highlight Java %}
JCustomComponent customComponent = new JCustomComponent();
customComponent.addPropertyChangeListener("volume", new PropertyChangeListener() {

   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      System.out.println("Volume changed to " + evt.getNewValue());
   }
});
{% endhighlight %}

The problem is that all this stuff produces a lot of redundant code and do not last for modern and big applications.

## Glue code and BeansBinding

Most of the time `PropertyChangeSupport` is used in Swing applications to connect the view to a data model. By doing so a lot of code looks like this:

{% highlight Java %}
//example code from http://goo.gl/T6Iqg
slider.addValueListener(new ChangeListener() {
   public void stateChanged(ChangeEvent e) {
      selectedObject.setFoo(slider.getValue());
   }
});

selectedObject.addPropertyChangeListener(new PropertyChangeListener() {
   public void propertyChanged(PropertyChangeEvent e) {
      if (e.getPropertyName() == null || "foo".equals(e.getPropertyName())) {
         slider.setValue(selectedObject.getFoo());
      }
   }
});
{% endhighlight %}

This code is really painful and vulnerable to (copy & paste) errors. Thanks to the BeansBinding API the modern Swing developer do not need this glue code and simply bind properties. The API allow to bind two different properties with each other so that all changes are automatically adopted by the opposite side. Here is a short example of a binding:

{% highlight Java %}
//example code from http://goo.gl/KmGz1
Property slideValue = BeanProperty.create("value");
Property tintValue = BeanProperty.create("tint");
Binding tintBinding = Bindings.createAutoBinding(UpdateStrategy.READ, tintSlider, slideValue, tintedPanel, tintValue);
tintBinding.bind();
tintSlider.setValue(0);
{% endhighlight %}

With the API you can create one way or bidirectional bindings. The difference is that in a one way binding only one side is a observer. In a bidirectional binding every change on any side is adopted by the opposite side.

If you are interested in BeansBinding with Swing I suggest you to [read](http://today.java.net/pub/a/2008/03/20/synchronizing-properties-with-beans-binding.html) [this](http://weblogs.java.net/blog/zixle/archive/2006/05/ease_of_swing_d.html) [articles](https://blogs.oracle.com/geertjan/entry/beans_binding_via_the_road).

## JavaFX Properties

JavaFX includes the [`javafx.beans.property.Property`](http://docs.oracle.com/javafx/2/api/javafx/beans/property/Property.html) Interface that extend property handling and binding with some great features and a very simple but powerful API. All JavaFX controls use the property API to grant access to their fields. Normally next to the getter & setter methods there is a new method to access the property. Here is a example for the "double cellWidth" attribute of [GridView]({{ site.baseurl }}{% post_url 2012-11-29-gridfx-is-moving-forward %}):

{% highlight Java %}
private DoubleProperty cellWidth;

public final DoubleProperty cellWidthProperty() {
   if (cellWidth == null) {
      cellWidth = new SimpleDoubleProperty(64);
   }
   return cellWidth;
}

public void setCellWidth(double value) {
   cellWidthProperty().set(value);
}

public double getCellWidth() {
   return cellWidth == null ? 64.0 : cellWidth.get();
}
{% endhighlight %}

As you can see there is no "double cellWidth" field in the code. Instead of this the attribute is wrapped in a Property. JavaFX offers a set of basic property classes for primitive datatypes like String or double. All this basic implementations are part of the package [`javafx.beans.property.*`](http://docs.oracle.com/javafx/2/api/javafx/beans/property/package-summary.html).

The getter and setter methods work directly with the Property instance and set or request the current value from the property. Next to all this `Simple*Property` classes there are some special implementations like read only implementations that can be used if you want to prevent your field from external changes. In this case only removing the setter-method is not enough because you can access the Property instance. It's recommend to use `ReadOnly**Property` classes (like [`ReadOnlyDoubleProperty`](http://docs.oracle.com/javafx/2/api/javafx/beans/property/ReadOnlyDoubleProperty.html)) in this case.

## The benefit of Properties

By using the above described design for bean properties in JavaFX you will get a lot of pros in your code. First of all JavaFX properties offer support for [`ChangeListener`](http://docs.oracle.com/javafx/2/api/javafx/beans/value/ChangeListener.html). So you can add listeners to every property:

{% highlight Java %}
SimpleStringProperty textProp = new SimpleStringProperty();
textProp.addListener(new ChangeListener<String>() {

   @Override
   public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
      System.out.println("Value changed: " + oldValue + " -> " + newValue);
   }
});

Slider mySlider = new Slider();
mySlider.valueProperty().addListener(new ChangeListener<Number>() {

   @Override
   public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
      System.out.println("Value changed!");
   }
});
{% endhighlight %}

The usage of `ChangeListener` in JavaFX is equivalent to PropertyChangeSupport in Swing. But in my eyes there are some benefits. The code looks much cleaner and it's very easy to add a listener to one specific field / property. In Swing you have to add the field name as a String parameter and produce plenty of refactoring risk. Next to the ChangeLister you can register [`InvalidationListener`](http://docs.oracle.com/javafx/2/api/javafx/beans/InvalidationListener.html) to your properties. You can read more about the difference between this two listener types [here](http://blog.netopyr.com/2012/02/08/when-to-use-a-changelistener-or-an-invalidationlistener/).

## Let's bind this stuff

Another and in my opinion the best feature of JavaFX properties is the binding function. For this the Property interface offers the following methods:

{% highlight Java %}
void bind(javafx.beans.value.ObservableValue other);
  
void unbind();
  
boolean isBound();
  
void bindBidirectional(javafx.beans.property.Property other);
  
void unbindBidirectional(javafx.beans.property.Property other);
{% endhighlight %}

By using this methods you can create bindings between JavaFX properties very easy and with much cleaner code than in Swing. In the following example the value of a slider will be bound to the cell width of a grid:

{% highlight Java %}
GridView<ITunesMedia> myGrid = new GridView<>();
Slider columnWidthSlider = SliderBuilder.create().min(10).max(512).build();
myGrid.cellWidthProperty().bind(columnWidthSlider.valueProperty());
{% endhighlight %}

By doing so the change of the slider will directly change the cell width of the grid because this property is bound to the value property of the slider. You can see the result in a video (0:20):

{% include posts/vimeo.html id="53462905" %}

By using the `bind(..)` method a change of the column width will not influence the slider value because we have a one way binding. But creating a bidirectional binding is easy as pie:

{% highlight Java %}
Slider mySlider1 = SliderBuilder.create().min(10).max(512).build();
Slider mySlider2 = SliderBuilder.create().min(10).max(512).build();
mySlider1.valueProperty().bindBidirectional(mySlider2.valueProperty());
{% endhighlight %}

Now whatever slider is changed, the other one will adopt it's value:

{% include posts/vimeo.html id="57128737" %}

You can simply remove a binding by calling `property.unbind()` in your code.

With this methods you can easily bind two or more properties with the same value type (String, double, etc.). But sometimes you need a more complex binding. Maybe you need to bind a slider value to the visible property of a label. The label should appear once the slider value reaches a maximum. The JavaFX Property API offers some conversion methods for this needs. Most property types provides specific methods that create a new binding. Here is an sample that uses some of this methods:

{% highlight Java %}
Slider mySlider1 = new Slider();
Label myLabel = LabelBuilder.create().text("ALERT!").visible(false).build();
myLabel.visibleProperty().bind(mySlider1.valueProperty().multiply(2).greaterThan(100));
{% endhighlight %}

In line 3 the valueProperty in converted to a new double binding that is always double the size of the wrapped property. Now by calling the greaterThan(..) method we create a boolean binding that is wrapped around the double binding. This bindings value is true while the wrapped value is > 100. So if the value of the slider is greater than 50 (50 * 2 > 100) the label will be visible:

{% include posts/vimeo.html id="57133467" %}

Next to this functions there is the util class [`javafx.beans.binding.Bindings`](http://docs.oracle.com/javafx/2/api/javafx/beans/binding/Bindings.html) that provides a lot of additional functions and support. For example you can add converters to a binding by using this class:

{% highlight Java %}
SimpleIntegerProperty intProp = new SimpleIntegerProperty();
SimpleStringProperty textProp = new SimpleStringProperty();
StringConverter<? extends Number> converter =  new IntegerStringConverter();

Bindings.bindBidirectional(textProp, intProp,  (StringConverter<Number>)converter);
{% endhighlight %}

Once you set the value of the StringProperty to "8" the IntegerProperty will update it's wrapped value to 8 and vice versa.

You can read more about the Property API & binding [here](http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm).

## Properties for custom controls

For custom components in JavaFX it is highly recommend to provide properties for all attributes of the control. By doing so you and other developers can easily bind this attributes to other properties or add change listener to them. Many JavaFX APIs (basic & 3rdParty) support the Property API. For example the next release of [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) will provide properties for all received data so that you can easily bind your control attributes to the data that will be loaded in background. So you can bind the items inside a ListView to the result of a REST request with only one line of code.

One of my next tutorials will show how you can bind your custom control properties to CSS properties.
