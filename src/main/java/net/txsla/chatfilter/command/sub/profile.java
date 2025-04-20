package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.config;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class profile {
    public static void run(String[] args, CommandSender sender) {
        // enables debug without needing to update the config

        if (args.length < 1) {
            sender.sendMessage("Profile is set to " + config.profile);
            return;
        }

        switch (args[1]) {
            case "enable":
                config.profile = true;
                sender.sendMessage("Profile enabled.");
                break;
            case "disable":
                config.profile = false;
                sender.sendMessage("Profile disabled.");
                break;
            case "query":
            default:
                sender.sendMessage("Profile is set to " + config.profile);
                break;
        }
    }
    public static List<String> tab(String[] args) {
        if (args.length > 1) {
            List<String> list = new ArrayList<>();
            list.add("enable");
            list.add("disable");
            list.add("query");
            return list;
        }
        return null;
    }
}
