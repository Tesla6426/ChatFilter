package net.txsla.chatfilter;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        // get sender info
        Player sender = event.getPlayer();
        String message = PlainTextComponentSerializer.plainText().serialize( event.message() );
        boolean muted = false;

        // bypass perms
        if (sender.hasPermission("chatfilter.filter.bypass")) return;

        // check if player is muted
        if ( mute.isUUIDMuted( sender.getUniqueId().toString() ) || mute.isNameMuted(sender.name().toString())) {
            send.messagePlayer(sender, format.ghostMessage(sender, message) );
            event.setCancelled(true);
            return;
        }

        // handle spam limiter
        if (spamLimiter.enabled) {
            spamLimiter.inc(sender, 1);
            if (!spamLimiter.canChat(sender)) {
                // shows the spammer their own messages, so they do not know they are being ignored
                send.messagePlayer(sender, format.ghostMessage(sender, message) );
                event.setCancelled(true);
                return;
            }
        }

        double timer = System.currentTimeMillis();;

        // scan message through filters
        String scanned_message = filters.scanString(sender, message);

        // output time it took to scan
        if (config.profile) System.out.println("SCANNED IN " + (System.currentTimeMillis() - timer) + " MILLISECONDS" );



        // if message is unchanged, then do nothing
        if (scanned_message == null) {
            if (config.profile) System.out.println(sender.getName() + "s' Message Cancelled" );
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if ( spamLimiter.enabled) spamLimiter.addPlayer(event.getPlayer());
    }


}
