package com.jeff_media.standalonepluginscreen;

import com.allatori.annotations.StringEncryption;

@StringEncryption("disable")
public class CLINagMessage extends NagMessage {

    @Override
    public void show() {
        getMessage().stream()
                .map(line ->
                        line.replace("{spigotLink}",getSetupSpigotLink())
                                .replace("{discordLink}",getDiscordLink())
                ).forEachOrdered(System.out::println);
    }
}
