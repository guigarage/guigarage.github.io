---
title: 'A quick overview to the DataFX MVC and Flow API'
layout: post
author: hendrik
categories: [DataFX, Desktop Application Framework (JSR 377), JavaFX]
excerpt: 'This is a short overview of the controller and flow API of DataFX with some small examples'
featuredImage: sample-1
permalink: '2015/02/quick-overview-datafx-mvc-flow-api/'
header:
  text: The DataFX MVC and Flow API
  image: sample
---
This is a short overview of the controller and flow API of DataFX that I have written for the [JSR377 mailing list](http://jsr377-api.40747.n7.nabble.com/Application-Framework-Architecture-td31.html#a52). If you want to discuss the architecture in general please add a reply to the mailing list.

DataFX provides a MVC approach that adds a lot of API sugar to the FXML API of JavaFX. As a first example you can find a simple controller here:

{% highlight java %}
@ViewController("view.fxml")
public class MyController {
  
  @ViewNode
  private Label myLabel;
  
  @ViewNode
  @ActionTrigger("action-id")
  private Button myButton;
  
  @PostContruct
  private void init() {
    myLabel.setText("");
  }
  
  @ActionMethod("action-id")
  private void onAction() {
    myLabel.setText("clicked...");
  }
  
}
{% endhighlight %}

DataFX Controllers supports a lot of standard annotations like `@PostConstruct`. In addition the `@ViewNode` and `@ViewController` Annotation can be used to provide a link to the view. `@ViewNode` does mainly the same as `@FXML` but provides additional functionalities. Actions can be defined by an unique id. Therefore you can use Annotations to bind controls to actions. If you want a action to be called in a background thread you only need to add the `@Async` Annotation to the method.

Next to this there are some special annotations to inject DataFX related Objects like an exception handler or the DataFX concurrency toolkit. Here is an example:

{% highlight java %}
@ViewController("view.fxml")
public class MyController {
  
  @ViewNode
  private Label myLabel;
  
  @ConcurrencyProvider
  private ObservableExecutor executor;
  
  @PostContruct
  private void init() {
    executor.createProcessChain().
    addSupplierInExecutor(() -> getDataFromServer()).
    addConsumerInPlatformThread(data -> myLabel.setText(data)).
    run();
  }
}
{% endhighlight %}

On top of the MVC API is the Flow API. By using this API you can define flows in your application. By doing so you can define links between views or actions that will open a specific view. A Flow can easily defined by using the controller classes:

{% highlight java %}
Flow flow = new Flow(WizardView1Controller.class).
                withLink(WizardView1Controller.class, "next", WizardView2Controller.class).
                withLink(WizardView2Controller.class, "next", WizardView3Controller.class).
                withLink(WizardView3Controller.class, "next", WizardView4Controller.class).
                withLink(WizardView4Controller.class, "next", WizardView5Controller.class);
{% endhighlight %}

When using a flow you can define actions as global actions for the flow or actions that are view specific.

Next to this the Flow API adds a lot of new annotations. By adding the `@BackAction` annotation to a node like a button the flow will navigate back to the last view once the button is clicked. Instead of defining the links in the flow definition you can use the `@LinkAction(NextViewController.class)` annotation.

In addition the Flow API provides CDI by using `@Inject`. To do so 4 different scopes are defined:

* application scope (singleton)
* flow scope (same instance will be injected in every controller / view / model of the flow)
* view scope (same instance will be injected in the controller / model of the view)
* dependend (always inject a new instance)

In addition DataFX provides a event system. This can be used to handle events between 2 separated flows, for example. Each event type is defined by a unique id. The following code snippet defines two controllers that can be used in complete different parts of the application and don’t know each other. By using the event system the first controller can send events to the second one by clicking the button:

{% highlight java %}
@ViewController("producer.fxml")
public class ProducerController {

    @ViewNode
    @EventTrigger("test-message")
    private Button sendButton;

    @ViewNode
    private TextField textField;

    @EventProducer("test-message")
    private String getMessage() {
        return textField.getText();
    }

}

@ViewController("receiver.fxml")
public class ReceiverController {

    @ViewNode
    private Label messageLabel;

    @OnEvent("test-message")
    private void onNewChatMessage(Event<String> e) {
        messageLabel.setText(e.getContent());
    }
}
{% endhighlight %}

If you want the async events you only need to add the `@Async` annotation to the producer or / and receiver. By doing so you can create events on a background thread that will be received on the platform thread.

DataFX provides a PlugIn API that can be used to define additional components / plugins that will be managed by DataFX. Therefore you can define two types of Annotations: A injection annotation that can be used to inject specific objects or a handler annotation that can be use to do some stuff with objects. Here are two examples:

## Example A

DataFX provides a plugin for feature driven development. By doing so you can define nodes in your controller like this:

{% highlight java %}
@HideByFeature("PLAY_FEATURE")
private Button playButton;

@ViewNode
@DisabledByFeature("FEATURE2")
private Button button2;
{% endhighlight %}

Now you can use the [togglz API](http://www.togglz.org/documentation/overview.html) to define feature toggles and change them at runtime. If you for example disable the `PLAY_FEATURE` the playButton will become hidden. This is en example for a plugin that provides handler annotations.

## Example B

DataFX provides a Plugin for remote EJBs. By using the plugin you can inject a Remote-EJB-Proxy in your controller:

{% highlight java %}
@ViewController("view.fxml")
public class EjbViewController {

    @RemoteEjb()
    RemoteCalculator calculator;

    @ViewNode
    @ActionTrigger("calc")
    Button myButton;

    @Async    
    @ActionMethod("calc")
    private void onAction() {
      System.out.println(calculator.add(3, 3));
    }

}
{% endhighlight %}

In this case the EJB Wrapper / Proxy will be injected by using the custom / plugin annotation

If you want to now more you should have a look at [the DataFX tutorials]({{ site.baseurl }}{% post_url 2014-05-19-datafx-8-0-tutorials %}) or ask me :)
