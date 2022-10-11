package me.colton.slimerancher.Spawners;

import me.colton.slimerancher.Enums.SpawnerType;
import org.bukkit.Location;

import java.util.UUID;

public interface Spawner {

    /**
     * Tick the spawner
     */
    void tick();

    /**
     * Get the location of the spawner
     * @return      the location
     */
    Location getLocation();

    /**
     * Get the owner of the area the spawner is in
     * @return      the owner
     */
    UUID getOwner();

    /**
     * Get the type of spawner
     *
     * @return the type of spawner
     */
    SpawnerType getType();

}
