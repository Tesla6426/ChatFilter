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

        // dispatch command asynchronously
        final String com = command;
        Thread execute_commands_asynchronously = new Thread(()-> {
            if (Bukkit.isPrimaryThread()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), com);
            } else {
                Bukkit.getScheduler().runTask(ChatFilter.plugin, () ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), com)
                );
            }
        });
        execute_commands_asynchronously.start();

    }
    public static void console(List<String> commands) {
        for (String command : commands) console(command);
    }
}
