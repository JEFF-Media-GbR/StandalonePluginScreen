package com.jeff_media.standalonepluginscreen;

import com.allatori.annotations.StringEncryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@StringEncryption("disable")
public abstract class NagMessage {

    private static final String SETUP_SPIGOT_LINK = "https://www.spigotmc.org/wiki/spigot-installation/";
    private static final String DISCORD_LINK = "https://discord.jeff-media.com/";
    private static final String PLUGIN_NAME;
    private static final String PLUGIN_VERSION;
    private static final String TITLE = "{plugin} {version} requires Spigot";
    private static final String[] MESSAGE = {
            "Thanks for downloading {plugin} {version}!",
            "",
            "{plugin} is a Spigot Plugin and is not meant to be run directly.",
            "You must put this .jar file into your server's plugins folder.",
            "",
            "See the following link for a tutorial on how to setup a Spigot server:",
            "{spigotLink}",
            "",
            "If you need help, feel free to join my Discord server:",
            "{discordLink}"
    };

    static {
        String name = "Unknown Plugin";
        String version = "?.?.?";
        try (final InputStream inputStream = Objects.requireNonNull(StandalonePluginScreen.class.getResourceAsStream("/plugin.yml"), "Can't find plugin.yml file");
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            java.util.List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            name = getStringFromYaml("name",lines);
            version = getStringFromYaml("version",lines);
        } catch (IOException | RuntimeException exception) {
            exception.printStackTrace();
        }
        PLUGIN_NAME = name;
        PLUGIN_VERSION = version;
    }

    public abstract void show();

    private static String getStringFromYaml(String field,java.util.List<String> lines) {
        return lines.stream()
                .filter(line -> line.split(":")[0].trim().equals(field))
                .map(line -> line.split(":")[1].replace("\"","").trim())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("plugin.yml does not contain required field " + field));
    }

    private static String insertPluginName(String text) {
        return text.replace("{plugin}",PLUGIN_NAME).replace("{version}",PLUGIN_VERSION);
    }

    static List<String> getMessage() {
        return Arrays.asList(MESSAGE).stream().map(NagMessage::insertPluginName).collect(Collectors.toList());
    }

    static String getTitle() {
        return insertPluginName(TITLE);
    }

    static String getSetupSpigotLink() {
        return SETUP_SPIGOT_LINK;
    }

    static String getDiscordLink() {
        return DISCORD_LINK;
    }



}
