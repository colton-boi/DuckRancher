package me.colton.slimerancher.Entities.Creatures;

import me.colton.slimerancher.Enums.SlimeType;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class Slime implements Creature {

    private SlimeType mainType;
    private SlimeType secondaryType;
    private UUID owner;
    private ArmorStand entity;
    private Random random = new Random();

    public Slime init(Location location, SlimeType mainType, UUID owner) {
        this.mainType = mainType;
        this.owner = owner;
        this.entity = location.getWorld().spawn(location, ArmorStand.class, (stand) -> {
            stand.setSmall(true);
            stand.setVisible(false);
            stand.getEquipment().setHelmet(mainType.getHeadItem());
        });

        return this;

    }

    @Override
    public void tick() {

        // Every ~100 ticks (5 seconds) while on the ground, jump
        if (random.nextDouble() > 0.99) {
            if (entity.isOnGround()) {
                jump();
            }
        }

        customTick();

    }

    public abstract void customTick();

    public abstract void customParticle();

    /**
     * Make the slime jump up and in a random direction
     */
    public void jump() {
        entity.setVelocity(entity.getVelocity().add(new Vector((random.nextDouble()-0.5), (random.nextDouble()*0.5)+0.5, (random.nextDouble()-0.5))));
        customParticle();
        updateDirection();
    }
    public void updateDirection() {
        Vector direction = entity.getVelocity().clone().normalize();
        // Thanks CocoRaid: https://www.spigotmc.org/threads/how-to-get-yaw-pitch-and-roll-from-a-vector.368669/
        double yaw = Math.atan2(direction.getZ(), direction.getX());
        entity.setHeadPose(new EulerAngle(0, yaw + Math.toRadians(90), 0));
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
        return entity.getLocation();
    }
}
