package me.colton.duckrancher.spawners;

import me.colton.duckrancher.entities.creatures.Creature;
import me.colton.duckrancher.enums.SpawnerType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface Spawner {
    List<Creature> creatures = new ArrayList<>();

    /**
     * Tick the spawner
     */
    void tick();

    /**
     * Spawn a creature at the spawner
     */
    void spawnCreature();

    /**
     * Set if this spawner is active
     * @param active  whether the spawner can spawn mobs
     */
    void setActive(boolean active);

    /**
     * Check if a creature can be spawned by this spawner
     * @return      a creature can spawn
     */
    boolean canSpawn();

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
