package net.txsla.chatfilter.command;

import net.txsla.chatfilter.ChatFilter;
import net.txsla.chatfilter.config;
import org.bukkit.Bukkit;

import java.util.List;

public class execute {
    public static void console(String command) {

        if (config.debug) System.out.println("execute command " + command);

        if (command.startsWith("/")) command = command.substring(1);

        if (config.debug) System.out.println("[Advanced Restart] executing command " + command);

        // dispatch synchronously
        if (Bukkit.isPrimaryThread()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } else {
            final String com = command; // fuck you java
            Bukkit.getScheduler().runTask(ChatFilter.plugin, () ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), com)
            );
        }

    }
    public static void console(List<String> commands) {
        for (String command : commands) console(command);
    }
}
