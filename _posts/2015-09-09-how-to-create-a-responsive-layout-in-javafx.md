---
title: 'How to create a responsive layout in JavaFX'
redirect_to: https://open-elements.com/posts/2015/09/09/how-to-create-a-responsive-layout-in-javafx/
layout: post
author: hendrik
categories: [JavaFX, Layout & UI]
excerpt: 'Some days ago I was asked at twitter about a responsive layout problem in JavaFX. Here I will show how a first solution to create such a responsive layout.'
featuredImage: response-fx
permalink: '2015/09/how-to-create-a-responsive-layout-in-javafx/'
header:
  text: Responsive layouts in JavaFX
  image: sample
---
Some days ago [I was asked at twitter about a responsive layout problem in JavaFX](https://twitter.com/j_e_willis/status/641000236119257088). Based on the work that I did with [ResponsiveFX]({{ site.baseurl }}{% post_url 2014-11-04-responsive-design-javafx %}) I was asked how I would create a specific responsive behavior.

In this special case, an image and a text should next to each other on a desktop. In a smaller (mobile) version the text should be placed under the image. You can find the [question at stackoverflow](http://stackoverflow.com/questions/32021293/javafx-creating-a-responsive-layout), too.

I want to start with a first look at how the solution should look like. The following picture shows the described layout on a big and on a small screen:

![responsive](/assets/posts/guigarage-legacy/responsive-1003x1024.png)

Let's discuss several approaches that we can use to create this behavior.

## Switch between HBox and VBox

One suggested solution is to switch between an HBox and a VBox at runtime. This means that on a big screen you will see the image and text wrapped in an HBox and when the size becomes smaller the HBox will be replaced by a VBox. By doing so we need to discuss if we want to reuse the text and image component in both layouts or create a separate instance for each layout. Here I think that recycling of the components is a good idea. If you use 2 instances you need to sync them. By using the JavaFX property API this isn't as hard as it sounds in the first moment but it can still create some errors. So let's have a look at a code snippet that creates the basic view:

{% highlight java %}
public class ResponsiveLayoutDemo extends Application {

  private Label textLabel;

  private ImageView imageView;

  private HBox hBox;

  private StackPane mainPane;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.textLabel = new Label("Lorem ipsum dolor sit amet...");
    textLabel.setWrapText(true);

    this.imageView = new ImageView("http://goo.gl/1tEZxQ");
    imageView.setFitWidth(240);
    imageView.setPreserveRatio(true);

    hBox = new HBox();
    hBox.setSpacing(8);

    hBox.getChildren().addAll(imageView, textLabel);

    mainPane = new StackPane(hBox);
    mainPane.setPadding(new Insets(24));

    primaryStage.setScene(new Scene(mainPane));
    primaryStage.show();
  }

  public static void main(String... args) {
    launch(args);
  }
}
{% endhighlight %}

When running this example the view will be shown with the text and image next to each other:

![layout1](/assets/posts/guigarage-legacy/layout1-1024x570.png)

As a next step we want to refactor the code by using a VBox:

{% highlight java %}
public class ResponsiveLayoutDemo extends Application {

  private Label textLabel;

  private ImageView imageView;

  private VBox vBox;

  private StackPane mainPane;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.textLabel = new Label("Lorem ipsum dolor sit amet...");
    textLabel.setWrapText(true);

    this.imageView = new ImageView("http://goo.gl/1tEZxQ");
    imageView.setFitWidth(240);
    imageView.setPreserveRatio(true);

    vBox = new VBox();
    vBox.setSpacing(8);

    vBox.getChildren().addAll(imageView, textLabel);

    mainPane = new StackPane(vBox);
    mainPane.setPadding(new Insets(24));

    primaryStage.setScene(new Scene(mainPane));
    primaryStage.show();
  }

  public static void main(String... args) {
    launch(args);
  }
}
{% endhighlight %}

In this example the text will be displayed under the image as it should look on small devices:

![small](/assets/posts/guigarage-legacy/small-586x1024.png)

As a last step we want to modify the code and create an application that will change it's layout dynamically:

{% highlight java %}
public class ResponsiveLayoutDemo extends Application {

    private Label textLabel;

    private ImageView imageView;

    private VBox vBox;

    private HBox hBox;

    private StackPane mainPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.textLabel = new Label("Lorem ipsum dolor sit amet...");
        textLabel.setWrapText(true);

        this.imageView = new ImageView("http://goo.gl/1tEZxQ");
        imageView.setFitWidth(240);
        imageView.setPreserveRatio(true);

        hBox = new HBox();
        hBox.setSpacing(8);

        vBox = new VBox();
        vBox.setSpacing(8);

        mainPane = new StackPane();
        mainPane.setPadding(new Insets(24));

        changeToLargeLayout();

        primaryStage.widthProperty().addListener(e -> {
            if(primaryStage.getWidth() < 600) {
                changeToSmallLayout();
            } else {
                changeToLargeLayout();
            }
        });

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }

    public void changeToSmallLayout() {
        hBox.getChildren().clear();
        vBox.getChildren().clear();
        vBox.getChildren().addAll(imageView, textLabel);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(vBox);
    }

    public void changeToLargeLayout() {
        hBox.getChildren().clear();
        vBox.getChildren().clear();
        hBox.getChildren().addAll(imageView, textLabel);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(hBox);
    }

    public static void main(String... args) {
        launch(args);
    }
}
{% endhighlight %}

In this first try, the mainPane contains the hBox or the vBox depending on the width of the scene. To do so a lister is attached to the width property. In addition, the children of the vBox and hBox will be cleared and the text and image will be attached to the currently visible panel. As you can see in this video the view already behaves as it should:

{% include posts/youtube.html id="wEt8WKDR7r8" %}

<p>There are still some ugly parts in the code. As you might have noticed the listener will be called for each repaint of our stage. therefore nodes will be replaced and added to the scene graph all the time. Here we can use the JavaFX binding API to create a more performant binding. Here is the code snippet that shows the changed code:

{% highlight java %}
primaryStage.widthProperty().greaterThan(600).addListener((obs, oldValue, newValue) -> {
            if (!newValue) {
                changeToSmallLayout();
            } else {
                changeToLargeLayout();
            }
        });
{% endhighlight %}

Now the scene graph will only be changed if the size will become greater or smaller than 600 pixels.

I think this is a valid solution for the given problem and we can create a responsive behavior this way. But I think that it's not the perfect solution and therefore I will show another and easier approach the next days.
