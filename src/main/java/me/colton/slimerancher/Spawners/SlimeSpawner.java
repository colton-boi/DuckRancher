package me.colton.slimerancher.Spawners;

import me.colton.slimerancher.Entities.Creatures.Creature;
import me.colton.slimerancher.Entities.Creatures.Slime;
import me.colton.slimerancher.Enums.SlimeType;
import me.colton.slimerancher.Enums.SpawnerType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.colton.slimerancher.SlimeRancher.creatureManager;
import static me.colton.slimerancher.SlimeRancher.spawnerManager;

public class SlimeSpawner implements Spawner {
    private final List<Creature> creatures = new ArrayList<>();
    private final Location location;
    private final UUID owner;
    private final SlimeType type;
    private double ticksSinceSpawn = 0;
    private boolean active = true;

    public SlimeSpawner(Location location, UUID owner, SlimeType type) {
        this.location = location;
        this.owner = owner;
        this.type = type;
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
        Slime spawned = creatureManager.spawnCreature(this.owner, this.location, this.type);
        spawned.jump();
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean canSpawn() {
        if (!active) {
            return false;
        }
        return (creatures.stream().filter(Creature::isAlive).count() < spawnerManager.maxCreaturesPerSpawner);
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
