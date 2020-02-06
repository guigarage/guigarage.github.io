---
layout: post
title:  'How AdoptOpenJDK provides enterprise ready OpenJDK builds'
author: hendrik
featuredImage: adopt-2
excerpt: 'TODO.'
categories: [Java, AdoptOpenJDK]
header:
  text: AdoptOpenJDK test suite
  image: sample
---

Since last year I'm part of the [AdoptOpenJDK](https://adoptopenjdk.net) technical steering committee. Since AdoptOpenJDK provides free OpenJDK distributions and has already way over **150.000.000 downloads** I would say that the project is a great success and brings a really important benefit to the Java community by providing open and free builds of the OpenJDK. By doing so you have a really good alternative next to downloading (and paying for) Oracle Java. If you want to know more about AdoptOpenJDK and other OpenJDK distributions you should [read this post]({{ site.baseurl }}{% post_url 2018-06-25-java-releases %}). If you have more questions about AdoptOpenOpenJDK just ping me or ask us directly in the [AdoptOpenJDK Slack](https://adoptopenjdk.slack.com/).

![AdoptOpenJDK logo](/assets/posts/2019-12-13-adopt-tests/adopt-logo.png){:class="image-two-third-width"}

In this article I will gives an overview about the test infrastructure of AdoptOpenJDK and describe the different types of tests that will be executed for each release. All this ends in a really well tested ditribution that is ready to used for the enterprise. 

{% include elements/block.html text="As the world of testing at AdoptOpenJDK is evolving and improving quickly, some documentation may fall behind the march of progress. The most updated version can always be found [in our github repo](https://github.com/AdoptOpenJDK/openjdk-tests)" %}

## Guide to the test jobs at AdoptOpenJDK

For all nightly and release builds, there are test jobs running as part of the [AdoptOpenJDK continuous delivery pipelines]((ci.adoptopenjdk.net)). For the test step of a build all tests are grouped by its type. Currently the tests of AdoptOpenJDK are splitted in 6 different groups / types. When running the test phase on our CI servers the defined groups will be executed in parallel. All this tests are defined in the open source test framework AQA that is provided by AdoptOpenJDK and can be found [here](https://github.com/AdoptOpenJDK/openjdk-tests). The following image shows the simplified pipeline of the AdoptOpenJDK builds and all the different test groups that are part of the test step in each build:

![ci pipeline](/assets/posts/2019-12-13-adopt-tests/ci-pipeline.png)

Additional information about the test concept and the integration in the delivery pipeline can be found [at this blog post](https://blog.adoptopenjdk.net/2017/12/testing-java-help-count-ways).

### Test groups

As already mentioned all test of AdoptOpenJDK are grouped based on its type in 6 different groups. The following table gives a first overview about the different test types:
 
 {:.table}
 | name       | Type                          | Description                                           | Documentation           |
 | ---------- | ----------------------------- | ----------------------------------------------------- | ----------------------- |
 | openjdk    | OpenJDK regression tests      | Tests from OpenJDK                                    | [link](TO_GROUP_README) |
 | system     | System and load tests         | Tests from the AdoptOpenJDK/openjdk-systemtest repo   | [link](TO_GROUP_README) |
 | external   | 3rd party application tests   | Test suites from a variety of applications, along with microprofile TCKs, run in Docker containers | [link](TO_GROUP_README) |
 | perf       | Performance benchmark suites  | Performance benchmark tests (both full suites and microbenches) from different open-source projects such as Acme-Air and AdoptOpenJDK/bumblebench | [link](TO_GROUP_README) |
 | functional | Unit and functional tests     | Functional tests not originating from the openjdk regression suite, that include locale/language tests and a subset of implementation agnostic tests from the openj9 project. | [link](TO_GROUP_README) |
 | jck        | Compliance tests              | TCK tests (under the OpenJDK Community TCK License Agreement), in compliance with the license agreement.  While this test material is currently not run at the AdoptOpenJDK project (see the [support statement](https://adoptopenjdk.net/support.html#jck) for details), those with their own OCTLA agreements may use the AdoptOpenJDK test automation infrastructure to execute their TCK test material in their own private Jenkins servers. | [link](TO_GROUP_README) |
 
As you can see next to the OpenJDK tests we added a lot of additional tests. The OpenJDK regression tests are a great start to test a JDK, but eventually you may want to be able to test the performance of your code, and whether some 3rd party applications still work. Here all the other test types comes in play. This does not only add performance tests and additional general unit tests. Next to those the test framework contains test suites from several big players from the Java ecosystem to directly check the accurate functionallity of important frameworks with the AdoptOpenJDK builds. The test suite for example executes all tests from the [Apache Tomcat](http://tomcat.apache.org) project and all TCKs (Test compatibilty kit) of the [Eclipse MicroProfile](https://microprofile.io).

TODO: Image would be good here...

TODO:
Text for Java TCK and usage for other Java distributions.
By using an intentionally thin wrapper around a varied set of tests, we can more
easily run all types of tests via make targets and as stages in our Jenkins CI pipeline builds.

Benefits of AQA:
- better, more flexible tests, with the ability to apply certain types of testing to different builds
- a common way to easily add, edit, group, include, exclude and execute tests on AdoptOpenJDK builds
- the latitude to use a variety of tests that use many different test frameworks
- test results to have a common look & feel for easier viewing and comparison


## Guide to running the tests yourself

Thanks to AQA you can even run all the tests by yourself on your own system and test any OpenJDK distribution. As already mentioned several other companies that provide OpenJDK distributions already use AQA to get a better test coverage and quality for their commercial OpenJDK distributions. For more details on how to run the same tests as AdoptOpenJDK on your laptop or in your build farm, please consult the [official user guide](https://github.com/AdoptOpenJDK/openjdk-tests/blob/master/doc/userGuide.md).
