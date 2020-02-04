---
title: 'invokeAndWait for JavaFX'
layout: post
author: hendrik
categories: [DataFX, General, JavaFX]
excerpt: "Swing offers the two methods SwingUtilities.invokeAndWait(...) and SwingUtilities.invokeLater(...) to execute a Runnable object on Swings event dispatching thread. Let's have a look how we can have the same functionallity in JavaFX"
featuredImage: java-5
permalink: '2013/01/invokeandwait-for-javafx/'
header:
  text: invokeAndWait for JavaFX
  image: sample
---
Swing offers the two methods `SwingUtilities.invokeAndWait(...)` and `SwingUtilities.invokeLater(...) to execute a Runnable object on Swings event dispatching thread. You can read more about this methods [http://javarevisited.blogspot.de/2011/09/invokeandwait-invokelater-swing-example.html](here).

As I currently know JavaFX provides only `Platform.runLater(...)` that is the equivalent of SwingUtilities.invokeLater(...). A "runAndWait" method doesn't exist at the moment. While developing some [DataFX]({{ site.baseurl }}{% link pages/projects/datafx.md %}) stuff and my [first Raspberry Pi demo]({{ site.baseurl }}{% post_url 2012-12-28-my-first-steps-with-javafx-on-raspberry-pi %}) I needed this feature in JavaFX. So I created a `runAndWait` method that will hopefully be part of DataFX in some future. Until then you can use this code in your project:

{% highlight Java %}
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;

public class FXUtilities {

    private static class ThrowableWrapper {
		Throwable t;
	}

	/**
	 * Invokes a Runnable in JFX Thread and waits while it's finished. Like
	 * SwingUtilities.invokeAndWait does for EDT.
	 * 
	 * @param run
	 *            The Runnable that has to be called on JFX thread.
	 * @throws InterruptedException
	 *             f the execution is interrupted.
	 * @throws ExecutionException
	 *             If a exception is occurred in the run method of the Runnable
	 */
	public static void runAndWait(final Runnable run)
			throws InterruptedException, ExecutionException {
		if (Platform.isFxApplicationThread()) {
			try {
				run.run();
			} catch (Exception e) {
				throw new ExecutionException(e);
			}
		} else {
			final Lock lock = new ReentrantLock();
			final Condition condition = lock.newCondition();
			final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
			lock.lock();
			try {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						lock.lock();
						try {
							run.run();
						} catch (Throwable e) {
							throwableWrapper.t = e;
						} finally {
							try {
								condition.signal();
							} finally {
								lock.unlock();
							}
						}
					}
				});
				condition.await();
				if (throwableWrapper.t != null) {
					throw new ExecutionException(throwableWrapper.t);
				}
			} finally {
				lock.unlock();
			}
		}
	}
}
{% endhighlight %}

It's working for all my needs. Please give me some feedback if there are any problems or bug.
