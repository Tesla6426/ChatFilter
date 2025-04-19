package net.txsla.chatfilter;

import net.txsla.chatfilter.log.file;
import net.txsla.chatfilter.spam.spamLimiter;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.nio.file.Path;

public final class ChatFilter extends JavaPlugin {
    public static Plugin plugin;
    public static Path dir;
    @Override
        public void onEnable() {
            // Plugin startup logic
            saveDefaultConfig();
            plugin = this;
            // get plugin file path
            dir = this.getDataPath();
            System.out.println("DATA DIRECTORY: " + dir);

            // load plugin configurations
            loadConfig();

            // load filters
            config.loadFilters();
            getLogger().info("Enabled Filters: [" + "]");

            // register events
            getServer().getPluginManager().registerEvents(new listener(), this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void loadConfig() {

        // load boolean
        net.txsla.chatfilter.log.file.enabled = this.getConfig().getBoolean("log.to-file");
        net.txsla.chatfilter.log.webhook.enabled = this.getConfig().getBoolean("log.to-discord");
        spamLimiter.enabled = this.getConfig().getBoolean("spam-limiter.enabled");
        config.debug = this.getConfig().getBoolean("debug");

    }
}
