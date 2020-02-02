---
title: 'Dolphin Platform 0.8 has beed released'
layout: post
author: hendrik
categories: [Dolphin Platform, JavaFX, Polymer]
excerpt: 'The 0.8 version of Dolphin Platform provides several new features like Java Bean Validation support of new bindings for JavaFX.'
featuredImage: dp-3
permalink: '2016/02/dolphin-platform-0-8-has-beed-released/'
header:
  text: Dolphin Platform 0.8
  image: sample
---
Yesterday we released version 0.8 of Dolphin Platform. The version contains several bugfixes and some new features that I want to show here.

## Validation

I think the biggest new feature is the support of Java Bean Validation (JSR-303). For this feature we introduce a new module to Dolphin Platform that you can easily add to your application dependencies:

{% highlight xml %}
<dependency>
    <groupId>com.canoo.dolphin-platform</groupId>
    <artifactId>dolphin-platform-bean-validation</artifactId>
    <version>DOLPHIN_PLATFORM_VERSION</version>
</dependency>
{% endhighlight %}

Once this is done you can use bean validation in the model layer. By doing so you can define your beans like this:

{% highlight java %}
@DolphinBean
public class MyModel {

    @NotNull
    private Property<String> value;

    public Property<String> valueProperty() {
        return value1;
    }
}
{% endhighlight %}

As you can see the `@NotNull` annotation is added to the property in the bean class. By doing so you can simply validate instances of the bean by using a `Validator`

Currently not all validation annotations are supported but this is just an open todo. at the moment the following annotations are supported:

* `@AssertFalse`
* `@AssertTrue`
* `@DecimalMax`
* `@DecimalMin`
* `@NotNull`
* `@Null`

For more information about the bean validation support in Dolphin Platform you should have a look at the [readme](https://github.com/canoo/dolphin-platform/tree/master/java-bean-validation).

## JavaFX bindings

For version 0.8 we added a lot of functionality to the JavaFX binding layer. Based on this it's no possible to simply bind a JavaFX list to an observable list of the Dolphin Platform model layer. To do so only one line if code is needed:

{% highlight java %}
FXBinder.bind(javaFXList).to(modelList);
{% endhighlight %}

Next to this we added support for converters. By doing so you can bind properties of lists of a different type to each other. This is interesting if you want to bind UI specific classes to the model layer. When using the JavaFX chart API the data model of the charts is defined by JavaFX specific classes. Since you don't want to have this classes in the model layer that is shared between client and server you can simply define a converter and bind the model of a chart to your custom bean type. Using such a converter is as easy as a normal binding:

{% highlight java %}
FXBinder.bind(javaFXChartModel).to(dolphinModel, myCustomConverter);
{% endhighlight %}

## Additional changes

Next to this we added some minor features like new convenience methods in the `ObservableList` and fixed some bugs. A complete list of the changes can be found [here](https://github.com/canoo/dolphin-platform/issues?q=milestone%3A0.8.0).
