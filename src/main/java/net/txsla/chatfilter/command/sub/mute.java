package net.txsla.chatfilter.command.sub;

import com.mojang.brigadier.Command;
import net.kyori.adventure.text.Component;
import net.txsla.chatfilter.config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class mute {
    public static void run(String[] args, CommandSender sender) {
        if (args.length <= 2) {
            sender.sendMessage("/cf mute <player> <duration> <reason>");
            return;
        }

        String muted_player = args[1];
        String duration = args[2];
        String muter = "[@]";
        String reason = null;

        // load reason into string
        if (args.length > 4) {
            reason = "";
            for (int i = 3; i < args.length; i ++) reason += args[i];
        }

        if (sender instanceof Player) {
            muter = sender.getName();
        }

        // mute player
        if ( net.txsla.chatfilter.mute.mutePlayer(muted_player, muter, reason, duration) ) {
            // if successful, tell player
            sender.sendMessage(Component.text("Player " + muted_player + " muted for " + duration));
            return;
        }


        // tell sender it was unsuccessful
        sender.sendMessage(Component.text("Failed to mute player: check command syntax"));
    }
    public static List<String> tab(String[] args) {
        List<String> list = new ArrayList();
        // / mute player duration
        switch (args.length) {
            case 2:
                return null;
            case 3:
                // retun num + time
                if (args[2].matches("[0-9]+")) {
                    // if user has typed numbers, suggest durations after
                    list.add(args[2] + "min"); // minutes
                    list.add(args[2] + "hr"); // hours
                    list.add(args[2] + "d"); // days
                    list.add(args[2] + "wk"); // weeks
                    list.add(args[2] + "yr"); // years
                }else {
                    // I hate to nest ifs like this, but I do not feel like rewriting this method,
                    // and it has no impact on performance
                    if (args[2].matches("[0-9]+[a-z]+")) {
                        // if player is typing time
                        list.add("min"); // minutes
                        list.add("hr"); // hours
                        list.add("d"); // days
                        list.add("wk"); // weeks
                        list.add("yr"); // years
                    } else {
                        list.add("<int>min"); // minutes
                        list.add("<int>hr"); // hours
                        list.add("<int>d"); // days
                        list.add("<int>wk"); // weeks
                        list.add("<int>yr"); // years
                    }
                }
                break;
            default:
                // tell user to provide reason
                list.add("<reason>");
                break;
        }


        return list;
    }
}
