package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.Filter;
import net.txsla.chatfilter.config;
import net.txsla.chatfilter.filters;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class disable {
    public static void run(String[] args, CommandSender sender) {
        // enables profiler without needing to update the config
        if (args.length <= 1) {
            sender.sendMessage("/cf disable <category>");
            return;
        }

        // enable category(s) with the same name
        for (Filter filter : filters.categories) {
            if (filter.getName().equals(args[1])) {
                filter.disable();
                sender.sendMessage("Category " + filter.getName() + " disabled.");
            }
        }
    }
    public static List<String> tab(String[] args) {
        return filters.registered_filters;
    }
}
