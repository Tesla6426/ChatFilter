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
        config.plugin = this;
        config.loadConfig();
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
    public static void reload() {
        // reload config file
        plugin.reloadConfig();

        // load plugin configurations
        config.loadConfig();
        if (config.debug) System.out.println("DEBUG OUTPUT ENABLED");

        // load filters
        filters.loadConfig();
        ChatFilter.plugin.getLogger().info("Registered Filters: " + filters.registered_filters);
        filters.loadCategories();

        // load mute configs
        mute.loadConfig();
        mute.loadMuteList();

        if (config.debug) webhook.add("ChatFilter Webhook Connected");

    }
}
