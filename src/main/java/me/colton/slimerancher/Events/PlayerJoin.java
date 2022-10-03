package me.colton.slimerancher.Events;

import me.colton.slimerancher.Items.VacPack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static me.colton.slimerancher.SlimeRancher.creatureManager;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        creatureManager.addPlayerToMap(player);
        new VacPack(e.getPlayer());
    }
}
