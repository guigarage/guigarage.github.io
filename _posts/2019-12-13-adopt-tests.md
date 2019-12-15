---
layout: post
title:  'TODO'
author: hendrik
featuredImage: sample-2
excerpt: 'TODO.'
categories: [Java, AdoptOpenJDK]
header:
  text: TODO
  image: sample
---

This document gives an overview about the test infrastructure of [AdoptOpenJDK](https://adoptopenjdk.net).

## Important note about this document

As the world of testing at AdoptOpenJDK is evolving and improving quickly, some documentation may fall behind the march of progress.
Please let us know and help us keep it up-to-date, and ask questions at the [AdoptOpenJDK testing Slack channel](https://adoptopenjdk.slack.com/messages/C5219G28G).

## Guide to the test jobs at AdoptOpenJDK

For nightly and release builds, there are test jobs running as part of the [AdoptOpenJDK continuous delivery pipelines]((ci.adoptopenjdk.net)).
The following graph gives a simplified overview of the CI pipeline:

PIC

For the test step of a build all tests are grouped by its type. Currently the tests of AdoptOpenJDK are splitted in 6 different groups. When running the test phase the defined groups will be executed in parallel.

![ci pipeline](/assets/posts/2019-12-13-adopt-tests/ci-pipeline.png)


Additional information about the test concept and the integration in the delivery pipeline can be found [at this blog post](https://blog.adoptopenjdk.net/2017/12/testing-java-help-count-ways).

### Test groups

The directory structure in this repository is meant to reflect the different types of tests. The following table gives a first overview about the different test types:
 
 {:.table}
 | name       | Type                          | Description                                           | Documentation           |
 | ---------- | ----------------------------- | ----------------------------------------------------- | ----------------------- |
 | openjdk    | OpenJDK regression tests      | Tests from OpenJDK                                    | [link](TO_GROUP_README) |
 | system     | System and load tests         | Tests from the AdoptOpenJDK/openjdk-systemtest repo   | [link](TO_GROUP_README) |
 | external   | 3rd party application tests   | Test suites from a variety of applications, along with microprofile TCKs, run in Docker containers | [link](TO_GROUP_README) |
 | perf       | Performance benchmark suites  | Performance benchmark tests (both full suites and microbenches) from different open-source projects such as Acme-Air and AdoptOpenJDK/bumblebench | [link](TO_GROUP_README) |
 | functional | Unit and functional tests     | Functional tests not originating from the openjdk regression suite, that include locale/language tests and a subset of implementation agnostic tests from the openj9 project. | [link](TO_GROUP_README) |
 | jck        | Compliance tests              | TCK tests (under the OpenJDK Community TCK License Agreement), in compliance with the license agreement.  While this test material is currently not run at the AdoptOpenJDK project (see the [support statement](https://adoptopenjdk.net/support.html#jck) for details), those with their own OCTLA agreements may use the AdoptOpenJDK test automation infrastructure to execute their TCK test material in their own private Jenkins servers. | [link](TO_GROUP_README) |
 
## Why are all this test types needed?

The OpenJDK regression tests are a great start to test a JDK, but eventually you may want to be able to test the performance of your code,
and whether some 3rd party applications still work. We will begin to incorporate more types of testing, including:

- additional API and functional tests
- stress/load tests
- system level tests such as 3rd party application tests
- performance tests
- TCK tests

The test infrastructure in this repository allows us to lightly yoke a great variety of tests together to be applied to
testing the AdoptOpenJDK binaries. By using an intentionally thin wrapper around a varied set of tests, we can more
easily run all types of tests via make targets and as stages in our Jenkins CI pipeline builds.

## Guide to running the tests yourself

For more details on how to run the same tests that we run at AdoptOpenJDK on your laptop or in your build farm, please
consult our [User Guide](doc/userGuide.md).

#### What is our motivation?

We want:
- better, more flexible tests, with the ability to apply certain types of testing to different builds
- a common way to easily add, edit, group, include, exclude and execute tests on AdoptOpenJDK builds
- the latitude to use a variety of tests that use many different test frameworks
- test results to have a common look & feel for easier viewing and comparison

There are a great number of tests available to test a JVM, starting with the OpenJDK regression tests.
In addition to running the OpenJDK regression tests, we will increase the amount of testing and coverage by pulling in
other open tests. These new tests are not necessarily written using the jtreg format.

#### How can you help?

You can:
- browse through the [openjdk-tests issues list](https://github.com/AdoptOpenJDK/openjdk-tests/issues), select one, add a comment to claim it and ask questions
- browse through the [openjdk-systemtest issues](https://github.com/AdoptOpenJDK/openjdk-systemtest/issues) or [stf issues](https://github.com/AdoptOpenJDK/stf/issues), claim one with a comment and dig right in
- triage live test jobs at [ci.adoptopenjdk.net](https://ci.adoptopenjdk.net), check out the [triage doc](https://github.com/AdoptOpenJDK/openjdk-tests/blob/master/doc/Triage.md) for guidance
  - if you would like to regularly triage test jobs, you can optionally 'sign up for duty' via the [triage rotas](https://github.com/AdoptOpenJDK/openjdk-tests/wiki/AdoptOpenJDK-Test-Triage-Rotas)
- ask questions in the [#testing channel](https://adoptopenjdk.slack.com/messages/C5219G28G) 