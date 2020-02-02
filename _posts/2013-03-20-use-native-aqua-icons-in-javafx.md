---
title: 'Use native Aqua icons in JavaFX'
layout: post
author: hendrik
categories: [AquaFX, JavaFX]
excerpt: 'While planning AquaFX we found a way how to access Mac OS specific icons without using any closed APIs or Mac OS related classes.'
featuredImage: sample-3
permalink: '2013/03/use-native-aqua-icons-in-javafx/'
header:
  text: Aqua icons in JavaFX
  image: sample
---
While planning [AquaFX]({{ site.baseurl }}{% post_url 2013-03-02-update-for-the-native-ones %}) we found a way how to access Mac OS specific icons without using any closed APIs or Mac OS related classes. We wrapped this in a simple util class. While using this on a Mac you will receive images by the underlying OS. On any other OS the function will return "null". We currently discuss if this should be part of AquaFX because this will be a part that is (under the hood) OS specific. All control skins of AquaFX are cross-platform. So you can skin your JavaFX application on Windows like a native Mac OS application (if you want :D ). Because we currently don't know if this sources will ever be part of AquaFX I simple will post them here:

This enum contains all fetch able images that we found:

{% highlight java %}
public enum NsImageIcon {
    QUICK_LOOK_TEMPLATE("NSQuickLookTemplate"), BLUETOOTH_TEMPLATE(
            "NSBluetoothTemplate"), I_CHAT_THEATER_TEMPLATE(
            "NSIChatTheaterTemplate"), SLIDESHOW_TEMPLATE("NSSlideshowTemplate"), ACTION_TEMPLATE(
            "NSActionTemplate"), SMART_BADGE_TEMPLATE("NSSmartBadgeTemplate"), PATH_TEMPLATE(
            "NSPathTemplate"), INVALID_DATA_FREESTANDING_TEMPLATE(
            "NSInvalidDataFreestandingTemplate"), LOCK_LOCKED_TEMPLATE(
            "NSLockLockedTemplate"), LOCK_UNLOCKED_TEMPLATE(
            "NSLockUnlockedTemplate"), GO_RIGHT_TEMPLATE("NSGoRightTemplate"), GO_LEFT_TEMPLATE(
            "NSGoLeftTemplate"), RIGHT_FACING_TRIANGLE_TEMPLATE(
            "NSRightFacingTriangleTemplate"), LEFT_FACING_TRIANGLE_TEMPLATE(
            "NSLeftFacingTriangleTemplate"), ADD_TEMPLATE("NSAddTemplate"), REMOVE_TEMPLATE(
            "NSRemoveTemplate"), REVEAL_FREESTANDING_TEMPLATE(
            "NSRevealFreestandingTemplate"), FOLLOW_LINK_FREESTANDING_TEMPLATE(
            "NSFollowLinkFreestandingTemplate"), ENTER_FULL_SCREEN_TEMPLATE(
            "NSEnterFullScreenTemplate"), EXIT_FULL_SCREEN_TEMPLATE(
            "NSExitFullScreenTemplate"), STOP_PROGRESS_TEMPLATE(
            "NSStopProgressTemplate"), STOP_PRPGRESS_FREESTANDING_TEMPLATE(
            "NSStopProgressFreestandingTemplate"), REFRESH_TEMPLATE(
            "NSRefreshTemplate"), REFRESH_FREESTANDING_TEMPLATE(
            "NSRefreshFreestandingTemplate"), FOLDER("NSFolder"), TRASH_EMPTY(
            "NSTrashEmpty"), TRASH_FULL("NSTrashFull"), HOME_TEMPLATE(
            "NSHomeTemplate"), BOOKMARKS_TEMPLATE("NSBookmarksTemplate"), CAUTION(
            "NSCaution"), STATUS_AVAILABLE("NSStatusAvailable"), STATUS_PARTIALLY_AVAILABLE(
            "NSStatusPartiallyAvailable"), STATUS_UNAVAILABLE(
            "NSStatusUnavailable"), STATUS_NONE("NSStatusNone"), APPLICATION_ICON(
            "NSApplicationIcon"), MENU_ON_STATE_TEMPLATE(
            "NSMenuOnStateTemplate"), MENU_MIXED_STATE_TEMPLATE(
            "NSMenuMixedStateTemplate"), USER_GUEST("NSUserGuest"), MOBILE_ME(
            "NSMobileMe"), MULTIPLE_DOCUMENTS("NSMultipleDocuments"), USER(
            "NSUser"), USER_GROUP("NSUserGroup"), EVERYONE("NSEveryone"), BONJOUR(
            "NSBonjour"), DOT_MAC("NSDotMac"), COMPUTER("NSComputer"), FOLDER_BURNABLE(
            "NSFolderBurnable"), FOLDER_SMART("NSFolderSmart"), NETWORK(
            "NSNetwork"), USER_ACCOUNTS("NSUserAccounts"), PREFERENCES_GENERAL(
            "NSPreferencesGeneral"), ADVANCED("NSAdvanced"), INFO("NSInfo"), FONT_PANEL(
            "NSFontPanel"), COLOR_PANEL("NSColorPanel"), ICON_VIEW_TEMPLATE(
            "NSIconViewTemplate"), LIST_VIEW_TEMPLATE("NSListViewTemplate"), COLUMN_VIEW_TEMPLATE(
            "NSColumnViewTemplate"), FLOW_VIEW_TEMPLATE("NSFlowViewTemplate");

    private String name;

    private NsImageIcon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
{% endhighlight %}

And here is the loader class:

{% highlight java %}
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class NsImageIconLoader {

    public static Image load(NsImageIcon icon) {
        System.setProperty("java.awt.headless", "false");
        java.awt.Image awtImage = Toolkit.getDefaultToolkit().getImage(
                "NSImage://" + icon.getName());
        BufferedImage bufferedImage = null;
        if (awtImage instanceof BufferedImage) {
            bufferedImage = (BufferedImage) awtImage;
        } else {
            bufferedImage = new BufferedImage(awtImage.getWidth(null),
                    awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(awtImage, 0, 0, null);
            g.dispose();
        }
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
{% endhighlight %}

Here is a overview of all icons that we have found:

![native-icons](/assets/posts/guigarage-legacy/native-icons.png)

If anyone knows more icon names that can be loaded by Mac OS please let us know ;)
