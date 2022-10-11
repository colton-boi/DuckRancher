package me.colton.slimerancher.Entities.Creatures;

import me.colton.slimerancher.Enums.SlimeType;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Slime implements Creature {

    private Location location;
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
        this.entity.getEquipment().setHelmet(mainType.getHeadItem());
    }

    @Override
    public void tick() {

        // Every ~100 ticks (5 seconds) while on the ground, jump
        if (Math.random() > 0.99) {
            if (entity.isOnGround()) {
                jump();
            }
        }

        entity.getLocation().setDirection(entity.getVelocity()); // Face the entity where it is heading
        location = entity.getLocation();

    }

    /**
     * Make the slime jump up and in a random direction
     */
    public void jump() {
        entity.setVelocity(entity.getVelocity().add(new Vector((Math.random()-0.5), (Math.random()*0.25)+0.5, (Math.random()-0.5)*2)));
    }

    public boolean isAlive() {
        return !entity.isDead();
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
