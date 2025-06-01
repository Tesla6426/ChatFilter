package net.txsla.chatfilter.command.sub;

import net.txsla.chatfilter.Filter;
import net.txsla.chatfilter.config;
import net.txsla.chatfilter.filters;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class status {
    public static void run(String[] args, CommandSender sender) {
        // prints information about a given filter
        if (args.length <= 1) {
            sender.sendMessage("/cf status <category>");
            return;
        }
        String out = "\n\n§b[ CHATFILTER ]\n§8Filter Status: " ;
        // ~~enable~~ +get+ category(s) with the same name
        for (Filter filter : filters.categories) {
            if (filter.getName().equals(args[1])) {

                out += "\n §9Name: " + filter.getName();


                out += "\n §9Enabled: ";
                    if (filter.isEnabled()) out += "§atrue";
                        else out += "§cfalse";

                out += "\n §9Actions: \n";
                        for (String action : filter.getAction()) out += "    §7-" + action + "\n";


                out += " §9Commands: \n";
                    for (String command : filter.getCommands()) out += "   §7-" + command + "\n";


                out += " §9regex: \n";
                    for (Pattern pattern : filter.getRegex()) out += "   §7-" + pattern.toString() + "\n";

            }
        }

        sender.sendMessage(out);
    }
    public static List<String> tab(String[] args) {
        return filters.registered_filters;
    }
}
