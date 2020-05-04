---
layout: post
title:  'TODO'
author: hendrik
featuredImage: java-6
excerpt: 'TODO'
categories: [Java, JPMS]
header:
  text: TODO
  image: sample
---

Since more and more project migrate to Java 11 the module system of Java (Java Platform Module System, JPMS) becomes more important. In this article I will have a look at the definition of modules and how you can define dependecies for modules.

## The sample application

In this article we will use a small sample application. This application defines 2 dependencies (module-a and module-b). 

![project dependency graph](/assets/posts/2020-04-20-module-requires/dependencies.png)

Let's assume a build and project management tool like Maven or Gradle is used to build the project and manage the dependencies of the project. The following code snippet shows how the dependency definition might look like in a a Maven pom.xml file for the project:

{% highlight xml %}
<dependency>
    <groupId>com.external</groupId>
    <artifactId>module-a</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.external</groupId>
    <artifactId>module-b</artifactId>
    <version>1.0.0</version>
</dependency>
{% endhighlight %}

Since normally a a JAR defines 1 module (TODO: kann auch 1 jar X module?) we can easily assume how the module graph of the project looks like. The following grapic gives a preview of the module graph:

![project module graph](/assets/posts/2020-04-20-module-requires/modules.png)

As you can see in the diagram a module is defined by a name. This name often reflects the base
package of the module. The name is not bound to the groupId or artifactId that is defined by Maven. In general
you can define any name for a modul (based on some restrictions LINK). As best practice developers often use
the base package path of the module as a name (LINK?). When using modules in Java each module must define 
a unique base package (LINK?). Based on this 2 JARs should never contain the same (base) package and several frameworks that are seperated in multiple jars needed to change the used Java packages (LINK?).

Next to this modules in Java do not have any version information (LINK?). While we specify versions for dependencies
in Gradle or Maven we do not have such information in the module system. While this might feel wrong when
starting to work with the module system in makes sense in several ways. TODO -> Weiter erklären

## How can my jar become a module?

There are 2 differnt ways how you can specifiy a JAR as a Java module:

* Defining the JAR as an automatic module
* Definig the JAR as a full module (BESSERER NAME?)

An automatic module only needs a definition of the module name in the manifest file of the jar. A full
module needs a full definition of the module. Such definition is done in a `module-info.java` file. Such file
is not only a text file and will be compiled by your build and added as a `module-info.class` file
to your jar file. This step should normally be done by the build system. When using Maven (MIN VERSION) you
only need to place the `module-info.java` file in the `src/main/java` folder and Maven will automatically
compile it (when executing the maven step XXXXX) and add it to the jar (when executing the maven step XXXXX).
The following images gives an overview on the general steps:

![Maven project sample](/assets/posts/2020-04-20-module-requires/maven-project.png)

TODO: Kurze einführung in Syntax der Module-Info

## How to require modules as dependencies

Hier requires erklären

Wenn requires muss es auch auf den Classpath / Modulepath sein, da Anwerdnung sonst nicht startet.

TODO: Hier passt static jetzt ganz gut

Requires with transient

Code Beispiel für Zugriff

{% highlight java %}
public class MyModuleClass {

  public static void printData() {
    ModuleAData.print();
    ModuleBData.print();
    ModuleCData.print();
  }

}
{% endhighlight %}

![project dependency graph](/assets/posts/2020-04-20-module-requires/modules-not-transient.png)

Geht auch besser. Hierfür muss aber Library Entzwickler handeln

![project dependency graph](/assets/posts/2020-04-20-module-requires/modules-transient.png)

Auto-Module? Was ist das nun und wier passt das hier rein?

Was passiert wenn Abhängigkeit noch nicht einmal AUto-Name hat?
