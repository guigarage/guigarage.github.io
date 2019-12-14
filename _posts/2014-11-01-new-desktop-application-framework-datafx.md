---
title: 'New Desktop Application Framework & DataFX'
layout: post
author: hendrik
categories: [DataFX, Desktop Application Framework (JSR 377), JavaFX]
excerpt: 'If DataFX should become an implementation of the JSR specification it must implement general interfaces and support a toolkit independent architecture.'
featuredImage: sample-8
permalink: '2014/11/new-desktop-application-framework-datafx/'
header:
  text: Desktop Application Framework
  image: sample
---
Maybe you have mentioned that [Andres Almiray](https://twitter.com/aalmiray) is planing [a new desktop application framework JSR](http://www.jroller.com/aalmiray/entry/new_desktop_application_framework_jsr). I had a chat with him some days ago at the canoo hq and we discussed some points of this project. In addition Andres gave me an introduction to [Griffon](http://griffon.codehaus.org) and I showed him [DataFX 8]({{ site.baseurl }}{% post_url 2014-10-22-datafx-8-released %}).

One of the core features of the framework should be UI toolkit independency. By doing so the framework will only contain general definitions and JavaFX or Swing specific implementations will be loaded by SPI, for example.

Griffon already contains this abstraction but DataFX is hardly coded against JavaFX. I think this is absolutely ok and at the moment there eis no plan to support other UI toolkits that JavaFX with DataFX. As said the application framework will define general classes and interfaces and maybe DataFX will be one of the JavaFX implementation. We will see what happens in the future.

## Generalizing the DataFX concepts

If DataFX should become an implementation of the JSR specification it must implement general interfaces and support a toolkit independent architecture. Therefore I did some tests and create a small platform independent framework based on DataFX architecture and APIs. I chose the concurrent API and the controller API of DataFX and created more general versions. As a benefit I created some cool code and features that will be integrated in DataFX 8.1. Let's have a look at the framework that is called "JWrap". You can find [the sources at GitHub](https://github.com/guigarage/jwrap). Because this was only a test there isn't any javadoc at the moment but the project contains a [Swing](https://github.com/guigarage/jwrap/tree/master/src/main/java/com/guigarage/uif/examples/swing) and a [JavaFX](https://github.com/guigarage/jwrap/tree/master/src/main/java/com/guigarage/uif/examples/javafx) example. JWrap has zero dependencies and defines a MVC and a concurrency API. Both API are platform independent and you don't need dependencies to Swing or JavaFX to use them. 

## JWrap concurrency utils

JWrap contains a [UIToolkit class](https://github.com/guigarage/jwrap/blob/master/src/main/java/com/guigarage/uif/concurrent/UIToolkit.java) that can be used to work with the event and rendering thread of the underlying UI toolkit. Here are the methods that are defined in the class:

{% highlight java %}
void runAndWait(Runnable runnable)

void runLater(Runnable runnable)

boolean isToolkitThread()

<T> T runCallableAndWait(Callable<T> callable)
{% endhighlight %}

By using these methods you can interact with the event and rendering thread of the used UI toolkit. To do so you must configure JWrap. This can be done by only one line of code. Here is an example how you configure JWrap to use the Swing EDT:

{% highlight java %}
UIToolkit.setPlatform(SwingPlatform.getInstance());
{% endhighlight %}

There are several other concurrency classes in JWrap that all depend on the UIToolkit class. By doing so you can now use all the concurrency helpers in JWrap and automatically the EDT will be used as application thread. I ported the [ProcessChain]({{ site.baseurl }}{% post_url 2014-10-22-datafx-8-released %}) of DataFX to JWarp and now you can code the following in your Swing application:

{% highlight java %}
ProcessChain.create().
    addSupplierInPlatformThread(() -> myTextField.getText()).
    addFunctionInExecutor((t) -> WeatherService.getWeather(t)).
    addConsumerInPlatformThread((t) -> myLabel.setText(t)).onException((e) -> {
        myLabel.setText("Error");
        e.printStackTrace();
    }).run();
{% endhighlight %}

I think this code is much better than using the SwingWorker. You can easily use the `ProcessChain` in any Swing application that supports Java 8.

## JWrap MVC API

DataFX contains the [controller and flow API]({{ site.baseurl }}{% post_url 2014-05-19-datafx-8-0-tutorials %}) that can be used to define MVC based views in JavaFX. I ported some parts of this API to JWarp and created a UI toolkit independent way to define MVC based controllers. JWrap contains some annotations that can be used to create a link between the view and the controller of a dialog.

Let's start with the swim example. As a first step we define the view and define names for all the UI components:

{% highlight java %}
public class SwingDemoView extends JPanel {

    public SwingDemoView() {
        setLayout(new BorderLayout());

        JButton myButton = new JButton("Get weather by city");
        myButton.setName("myButton");

        JTextField myTextField = new JTextField();
        myTextField.setName("myTextField");

        JLabel myLabel = new JLabel("Result...");
        myLabel.setName("myLabel");

        add(myTextField, BorderLayout.NORTH);
        add(myButton, BorderLayout.CENTER);
        add(myLabel, BorderLayout.SOUTH);
    }
}
{% endhighlight %}

The second class of the dialog will be the controller class. In this class JWrap annotations can be sued to inject view components in the controller and define interaction:

{% highlight java %}
public class SwingDemoController {

    @ViewNode
    @ActionTrigger("copy-action")
    private JButton myButton;

    @ViewNode
    private JTextField myTextField;

    @ViewNode
    private JLabel myLabel;

    @ActionMethod("copy-action")
    private void copy() {
        ProcessChain.create().
                addSupplierInPlatformThread(() -> myTextField.getText()).
                addFunctionInExecutor((t) -> WeatherService.getWeather(t)).
                addConsumerInPlatformThread((t) -> myLabel.setText(t)).onException((e) -> {
            myLabel.setText("Error");
            e.printStackTrace();
        }).run();
    }

    @PostConstruct
    private void init() {
        System.out.println("TADA");
    }
}
{% endhighlight %}

The `@ViewNode` annotation can be compared to the `@FXML` annotation that is used in JavaFX and DataFX to inject view nodes that are defined in FXML in a controller. The `@ViewNode` annotation has some benefits because it can be used for FXML based view and for coded view (this is one of the features that I will integrate in DataFX 8.1).

The JavaFX version looks mainly the same. Here is the code for the view class:

{% highlight java %}
public class JavaFXDemoView extends VBox {

    public JavaFXDemoView() {
        setSpacing(12);
        setPadding(new Insets(12));

        Button myButton = new Button("Get weather by city");
        myButton.setId("myButton");

        TextField myTextField = new TextField();
        myTextField.setId("myTextField");

        Label myLabel = new Label("Result...");
        myLabel.setId("myLabel");

        getChildren().addAll(myTextField, myButton, myLabel);

    }
}
{% endhighlight %}

And here we have the controller class:

{% highlight java %}
public class JavaFXDemoController {

    @ViewNode
    @ActionTrigger("copy-action")
    private Button myButton;

    @ViewNode
    private TextField myTextField;

    @ViewNode
    private Label myLabel;

    @ActionMethod("copy-action")
    private void copy() {
        ProcessChain.create().
                addSupplierInPlatformThread(() -> myTextField.getText()).
                addFunctionInExecutor((t) -> WeatherService.getWeather(t)).
                addConsumerInPlatformThread((t) -> myLabel.setText(t)).onException((e) -> {
            myLabel.setText("Error");
            e.printStackTrace();
        }).run();
    }

    @PostConstruct
    private void init() {
        System.out.println("TADA");
    }
}
{% endhighlight %}

As you can see the Swing controller class and the JavaFX controller looks mainly the same. Annotations like `@ViewNode` can be used in Swing and JavaFX.

## The future of JWrap

I created this project to test of a UI independent API can look like. I don't plan to continue working on the library. Maybe I will use it when checking some other ideas for the application framework JSR.

I think that the library can be a benefit for Swing developers. By using JWrap they will get some lambda based concurrency APIs and a MVC framework that can be used to structure the code or prepare a migration to JavaFX.
