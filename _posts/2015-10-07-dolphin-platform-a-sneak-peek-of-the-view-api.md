---
title: 'Dolphin Platform: A Sneak Peek of the view API'
layout: post
author: hendrik
categories: [Dolphin Platform, JavaFX]
excerpt: 'This post shows the view API of the Dolphin Platform. The Dolphin Platform is a MVC / MVP based open source framework for enterprise applications.'
featuredImage: sample-1
permalink: 'dolphin-platform-a-sneak-peek-of-the-view-api/'
header:
  text: Dolphin Platform
  image: sample
---
The last days I blogged about the [different APIs of the Dolphin Platform]({{ site.baseurl }}{% post_url 2015-10-04-dolphin-platform-a-sneak-peek %}). The Dolphin Platform is a new Open Source Framework by Canoo that will be released the next weeks. But one part is still missing: the view. Therefore I will show how you can create JavaFX based clients by using the Dolphin Platform. As already mentioned the Dolphin Platform will provide support for several client platforms like __JavaFX__, __AngularJS__ or __Polymer__.

![clients](/assets/posts/guigarage-legacy/clients.png)

In this post I will focus on the JavaFX support. Other client libraries and APIs will be described in future posts.

## The View API

Last but not least I will show how a view will be coded based on the Dolphin Platform. Here the client specific APIs differs in some points because we want to support the core concepts of the underlying UI toolkit. One of the most important points is that a developer can use the concepts he already knows and therefore we decided to not provide the exact same API for all client types. But when having a deeper look at the Dolphin Platform client libraries you will see that the core and low level APIs are the same on all platforms. In this first overview I will give a short sample how a view can be coded in JavaFX.

When creating a JavaFX client you will normally use FXML to create your view. Next to the FXML file you will have a view controller to bind all the properties of the view and attach action handlers.

![fxml](/assets/posts/guigarage-legacy/fxml.png)

When talking about the dolphin Platform this view controller is the perfect point to bind the view to the synchronized model and the server side controller. Therefore we call this class the "Binder". There are several ways how you can define such a binding but the most easy one is to use extend the AbstractBinder class that is part of the Dolphin Platform JavaFX library. This class already implements the complete lifecycle of the view and you can simply bind the synchronized presentation model to your view properties. Here is a small example for a view that contains only one textfield and a button:

{% highlight java %}
public class MyBinder extends AbstractBinder<MyModel> {
  
  @FXML
  private Button button;
  
  @FXML
  private Textfield textfield;
  
  public MyController() {
    super(ControllerConstants.NAME);
  }
  
  @Override
  public void onInit() {
    FXBinder.bind(textfield.textProperty()).bidirectionalTo(getModel().nameProperty());
    button.setOnAction(e -> invoke(ControllerConstants.SAVE));
  }
  
}
{% endhighlight %}

Once the view binder is instantiated the server controller and the model will automatically be created on the server. Since the model will be synchronized all the time between client and server you don't need to create it on the client. After this initialization is done the `onInit()` method of the binder will called. Here we bind the the `name property` that is part of the synchronized model to the text property of the textfield. In addition we define an action handler for the button. When the button is pressed a action in the server side controller should be called. Top do so the abstract binder provides the `invoke(String name)` method that triggers actions on the server controller. In this specific case the server controller might look like this:

{% highlight java %}
@DolphinController
public class Controller {
  
  @Inject
  private PersistenceService persistence;
  
  @DolphinModel
  private MyModel model;
  
  @DolphinAction
  public void save() {
    persistence.insert(model.getName());
  }
  
}
{% endhighlight %}

As you can see we never send any data to the server. Since the model will be automatically synchronized we can directly store the name string in the model to the database. The Dolphin Platform guarantee that the model will be the same as it's on the client when pressing the button.

![mvc](/assets/posts/guigarage-legacy/mvc-1024x350.png)

Another nice benefit that you might notice is that even if we have communication between the server and the client we don't need to handle several threads. The Dolphin Platform handles all the concurrency and handles all actions in the right thread. By doing so the binding between JavaFX properties and Dolphin properties will automatically be handled on the JavaFX application thread.

I hope this post gives a first overview how a client can be created. As already said the Dolphin Platform will provide support for several client platforms like __JavaFX__, __AngularJS__ or __Polymer__. I will blog about the specific client APIs in future posts.
