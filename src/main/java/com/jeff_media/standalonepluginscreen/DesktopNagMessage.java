package com.jeff_media.standalonepluginscreen;

import com.allatori.annotations.StringEncryption;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@StringEncryption("disable")
public final class DesktopNagMessage extends NagMessage {

    @Override
    public void show() {
        final JLabel label = new JLabel();
        final Font font = label.getFont();
        final String style = String.format("font-family:%s;font-weight:%s;font-size:%dpt;",
                font.getFamily(),
                (font.isBold() ? "bold" : "normal"),
                font.getSize());

        final JEditorPane pane = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
                + getMessage().stream()
                .map(line ->
                        line.replace("{spigotLink}", hyperlink(getSetupSpigotLink(),getSetupSpigotLink()))
                                .replace("{discordLink}", hyperlink(getDiscordLink(),getDiscordLink()))
                ).collect(Collectors.joining(System.lineSeparator() + "<br/>"))
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

        JFrame frame = new JFrame(getTitle());

        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JOptionPane.showMessageDialog(frame, pane, getTitle(), JOptionPane.ERROR_MESSAGE);

        frame.dispose();
    }

    private static String hyperlink(String text, String link) {
        return "<a href=\"" + link + "\">" + text + "</a>";
    }


}
