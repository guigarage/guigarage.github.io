---
title: 'Dolphin Platform & KumuluzEE: JavaEE Microservices with dynamic and rich frontends'
layout: post
author: hendrik
categories: [General]
excerpt: 'TODO'
featuredImage: sample-9
permalink: '2015/11/dolphin-platform-kumuluzee-javaee-microservices-with-dynamic-and-rich-frontends/'
header:
  text: Dolphin Platform & KumuluzEE
  image: sample
---
Have you heard of [KumuluzEE](https://ee.kumuluz.com) that is one of the 2015 Java Duke's Choice Award winners? Well, if you haven't heard about this cool project you should have a look. KumuluzEE is a JavaEE based framework to create microservices and JavaEE based applications that can start on their own without deploying them to an application server. If you know [Spring Boot](http://projects.spring.io/spring-boot/) the description is quite easy: KumuluzEE is Spring Boot for the JavaEE framework. In this post I will show how you combine kumuluzEE with [Dolphin Platform](http://www.dolphin-platform.io) to create lightweight microservices with a dynamic frontend.

## KumuluzEE overview

When using KumuluzEE you can work with all the JavaEE specs that you already know. Let's say you want to create an application that provides some REST endpoints and uses CDI internally. To do so you need to add the following dependencies to your project:

{% highlight xml %}
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-core</artifactId>
  <version>${kumuluzee.version}</version>
</dependency>
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-servlet-jetty</artifactId>
  <version>${kumuluzee.version}</version>
</dependency>
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-cdi</artifactId>
  <version>${kumuluzee.version}</version>
</dependency>
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-jax-rs</artifactId>
  <version>${kumuluzee.version}</version>
</dependency>
{% endhighlight %}

Let's have a deeper look what this dependencies define and add to your project:

* The `kumuluzee-core` dependencies is the basic dependency of KumuluzEE and must be added to each project
* The `kumuluzee-servlet-jetty` dependencies adds an embedded jetty. This includes the implementation of the servlet API. If you only want to code based on servlets you are done now.
* The `kumuluzee-cdi` dependencies adds CDI support to you application. Once this dependency is on your class path you can use CDI in your server application.
* The `kumuluzee-jax-rs` dependencies adds support for JAX-RS. By adding this dependency you can add REST endpoints to your application.

Once this is done you can create your (microservice) JavaEE application. You can use all the defaults of the specs that are added as dependencies. The following code shows how a REST endpoint might look like:

{% highlight java %}
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class OrdersResource {

    @Inject
    private MyService service;

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Integer id) {
        BookOrder o = service.find(BookOrderid);
        if (o == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(o).build();
    }
}
{% endhighlight %}

To start your app kumuluzEE adds the class `com.kumuluz.ee.EeApplication` that contains a default main method. By starting the application your endpoints will be up faster than you can spell "weblogic".

## KumuluzEE and Dolphin Platform

Since KumuluzEE supports all the JavaEE specs that are needed for the Dolphin Platform it's quite easy to create a Dolphin Platform application based on KumuluzEE. To do so you only need to add the following dependencies to your application:

{% highlight xml %}
 <dependency>
  <groupId>com.canoo.dolphin-platform</groupId>
  <artifactId>dolphin-platform-server-javaee</artifactId>
  <version>0.7.0-SNAPSHOT</version>
</dependency>
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-core</artifactId>
  <version>1.0.0</version>
</dependency>
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-servlet-jetty</artifactId>
  <version>1.0.0</version>
</dependency>
<dependency>
  <groupId>com.kumuluz.ee</groupId>
  <artifactId>kumuluzee-cdi</artifactId>
  <version>1.0.0</version>
</dependency>
{% endhighlight %}

Please notice that the feature is still in development and will be part of Dolphin Platform 0.7.0 ;)

Once this is done you can start to create your Dolphin Controllers that will automatically be managed by JavaEE and support features like CDI.

{% highlight java %}
@DolphinController
public class MyController {

    @DolphinModel
    private MyModel model;
    
    @Inject
    private MyJavaEEService service;

    @PostConstruct
    public void onInit() {
        System.out.println("Init");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroyed");
    }

    @DolphinAction
    public void someAction() {
        System.out.println("Action");
    }
}
{% endhighlight %}

## Conclusion

Thanks to KumuluzEE you can create lightweight Dolphin Platform applications by using the enterprise framework APIs that you might now and like since years. The best: Since you don't use specific JavaEE or Spring code in your Dolphin Platform controllers they will work in both Spring and JavaEE. Just add the right dependency and your controllers will be automatically managed by the container. In my last tests both implementations bootstrapped in under 7 seconds :)
