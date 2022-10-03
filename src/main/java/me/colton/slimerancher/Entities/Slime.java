package me.colton.slimerancher.Entities;

import me.colton.slimerancher.Entities.Enums.SlimeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Slime implements Creature {

    public Location location;
    private final SlimeType mainType;
    private SlimeType secondaryType;
    private final UUID owner;
    private final ArmorStand entity;

    public Slime(Location location, SlimeType mainType, UUID owner) {
        this.mainType = mainType;
        this.owner = owner;
        this.entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        this.entity.setSmall(true);
        this.entity.setVisible(false);
        this.entity.getEquipment().setHelmet(new ItemStack(Material.SLIME_BLOCK));
    }

    /**
     * the ticker for slime entity
     */
    public void tick() {

        if (entity.isOnGround()) {
            if (Math.random() > 0.99) {
                // Make the slime jump up and in a random direction every ~100 ticks (5 seconds)
                entity.setVelocity(entity.getVelocity().add(new Vector((Math.random()-0.5), (Math.random()*0.25)+0.5, (Math.random()-0.5)*2)));
            }
        }

        entity.getLocation().setDirection(entity.getVelocity()); // Face the entity where it is heading
        location = entity.getLocation();

    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
