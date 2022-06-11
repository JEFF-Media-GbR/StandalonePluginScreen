package com.jeff_media.standalonepluginscreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//@StringEncryption("disable")
public abstract class NagMessage {

    private static final String SETUP_SPIGOT_LINK;
    private static final String DISCORD_LINK;
    private static final String PLUGIN_NAME;
    private static final String PLUGIN_VERSION;
    private static final String TITLE;
    private static final String[] MESSAGE;

    static {
        String name = "Unknown Plugin";
        String version = "?.?.?";
        try (final InputStream inputStream = Objects.requireNonNull(StandalonePluginScreen.class.getResourceAsStream("/plugin.yml"), "Can't find plugin.yml file");
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            java.util.List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            name = getStringFromYaml("name", lines);
            version = getStringFromYaml("version", lines);
        } catch (IOException | RuntimeException exception) {
            exception.printStackTrace();
        }
        PLUGIN_NAME = name;
        PLUGIN_VERSION = version;

        String title = "{plugin} {version} requires Spigot";
        String discordLink = "No discord link found";
        String spigotLink = "No spigot link found";
        String[] message = {
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

        try (final InputStream inputStream = Objects.requireNonNull(StandalonePluginScreen.class.getResourceAsStream("/pluginscreen.yml"), "Can't find pluginscreen.yml file");
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            java.util.List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            System.out.println(lines);
            title = getStringFromYaml("title", lines, title);
            spigotLink = getStringFromYaml("spigot-link", lines, spigotLink);
            discordLink = getStringFromYaml("discord-link", lines, discordLink);
            message = getStringListFromYaml("message", lines, message);
        } catch (IOException | RuntimeException exception) {
            exception.printStackTrace();
        }

        SETUP_SPIGOT_LINK = spigotLink;
        DISCORD_LINK = discordLink;
        TITLE = title;
        MESSAGE = message;
    }

    public abstract void show();

    private static String getStringFromYaml(String field, java.util.List<String> lines) {
        return lines.stream()
                .filter(line -> line.split(":")[0].trim().equals(field))
                .map(line -> line.split(":")[1].replace("\"", "").trim())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("plugin.yml does not contain required field " + field));
    }

    private static String getStringFromYaml(String field, java.util.List<String> lines, String defaultValue) {
        return lines.stream()
                .filter(line -> line.split(":")[0].trim().equals(field))
                .map(line -> line.split(":", 2)[1].replace("\"", "").trim())
                .findFirst().orElse(defaultValue);
    }

    private static String[] getStringListFromYaml(String field, java.util.List<String> lines, String[] defaultValue) {
        boolean found = false;
        List<String> fieldLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).split(":")[0].trim().equals(field) && !found) {
                found = true;
                continue;
            }

            if (found) {
                if (lines.get(i).startsWith("  -")) {
                    fieldLines.add(lines.get(i).substring(3).replace("\"", "").trim());
                } else {
                    return fieldLines.toArray(new String[0]);
                }
            }
        }
        if (found)
            return fieldLines.toArray(new String[0]);
        else
            return defaultValue;
    }

    private static String insertPluginName(String text) {
        return text.replace("{plugin}", PLUGIN_NAME).replace("{version}", PLUGIN_VERSION);
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