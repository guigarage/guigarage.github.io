---
title: 'The JavaOne Voting Machine'
layout: post
author: hendrik
categories: [IoT, JavaFX]
excerpt: 'If you visited JavaOne this year you should have noticed the Voting Machines that were placed at each session room at JavaOne. In this post I will share some insides about the creation of the machines.'
featuredImage: voting
permalink: '2016/01/the-javaone-voting-machine/'
header:
  text: The JavaOne Voting Machine
  image: sample
---
If you visited JavaOne this year you should have noticed the Voting Machines that were placed at each session room at JavaOne.

![Logo](/assets/posts/guigarage-legacy/voting-logo.png)

By using this machines you had the chance to give a vote for the current session. If you liked the session you simple pressed the green Duke, if you didn't liked the presentation you choosed the red Duke and if it was quite ok you selected the Duke in the middle.

![dukes](/assets/posts/guigarage-legacy/dukes-1024x257.png)

Here you can see an image of a final machine at JavaOne:

![action](/assets/posts/guigarage-legacy/action-1024x706.png)

In this post I will show how we created the Voting Machines and the problems and pitfall that Michael and I were confronted with.

## The concept

From the beginning it was clear to us that we use JavaFX to create the Voting Machine UI. We had the idea with the animated Dukes in our first brainstorming session and so the main concept was created in only a few hours. After that I created some raw drawings off the concept:

![drawings](/assets/posts/guigarage-legacy/drawings.png)

Based on this we had defined some main tasks:

* Find a good designer and create the Duke animations
* Create the JavaFX UI
* Create a cloud based server application to store the data
* Find some hardware that will work for this project
* Create cover for the machine

## The Duke animations

