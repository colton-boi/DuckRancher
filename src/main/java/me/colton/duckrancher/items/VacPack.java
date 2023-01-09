package me.colton.duckrancher.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftHumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static me.colton.duckrancher.SlimeRancher.instance;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getWorlds;

public class VacPack {

    private final Player player;

    public VacPack(Player player) {
        this.player = player;
        vacPacks.put(player,this);
        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
    }

    private static final Map<Player,VacPack> vacPacks = new HashMap<>();

    public static VacPack getHandlerForPlayer(Player player) {
        return vacPacks.get(player);
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
            if (!player.isOnline()){
                getScheduler().cancelTask(task);
                vacPacks.remove(player);
            }
            if(player.isBlocking()){
                useVacPack();
            }
        }

    }, 0L, 1L);

    public void onRightClickSlime() {
        useVacPack();
    }
}
