package com.jeff_media.standalonepluginscreen;

//@StringEncryption("disable")
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
