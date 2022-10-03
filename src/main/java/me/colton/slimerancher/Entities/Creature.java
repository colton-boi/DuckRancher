package me.colton.slimerancher.Entities;


import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.UUID;

public interface Creature {

    /**
     * tick the creature
     */
    void tick();

    /**
     * get the entity attached to the creature
     */
    Entity getEntity();

    /**
     * get the UUID of the owner of the creature
     */
    UUID getOwner();

    /**
     * get the current location of the creature
     */
    Location getLocation();

}
