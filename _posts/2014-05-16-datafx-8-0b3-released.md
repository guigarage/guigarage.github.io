---
title: 'DataFX 8.0b3 has been released'
layout: post
author: hendrik
categories: [DataFX, JavaFX]
excerpt: 'The 3rd beta of DataFX 8.0 has been released.'
featuredImage: sample-6
permalink: '2014/05/datafx-8-0b3-released/'
header:
  text: DataFX 8.0b3 has been released
  image: sample
---
The [3rd beta of DataFX 8.0](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.javafxdata%22%20AND%20v%3A%228.0b3%22) has been released. Here are all new features / bugfixes of the version:

* Injection is working in superclasses ([issue 30](https://bitbucket.org/datafx/datafx/issue/30/injection-does-not-works-with-abstract))
* @PreDestroy is now working correctly for view controllers in a flow ([issue 22](https://bitbucket.org/datafx/datafx/issue/22/predestroy-will-not-be-called))
* DataFX-UI module contains a helper class to create form based views (see [org.datafx.control.form.SimpleForm](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-ui/src/main/java/org/datafx/control/form/SimpleForm.java?at=default))
* Some general classes are moved to the DataFX-core module
* DataFX ExceptionHandling and the default thread pool can be configured (see [org.datafx.DataFXConfiguration](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/DataFXConfiguration.java?at=default))
* Each view can define it's metadata (title, icon, ...) by using the [ViewMetadata](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/controller/context/ViewMetadata.java?at=default) class. The current instance can be injected in a controller by using the [@Metadata](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/controller/context/Metadata.java?at=default) annotation. Example can be found [here](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-samples/src/main/java/org/datafx/samples/multitab/SampleTabController.java?at=default).
* The default title of a dialog and it's icon can be defined by the [@FXMLController](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/controller/FXMLController.java?at=default) annotation.
* The [Flow](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-flow/src/main/java/org/datafx/controller/flow/Flow.java?at=default) class and the [ViewFactory](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/controller/ViewFactory.java?at=default) class provides methods to generate Tab instances. By doing so views or flows can simply be added to a TabbedPane and the title / icon will automatically be set.
* Jackson dependency was removed. DataFX now uses the default JSON spec for JEE.
* [@ActionMethod](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-flow/src/main/java/org/datafx/controller/flow/action/ActionMethod.java?at=default) annotation can be used to define flow actions directly in a controller class.
* [@LinkAction](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-flow/src/main/java/org/datafx/controller/flow/action/LinkAction.java?at=default) annotation can be used to define flow links directly in a controller class.
* [DataFxTask](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/concurrent/DataFxTask.java?at=default) contains new then(...) method to support reactive programming
* All methods of the [ProcessChain](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/concurrent/ProcessChain.java?at=default) has been renamed. By doing so no casting when using lambdas is needed.
* [FlowHandler](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-flow/src/main/java/org/datafx/controller/flow/FlowHandler.java?at=default) class contains JFX properties instead of normal properties
* The [ProcessChain](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-core/src/main/java/org/datafx/concurrent/ProcessChain.java?at=default) has support to repeat it's action.
* A first JPA [sample](https://bitbucket.org/datafx/datafx/src/143d92d09508e2fcce56815ad2b785a32dba6fe0/datafx-samples/src/main/java/org/datafx/samples/jpacrud/?at=default) was added for the new DataFX-crud module

For the next version we plan to add a lot of documentation and samples to the DataFX-core and DataFX-flow module. In addition we will change the build to gradle and modernize the usage of JSON. In addition we will try to fix the open issues.

You can find more information about DataFX [here]({{ site.baseurl }}{% link pages/projects/datafx.md %}), our [Google Group](https://groups.google.com/forum/#!forum/datafx-dev) or at [the Bitbucket repo](https://bitbucket.org/datafx/datafx/).
