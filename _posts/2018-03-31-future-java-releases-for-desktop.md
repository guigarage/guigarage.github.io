---
title: 'What future Java releases mean for legacy desktop apps'
layout: post
author: hendrik
categories: [General]
excerpt: "Java 11 will mean the end of Java Web Start. Let's see what this means for legacy desktop apps and how developers can be ready for the change."
featuredImage: sample-6
header:
  text: Future of Java Desktop
  image: sample
---
{% include elements/block.html text="This post was originally posted at [DZone](https://dzone.com/articles/what-the-future-java-releases-will-mean-for-legacy)" %}

This article will give an overview of the changes in future Java versions based on the new release train and Oracle's decision to remove deprecated and old parts of the JRE. Since some of the removed parts are critical for developers that create desktop applications with Java, this article will have a deeper look at the planned changes in that area. One thing that will affect several companies and developers is the planned removal of Java Web Start with Java 11. Based on this, the article will give an overview of future Java Web Start support.

## The New Java Release Train

Some months ago, Oracle introduced the new release cadence for Java. As a result, the Java ecosystem will become much more agile, since we will get a new major Java release every 6 months. Thus, new Java features will be usable much faster. A perfect example is the usage of var that was introduced in Java 10.

Most Java developers haven’t played that much with Java 9, released 6 months ago. Big new features like modularity were introduced with that version, and now Java 10 has already been released, adding several new features. Based on that, Java developers need to learn new functionality and features much faster if they want to stay updated. While this approach is awesome for new projects with small microservices that can easily be adopted to new Java versions, it will still be hard for big legacy projects. But maybe this project won’t update to each new Java version. Here Oracle will provide LTS (long-term support) releases of Java that will provide a longer support time. So while the lifetime of Java 9 and Java 10 is just 6 months, the release of Java 11 in September 2018 will be the first new LTS release.

![Java release train](/assets/posts/2018-03-31-future-java-releases-for-desktop/releases.png)

Based on that, Oracle will support Java 11 for several years and provide updates and security patches for that version. I will write about the concrete approach and limitations of LTS releases later in this article.

### How Oracle Cleaned Up the JRE

One year ago, there was a big discussion in the Java community, after Oracle announced it was removing parts of the JRE. Until Java 8, the only way to wipe away unwanted old code in the JRE was by using the `@Deprecated` annotation. With Java 9, Oracle started to remove parts of Java actively. Here, the biggest discussions were about the sun.misc.Unsafe class. This class is not part of the JCP standard that is defined by all classes under the `java.*` and `java.*` packages and therefore, normally, no developer should use them. But even some of the real standard functionalities were removed in Java 9, such as these methods of the `java.util.logging.LogManager` class:

* `java.util.logging.LogManager.addPropertyChangeListener`
* `java.util.logging.LogManager.removePropertyChangeListener`

I fully understand the reason why some parts must be removed to create a modular Java that is ready for the future. The LogManager methods were rarely used and therefore a migration is not that hard.

But starting with Java 9, Oracle continued removing deprecated things that just blow up the JRE. In Java 10, methods of the java.lang.Runtime and java.lang.SecurityManager classes were removed, for example. Based on this, using deprecated functionality of the JRE is completely different than it was 5 years ago. Today, a Java developer must try more actively to not use such APIs and to refactor legacy code to not use them anymore. Maybe that API will be removed in one of the next Java versions and with the new release train, we do not talk about debates when thinking about the next 2-3 versions.

## What Does All This Mean for a Desktop Developer?

If you are working in a team that created applications with Swing or JavaFX frontends for some years, you might think that the given information is not really relevant to you. In that case, you are totally wrong. Starting with Java 11, which will be released in September this year, several important parts that are highly relevant for desktop development with Java will be removed from the JRE:

* Java Applets
* Java Web Start
* JavaFX

