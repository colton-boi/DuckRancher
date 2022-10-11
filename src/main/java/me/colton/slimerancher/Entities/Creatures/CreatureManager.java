package me.colton.slimerancher.Entities.Creatures;

import me.colton.slimerancher.Enums.SlimeType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static me.colton.slimerancher.SlimeRancher.instance;
import static me.colton.slimerancher.SlimeRancher.spawnerManager;
import static org.bukkit.Bukkit.getScheduler;

public class CreatureManager {

    private final HashMap<UUID, List<Creature>> creatures = new HashMap<>();
    private int maximumCreatures;
    private int maximumCreaturesPerPlayer;

    public CreatureManager(int maximumCreatures, int maximumCreaturesPerPlayer) {
        this.maximumCreatures = maximumCreatures;
        this.maximumCreaturesPerPlayer = maximumCreaturesPerPlayer;
    }

    /**
     * Create an empty arraylist in the creatures hashmap with a key
     * @param player    the UUID to use as a key
     */
    public void addPlayerToMap(UUID player) {
        creatures.put(player, new ArrayList<>());
    }

    /**
     * Remove a single creature from the list of creatures associated with the player
     * @param player    the UUID of the player
     * @param entity    the creature to remove
     */
    public void removeCreature(UUID player, Creature entity) {
        creatures.get(player).remove(entity);
    }

    /**
     * Remove several creatures from the list of creatures associated with the player
     * @param player    the UUID of the player
     */
    public void removeCreatures(UUID player, List<Creature> removed) {
        this.creatures.get(player).removeAll(removed);
    }

    /**
     * Clears the list of creatures for a player
     * @param player    the player to clear
     * @param remove    whether to remove the player from the hashmap
     */
    public void removeCreatures(UUID player, boolean remove) {
        if (remove) {
            creatures.remove(player);
        } else {
            creatures.get(player).clear();
        }
    }

    public Slime spawnCreature(UUID player, Location location, SlimeType type) {
        Slime spawned = new Slime(location, type, player);
        creatures.get(player).add(spawned);
        return spawned;
    }
    /*
    public Livestock spawnCreature(UUID player, Location location, LivestockType type) {
        creatures.get(player).add(new Livestock(location, (LivestockType) type, player));
    }
    */

    public int creatureManagerTask = getScheduler().scheduleSyncRepeatingTask(instance, () -> {

        for (List<Creature> list : creatures.values()) {
            for (Creature creature : list) {
                creature.tick();
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (creatures.size() < maximumCreatures) {
                if (creatures.get(p.getUniqueId()) == null || creatures.get(p.getUniqueId()).size() < maximumCreaturesPerPlayer) {
                    if (Math.random()>0.95) {
                        spawnerManager.tickNearestSlimeSpawner(p.getUniqueId());
                    }
                    if (Math.random()>0.995) {
                        //getClosestChickenSpawn(player);
                        spawnerManager.tickNearestCreatureSpawner(p.getUniqueId());
                    }
                }
            }
        }
    }, 0L, 1L);
}
