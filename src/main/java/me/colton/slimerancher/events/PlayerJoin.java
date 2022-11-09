package me.colton.slimerancher.events;

import me.colton.slimerancher.enums.SlimeType;
import me.colton.slimerancher.items.VacPack;
import me.colton.slimerancher.spawners.SlimeSpawner;
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
