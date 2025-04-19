package net.txsla.chatfilter;

import net.txsla.chatfilter.spam.*;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncChatEvent event) {

        // get sender info
        Player sender = event.getPlayer();
        String message = event.message().toString();

        // for dev only
        System.out.println(sender.name() + ": MESSAGE : " + message);



    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if ( spamLimiter.enabled) spamLimiter.addPlayer(event.getPlayer());
    }


}
