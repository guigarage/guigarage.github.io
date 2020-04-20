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

Modul-System

Wie Dependencies bisher

![project dependency graph](/assets/posts/2020-04-20-module-requires/dependencies.png)

Maven Dependency code
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

Gradle Dependency code

{% highlight java %}
dependencies {
    compile 'com.external:module-a:1.0.0'
    compile 'com.external:module-b:1.0.0'
}
{% endhighlight %}

Was sind Module

Modul dependencies

![project module graph](/assets/posts/2020-04-20-module-requires/modules.png)

Wenn requires muss es auch auf den Classpath sein, da Anwerdnung sonst nicht startet.

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
