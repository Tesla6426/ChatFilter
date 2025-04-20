package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.config;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class mute {
    public static void run(String[] args, CommandSender sender) {
        // enables debug without needing to update the config

        if (args.length < 1) {
            sender.sendMessage("/mute <player> <duration> <reason>");
            return;
        }

        switch (args[1]) {
            case "enable":
                config.debug = true;
                sender.sendMessage("Debug enabled.");
                break;
            case "disable":
                config.debug = false;
                sender.sendMessage("Debug disabled.");
                break;
            case "query":
            default:
                sender.sendMessage("Debug is set to " + config.debug);
                break;
        }
    }
    public static List<String> tab(String[] args) {
        List<String> list = new ArrayList();
        // / mute player duration
        switch (args.length) {
            case 1:
                return null;
            case 2:
                // retun num + time
                if (args[1].matches("[0-9]+")) {
                    // if user has typed numbers, suggest durations after
                    list.add(args[1] + "m"); // minutes
                    list.add(args[1] + "h"); // hours
                    list.add(args[1] + "d"); // days
                    list.add(args[1] + "w"); // weeks
                    list.add(args[1] + "y"); // years
                }else {
                    list.add("<int>m"); // minutes
                    list.add("<int>h"); // hours
                    list.add("<int>d"); // days
                    list.add("<int>w"); // weeks
                    list.add("<int>y"); // years
                }
                break;
            case 3:
                // tell user to provide reason
                list.add("<reason>");
                break;
        }


        return list;
    }
}
