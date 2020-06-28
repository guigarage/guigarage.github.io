---
layout: post
title:  'The road to Java 11 - builds and licences'
author: hendrik
featuredImage: java-2
excerpt: 'This post is the first of our Java 11 posts that will introduce all needed information about the next Java release. In this post you can find all needed information about the free and commercial versions of Java 11.'
categories: [Java]
header:
  text: The road to Java 11
  image: sample
---
{% include elements/block.html text="This post was originally posted at [dev.karakun.com](https://dev.karakun.com)" %}

The next version of Java (Java 11) will be released on the 25th of September 2018. Several developers and companies plan to switch from Java 8 to Java 11 within the next month. Since Java 9 and Java 10 were the 2 first versions that are based on the new release train of Oracle a lot of people assume an LTS (Long term support) release of Java 11 by Oracle that they can easily use the next year in production.

![Duke](/assets/posts/2018-09-16-java-11-licence/duke-11.jpg)

While there are still some rumors and wrong assumptions about the new release train, the content of Java 11, and its pricing model we will have a deeper look at all these points in a series of blog posts.

In this first post, we will have a general look at the release model and license of Java 11 and any constraints that are based on these topics.

## Oracles new release and licence model for Java

When having a look at the current state of Java 11 you can already download an "early access" build. Here Oracle provides 2 different types of builds: "OpenJDK builds" and "Oracle JDK builds"

![Current Java 11 early access downloads](/assets/posts/2018-09-16-java-11-licence/download.png)

As you can see in the screenshot the 2 versions differ in the supported operating systems and the license. Once Java 11 is released you will be able to download the final release binaries for both builds types. It is very important to understand the difference between the offered versions. To be true there are no real differences in the functionality of these builds. Both will build upon the same sources (from OpenJDK) and will contain the same functions and Java APIs. The main differences will be the license that defines how and when the build can be used and the future support for these 2 builds.

### OpenJDK builds

* These binaries are build based on OpenJDK
* Like earlier Java versions you can simply use this builds everywhere (for example on a cloud server in production)
* These builds will be supported for only 6 months per Java version. Based on this you will not get any Java 11 updates after 6 months for the OpenJDK builds from Oracle. If you want to get access to security updates and bugfixes you need to switch to Java 12 in March 2019

### Oracle JDK builds

* These binaries are build based on OpenJDK
* If you do not refer commercial support by Oracle you **must not** use this builds in production. It's fine to use this builds for development and test environments but based on the license it will be forbidden to use them in any productive environment (without a commercial license)
* With a commercial license you can download and use updates of Java 11 till 2023. Based on this you will get access to the so-called LTS release of Java 11
* Without commercial support you will not get any Java 11 updates after 6 months for the Oracle JDK builds from Oracle. If you want to get access to security updates and bugfixes you need to switch to Java 12 in March 2019

Based on this information each developer needs to carefully decide which Java builds he wants to download and use. If you do not want to send any money to Oracle than choose the $free OpenJDK build. But be aware that it will not come with LTS support. You will be forced to upgrade every 6 months or run an outdated potentially vulnerable system. If you depend on LTS support then the OracleJDK build is for you. But it is not for free. Oracle will have you pay for this extra support they are offering.

## Think out of the box

For several years other vendors provide free and commercial builds for Java. Most developers may have already seen IBM, Azul, or Red Hat JDK somewhere in the wild. With the new Java release train, most of these companies updated their strategy, licenses, and business model for JDKs and provide several alternatives to Oracle's JDK. In [our article about the price and support models for Java]({{ site.baseurl }}{% post_url 2018-06-25-java-releases %}), you can find a good overview of the most popular vendors. Based on your development workflow and future plans, one of the Java builds from another vendor might suit you better than the Oracle one. Since all these builds are based on the same sources from OpenJDK and successfully passed the [TCK](https://en.wikipedia.org/wiki/Technology_Compatibility_Kit) you do not need to be afraid to use them. Most likely the number of Java vendors will increase because of the new Java release train. This will help developers and companies to find a release and license model that fits to their internal needs. And once you found "the perfect Java" for your - it's time to think again about creating cool applications, frameworks, and architecture.

Do not stress yourself with the changes in the Java ecosystem. Inform yourself about the alternatives next to Oracle within the next month and be prepared for the bright future of Java. By doing so you can be relaxed ;)

## Preview

In the next post, we will have a look at some features and functionality that were removed or have been changed between Java 8 and Java 11. Based on this we will see what kind of backward compatible Java 11 provides and how you can fix issues in that area.
