---
title: 'Maven vs. Gradle and the Best of Both Worlds'
redirect_to: https://open-elements.com/posts/2016/10/28/maven-vs.-gradle-and-the-best-of-both-worlds/
layout: post
author: hendrik
categories: [Java]
excerpt: 'See how both Maven and Gradle succeed and fail and consider static modules, an idea that would bring out and combine their best aspects.'
featuredImage: java-2
permalink: 'my-thoughts-about-java-build-tools/'
header:
  text: Maven vs. Gradle
  image: sample
---
{% include elements/block.html text="This post was originally posted at [DZone](https://dzone.com/articles/maven-vs-gradle-and-the-best-of-both-worlds)" %}

Since several years I’m really frustrated of the build tools that can currently be used to build Java based projects. In my daily job I have seen several Java projects and modules that use different build systems. Thankfully mostly all projects I have seen the last years are build on [Maven](https://maven.apache.org) or [Gradle](https://gradle.org). Based on this you need to know only 2 different systems to understand the basic structure and dependencies of a project. I don’t want to give an overview of all build systems that can currently be used to define the build of a Java based project since I think that they all can easily be splitted in 2 different types:

* Script based build system
* Configuration based build systems

Currently Maven and Gradle are the best known build systems and each of them is related to one type. Let's have a deeper look at both of them.

## About Gradle

From my point of view Gradle is a script based build system. For such a build system you can define your own tasks based on commands or an API. In Gradle you can [write custom build tasks](https://docs.gradle.org/current/userguide/custom_tasks.html) easily by using [Groovy](http://groovy script). Gradle provides some [basic plugins](https://docs.gradle.org/current/userguide/tutorial_java_projects.html) for a common build workflow of Java projects. Based on this you do not need to define any task like the compilation of the Java sources for each project again and again. But since Gradle is based on tasks that can easily be defined in the build script you do not have any defined structure or best practice how you should structure your build script and how you should define information like the project name or the dependencies of a project. Don’t get me wrong: Gradle provides good APIs for all this but it’s up to the developer where in the build script he for example defines the dependencies of a project. In addition you always need to run the build script to get information about the project.

## About Maven

The functionality of Maven is against a script based build tool very limited. All information about the project and how to build it must be specified in a [pom.xml](http://pom.xml dependency) file. Internally you can configure the project and the build by using XML syntax. All possibilities that you have to configure the project are defined by [Maven in a XSD file](https://maven.apache.org/xsd/maven-4.0.0.xsd). By doing so it’s quite easy to define static metadata of the project like the name or the project description. Even technical information like groupId, artifactId, version or static dependencies can easily be defined. By using Maven as a build tool your project will be build by using [a best practice workflow](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) to build Java projects that is defined in several tasks. This is fine for small projects and APIs but if you need to do something special you need to add plugins to Maven. Such a plugin must be defined for your project by using the limited XML syntax of Maven. The functionality of such a plugin must be [coded in Java](https://darylmathison.com/2015/04/12/there-is-a-mojo-in-my-dojo-how-to-write-a-maven-plugin/) and provided as a JAR. For large and complex projects you will need several of this projects that will always end in a large and unreadable XML file as description and build definition of your project.

## Conclusion

One point that thankfully both approaches have in common is the way how dependencies will be revolved. Both, Maven and Gradle, will download (transitive) dependencies from any artifact repository. Maven uses [Maven Central](http://search.maven.org) here as default and Gradle uses [JCenter](https://bintray.com/bintray/jcenter). In addition any other repository (like a private company repository) can easily be defined. Since artifact repositories follow some common standards all mentioned repositories can easily be used in Maven or Gradle.

On the other hand both tools / build systems have some big disadvantage from my point of view. Since it's quite easy to define project metadata and dependencies in Maven it's absolutely horrible to create a highly customized build with Maven. If you want to create a asciidoc based documentation or upload the final artifact to a JavaEE server your pom file will fast become unreadable. The [Maven pom of the hazelcast project](https://github.com/hazelcast/hazelcast/blob/master/pom.xml) has for example over 1000 lines. Understanding a 1000 line XML based build definition can be very frustrated for a new developer. So Maven is nice for small modules and APIs like [Apache commons](https://commons.apache.org) or [GSON](https://github.com/google/gson). Developers can get really fast an overview about the project and its dependencies by simply having a look at the pom file. In addition tools do not need to run a build process / script to get information. The pom file can simply be parsed.

Gradle on the other hand provides a lot of flexibility. Since it is based on a script you can do really everything and supporting custom build steps is much easier than in Maven. This is very good if you want to deploy your artifacts to a server or create for example a documentation. But based on the flexibility a build script can become complex, too. In most big Java projects have seen the last years only some developers know how to change something in the gradle build. In addition any tool needs to run a gradle build to get basic information about the project. Since Gradle build scripts are based on a script language it's impossible to parse them. So at the end Gradle is sadly not the perfect solution for building Java projects.

Based on this I would say that currently no Java build tool is the prefect solution for all Java based projects. Maven is to limited but great for small projects that follow the defined Maven lifecycle and definitions. Gradle on the other hand can do everything that you want but even small projects may differ in it’s definition since you can structure / define your build description in any way. In addition you need to run the build to receive information about the project.

Based on this points I think that there is a way how all the benefits can be simply combined. When having a look at [JavaScript](http://www.w3schools.com/js/) and build systems for JavaScript you can see a difference. Modern build systems for JavaScript like [gulp](http://gulpjs.com) work more or less like Gradle. You can easily define your own custom tasks based on a script language. In addition the metadata of the project is often defined in a separate file. Most projects that I have seen the last months use [bower](https://bower.io) to define the static metadata of a project. This include a description of the project (name, description, license, …) and its dependencies. Build tools like gulp can now use the information of bower to build the project (yes, this is a very easy description of the internal process). Based on this I ask myself why Gradle for example can’t do the same. Let’s think about a static definition for Java projects and modules that can easily integrated in a Gradle or Maven build. Since such a definition would be used by both tools it would be much easier for developers to learn how to read and use such a static definition. In addition tools do not need to start any external process like a build script to get information about the Java project. For complete projects you can simply create custom Gradle tasks that internally get get all the information of the static definition and reuse it for the real build.

In the following paragraphs I will try to sketch how such a static definition might look like and how it could be used.

## Defining a static module description

A static module definition should contain a readable description of the module. This should include several parameters like:

* module name
* module description
* licence
* urls (repo, issue tracker, doc, …)
* developer information (name, mail, …)

Based on this information a module description might look like this:

{% highlight json %}
{
"name": "My cool API",
"description": "A very cool API",
"licence": "Apache 2.0",
"urls": [{
  "name": "Github repository",
  "url": "www.github.com/cool/api",
  "type": "repo"
  },{
  "name": "Stackoverflow section",
  "url": "www.stackoverflow.com/cool",
  "type": "custom"
  }],
"developers": [{
  "name": "John Doe",
  "mail": "john.doe@mail.com"
  }]
}
{% endhighlight %}

Next to this information a project needs a unique identifier. Since in Maven and Gradle this can easily be defined by the groupId and artifactId a static definition should reuse this properties:

{% highlight json %}
{
"groupId": "com.cool.api",
"artifactId": "cool-api"
}
{% endhighlight %}

To define a specific version of a project the versionId should be added, too. Based on this information everything to provide the module to Maven Central or JCenter is defined. In addition a Maven pom.xml can easily be created based on this information and other modules can depend on this module. After adding the properties a static project definition might look like this:

{% highlight json %}
{
  "name": "My cool API",
  "description": "A very cool API",
  "licence": "Apache 2.0",
  "groupId": "com.cool.api",
  "artifactId": "cool-api",
  "version": "2.1.9"
}
{% endhighlight %}

To compile a java module we need some additional information. I think the most basic informations are the source encoding and the Java version that should be used to compile. Here we need to specify a Java version that defines the minimum version that is needed to compile the sources and a java version that defined the compile target version. Adding this information to a module description might end in the following file:

{% highlight json %}
{
"name": "My cool API",
"description": "A very cool API",
"licence": "Apache 2.0",

"groupId": "com.cool.api",
"artifactId": "cool-api",
"version": "2.1.9",

"encoding": "UTF-8",
"source-java": "8",
"target-java": "8"
}
{% endhighlight %}

Based on this information a project that needs no additional classes next to the basic Java classes in the class path can easily be compiled. Since the complete module definition is provided in a static way a support for this can easily be integrated in any IDE or build tool.

Since most projects depend on external APIs and modules the static module definition should provide information about the dependencies of the module. Like in Maven or Gradle a definition of the dependencies based on artifactId, groupId and version is the best way to do it. At compilation a build tool or IDE can than easily download the (transitive) dependencies from Maven Central or JCenter. A static project definition should offer mostly all features that are part of the Maven dependency definition but in most use cases simply adding the needed dependencies is all you need. By adding dependency information a module definition will look like this:

{% highlight json %}
{
"name": "My cool API",
"description": "A very cool API",
"licence": "Apache 2.0",

"groupId": "com.cool.api",
"artifactId": "cool-api",
"version": "2.1.9",

"encoding": "UTF-8",
"source-java": "8",
"target-java": "8",

"dependencies": [{
  "groupId": "com.cool.spi",
  "artifactId": "cool-spi",
  "version": "1.0.0"
  },{
  "groupId": "com.cool.logging",
  "artifactId": "cool-logging",
  "version": "1.0.0"
  }]
}
{% endhighlight %}

A Java module that is defined by such a static structure must follow some best practices and basic rules that are well known from Maven and Gradle based project:

* All Java sources must be placed under src/main/java
* All resources like images or configuration files must be placed under src/main/resources
* All Java sources for unit tests must be placed under src/test/java
* All resources for unit tests must be placed under src/test/resources
* The static definition of the project must be defined in a UTF-8 based file in the root folder of the project. The file must be named „metadata.jmm“ (jmm stands for Java module metadata).

## Why should I use such a static description in my Java project?

Most of you will already use a build tool like Maven, Gradle or maybe Ant to define the build of a Java project. I think this is quite fine and should be used in future, too. But especially when using Gradle, which is the newest of the mentioned build tools, developers have so many possibilities to create a custom build file that normally each build works in a different way and it can be hard to understand the build process. In all this files the information about the build process (like a build script) and the metadata of a project are mixed. By encapsulating the metadata from the build it will be much easier to get a general overview of a module or a build. In addition each build file or script depends on the used build system. This means that a developer that always used Maven often can not read or interpret a Gradle build script. By defining the metadata in a tool independent way any developer can understand the information of any Java project as soon as he worked at least with one project that provide static metadata. But the metadata will not only offer a better readability for developers. Build tools could provide support to interpret the metadata. By doing so all information that is part of the metadata file should not be redefined in the build script. The build tool can directly use the information of the metadata file to build the project. By doing so a Gradle file only build a JAR file based on a module that has a static metadata description can look like this:

{% highlight java %}
apply plugin: 'java'
apply plugin: 'jmm'
{% endhighlight %}

This will be enough to compile all sources of the project, run all unit tests and build a JAR which name is created by the artifactId and version value of the metadata file.

Next to general build tools IDEs can provide support for the static module metadata. In this case you do not even need a build script. Based on the information that can be defined in the metadata an IDE can download all needed dependencies, compile the sources of the project and run all the unit tests. And this is only the beginning. Based on this approach it would be possible to scan all java projects at GitHub, GitLab and BitBucket and find the usage of a module. Providing a graphical overview of the transitive dependencies of a module will be easy, too. Instead of running a Gradle build to receive information of the dependencies any tool can simply parse the static metadata. Not even any build tool must be installed to do so. Next to build tools and IDEs several other tools like build servers will benefit by this approach.

Once the most important tools will offer support for static metadata the maintenance of mostly all Java projects will be much easier. Thinks like the version will be defined at only one central point and changing the version for a release will be really easy.

## Limitations of the approach

To be true static metadata will not work perfect for any project. Some projects need to generate sources at runtime or have dynamic dependencies. For such projects the static metadata might not be enough to compile the project. But even here such a metadata definition is not useless. Readable metadata like the name or the license can be specified and all non-dynamic dependencies can be part of the metadata file, too. This will end in a less code that is needed for the build file and tools that interpret the static metadata can at least work with a subset of the project description. And yes, this approach is currently not useable for projects that are based on another programming language like Kotlin or Groovy. But as said: this is just an initial idea and I would like to get your thoughts about this topic ;)
