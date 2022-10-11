package me.colton.slimerancher.Spawners;

import me.colton.slimerancher.Entities.Creatures.Creature;
import me.colton.slimerancher.Enums.SpawnerType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SlimeSpawner implements Spawner {
    private final List<Creature> creatures = new ArrayList<>();
    private final Location location;
    private final UUID owner;
    private double ticksSinceSpawn = 0;

    public SlimeSpawner(Location location, UUID owner) {
        this.location = location;
        this.owner = owner;
    }

    public void tick() {
        ticksSinceSpawn++;
        if (!canSpawn()) {
            return;
        }
        if (Math.random() > 0.99) {
            spawnCreature();
            ticksSinceSpawn = 0;
        } else if (ticksSinceSpawn > 50) {
            spawnCreature();
            ticksSinceSpawn = 0;
        }
    }

    @Override
    public void spawnCreature() {

    }

    @Override
    public boolean canSpawn() {
        return false;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public SpawnerType getType() {
        return SpawnerType.Slime;
    }
}
