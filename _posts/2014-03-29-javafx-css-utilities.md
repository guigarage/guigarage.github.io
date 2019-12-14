---
title: 'JavaFX CSS Utilities'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'Ever tried to add a Styleable property to a JavaFX Control or Skin? By doing so you can add additional CSS support to a Control type.'
featuredImage: sample-3
permalink: '2014/03/javafx-css-utilities/'
header:
  text: JavaFX CSS Utilities
  image: sample
---
Ever tried to add a Styleable property to a JavaFX Control or Skin? By doing so you can add additional CSS support to a Control type. [Gerrit Grunwald](https://twitter.com/hansolo_) has described the benefits of styleable properties in a [blog post](http://harmoniccode.blogspot.de/2013/05/css-confusing-style-sheets.html). One big problem is the boilerplate code that will be created when implementing these properties in a Control class. Here is an example how a Control with only one property will look like:

{% highlight java %}
public class MyControl extends Control {
    private StyleableObjectProperty<Paint> backgroundFill;
    public Paint getBackgroundFill() {
        return backgroundFill == null ? Color.GRAY : backgroundFill.get();
    }
    public void setBackgroundFill(Paint backgroundFill) {
        this.backgroundFill.set(backgroundFill);
    }
    public StyleableObjectProperty<Paint> backgroundFillProperty() {
        if (backgroundFill == null) {
            backgroundFill = new SimpleStyleableObjectProperty<Paint>(StyleableProperties.BACKGROUND_FILL, MyControl.this, "backgroundFill", Color.GRAY);
        }
        return backgroundFill;
    }
    private static class StyleableProperties {
        private static final CssMetaData< MyControl, Paint> BACKGROUND_FILL =
                new CssMetaData< MyControl, Paint>("-fx-background-fill",
                        PaintConverter.getInstance(), Color.GRAY) {
                    @Override
                    public boolean isSettable(MyControl control) {
                        return control.backgroundFill == null || !control.backgroundFill.isBound();
                    }
                    @Override
                    public StyleableProperty<Paint> getStyleableProperty(MyControl control) {
                        return control.backgroundFillProperty();
                    }
                };
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            Collections.addAll(styleables,
                    BACKGROUND_FILL
            );
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
}
{% endhighlight %}

That's a lot of code for only one property. Therefore I created some helper classes to do all the work. These classes are part of the "css-helper" library that I released today.

Here is an example how the Control will look like when using the "css-helper" library:

{% highlight java %}
public class MyControl extends Control {
    private StyleableObjectProperty<Paint> backgroundFill;
    public Paint getBackgroundFill() {
        return backgroundFill == null ? Color.GRAY : backgroundFill.get();
    }
    public void setBackgroundFill(Paint backgroundFill) {
        this.backgroundFill.set(backgroundFill);
    }
    public StyleableObjectProperty<Paint> backgroundFillProperty() {
        if (backgroundFill == null) {
            backgroundFill = CssHelper.createProperty(StyleableProperties.BACKGROUND_FILL, MyControl);
        }
        return backgroundFill;
    }
    private static class StyleableProperties {
        private static final CssHelper.PropertyBasedCssMetaData<TriangleButton, Paint> BACKGROUND_FILL = CssHelper.createMetaData("-fx-background-fill", PaintConverter.getInstance(), "backgroundFill", Color.LIGHTGREEN);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(Control.getClassCssMetaData(), BACKGROUND_FILL);
    }
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
}
{% endhighlight %}

By using the static methods of the CssHelper class the code is much more readable.

But there is one problem with the API: It uses reflection internally and because of this the CSS algorithm will be slower as when using the first aproach. So the CssHelper should only be used for Controls that should not be part of an open source library and don't appear often in the scene graph. If you need a special Control in your application or add a CSS property to an existing one you can use these classes to minimize the source code.

The Library is deployed to [Maven Central](http://search.maven.org/#artifactdetails%7Ccom.guigarage%7Ccss-helper%7C0.1%7Cjar) and can be easily added to a Maven project:

{% highlight xml %}
<dependency>
    <groupid>com.guigarage</groupid>
    <artifactid>css-helper</artifactid>
    <version>0.1</version>
</dependency>
{% endhighlight %}
