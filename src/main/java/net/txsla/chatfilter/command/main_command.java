package net.txsla.chatfilter.command;

import net.txsla.chatfilter.command.sub.debug;
import net.txsla.chatfilter.command.sub.profile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.List;

public class main_command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // if no args, then return version
        if (args.length == 0) {
            sender.sendMessage("Advanced Restart v1.2.0");
            return true;
        }

        // send args to subcommand
        switch (args[0]) {
            case "debug":
                debug.run(args, sender);
                break;
            case "profile":
                profile.run(args, sender);
                break;
            default:
                return false;
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // select subcommand
        if (args.length <= 1) {
            List<String> list = new ArrayList<>();
            list.add("profile");
            list.add("debug");

            return list;
        }

        // return data from subcommand
        switch (args[0]) {
            case "debug":
                return debug.tab(args);
            case "profile":
                return debug.tab(args);
            default:
                return null;
        }
    }
}
