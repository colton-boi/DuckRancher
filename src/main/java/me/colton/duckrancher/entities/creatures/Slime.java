package me.colton.duckrancher.entities.creatures;

import me.colton.duckrancher.enums.SlimeType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

import static me.colton.duckrancher.SlimeRancher.creatureManager;

public abstract class Slime implements Creature {

    private SlimeType mainType;
    private SlimeType secondaryType;
    private UUID owner;
    private ArmorStand entity;
    private boolean wasOnGround = true;
    public final Random random = new Random();
    public long lastAnger = System.currentTimeMillis();
    public long foodRemaining = 20;
    public long ticksAlive = 0;

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

        if (entity.isDead()) {
            creatureManager.removeCreature(getOwner(), this);
            return;
        }

        // Every ~100 ticks (5 seconds) while on the ground, jump
        if (random.nextDouble() > 0.99) {
            if (entity.isOnGround()) {
                jump();
            }
        }

        if(!wasOnGround && entity.isOnGround()){
            playLandingSound();
            playLandingParticles();
        }

        ticksAlive++;

        if (ticksAlive % 600 == 0 && foodRemaining > 0) foodRemaining--; // every 30 seconds, remove 1 food

        if (ticksAlive % 20 == 0 && foodRemaining == 0) customAnger(false); // make angry when not fed

        customTick();
        wasOnGround = entity.isOnGround();
    }

    protected void playLandingSound() {
        Player player = entity.getServer().getPlayer(getOwner());
        if(player == null) return;
        player.playSound(entity.getLocation(),Sound.ENTITY_SLIME_SQUISH,2,getModifiedPitch());
    }

    private void playJumpingSound() {
        Player player = entity.getServer().getPlayer(getOwner());
        if(player == null) return;
        player.playSound(entity.getLocation(),Sound.ENTITY_SLIME_JUMP,2, getModifiedPitch());
    }

    private float getModifiedPitch() {
        return (float) (1 + random.nextDouble(-0.2d, 0.1d));
    }

    protected abstract void playLandingParticles();

    /**
     * Run custom stuff
     */
    public abstract void customTick();

    /**
     * Spawn custom particles per player
     */
    public abstract void playJumpingParticles();

    /**
     *
     */
    public abstract void customAnger(boolean skipCooldown);

    /**
     * Make the slime jump up and in a random direction
     */
    public void jump() {
        Vector velocity = entity.getVelocity().add(new Vector((random.nextDouble()-0.5), (random.nextDouble()*0.5)+0.5, (random.nextDouble()-0.5)));
        entity.teleport(getLocation().setDirection(velocity));
        entity.setVelocity(velocity);
        playJumpingParticles();
        playJumpingSound();
    }



    @Override
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
