package net.txsla.chatfilter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.regex.Matcher;

public class format {
    public static String log_format;
    public static String ghost_format;
    public static String notify_format;
    public static String ghostMessage(Player player, String message) {
        String formattedMessage = ghost_format;

        // replace placeholders
        return formattedMessage
                .replaceAll("%WORLD%", player.getWorld().toString())
                .replaceAll("%SERVER%", ChatFilter.plugin.getServer().getName())
                .replaceAll("%PLAYER%", player.getName())
                .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message));
    }
    public static String notifyMessage(Player sender, Player recipient, String category, String message) {
        String formattedMessage = notify_format;

        // replace placeholders
        return formattedMessage
                .replaceAll("%CAREGORY%", category)
                .replaceAll("%RECIPIANT%", recipient.getName())
                .replaceAll("%PLAYER%", sender.getName())
                .replaceAll("%MESSAGE%", Matcher.quoteReplacement(message));
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
