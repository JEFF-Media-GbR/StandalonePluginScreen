package com.jeff_media.standalonepluginscreen;

import java.awt.*;

//@DoNotRename
//@StringEncryption("disable")
public final class StandalonePluginScreen {

    //@DoNotRename
    public static void main(String[] args) {
        new CLINagMessage().show();
        if(!GraphicsEnvironment.isHeadless()) new DesktopNagMessage().show();
    }

}
