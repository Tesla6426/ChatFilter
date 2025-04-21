package net.txsla.chatfilter;


import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class mute {
    public static boolean requireReason;
    public static List<String> muted_name = new ArrayList<>();
    public static List<String> muted_uuid = new ArrayList<>();
    private static File file;
    private static YamlConfiguration muteConfig;

    public static void loadConfig() {

        // load config file
        file = new File(getPlugin(ChatFilter.class).getDataFolder(), "mute.yml");
        if (!file.exists()) getPlugin(ChatFilter.class).saveResource("mute.yml", false);
        muteConfig = new YamlConfiguration(); muteConfig.options().parseComments(true);
        try { muteConfig.load(file); } catch (Exception e) {e.printStackTrace();}

        // load configs
        requireReason = muteConfig.getBoolean("require-reason");

    }

    public static boolean isUUIDMuted(String uuid) {
        for (String u : muted_uuid) if (u != null) if (u.equals(uuid)) return true;
        return false;
    }
    public static boolean isNameMuted(String username) {
        for (String s : muted_name) if (s.equals(username)) return true;
        return false;
    }
    public static boolean mutePlayer(String username, String mute_enforcer, String reason, String duration ) {
        // add player to mute list file and reload config
        Date date = new Date();
        UUID uuid = null;
        try {
            // requires player to be online to find UUID - maybe add null search later whenever a player joins and matched mute list
            uuid = ChatFilter.plugin.getServer().getPlayer(username).getUniqueId();
        } catch (Exception e) {
            System.out.println("Player " + username + " not found. Please populate UUID field manually. (Mute is still in effect unless player changes their name)");
        }

        // figure out muted_until unix timestamp from duration
        long muted_until = date.getTime();
        if (duration.matches("[0-9]+[mhdwy]")) {
            String let = duration.substring(duration.length()-1);
            int num = Integer.parseInt(duration.substring(0, duration.length()-1));

            // convert duration to a unix time code
            if (!(num > 0)) return false;
            switch (let) {
                case "min": muted_until += num*60000L; break;
                case "hr": muted_until += num*3600000L; break;
                case "d": muted_until += num*86400000L; break;
                case "wk": muted_until += num*604800000L; break;
                case "yr": muted_until += num*31557600000L; break;
                // invalid input
                default: return false;
            }
        }else { return false; /*bail if duration does not match regex*/ }

        // add username to mute list
        List<String> usernames = muteConfig.getStringList("mute-list");
        usernames.add(username);
        muteConfig.set("mute-list", usernames);

        // populate punishment-info in config
        muteConfig.createSection("punishment-info." +username);
        if (uuid != null) muteConfig.set("punishment-info." + username + ".uuid", uuid.toString());
        muteConfig.set("punishment-info." +username+".time-muted", date.getTime() );
        muteConfig.set("punishment-info." +username+".muted-until", muted_until);
        muteConfig.set("punishment-info." +username+".muted-by", mute_enforcer);
        muteConfig.set("punishment-info." +username+".reason", reason);

        muteLog( username + " (" + uuid + ") was muted by " + mute_enforcer + " for " + reason);

        // save
        try {
            muteConfig.save(file);
        } catch (Exception e) {
            System.out.println(e + "\n[ProxyChat] Error saving mute config!");
            return false;
        }
        loadMuteList();
        return true;
    }
    public static boolean unmutePlayer(String username, String sender) {
        if (!isNameMuted(username)) return false;
        try {
            List<String> mute_list = muteConfig.getStringList("mute-list");
            mute_list.remove(username);
            muteConfig.set("mute-list", mute_list);
            muteConfig.save(file);
            loadMuteList();
            muteLog(username + " unmuted by " + sender);
        } catch (Exception e) {System.out.println(e); return false;}
        return true;
    }
    public static String getMuteNameList() {
        String mute_list = "[";
        for (String s : muted_name) mute_list += s + ", ";
        return mute_list.replaceAll(", $", "") + "]";
    }
    public static String getMuteUUIDList() {
        String mute_list = "[";
        for (String s : muted_uuid) mute_list += s + ", ";
        return mute_list.replaceAll(", $", "") + "]";
    }
    public static String muteInfo(String username) {
        Date date = new Date();
        // return info on a player's mute status
        boolean is_muted = isNameMuted(username);
        String mute_info =
                "player: " + username + "\n" +
                        "is_muted: " + is_muted + "\n";
        if (is_muted) {
            // return further info if player is, in fact, muted
            long issued = muteConfig.getLong("punishment-info."+username+".time-muted");
            long until = muteConfig.getLong("punishment-info."+username+".muted-until");
            double days_left = (until - date.getTime()) * 0.000000011574074;
            mute_info +=
                    "uuid: " + muteConfig.get("punishment-info."+username+".uuid") + "\n" +
                            "date-issued: " + issued + "\n" +
                            "muted_until: " + until + "\n" +
                            "days_left: " + days_left + "\n" +
                            "muted_by: " + muteConfig.get("punishment-info."+username+".muted-by") + "\n" +
                            "reason: " + muteConfig.get("punishment-info."+username+".reason") + "\n";
        }
        return mute_info;
    }

    public static void loadMuteList() {
        muted_name = null;
        muted_uuid = new ArrayList<>();

        // (re)load the mute list from config file
        muted_name = muteConfig.getStringList("mute-list");
        for (String s : muted_name) muted_uuid.add( muteConfig.getString("punishment-info." + s + ".uuid") );
    }
    public static void muteLog(String log) {
        // log all mute actions to log file
        net.txsla.chatfilter.log.log.add(log);
    }
}
