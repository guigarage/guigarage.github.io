---
title: 'Pimp your App by using the BlurPane'
layout: post
author: hendrik
categories: [JavaFX]
excerpt: 'For a skin we needed a special blur effect. Our goal was to blur the complete application once a message is shown on the screen. By doing so the user is targeted only to the displayed message because the rest of the application is blured.'
featuredImage: java-1
permalink: '2013/08/pimp-your-app-by-using-the-blurpane/'
header:
  text: Pimp your App by using the BlurPane
  image: sample
---
For a skin we needed a special blur effect. Our goal was to blur the complete application once a message is shown on the screen. By doing so the user is targeted only to the displayed message because the rest of the application is blured. Here is a short example:

![overlay-combo](/assets/posts/guigarage-legacy/overlay-combo.png)

Whenever a message will be shown the complete applications will become blurred:

![overlay](/assets/posts/guigarage-legacy/overlay.png)

We have choosen a similar approach as it is done in the JXLayer in Swing. A invisible StackPane is placed on top of the application as an overlay. When a message appears the StackPane will become visible. The StackPane contains a ImageView that shows a blurred snapshot of the complete area under the Pane. By doing so the applications seams to be blurred. The BlurPane will be part in one of our next open source libs. But because this class has only dependencies to the JDK we want to share it here with you:

{% highlight java %}
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class BlurPane extends StackPane {

    private ImageView imageView;

    public BlurPane() {
        imageView = new ImageView();
        imageView.setFocusTraversable(false);
        BoxBlur bb = new BoxBlur();
        bb.setWidth(8);
        bb.setHeight(8);
        bb.setIterations(3);
        imageView.setEffect(bb);
    }

    @Override 
    protected void layoutChildren() {
        super.layoutChildren();
        if (getParent() != null && isVisible()) {
            setVisible(false);
            getChildren().remove(imageView);
            SnapshotParameters parameters = new SnapshotParameters();
            Point2D startPointInScene = this.localToScene(0, 0);
            Rectangle2D toPaint = new Rectangle2D(startPointInScene.getX(), startPointInScene.getY(), getLayoutBounds().getWidth(), getLayoutBounds().getHeight());
            parameters.setViewport(toPaint);
            WritableImage image = new WritableImage((int) toPaint.getWidth(), (int) toPaint.getHeight());
            image = getScene().getRoot().snapshot(parameters, image);
            imageView.setImage(image);
            getChildren().add(imageView);
            imageView.toBack();
            setClip(new Rectangle(toPaint.getWidth(), toPaint.getHeight()));
            setVisible(true);
        }
    }
}
{% endhighlight %}