We found a good designer that already had some knowledge in drawing Duke since he did it several times for projects like [DukeScript](https://dukescript.com). Together with the designer we defined the animation and in the end we had over 3 minutes of animations based on more than 4800 images.

You can find a short example how these animations look like in this YouTube video:

{% include posts/youtube.html id="GTFJ_1CoeU" %}

Even if the designer already drew some Duke images he misunderstood the face of Duke and thought Duke is a Cyclops as you can see in this first preview of the sleeping animation:

![schlaf1](/assets/posts/guigarage-legacy/schlaf1.png)

## The Cover

We wanted to have a cover for the voting machine that hides the unused area of the screen. To do so we started with covers that were made by cardboard. It was quite easy to define the right size for all areas since it took only some minutes to create anew cover. At this stage we didn't want to have a cover that looks good. We wanted to define the physical dimension of all visual areas and how they should be sized and placed in JavaFX.

![PAPER](/assets/posts/guigarage-legacy/PAPER.png)

Once all dimensions were defined we ordered a first prototype based on plastic. Sadly something went wrong with the communication with the producer and some of the dimensions were wrong:

![bla](/assets/posts/guigarage-legacy/bla.png)

After we corrected the dimensions we ordered the final cover based on a much better material.

![vm](/assets/posts/guigarage-legacy/vm.png)

## The Hardware

We used a Raspberry Pi to run the JavaFX application on the Voting Machine. It's quite easy to create a touch based JavaFX application for the Pi by using [this special JavaFX Embedded build that is provided by Gluon](http://gluonhq.com/open-source/javafxports/downloads/). In addition we found a [large touch monitor](http://www.amazon.com/gp/product/B00X7ZSCYQ?refRID=7K72H1XSQCA86DBMYDMD&amp;ref_=pd_ybh_a_21) that works perfectly with touch on Linux. Well, to be true the European versions worked perfectly. After we installed the Weezy image on the Pi we didn't need to do anything to receive touch events in Linux. For the US version of the monitor we needed to reconfigure Linux that cost us more than a day.

## The Software

Managing more than 4000 images on a Pi isn't that easy ;) We used a lot of tricks to create an interactive UI with smooth animations. I don't want to go deep into the details since Michael did most of the performance tweaks but at the end we found a good mix of in-memory images and lazy loaded images to create the final smooth UI.

Next to the software that runs on the Voting Machines, we created a fully animated JavaFX leaderboard application that was shown on big screens at JavaOne:

![screen](/assets/posts/guigarage-legacy/screen.png)

## The Manufacturing

The days before JavaOne Michael and I built all the Voting Machine for JavaOne in a Hilton Meeting Room. Here are some impressions that show how we build our embedded robot army:

![expressions](/assets/posts/guigarage-legacy/voting-machine-expressions.png)

## The Feedback

We got a lot of positive feedback at JavaOne and several people twittered about the Voting Machines:

<blockquote class="twitter-tweet" data-lang="de"><p lang="en" dir="ltr">RT <a href="https://twitter.com/JavaOneConf?ref_src=twsrc%5Etfw">@JavaOneConf</a>: Give feedback at <a href="https://twitter.com/hashtag/JavaOne?src=hash&amp;ref_src=twsrc%5Etfw">#JavaOne</a> each session room has a voting machine inside! <a href="https://t.co/3OcwKdSTKm">https://t.co/3OcwKdSTKm</a> <a href="https://t.co/bTQR6TkNhE">pic.twitter.com/bTQR6TkNhE</a></p>&mdash; JRebel, XRebel by Perforce (@JRebel_Java) <a href="https://twitter.com/JRebel_Java/status/658668303414640640?ref_src=twsrc%5Etfw">26. Oktober 2015</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

<blockquote class="twitter-tweet" data-lang="de"><p lang="en" dir="ltr">Vlad is play guitar hero. Is beating my high score. Is not nice. <a href="https://twitter.com/hashtag/JavaOne?src=hash&amp;ref_src=twsrc%5Etfw">#JavaOne</a> <a href="https://t.co/ubDb2d4JRA">pic.twitter.com/ubDb2d4JRA</a></p>&mdash; The Only One Borat (@CodeOneBorat) <a href="https://twitter.com/CodeOneBorat/status/659180143479406592?ref_src=twsrc%5Etfw">28. Oktober 2015</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

<blockquote class="twitter-tweet" data-lang="de"><p lang="en" dir="ltr">Liked the session? Remember to leave the feedback! <a href="https://twitter.com/hashtag/javaone?src=hash&amp;ref_src=twsrc%5Etfw">#javaone</a> <a href="https://t.co/l3s4qUVT6Z">pic.twitter.com/l3s4qUVT6Z</a></p>&mdash; Kasia Mrowca (@MrowcaKasia) <a href="https://twitter.com/MrowcaKasia/status/659118649748275200?ref_src=twsrc%5Etfw">27. Oktober 2015</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

<blockquote class="twitter-tweet" data-lang="de"><p lang="en" dir="ltr">Just love the new voting system <a href="https://twitter.com/JavaOneConf?ref_src=twsrc%5Etfw">@JavaOneConf</a> <a href="https://twitter.com/hashtag/JavaOne?src=hash&amp;ref_src=twsrc%5Etfw">#JavaOne</a> <a href="https://twitter.com/hashtag/JavaOne2015?src=hash&amp;ref_src=twsrc%5Etfw">#JavaOne2015</a> <a href="https://t.co/QchT70h8H3">pic.twitter.com/QchT70h8H3</a></p>&mdash; Gian Uy (@gianuy) <a href="https://twitter.com/gianuy/status/658797879671197696?ref_src=twsrc%5Etfw">27. Oktober 2015</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

In addition a nice reminder was show before each session:

![reminder](/assets/posts/guigarage-legacy/reminder.png)

But to be true: The coolest moment was the big announcement of the Voting Machine by [Sharat Chander](https://twitter.com/Sharat_Chander) at the **JavaOne keynote**:

![keynote](/assets/posts/guigarage-legacy/keynote.png)

If you haven't seen the keynote you can [watch it online](https://www.oracle.com/javaone/on-demand/index.html?playvid=4578010697001).

## Voting analytics

At the end we tracked **over 23000 votes** for sessions and I think this is a big success. Here are some facts about the voting results that might interest you:

The average number of votes per session is 55. Most of these votes are positive as you can see in the following diagram:

![Avaerage-pos-neu-neg](/assets/posts/guigarage-legacy/voting-machine-graph-1.png)

Only 6 percent of all votes for sessions are negative. To me this indicates that the Java sessions in general are very good. A funny fact is that the negative button was mostly pressed when no session was scheduled for a room. I think people wanted to see the reactions of Duke when pressing the red button. Over 7000 clicks were registered in the break between sessions which makes 25% of all clicks. For me this is a good feedback that people liked the UX with the funny Duke animations :)

We tracked votes for 428 session. This includes most session types like the conference sessions, BOFs, tutorials. Until now I haven't done an analysis based on the session types but I can show you a general overview over all session types. The following diagram shows the distribution of votes. As you can see there are almost 200 sessions that have more than 50 votes:

![votes](/assets/posts/guigarage-legacy/voting-machine-graph-2.png)

## Conculsion

Based on the positive feedback and the voting results I think that the JavaOne Voting Machine was a great success. As far as I know all Voting Machines will be reused at JavaOne next year and maybe there will be some time to even improve them and add some additional Duke images or interaction, for example. In addition we are currently talking with other Java conferences about our voting concept and maybe you will find Voting Machines on your next local conference :)
