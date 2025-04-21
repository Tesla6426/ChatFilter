package net.txsla.chatfilter.command.sub;

import com.mojang.brigadier.Command;
import net.kyori.adventure.text.Component;
import net.txsla.chatfilter.config;
import net.txsla.chatfilter.mute;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;

public class unmute {
    public static void run(String[] args, CommandSender sender) {
        if (args.length <= 1) {
            sender.sendMessage("/cf unmute <player>");
            return;
        }
        if ( mute.unmutePlayer( args[1] , sender.getName() ) ) {
            sender.sendMessage(Component.text(args[1] + " unmuted!"));
            return;
        }
        sender.sendMessage(Component.text("Failed to unmute " + args[1] + "! Is player already unmuted?"));
        return;
    }
    public static List<String> tab(String[] args) {
        if (args.length > 1) {
            List<String> list = new ArrayList<>();
            for (String name : mute.muted_name) list.add(name);
            return list;
        }
        return null;
    }
}
