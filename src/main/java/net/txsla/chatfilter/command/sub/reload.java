package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.ChatFilter;
import net.txsla.chatfilter.config;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class reload {
    public static void run(String[] args, CommandSender sender) {
        // reloads the plugin config file without restarting the server
        sender.sendMessage("Reloading Chat Filter configs...");
        sender.sendMessage("Please note it is not generally recommended to reload the plugin if you do not need to.");
        net.txsla.chatfilter.ChatFilter.reload();
        sender.sendMessage("Reload complete!");

    }
    public static List<String> tab(String[] args) {
        return null;
    }
}
