package net.txsla.chatfilter;

import net.txsla.chatfilter.command.main_command;
import net.txsla.chatfilter.log.webhook;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ChatFilter extends JavaPlugin {
    public static Plugin plugin;
    public static Path dir;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        dir = this.getDataPath();


        // load plugin configurations
        saveDefaultConfig();
        loadConfig();
        if (config.debug) System.out.println("DEBUG OUTPUT ENABLED");

        // bstats
        int pluginId = 25558;
        Metrics metrics = new Metrics(this, pluginId);

        // load filters
        filters.loadConfig();
        getLogger().info("Registered Filters: " + filters.registered_filters);
        filters.loadCategories();


        // load mute configs
        mute.loadConfig();
        mute.loadMuteList();

        // register events
        getServer().getPluginManager().registerEvents(new listener(), this);

        // start spam limiter decrement timer
        spamLimiter.startDecTimer();

        // load log file
        try {
            net.txsla.chatfilter.log.file.loadLogFile();
            net.txsla.chatfilter.log.file.add("Log File Loaded");
        } catch (Exception e) {
            getLogger().warning("Unable to load log File");
            if (config.debug) System.out.println(e);
        }

        // Load webhook
        if (config.debug) webhook.add("ChatFilter Webhook Connected");


        // register command
        getCommand("chatfilter").setExecutor(new main_command());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void loadConfig() {

        config.debug = this.getConfig().getBoolean("debug");
        config.profile = this.getConfig().getBoolean("profile");

        // log configs
        net.txsla.chatfilter.log.file.enabled = this.getConfig().getBoolean("log.to-file");
        net.txsla.chatfilter.log.webhook.enabled = this.getConfig().getBoolean("log.to-discord");
        webhook.webhook = this.getConfig().getString("log.webhook");


        // spam limiter / mute configs
        spamLimiter.enabled = this.getConfig().getBoolean("spam-limiter.enabled");
        spamLimiter.ghost = this.getConfig().getBoolean("spam-limiter.ghost-player");
        spamLimiter.decTimer = this.getConfig().getInt("spam-limiter.decrement-timer");
        spamLimiter.maxCounter = this.getConfig().getInt("spam-limiter.counter-ceiling");

        // formatting config
        format.ghost_format = this.getConfig().getString("ghost-format");
        format.notify_format = this.getConfig().getString("notify.message");
        format.log_format = this.getConfig().getString("log.message-format");
        format.notify_hover = this.getConfig().getString("notify.hover");

        // Filters / Categories
        filters.registered_filters = this.getConfig().getStringList("registered-categories");
    }
}
