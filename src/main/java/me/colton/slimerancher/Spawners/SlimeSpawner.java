package me.colton.slimerancher.Spawners;

import me.colton.slimerancher.Enums.SpawnerType;
import org.bukkit.Location;

import java.util.UUID;

public class SlimeSpawner implements Spawner {

    @Override
    public void tick() {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public UUID getOwner() {
        return null;
    }

    @Override
    public SpawnerType getType() {
        return SpawnerType.Slime;
    }
}
