package com.jeff_media.standalonepluginscreen;

import com.allatori.annotations.DoNotRename;
import com.allatori.annotations.StringEncryption;

import java.awt.*;

@DoNotRename
@StringEncryption("disable")
public final class StandalonePluginScreen {

    @DoNotRename
    public static void main(String[] args) {
        new CLINagMessage().show();
        if(!GraphicsEnvironment.isHeadless()) new DesktopNagMessage().show();
    }

}
