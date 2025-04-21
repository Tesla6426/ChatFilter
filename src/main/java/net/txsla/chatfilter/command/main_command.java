package net.txsla.chatfilter.command;

import net.txsla.chatfilter.ChatFilter;
import net.txsla.chatfilter.command.sub.*;
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
            sender.sendMessage(ChatFilter.plugin.getPluginMeta().getName() + " " + ChatFilter.plugin.getPluginMeta().getVersion());
            return true;
        }

        // send args to subcommand
        switch (args[0]) {
            case "status":
                status.run(args, sender);
                break;
            case "debug":
                debug.run(args, sender);
                break;
            case "enable":
                enable.run(args, sender);
                break;
            case "disable":
                disable.run(args, sender);
                break;
            case "profile":
                profile.run(args, sender);
                break;
            case "mute":
                mute.run(args, sender);
                break;
            case "unmute":
                unmute.run(args, sender);
                break;
            case "reload":
                reload.run(args, sender);
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
            list.add("mute");
            list.add("unmute");
            list.add("reload");
            list.add("enable");
            list.add("disable");
            list.add("status");
            return list;
        }

        // return data from subcommand
        switch (args[0]) {
            case "debug":
                return debug.tab(args);
            case "mute":
                return mute.tab(args);
            case "profile":
                return profile.tab(args);
            case "disable":
                return disable.tab(args);
            case "enable":
                return enable.tab(args);
            case "unmute":
                return unmute.tab(args);
            case "status":
                return status.tab(args);
            default:
                return null;
        }
    }
}
