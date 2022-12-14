package me.colton.duckrancher.spawners;

import me.colton.duckrancher.enums.SpawnerType;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpawnerManager {
    private final HashMap<UUID, List<Spawner>> spawners = new HashMap<>();
    public final double maxCreaturesPerSpawner;

    public SpawnerManager(double maxCreaturesPerSpawner) {
        this.maxCreaturesPerSpawner = maxCreaturesPerSpawner;
    }


    public List<Spawner> getSpawners() {
        return spawners.values().stream().flatMap(List::stream).toList();
    }

    public List<Spawner> getSpawners(UUID player) {
        return spawners.computeIfAbsent(player, k -> new ArrayList<>());
    }

    private List<Spawner> getSpawners(UUID player, SpawnerType type, boolean isType) {
        return getSpawners(player).stream()
                .filter(spawner -> (spawner.getType() == type) == isType)
                .toList();
    }

    private Spawner getClosest(UUID player, SpawnerType type, boolean isType) {
        List<Spawner> slimeSpawners = getSpawners(player, type, isType);
        double shortestDist = Double.MAX_VALUE;
        Spawner closest = null;
        for (Spawner spawner : slimeSpawners) {
            if ((Bukkit.getPlayer(player).getLocation().distance(spawner.getLocation())) < shortestDist && spawner.canSpawn()) {
                shortestDist = (Bukkit.getPlayer(player).getLocation().distance(spawner.getLocation()));
                closest = spawner;
            }
        }
        return closest;
    }

    /**
     * tick the nearest slime spawner belonging to the player
     * @param player    the player's uuid
     */
    public void tickNearestSlimeSpawner(UUID player) {
        Spawner spawner = getClosest(player, SpawnerType.Slime, true);
        if (spawner != null) {
            spawner.tick();
        }
    }

    /**
     * tick the nearest spawner, that is not a slime spawner, belonging to the player
     * @param player    the player's uuid
     */
    public void tickNearestCreatureSpawner(UUID player) {
        getClosest(player, SpawnerType.Slime, false).tick();
    }
}
