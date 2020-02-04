---
title: 'Change Java version on Mac OS'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: "I'm currently working with JDK 6, 7 and 8 on my MacBook and it's really a pain to switch between this Java version by shell. Because of that I created a tool that can do all the work for you."
featuredImage: java-8
permalink: '2013/02/change-java-version-on-mac-os/'
header:
  text: Change Java version on Mac OS
  image: sample
---
I'm currently working with JDK 6, 7 and 8 on my MacBook and it's really a pain to switch between this Java version by shell. Because of that I created a tool that can do all the work for you:

![cjv](/assets/posts/guigarage-legacy/cjv.png)

The tool shows all Java version that are currently installed on your system and you can switch between them just by a mouse click. The new version will work in any new shell and for every application that is started after switching the version. If you switch to JDK 8 and start Eclipse after that, the IDE will use JDK 8 as default JDK. If you need JDK 7 you have to restart Eclipse after switching the version. To set a new version the root password is needed because the tool will edit your "/env/launchd.conf" file.

You can download the tool as runnable JAR [here](/assets/downloads/java-version-manager/jvc.jar) or build it by your own. The sources are hosted at [github](https://github.com/guigarage/JavaVersionChanger).

## How does the tool work

As you can read [here](http://blog.hgomez.net/blog/2012/07/20/understanding-java-from-command-line-on-osx/) the "/usr/libexec/java_home -V" command will show you all installed Java versions. The tool executes the command and parses the output. All found versions are displayed in a JTable. When setting a new version the tool will update the "/env/launchd.conf" file and sets the JAVA_HOME environment in this file. Any old JAVA_HOME environment will be removed. Then the tool calls a shell command that will update the OS environment by using the launchd.conf. You can read more about this function [here](http://stackoverflow.com/questions/135688/setting-environment-variables-in-os-x). The root password is needed because "sudo" is needed to edit the "launchd.conf" file.

I hope the tool will be for be as useful as it is currently for me. If you have any problems, bugs or feature requests please fill a issue at github or write me a mail.
