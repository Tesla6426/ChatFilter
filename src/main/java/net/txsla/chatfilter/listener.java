package net.txsla.chatfilter;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.txsla.chatfilter.spam.*;
import net.txsla.chatfilter.mute.*;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        // get sender info
        Player sender = event.getPlayer();
        String message = event.message().toString();
        boolean muted = false;

        // check if player is muted
        if ( mute.isMuted(sender.getUniqueId()) || mute.isMuted(sender.name().toString())) muted = true;

        if (spamLimiter.enabled) {
            spamLimiter.inc(sender, 1);
            if (!spamLimiter.canChat(sender)) {
                // shows the spammer their own messages, so they do not know they are being ignored
                send.messagePlayer(sender, format.playerMessage(sender, message) );
                return;
            }
        }


    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if ( spamLimiter.enabled) spamLimiter.addPlayer(event.getPlayer());
    }


}
