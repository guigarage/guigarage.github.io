---
title: 'Vagrant-Binding-Demos'
layout: post
author: hendrik
categories: [Vagrant-Binding]
excerpt: 'I created several demos to show the usage of Vagrant-Binding.'
featuredImage: linux-container-2
permalink: '2012/11/vagrant-binding-demos/'
header:
  text: Vagrant-Binding demos
  image: sample
---
I started a [demo project](https://github.com/guigarage/vagrant-binding-demos) for [Vagrant-Binding](https://github.com/guigarage/vagrant-binding). You can use the examples in this project to start your work with Vagrant-Binding. The [Vagrant-Binding-Demos](https://github.com/guigarage/vagrant-binding-demos) project is hosted at github.

Currently there is only one [example](https://github.com/guigarage/vagrant-binding-demos/blob/master/src/test/java/com/guigarage/vagrant/dbhandler/DbHandlerTest.java) showing the use of the VagrantBuilderRule with JUnit.

## DbHandler UnitTest

This [UnitTest](https://github.com/guigarage/vagrant-binding-demos/blob/master/src/test/java/com/guigarage/vagrant/dbhandler/DbHandlerTest.java) checks the functionality of the [DbHandler](https://github.com/guigarage/vagrant-binding-demos/blob/master/src/main/java/com/guigarage/vagrant/dbhandler/DbHandler.java) class. This class offers some simple methods for the work with a mysql database. You can connect to a existing mysql server by JDBC and create some dummy data in it. The UnitTests checks this functions. Normally a UnitTest like the given on has some problems:

* The mySQL server must be available
* The tables must be empty at the start of the test
* you can not run the test parallel to other UnitTests that use the database or the same tables

For this type of UnitTests people often use a in-memory database like Derby or H2. But sometimes you have to use a specific database. With the Vagrant-Binding you can exclude all the mentioned problems. Vagrant-Bindings offers a JUnit Rule for you that works like a wrapper around your UnitTests. With the [VagrantTestRule](https://github.com/guigarage/vagrant-binding/blob/master/src/main/java/com/guigarage/vagrant/junit/VagrantTestRule.java) you can sync all your tests with the lifecycle of virtual machines. In the DbHandler example a VagrantTestRule is defined with the configuration of a vm that runs MySQL server on it. The vm will be created and started before every test. When the test finishes the vm will be destroyed. So you can run your tests in parallel because every one uses its own virtual machine. The vm is created on startup with a new and empty MySQL Server on it. So you will never have a problem with old data again. The vm for this test is created by a [Vagrant](http://vagrantup.com/) / [Puppet](http://puppetlabs.com/) config that is created by Java code.

You can check out a [more detailed documentation on github](https://github.com/guigarage/vagrant-binding-demos).

Hope you like it.
