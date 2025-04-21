package net.txsla.chatfilter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.regex.Matcher;

public class format {
    public static String log_format;
    public static String ghost_format;
    public static String notify_format;
    public static String notify_hover;
    public static String ghostMessage(Player player, String message) {
        String formattedMessage = ghost_format;

        // replace placeholders
        return formattedMessage
                .replaceAll("%WORLD%", player.getWorld().toString())
                .replaceAll("%SERVER%", ChatFilter.plugin.getServer().getName())
                .replaceAll("%PLAYER%", player.getName())
                .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message));
    }
    public static Component notifyMessage(final Player sender, final Player recipient, final String category, final String message, final String pattern) {
        String formattedMessage = notify_format;
        String formattedHover = notify_hover;

        // replace placeholders
        formattedMessage = formattedMessage
                .replaceAll("%CATEGORY%", category)
                .replaceAll("%RECIPIENT%", recipient.getName())
                .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message))
                .replaceAll("%PATTERN%", Matcher.quoteReplacement(pattern))
                .replaceAll("%PLAYER%", sender.getName());

        // send as is if hover is disabled
        if (formattedHover == null) return MiniMessage.miniMessage().deserialize(formattedMessage);

        // format hover
        net.kyori.adventure.text.event.HoverEvent<Component> hover =
                MiniMessage.miniMessage().deserialize(
                    formattedHover.replaceAll("%CATEGORY%", category)
                        .replaceAll("%RECIPIANT%", recipient.getName())
                        .replaceAll("%PLAYER%", sender.getName())
                        .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message))
                ).asHoverEvent();

        // this might work hopefully
        return MiniMessage.miniMessage()
                .deserialize(formattedMessage)
                .hoverEvent(hover);
    }
    public static String command(String command, Player atP, String message) {
        if (message.length() > 33) message = message.substring(0, 31);
        return command.replaceAll("%PLAYER%", atP.getName())
                .replaceAll("%UUID%", Matcher.quoteReplacement(atP.getUniqueId().toString()))
                .replaceAll("%IP%", Matcher.quoteReplacement(Objects.requireNonNull(atP.getAddress()).toString()) + "")
                .replaceAll("%WORLD%", Matcher.quoteReplacement(atP.getWorld().getName()))
                .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message));
    }
    public static String log(Player p, String message, String category, String regex) {
        String formattedMessage = log_format;
        // format log messages
        return formattedMessage.replaceAll("%PLAYER%", p.getName())
                .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message))
                .replaceAll("%CATEGORY%", Matcher.quoteReplacement(category))
                .replaceAll("%PATTERN%", Matcher.quoteReplacement(category));
    }
    public static Component message(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }
}
