package me.colton.slimerancher.Entities;

import me.colton.slimerancher.Entities.Enums.SlimeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

import static org.bukkit.Bukkit.getScheduler;
import static me.colton.slimerancher.SlimeRancher.instance;

public class Slime {

    private final Location location;
    private final World world;
    private final SlimeType mainType;
    private final UUID owner;
    private final ArmorStand entity;

    public Slime(Location location, SlimeType type, UUID owner) {
        this.location = location;
        this.world = location.getWorld();
        this.mainType = type;
        this.owner = owner;
        this.entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        this.entity.setSmall(true);
        this.entity.setVisible(false);
        this.entity.setGravity(false);
        this.entity.getEquipment().setHelmet(new ItemStack(Material.SLIME_BLOCK));
    }

    private boolean inAir = false;
    private boolean jumping = false;
    private static final Vector velocity = new Vector(0, 0, 0);
    private static final Vector g = new Vector(0, -0.4903325, 0);
    private static final Vector aR = new Vector(0.99, 0.999, 0.99);
    private static final Vector gR = new Vector(0.03, 0, 0.03);

    int mainTask = getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

        @Override
        public void run() {
            inAir = (world.getBlockAt(location).isPassable() || jumping);

            entity.teleport(location);

            // Calculate movement on a separate thread
            getScheduler().runTaskAsynchronously(instance, () -> {
                jumping = false;

                if (inAir) {
                    velocity.add(g);
                    velocity.multiply(aR);
                } else {
                    velocity.multiply(gR);
                    if (Math.random() > 0.98) { // Every ~50 ticks (on average), make the slime jump
                        velocity.add(new Vector((Math.random() - 0.5) * 4, 8, (Math.random() - 0.5) * 4)); // Jumps 3.4 blocks upward
                        inAir = true;
                        jumping = true;
                    }
                }

                location.add(velocity.clone().multiply(0.05));

            });
        }

    }, 200L, 1L);
}