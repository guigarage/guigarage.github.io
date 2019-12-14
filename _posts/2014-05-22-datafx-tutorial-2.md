---
title: 'DataFX 8 Tutorial 2'
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'In this DataFX tutorial I will show how navigation between different views can easily be managed with DataFX and its Flow API.'
featuredImage: sample-9
permalink: '2014/05/datafx-tutorial-2/'
header:
  text: DataFX 8 Tutorial
  image: sample
---
As I mentioned [here]({{ site.baseurl }}{% post_url 2014-05-19-datafx-8-0-tutorials %}) I started a series of [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) tutorials. The first tutorial can be found [here]({{ site.baseurl }}{% post_url 2014-05-20-datafx-tutorial-1 %}).

In this second tutorial I will show you the basics about __navigation in a DataFX flow__. In this example we will define two different views that are part of a flow and then navigate from one view to the other one. The source of the tutorial can be found[here](https://bitbucket.org/datafx/datafx/src/a92bddc1904a905be89205d5edf3a39015149227/datafx-tutorial2/?at=default).

The following pictures shows the two views of the tutorial and its interaction:

![tutorial2](/assets/posts/guigarage-legacy/tutorial2.png)

As shown in the first tutorial we want to start by defining the views in __FXML__. Here is the __FXML__ file for the first view:

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.geometry.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.paint.*?>
<stackpane id="StackPane" maxheight="-Infinity" maxwidth="-1.0" minheight="-Infinity" minwidth="-Infinity" prefheight="-1.0" prefwidth="-1.0" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/2.2">
  <children>
    <vbox alignment="CENTER" prefheight="-1.0" prefwidth="300.0" spacing="12.0">
      <children>
        <label fx:id="resultLabel" text="This is view 1">
        <button fx:id="actionButton" mnemonicparsing="false" text="Navigate to view 2">
        </button></label>
      </children>
      <padding>
        <insets bottom="12.0" left="12.0" right="12.0" top="12.0" />
      </padding>
    </vbox>
  </children>
  <padding>
    <insets />
  </padding>
</stackpane>
{% endhighlight %}

This defines a view that looks like this:

![pic1](/assets/posts/guigarage-legacy/pic1.png)

The second view differ only in a few points from the first one. Here is the FXML definition of the second view:

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.geometry.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.paint.*?>
<stackpane id="StackPane" maxheight="-Infinity" maxwidth="-1.0" minheight="-Infinity" minwidth="-Infinity" prefheight="-1.0" prefwidth="-1.0" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/2.2">
  <children>
    <vbox alignment="CENTER" prefheight="-1.0" prefwidth="300.0" spacing="12.0" style="-fx-background-color: red;">
      <children>
        <label fx:id="resultLabel" text="This is view 2">
          <button fx:id="actionButton" mnemonicparsing="false" text="Navigate to view 1"></button>
        </label>
      </children>
      <padding>
        <insets bottom="12.0" left="12.0" right="12.0" top="12.0" />
      </padding>
    </vbox>
  </children>
  <padding>
    <insets />
  </padding>
</stackpane>
{% endhighlight %}

As you can see in the code there is a style addition in the __VBox__ tag. Here the red background color of the view is defined by __CSS__. The rendered view will look like this:

![pic2](/assets/posts/guigarage-legacy/pic2.png)

As a next step we create __view controllers__ for the views. As shown in the last tutorial you have to define a class for each view that acts as its controller. In a first step we create some empty controller classes that are only annotated by `@FXMLController`:

{% highlight java %}
@FXMLController("view1.fxml")
public class View1Controller {
    @FXML
    private Button actionButton;
}
{% endhighlight %}

Only the button that is defined by the `fx:id` attribute in the FXML file is part of the first controller class. The second one will look similar:

{% highlight java %}
@FXMLController("view2.fxml")
public class View2Controller {
    @FXML
    private Button actionButton;
}
{% endhighlight %}

Once this is done an application that is based on a flow can be created. As learned in tutorial 1 there is an easy way to create an application that only contains one flow. We will use it here, too. The main class of this example will look like this:

{% highlight java %}
public class Tutorial2Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Flow(View1Controller.class).startInStage(primaryStage);
    }
}
{% endhighlight %}

By doing so a new flow is created that defines `view1` as its start view. If you start the application you will already see the first view but the button won't trigger any action. To add an action that links to the second view DataFX provides a special annotation called <`@LinkAction`. This annotation is working like the `@ActionTrigger` annotation that was introduced in tutorial 1. Once the button is clicked an action event will be fired and handled by the DataFX container. When using the `@LinkAction` annotation the controller of the target view must be specified. Therefore the Annotations provides a value that can be defined as the controller class. Here is the updated code of the first controller class:

{% highlight java %}
@FXMLController("view1.fxml")
public class View1Controller {
    @FXML
    @LinkAction(View2Controller.class)
    private Button actionButton;
}
{% endhighlight %}

Whenever the button is pressed the flow will navigate to the second view that is defined by the `View2Controller` class. The `@LinkAction` can be added to any JavaFX Node. If the component extends the `ButtonBase` class or the `MenuItem` class a handler for action events will be added to the control. Otherwise the action will be called once the control is clicked by mouse. By using the annotation a developer doesn't need to handle the complete navigation like changing the view or create a new data model. DataFX will handle all these steps automatically and the defined view will appear on screen once the action is triggered.

Maybe you can already imagine how the final code for the second view controller will look like ;)

{% highlight java %}
@FXMLController("view2.fxml")
public class View2Controller {
    @FXML
    @LinkAction(View1Controller.class)
    private Button actionButton;
}
{% endhighlight %}

Once this is done you have created the first simply flow with DataFX. By using the `@LinkAction` annotation you can create big flows that contain a lot different views. In later tutorials you will that DataFX provides some other cool features to manage the navigation in a flow.

As said in the first example each action in DataFX is defined by an unique ID. In the case of a LinkAction, the developer doesn't need to define an ID on its own. DataFX will create a unique ID once the controller will be initialized. By doing so the source code is much shorter and cleaner. As you will see later in the tutorials there are several other ways to define a navigation in a flow. Some of these work with an unique ID as shown in tutorial 1.
