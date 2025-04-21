package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.Filter;
import net.txsla.chatfilter.config;
import net.txsla.chatfilter.filters;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class enable {
    public static void run(String[] args, CommandSender sender) {
        // enables profiler without needing to update the config
        if (args.length <= 1) {
            sender.sendMessage("/cf enable <category>");
            return;
        }

        // enable category(s) with the same name
        for (Filter filter : filters.categories) {
            if (filter.getName().equals(args[1])) {
             filter.enable();
                sender.sendMessage("Category " + filter.getName() + " enabled.");
            }
        }
    }
    public static List<String> tab(String[] args) {
            return filters.registered_filters;
    }
}
