---
layout: post
title:  'Back to the future - with Java'
author: hendrik
featuredImage: java-5
excerpt: 'Each year several Java applications might show date information for the first dates of a year. This post gives an overview about the problem when using DateTimeFormatter and how you can easily avoid such problems'
categories: [Java]
header:
  text: A common problem with the date format
  image: sample
---
Since Java 8 we have a new date & time API as part of Java. The APi is really good and offers a lot of functionallity and flexibility when working with date and time informations. Most usecases can easily be implemented by understanding the basic API, the JavaDoc and maybe some help from Stackoverflow.

Let's have a look at a common usecase:

{% highlight java %}
final LocalDate myDate = LocalDate.of(2015, 11, 30);
final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
final String formattedDate = formatter.format(myDate);
System.out.println("The date is " + formattedDate);
{% endhighlight %}

Even if you have not used the API that often it's quite easy to understand that the code will print `The date is 30.11.2015` to the console. Based on this experience we can create a method like this:

{% highlight java %}
/**
* Prints the given date in the format that is normally used in europe.
* The format is described as 
* [day of month (2 digits)].[month of year (2 digits)].[year (4 digets)]
*
* @param date the date
*/
public static void printDate(final LocalDate date) {}
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
  final String formattedDate = formatter.format(myDate);
  System.out.println("The date is " + formattedDate);
}
{% endhighlight %}

What if I tell you that this code already contains a common problem, that I already seen in several projects within the last years. To understand that problem we should call the method with a set of
different dates:

* `LocalDate.of(2015, 11, 30)` results in `The date is 30.11.2015`
* `LocalDate.of(1992, 4, 12)` results in `The date is 12.04.1992`
* `LocalDate.of(2008, 12, 28)` results in `The date is 28.12.2008`
* `LocalDate.of(2021, 1, 1)` results in `The date is 01.01.2020`

If you realized the missmatch and you are not aware of the problem you might have reacted with WTF.

## It's not a bug, it's a feature

To be true the described behavior is not a bug of the JDK, it's really a feature that a lot of people are not aware of.