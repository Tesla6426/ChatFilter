package net.txsla.chatfilter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.HoverEvent;
import net.txsla.chatfilter.command.execute;
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
                .replaceAll("%[Ww][Oo][Rr][Ll][Dd]%", player.getWorld().getName())
                .replaceAll("%[Ss][Ee][Rr][Vv][Ee][Rr]%", ChatFilter.plugin.getServer().getName())
                .replaceAll("%[Pp][Ll][Aa][Yy][Ee][Rr]%", player.getName())
                .replaceAll("%[Mm][Ee][Ss][Ss][Aa][Gg][Ee]%", Matcher.quoteReplacement(message))
                .replaceAll("<[Nn][Ll]>", Matcher.quoteReplacement("<newline>"));
    }
    public static Component notifyMessage(final Player sender, final Player recipient, final String category, final String message, final String pattern) {
        String formattedMessage = notify_format;
        String formattedHover = notify_hover;

        // replace placeholders
        formattedMessage = formattedMessage
                .replaceAll("%[Cc][Aa][Tt][Ee][Gg][Oo][Rr][Yy]%", category)
                .replaceAll("%[Rr][Ee][Cc][Ii][Pp][Ii][Ee][Nn][Tt]%", recipient.getName())
                .replaceAll("%[Mm][Ee][Ss][Ss][Aa][Gg][Ee]%", Matcher.quoteReplacement(message))
                .replaceAll("%[Pp][Aa][Tt][Tt][Ee][Rr][Nn]%", Matcher.quoteReplacement(pattern))
                .replaceAll("%[Pp][Ll][Aa][Yy][Ee][Rr]%", sender.getName())
                .replaceAll("<[Nn][Ll]>", Matcher.quoteReplacement("<newline>"));

        return MiniMessage.miniMessage().deserialize(formattedMessage);
    }
    public static void command_and_execute_async(String command, Player atP, String message, String regex, String category) {
        new Thread (()->{
            execute.console( format.command(command, atP, message, regex, category) );
        }).start();
    }
    public static String command(String command, Player atP, String message, String regex, String category) {
        if (message.length() > 33) message = message.substring(0, 31);
        return command.replaceAll("%[Pp][Ll][Aa][Yy][Ee][Rr]%", atP.getName())
                .replaceAll("%[Uu][Uu][Ii][Dd]%", Matcher.quoteReplacement(atP.getUniqueId().toString()))
                .replaceAll("%[Ii][Pp]%", Matcher.quoteReplacement(Objects.requireNonNull(atP.getAddress()).toString()) + "")
                .replaceAll("%[Ww][Oo][Rr][Ll][Dd]%", Matcher.quoteReplacement(atP.getWorld().getName()))
                .replaceAll("%[Mm][Ee][Ss][Ss][Aa][Gg][Ee]%", Matcher.quoteReplacement(message))


                .replaceAll("%[Pp][Aa][Tt][Tt][Ee][Rr][Nn]%", Matcher.quoteReplacement(regex))
                .replaceAll("%[Cc][Aa][Tt][Ee][Gg][Oo][Rr][Yy]%", Matcher.quoteReplacement(category));
    }
    public static String log(Player p, String message, String category, String regex) {
        String formattedMessage = log_format;
        // format log messages
        return formattedMessage.replaceAll("%[Pp][Ll][Aa][Yy][Ee][Rr]%", p.getName())
                .replaceAll("%[Mm][Ee][Ss][Ss][Aa][Gg][Ee]%", Matcher.quoteReplacement(message))
                .replaceAll("%[Cc][Aa][Tt][Ee][Gg][Oo][Rr][Yy]%", Matcher.quoteReplacement(category))
                .replaceAll("%[Pp][Aa][Tt][Tt][Ee][Rr][Nn]%", Matcher.quoteReplacement(category));
    }
    public static Component message(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }
}
