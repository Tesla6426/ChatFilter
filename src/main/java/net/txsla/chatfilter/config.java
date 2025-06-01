package net.txsla.chatfilter;

import net.txsla.chatfilter.log.file;
import net.txsla.chatfilter.log.webhook;
import org.bukkit.plugin.Plugin;

public class config {
    public static Plugin plugin;
    public static boolean debug;
    public static boolean profile;
    public static void loadConfig() {

        config.debug = plugin.getConfig().getBoolean("debug");
        config.profile = plugin.getConfig().getBoolean("profile");

        // log configs
        net.txsla.chatfilter.log.file.enabled = plugin.getConfig().getBoolean("log.to-file");
        net.txsla.chatfilter.log.webhook.enabled = plugin.getConfig().getBoolean("log.to-discord");
        webhook.webhook = plugin.getConfig().getString("log.webhook");


        // spam limiter / mute configs
        spamLimiter.enabled = plugin.getConfig().getBoolean("spam-limiter.enabled");
        spamLimiter.ghost = plugin.getConfig().getBoolean("spam-limiter.ghost-player");
        spamLimiter.decTimer = plugin.getConfig().getInt("spam-limiter.decrement-timer");
        spamLimiter.maxCounter = plugin.getConfig().getInt("spam-limiter.counter-ceiling");

        // formatting config
        format.ghost_format = plugin.getConfig().getString("ghost-format");
        format.notify_format = plugin.getConfig().getString("notify.message");
        format.log_format = plugin.getConfig().getString("log.message-format");
        format.notify_hover = plugin.getConfig().getString("notify.hover");

        // Filters / Categories
        filters.registered_filters = plugin.getConfig().getStringList("registered-categories");
    }
}
