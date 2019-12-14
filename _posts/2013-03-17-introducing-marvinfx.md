---
title: 'Introducing MarvinFx'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'TODO'
featuredImage: sample-1
permalink: '2013/03/introducing-marvinfx/'
header:
  text: Introducing MarvinFx
  image: sample
---
For all my current JavaFX work I need a simple test framework to check the behavior of controls or complete scenes. Since I'm working more and more with the [Property API]({{ site.baseurl }}{% post_url 2013-01-10-custom-ui-controls-with-javafx-part3 %}) I mainly wanted to to test the properties and their behavior of controls and scenes.

[FEST](http://fest.easytesting.org) is doing a great job for automated tests in Swing and with [JemmyFX](http://jemmy.java.net/JemmyFXGuide/jemmy-guide.html) a first framework for JavaFX is available. But both of this framework don't have a (good) support for Properties.

Because of this I started my own testing framework for JavaFX. [MarvinFX](https://github.com/guigarage/MarvinFX) has the goal to easily test JavaFX controls and scenes with a special attention on properties. The framework is currently in a very early state and only a few parts are implemented. But I think that already the current state can point out where the journey will lead. And maybe I will receive some helpful suggestions.

You can simply use MarvinFX with a JUnit test. By using Marvin you can create a JavaFX Scene of the Application part you want to test:

{% highlight java %}
@Test
public void test1() {
	Button b1 = new Button("Test123");
	MarvinFx.show(b1);
}
{% endhighlight %}

Marvin will generate a Parent (StackPane) for the Button and put everything in a Scene. The Scene will automatically be shown on the screen.

To test parts of the ui you need a Robot that will generate user interactions for you. It is planned that MarvinFX will provide a OS based robot (by `AWTRobot`) and a Java based robot (by `JFXRobot`) under the surface. At the current state the mouse handling of the robot is working.

{% highlight java %}
@Test
public void test2() {
	Button b1 = new Button("Test123");
	MarvinFx.show(b1);
	NodeFixture<Button> b1Fixture = new NodeFixture<Button>(b1);
	b1Fixture.mouse().click();
	b1Fixture.mouse().click(MouseButton.SECONDARY);
}
{% endhighlight %}

As you can see MarvinFX uses Fixture-classes like FEST does. The fixture is a wrapper for a node and provides all methods that are needed to test the node.

To check the state of properties MarvinFX provide so called PropertySupervisor classes. By using them you can write unit tests for JavaFX properties. A PropertySupervisor is a wrapper class for a property:

{% highlight java %}
@Test
public void test3() {
	Button b1 = new Button("Test123");
	MarvinFx.show(b1);
	PropertySupervisor<String> textPropertySupervisor = new PropertySupervisor<>(b1.textProperty());
	textPropertySupervisor.assertCurrentValue("Test123");

	TextField textField = new TextField("1");
	TextfieldFixture textfieldFixture = new TextfieldFixture(textField);
	PropertySupervisor<String> textSupervisor = textfieldFixture.createTextPropertySupervisor();
}
{% endhighlight %}

By using the supervisors you can easily check the current value of a property. But a supervisor can do more. You can define rules that will be checked in the future while a property will change:

{% highlight java %}
@Test
public void test3() {
	TextField textField = new TextField("1");
	MarvinFx.show(textField);
	TextfieldFixture textfieldFixture = new TextfieldFixture(textField);
	PropertySupervisor<String> textSupervisor = textfieldFixture.createTextPropertySupervisor();

	textSupervisor.assertWillChange();
	textSupervisor.assertWillChangeByDefinedCount(2);
	textSupervisor.assertWillChangeThisWay("7", "14");
	textfieldFixture.setText("7");
	textfieldFixture.setText("14");
	textSupervisor.confirm();
}
{% endhighlight %}

Property supervisors offers some methods that start with `assertWill...`. All this methods defines a rule for the supervisor and every time the property will changed all rules of the supervisor will be checked. After all user interaction is done (in this case by setting the text) you need to invoke a confirm() on the supervisor. If any of the rules were failed the unit test will fail with the confirmation. By calling confirm() all defined rules will be deleted and the supervisor can be used for other tests.

Putting all this stuff together you can simply write unit tests for your JavaFX controls and scenes. Here is a basic example that uses all the features and checks the behavior of a simplified Scene:

{% highlight java %}
@Test
public void test4() {
	final TextField textField = new TextField("1");
	Button button = new Button("Button");
	button.setOnAction(new EventHandler<ActionEvent>() {
			
		@Override
		public void handle(ActionEvent event) {
			int value = Integer.parseInt(textField.getText()) * 2;
			textField.setText(value + "");
		}
	});
	MarvinFx.show(VBoxBuilder.create().children(textField, button).build());
		
	TextfieldFixture textfieldFixture = new TextfieldFixture(textField);
	PropertySupervisor<String> textSupervisor = textfieldFixture.createTextPropertySupervisor();
	NodeFixture<Button> buttonFixture = new NodeFixture<Button>(button);
		
	textSupervisor.assertValueIsNotNull();
	textSupervisor.assertCurrentValue("1");
	buttonFixture.mouse().click();
	textSupervisor.assertCurrentValue("2");
		
	textSupervisor.assertWillChange();
	textSupervisor.assertWillChangeByDefinedCount(4);
	textSupervisor.assertWillChangeThisWay("4", "8", "16", "32");
	buttonFixture.mouse().click(4);
	textSupervisor.confirm();
		
	textSupervisor.assertWillNeverChange();
	textSupervisor.confirm();
		
	textSupervisor.assertWillChange();
	textSupervisor.assertWillChangeByDefinedCount(2);
	textSupervisor.assertWillChangeThisWay("7", "14");
	textfieldFixture.setText("7");
	buttonFixture.mouse().click();
	textSupervisor.confirm();
}
{% endhighlight %}

MarvinFX is [hosted at github](https://github.com/guigarage/MarvinFX). At the moment there is no JavaDoc and as I said the API is not finished and some parts are buggy at the moment. But maybe some people have good ideas for Marvin or can offer some feedback :)
