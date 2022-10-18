package me.colton.slimerancher.Events;

import me.colton.slimerancher.Enums.SlimeType;
import me.colton.slimerancher.Items.VacPack;
import me.colton.slimerancher.Spawners.SlimeSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static me.colton.slimerancher.SlimeRancher.creatureManager;
import static me.colton.slimerancher.SlimeRancher.spawnerManager;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        creatureManager.addPlayerToMap(player);
        spawnerManager.getSpawners(player).add(new SlimeSpawner(e.getPlayer().getLocation(), player, SlimeType.PINK));
        new VacPack(e.getPlayer());
    }
}
