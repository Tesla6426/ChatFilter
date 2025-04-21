package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.Filter;
import net.txsla.chatfilter.config;
import net.txsla.chatfilter.filters;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class status {
    public static void run(String[] args, CommandSender sender) {
        // prints information about a given filter
        if (args.length <= 1) {
            sender.sendMessage("/cf status <category>");
            return;
        }
        String out = "\n\n[ CHATFILTER ]\nFilter Status: " ;
        // enable category(s) with the same name
        for (Filter filter : filters.categories) {
            if (filter.getName().equals(args[1])) {

                out+= "\n Name: " + filter.getName();
                out+= "\n Enabled: " + filter.isEnabled();
                out+= "\n Actions: \n";
                        for (String action : filter.getAction()) out += "   -" + action + "\n";
                out+= " Commands: \n";
                    for (String command : filter.getCommands()) out += "   -" + command + "\n";
                out+= " regex: \n";
                    for (String pattern : filter.getRegex()) out += "   -" + pattern + "\n";

            }
        }

        sender.sendMessage(out);
    }
    public static List<String> tab(String[] args) {
        return filters.registered_filters;
    }
}
