package me.colton.slimerancher.Events;

import me.colton.slimerancher.Entities.Enums.SlimeType;
import me.colton.slimerancher.Entities.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        int i = 0;
        while (i < 100) {
            new Slime(e.getPlayer().getLocation(), SlimeType.BOOM, player);
            i++;
        }
    }
}
