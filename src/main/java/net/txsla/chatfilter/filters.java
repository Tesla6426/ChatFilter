package net.txsla.chatfilter;

import net.txsla.chatfilter.command.execute;
import net.txsla.chatfilter.log.log;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class filters {
    private static File file;
    private static YamlConfiguration filterConfig;
    public static List<String> registered_filters;
    public static List<Filter> categories;
    public static void loadConfig() {

        // load filter.yml
        file = new File(getPlugin(ChatFilter.class).getDataFolder(), "filter.yml");
        if (!file.exists()) getPlugin(ChatFilter.class).saveResource("filter.yml", false);
        filterConfig = new YamlConfiguration(); filterConfig.options().parseComments(true);
        try { filterConfig.load(file); } catch (Exception e) {e.printStackTrace();}

    }

    public static void loadCategories() {
        if (config.debug) System.out.println("Loading Categories..");

        // declare/clear list
        categories = new ArrayList<>();

        // add registered filters to list
        for (String filter : registered_filters) {
            try {
                // create filter object and add to list
                categories.add(new Filter(
                                filter,
                                filterConfig.getBoolean("categories." + filter + ".enabled"),
                                filterConfig.getStringList("categories." + filter + ".action"),
                                filterConfig.getStringList("categories." + filter + ".regex"),
                                filterConfig.getStringList("categories." + filter + ".commands")
                        )
                );
                // print cat to console if debug
                if (config.debug) System.out.println("CATEGORY " + filter + " " +
                        filterConfig.getBoolean("categories." + filter + ".enabled")+ " " +
                        filterConfig.getStringList("categories." + filter + ".action")+ " " +
                        filterConfig.getStringList("categories." + filter + ".regex")+ " " +
                        filterConfig.getStringList("categories." + filter + ".commands"));
            } catch (Exception e) {
                if (config.debug) System.out.println("ERROR RETRIEVING CATEGORY " + filter);
            }
        }
        if (config.debug) System.out.println("Categories Loaded");
    }

    public static String scanString(Player p, String string) {
        if (config.debug) System.out.println("[scanString] Checking String: '" + string + "'");
        String scanString = string;

        // Your compiler will tell you this is redundant, but I can assure you it is not
        Player placeholder = p;

        // if a category has been found
        boolean matched = false;
        // used for censor (there could be multiple matches in the same string that need to be censored)
        boolean scan_full_list = false;
        // make sure users do not get messaged twice if more than one match is found
        boolean notified = false;

        // scan text against all filters
        for (Filter filter : categories) {
            if (matched) scanString = "error"; // make sure the loop does not keep going
            // make sure filter is enabled
            if (filter.isEnabled()) {
                if (config.debug) System.out.println("[scanString] [L1] Checking Against Filter: '" + filter.getName() + "'");
                // scan through all regex patterns in list
                for (String regex : filter.getRegex()) {
                    if (config.debug) System.out.println("[scanString] [L2] Checking Against Regex: '" + regex + "'");
                    // check if message matches pattern
                    if ( match(scanString, regex) ) {

                        // handle actions for each
                        for (String action : filter.getAction()) {
                            if (config.debug) System.out.println("[scanString] [L3] Running action: '" + action + "'");
                            // switch statement to handle each action
                            switch (action) {
                                case "notify":
                                    // notify users then set flag to prevent spam
                                    if (!notified) {
                                        send.notifyUser(p, filter.getName(), string, regex);
                                        notified = true;
                                    }
                                    break;

                                // cancel actions (set null)
                                case "cancel":
                                    scanString = null;
                                    scan_full_list = false;
                                    break;
                                case "ghost":
                                    send.ghost_message(p, string);
                                    scanString = null;
                                    scan_full_list = false;
                                    break;

                                // modify actions
                                case "censor":
                                    // try/catch in case message is null
                                    try {
                                        // replace all matched text with asterisk
                                        scanString.replaceAll(regex, "*");
                                    } catch (Exception e) {
                                        if (config.debug) System.out.println(e);
                                        // set to null again for redundancy
                                        scanString = null;
                                    }
                                    // scan entire list in case of other matches in the string
                                    scan_full_list = true;
                                    break;

                                // ignore actions
                                case "ignore":
                                case "null":
                                default:
                                    break;
                            }
                        }

                        // log event once
                        if (!matched) log.add(format.log(p, string, filter.getName(), regex));

                        // execute commands
                        if (!matched) {
                            // format and execute commands
                            for (String command : filter.getCommands()) {
                                execute.console( format.command(command, placeholder, string) );
                            }
                        }

                        // return string
                        if (!scan_full_list) return scanString;

                        // redundancy
                        if (scanString == null) return null;

                        // check if code has already been run
                        matched = true;
                    }
                }
                // if scan full list is set, then return after scanning full list
                if (scan_full_list) return scanString;
            } // END IF ENABLED
        }
        // return as is if no match is found
        return scanString;
    }
    // For some reason java has String.matches() which adds a ^ and $ to the ends of your pattern making it useless for this purpose
    // Why? We can add that easily add that ourselves, why does dumb ass java feel the need to add it for us?
    public static boolean match(String string, String regex) {
        // why doesn't java have this be default :/
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);
        return m.find();
    }
}
