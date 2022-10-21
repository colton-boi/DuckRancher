package me.colton.slimerancher.Entities.Creatures;


import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.UUID;

public interface Creature {

    /**
     * Ticks the creature
     */
    void tick();

    /**
     * Check if the entity attached to this creature is alive
     * @return      if the entity is living
     */
    boolean isAlive();

    /**
     * Gets the entity attached to the creature
     * @return      the entity
     */
    Entity getEntity();

    /**
     * Gets the UUID of the owner of the creature
     * @return      the owner's uuid
     */
    UUID getOwner();

    /**
     * Gets the current location of the creature
     * @return      the current location
     */
    Location getLocation();
}