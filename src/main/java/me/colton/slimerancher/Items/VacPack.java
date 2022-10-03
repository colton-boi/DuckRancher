package me.colton.slimerancher.Items;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static me.colton.slimerancher.SlimeRancher.instance;
import static org.bukkit.Bukkit.getScheduler;

public class VacPack {

    private final Player player;

    public VacPack(Player player) {
        this.player = player;
    }

    private void useVacPack() {
        Vector facing = player.getEyeLocation().getDirection().normalize();
        Location pL = player.getEyeLocation();
        // double maxDist = (double) getPlayerData(player.getUniqueId()).get("VacPackDistance");
        double maxDist = 5;

        for (Entity e : player.getNearbyEntities(maxDist, maxDist, maxDist)) {
            if (e == player) return;

            Location l = e.getLocation();
            Vector toEntity = new Vector((l.getX()-pL.getX()), (l.getY()-pL.getY()), (l.getZ()-pL.getZ()));
            Vector normalToEntity = toEntity.clone().normalize();

            if (!(facing.getX()+0.5 > normalToEntity.getX() && normalToEntity.getX() > facing.getX()-0.5)) {
                continue;
            }
            if (!(facing.getY()+0.5 > normalToEntity.getY() && normalToEntity.getY() > facing.getY()-0.5)) {
                continue;
            }
            if (!(facing.getZ()+0.5 > normalToEntity.getZ() && normalToEntity.getZ() > facing.getZ()-0.5)) {
                continue;
            }

            e.setVelocity(e.getVelocity().add(toEntity.multiply(-0.05)));

        }
    }

    int task = getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

        @Override
        public void run() {
            if (!player.isOnline()) getScheduler().cancelTask(task);
            if (!player.isBlocking()) return;

            useVacPack();

        }

    }, 0L, 1L);

}
