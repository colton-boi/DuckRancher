package me.colton.slimerancher.Spawners;

import me.colton.slimerancher.Entities.Creatures.Creature;
import me.colton.slimerancher.Enums.SpawnerType;
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
