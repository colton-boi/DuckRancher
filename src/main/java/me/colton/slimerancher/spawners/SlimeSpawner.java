package me.colton.slimerancher.spawners;

import me.colton.slimerancher.entities.creatures.Creature;
import me.colton.slimerancher.entities.creatures.Slime;
import me.colton.slimerancher.enums.SlimeType;
import me.colton.slimerancher.enums.SpawnerType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static me.colton.slimerancher.SlimeRancher.creatureManager;
import static me.colton.slimerancher.SlimeRancher.spawnerManager;

public class SlimeSpawner implements Spawner {
    private final List<Creature> spawnedCreatures = new ArrayList<>();
    private final Location location;
    private final UUID owner;
    private final SlimeType type;
    private double ticksSinceSpawn = 0;
    private boolean active = true;
    private Random random = new Random();

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
        if (random.nextDouble() > 0.99) {
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
        this.spawnedCreatures.add(spawned);
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
        return (spawnedCreatures.stream().filter(Creature::isAlive).count() < spawnerManager.maxCreaturesPerSpawner);
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
