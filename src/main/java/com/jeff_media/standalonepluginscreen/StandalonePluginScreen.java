package com.jeff_media.standalonepluginscreen;

import com.allatori.annotations.DoNotRename;
import com.allatori.annotations.StringEncryption;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@DoNotRename
@StringEncryption("disable")
public final class StandalonePluginScreen {

    private static final String SETUP_SPIGOT_LINK = "https://www.spigotmc.org/wiki/spigot-installation/";
    private static final String DISCORD_LINK = "https://discord.jeff-media.com/";
    private static final String PLUGIN_NAME;
    private static final String PLUGIN_VERSION;
    private static final String TITLE = "{plugin} {version} requires Spigot";
    private static final String[] message = {
            "Thanks for downloading {plugin} {version}!",
            "",
            "{plugin} is a Spigot Plugin and is not meant to be run directly.",
            "You must put this .jar file into your server's plugins folder.",
            "",
            "See the following link for a tutorial on how to setup a Spigot server:",
            getLink(SETUP_SPIGOT_LINK, SETUP_SPIGOT_LINK),
            "",
            "If you need help, feel free to join my Discord server:",
            getLink(DISCORD_LINK, DISCORD_LINK)
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

    @DoNotRename
    public static void main(String[] args) {
        final JLabel label = new JLabel();
        final Font font = label.getFont();
        final String style = String.format("font-family:%s;font-weight:%s;font-size:%dpt;",
                font.getFamily(),
                (font.isBold() ? "bold" : "normal"),
                font.getSize());

        final JEditorPane pane = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
                + Arrays.stream(message)
                .map(StandalonePluginScreen::replacePlaceholder)
                .collect(Collectors.joining(System.lineSeparator() + "<br/>"))
                + "</body></html>");

        pane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (IOException | URISyntaxException exception) {
                    exception.printStackTrace();
                }
            }
        });
        pane.setEditable(false);
        pane.setBackground(label.getBackground());
        pane.setFocusable(false);

        JOptionPane.showMessageDialog(null, pane, replacePlaceholder(TITLE), JOptionPane.ERROR_MESSAGE);
    }

    private static String getStringFromYaml(String field,java.util.List<String> lines) {
        return lines.stream()
                .filter(line -> line.split(":")[0].trim().equals(field))
                .map(line -> line.split(":")[1].replace("\"","").trim())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("plugin.yml does not contain required field " + field));
    }

    private static String replacePlaceholder(String text) {
        return text.replace("{plugin}",PLUGIN_NAME).replace("{version}",PLUGIN_VERSION);
    }

    private static String getLink(String text, String link) {
        return "<a href=\"" + link + "\">" + text + "</a>";
    }
}
