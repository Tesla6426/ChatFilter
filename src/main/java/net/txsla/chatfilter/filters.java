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

                if (!filter.equals("gamersafer")) {
                    categories.add(new Filter(
                            filter,
                            filterConfig.getBoolean("categories." + filter + ".enabled"),
                            filterConfig.getStringList("categories." + filter + ".action"),
                            regex.stringListToRegex(filterConfig.getStringList("categories." + filter + ".regex")),
                            filterConfig.getStringList("categories." + filter + ".commands")
                    ));
                }else {
                    // for gamersafer list
                    categories.add(new Filter(
                            filter,
                            filterConfig.getBoolean("categories." + filter + ".enabled"),
                            filterConfig.getStringList("categories." + filter + ".action"),
                            net.txsla.chatfilter.gamersafer.load.patterns(
                                        filterConfig.getInt("categories." + filter + ".strictness")
                            ),
                            filterConfig.getStringList("categories." + filter + ".commands")
                    ));


                }
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

    public static boolean fasterScanString(Player p, String string) {
        boolean matched = false;
        boolean done = false;

        for (Filter filter : categories) {
            // check if filter is enabled
            if (!filter.isEnabled()) continue;

            // check against each pattern
            for (Pattern regex : filter.getRegex()) {

                // check for matches
                if (regex.matcher(string).find()) {
                    // execute code

                    for (String action : filter.getAction()) {

                        // switch statement to handle each action
                        switch (action) {
                            // notify users then set flag to prevent spam
                            case "notify":
                                if (!matched) {
                                    // multithreading bc 10ms is too long
                                    new Thread (()-> send.notifyUser(p, filter.getName(), string, regex.toString())).start();
                                    matched = true;
                                }
                                break;

                            case "cancel":
                                done = true;
                                break;

                            case "ghost":
                                send.ghost_message(p, string);
                                done = true;
                                break;

                            // ignore actions
                            case "ignore":
                            case "null":
                            default:
                                break;
                        }
                    }
                    // run commands and log for each triggered category

                    // log event once
                    if (!matched) log.add(format.log(p, string, filter.getName(), regex.toString()));


                    // format and execute commands
                    for (String command : filter.getCommands()) {
                        execute.console( format.command(command, p, string, regex.toString(), filter.getName()));
                    }


                    if (done) return true;
                    matched = true;
                    break;
                }
            }
        }
        return false;
    }
    public static List<String> GamerSaferExclude() {
        return filterConfig.getStringList("categories.gamersafer.exclude");
    }
}