A full overview of the changes can be found in the [official Java client roadmap](http://www.oracle.com/technetwork/java/javase/javaclientroadmapupdate2018mar-4414431.pdf) that was published by Oracle in March.

I won’t talk that much about Applets, since I really hope that they are gone in mostly all legacy projects. Already, browser vendors have stopped support or at least announced to stop the support for plugins like Applets, Flash, or Silverlight. The other 2 points on the list are quite a bit more important. While several articles have been written about JavaFX and the absence of the UI toolkit in Java 11, I would love to concentrate on Java Web Start in this article. I plan to write a similar one for JavaFX (and maybe Swing/AWT) once more things in this area are clear and the first decisions of JavaFX's future are made. Currentl,y such an article would more or less contain only assumptions.

### What Does This Mean for Java Web Start-Based Clients?

Well, for all companies that maintain some Java Web Start-based applications in the wild, this is really bad news. If you do not control the Java versions of the machines that run the client, you could get in real trouble. Once Java 11 is released in September this year and users install it as the default JRE on any machine, Java Web Start cannot be used anymore.

While I've tried to avoid that technology for years, I know a lot of companies that are running Web Start-based projects. Some use them use Web Start only in internal applications and can control the JRE version that is installed on the clients. Other companies provide Web Start-based applications to several customers. Here, it is mostly impossible to control the JRE versions of all clients at several customers.

As you can see, some application developers might get in trouble and need to find a way to remove Web Start or to make all clients stay on an old Java version. For big legacy applications, this can end in a lot of work, and some companies already know today that it won't be possible to remove the usage of Java Web Start before autumn this year.

Thus there are two scenarios that make sense for Java Web Start-based applications:

* Take care of the installed Java versions on the client. As a result, you will have more time to refactor your application.
* Remove Java Web Start as fast as possible and be ready for Java 11.

Let's have a look at both scenarios and see what you can do to migrate away from Java Web Start.

### Java Web Start and LTS

As already mentioned, Java Web Start will be removed with Java 11. Sadly, Java 11 is the next Java release that will provide long-term support. Both Java 9 and Java 10 will only provide updates and support for 6 months. Even if you are willing to buy Java support at Oracle, you won't get any further updates for these Java versions.

Even with Java 11, the LTS will work completely different than the way you know it from old Java versions like 6, 7, or 8. For all LTS versions of Java that will be released in the coming years, free support is only given for 6 months. So if you won't buy commercial support from Oracle and want to get Java updates with bugfixes and security fixes, you need to change the Java version every 6 months.

For LTS versions like Java 11, Oracle will provide commercial support for several years. This is a big change from old Java versions that contained free support for a long period, even after the following version was released. And that "old" version can be a rescue for some Web Start-based projects. Against Java 9 and 10, which only provide support for 6 months, Java 8 is still supported. The free support of Java 8 will end in January 2019. So if you are running a Web Start application on Java 8 and control the installed JREs on clients, you will have until the end of the year to update your application without the need of commercial support.

![release train](/assets/posts/2018-03-31-future-java-releases-for-desktop/releases-8.png)

But even if you will need more time, there is still a way — since Oracle will provide commercial support for Java 8 until 2025. I think most Java developers never cared about commercial support for Java, since the free lifetimes of all previous Java versions were long enough to do an update to the newest version easily. Starting with the new release cadence and issues like the removal of Web Start, this might change for some companies. Based on this, I will give a short introduction about the commercial support of Java.

## Commercial Support for Java

Since I'm not an Oracle employee and information about commercial support is quite hard to find, I can only give an overview of the points that I have found so far. Fortunately, Wolfgang Weigend from Oracle helped get some important information about this topic.

When talking about commercial support of Java, there are two different models that you can choose from:

* CPU-based license
* Named User Plus license

The CPU-based license makes sense if you use Java on the server. In that case, you will pay for each CPU on the server machine that is running your Java application. For Java-based desktop applications, this does not make sense. Here the NUP (Named User Plus) license must be used. With this license, you need to pay for each user that is using your Java application. In reality, this means that you will pay for each client machine.

Let's say you have a Web Start-based application that is used by 100 clients. Even if, normally, only 20 clients will run in parallel, you need to pay for the 100 machines. The good point is that such a license isn't that expensive. The concrete price depends on the country your company is located in, but in general, it will be something between $30-40 per year. With such a license, you will get bugfix and security updates of all supported Javas versions about 4 times a year. If you maintain a Java Web Start-based application with 100 clients and you do not want to give up Java security releases, you can buy commercial support for about $3,500 each year.

When talking about commercial support for Java, I think that it's important to mention that Oracle is not the only company that provides such support. Azul, for example, provides support for the [Zulu distribution](https://www.azul.com/products/zulu-and-zulu-enterprise/). Zulu is a certified build of OpenJDK and can easily be used instead of the Oracle JRE. One big benefit that Zulu brings is that it will support future non-LTS versions longer than Oracle. Unfortunately for companies that use Java Web Start, Zulu is not a real option, since it never contained Web Start.

Even if you can control the installed JREs on all the clients and are willing to pay commercial support, you should start to create a plan how to remove the usage of Java Web Start. If the release train of Java doesn't change, Java 23 will be out in 2025, and you really don't want to be on version 8 anymore. Based on this, it's time to discuss how Web Start can be replaced.

### Get rid of Java Web Start

There is no simple "one and only" cookbook that helps you migrate away from Java Web Start. At the moment, there is no tool or technology that provides the same features that Java Web Start does. One big benefit of Web Start was the easy usage, since parts of Web Start are bundled in the native executables of a JRE.

Based on that, a JNLP file that was downloaded by a browser was automatically started and interpreted by Java. Since Web Start will be removed, this isn't possible anymore in the future. As a result, any successor of Web Start needs at least a part that must be installed on the client. Another way is via native OS tools that are already often used in companies to install and manage software on client machines. An example are Active Directory Group Policy Objects, which can be used to install and maintain software on Windows clients.

Since I'm not an administrator, I have more or less no experience in this area. But some customers work exactly this way and already distribute native Java builds this way. Such native builds can easily be created by using the javapackager. This tool is part of Java and can be used to create native bundles of an application that contains the application code and a bundled JRE. By doing so, a developer can easily define the JRE that should be used to run the software and on the client machine — no Java installation is needed to run the application.

Since this approach is really easy to use and could be a good workflow to get rid of Web Start, it is actually unclear if the javapackager will be part of Java 11. As already said, Java 11 will not contain Applets, Web Start, and JavaFX anymore. The javapackager is defined as part of JavaFX, since it was developed together with JavaFX.

Although the tool is not JavaFX-dependent and can easily be used to create a native bundle for Swing, AWT, or command-line-based applications, the history of the tool might be the cause of its removal with Java 11. So at the moment, it is unclear if javapackager is the tool to use.

Sometimes Oracle mentions 'jlink', which was introduced with Java 9 and provides a way to create a JVM that only contains the functionality that is needed for a specific application by only including the needed modules. At the moment, it is not possible to create a real native bundle by using jlink. So if you want to use jlink, you need to write a batch script that starts your application. A good example of jlink can be found [here](https://github.com/steve-perkins/jlink-demo).

Next, there are several third-party tools that help you create a native bundle out of your Java application like [install4J](https://www.ej-technologies.com/products/install4j/overview.html), [JWrapper](https://www.jwrapper.com/), or [IzPack](http://izpack.org/). All these approaches work quite well if your customer has its own custom solution to distribute applications and application updates to all clients.

If you want to create a general solution for your Web Start-based application that works independently of the administration of your customers there are some open source projects that provide functionality that is similar to Web Start. Examples are [UpdateFX](https://github.com/vinumeris/updatefx) or [GetDown](https://github.com/threerings/getdown). Sure, all these approaches do not provide all functionalities of Web Start, and some of them are not really well-maintained. From my point of view, the perfect successor of Web Start is still not there. I think such a tool should provide the following features:

* Small and native client tool that only needs to be installed once
* The tool should be able to manage multiple applications
* The tool should be able to automatically download updates for applications
* The tool should support the Java security manager
* The tool should support the check for signed JARs
* The tool should be able to manage installed JREs and install custom/additional JREs
* The tool should be able to download JREs from a defined endpoint
* An application can specify a version range or a special version for the JRE that should be used
* An application can specify a custom JRE that was created by jlink for the application.
* The tool should be able to install application shortcuts in the OS

From my perspective, currently no tool supports all these points. Hopefully, someone will see the current situation as a chance to provide and maintain such a tool. Otherwise, the developer community for Java-based desktop applications will be divided into endless custom made solutions.

## A Bright Future?

As I mentioned earlier, several points about the future of the Java desktop APIs are currently in discussion, and I really hope that a foundation will be formed in the next months that will take care of the future of JavaFX, Swing, and AWT. Maybe a general tool that will supersede Java Web Start can be created and maintained by such an organization.

But as we can see with Jakarta EE, creating such organization will really take some time, and the community should start today to be prepared for the future. I already had several discussions about that topic with other Java leaders during the last few weeks and continued to contribute to this topic. If you want to know more about that topic, feel free to ping me on Twitter. As soon as the first real solution is found in this area, I will write an additional article about that topic.

## Conclusion

As you can see, several points are not really clear at the moment, and the future of the Java desktop APIs needs to be discussed and formed. The only thing that is fixed today is how the Oracle JRE will be structured and released in the next years. As a consequence, it is clear that the Java Desktop APIs and features will become less important for Oracle.

And even if a strong open source foundation can be formed to continue to work on a Java desktop framework — and maybe even do it much better than Oracle did during these last few years — there is one feature that will disappear anyway: Java Web Start. So if you are developing an application that makes use of this technology today, you should really take care to find a solution within the next month, whether it is a direct replacement or a long-term issue in additional commercial Java support.
