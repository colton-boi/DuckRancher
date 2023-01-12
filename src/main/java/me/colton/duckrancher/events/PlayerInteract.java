package me.colton.duckrancher.events;

import me.colton.duckrancher.items.VacPack;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class PlayerInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event){
        if(!(event.getRightClicked() instanceof ArmorStand)){
            return;
        }
        VacPack.getHandlerForPlayer(event.getPlayer()).onRightClickSlime();
        event.setCancelled(true);
    }
}
