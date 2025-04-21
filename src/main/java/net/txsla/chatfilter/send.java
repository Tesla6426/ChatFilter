package net.txsla.chatfilter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class send {
    public static void messagePlayer(Player player, String message) {
        // send a message to a specific player connected to the proxy
        player.sendMessage(format.message(message));
    }
    public static void messagePlayer(Player player, Component message) {
        // send a message to a specific player connected to the proxy
        player.sendMessage(message);
    }
    public static void notifyUser(Player p, String category, String message, String pattern) {
        // notify all players who have the permission node
        for (Player player : ChatFilter.plugin.getServer().getOnlinePlayers()) {
            if (player.hasPermission("chatfilter.notify")) {
                messagePlayer(player, format.notifyMessage(p, player, category , message, pattern) );
            }
        }
    }
    public static void ghost_message(Player p, String message) {
        messagePlayer(p, format.ghostMessage(p, message));
    }
}
