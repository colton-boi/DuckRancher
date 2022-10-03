package me.colton.slimerancher.Entities;

import me.colton.slimerancher.Entities.Enums.SlimeType;
import org.bukkit.Bukkit;
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
     * @param player    the player the creature belongs to
     * @param entity    the creature to remove
     */
    public void removeCreature(UUID player, Creature entity) {
        creatures.get(player).remove(entity);
    }

    /**
     * Clears the list of creatures for a player
     * @param player    the player to clear
     * @param remove    whether to remove the player from the hashmap
     */
    public void removeCreatures(UUID player, Boolean remove) {
        creatures.remove(player);
    }


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
                        //creatures.get(p.getUniqueId()).add(new Slime(p.getLocation(), SlimeType.PINK, p.getUniqueId()));
                        spawnerManager.tickNearestSlimeSpawner(p);
                    }
                    if (Math.random()>0.995) {
                        //getClosestChickenSpawn(player);
                        spawnerManager.tickNearestCreatureSpawner(p);
                    }
                }
            }
        }
    }, 0L, 1L);
}
