package me.colton.slimerancher;

import me.colton.slimerancher.Entities.Creatures.CreatureManager;
import me.colton.slimerancher.Events.PlayerJoin;
import me.colton.slimerancher.Spawners.SpawnerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SlimeRancher extends JavaPlugin {

    public static SlimeRancher instance;
    public static CreatureManager creatureManager;
    public static SpawnerManager spawnerManager;

    @Override
    public void onEnable() {
        instance = this;
        creatureManager = new CreatureManager(2500, 25);
        spawnerManager = new SpawnerManager(5);

        registerEvents();
        registerCommands();
        saveDefaultConfig();

        getLogger().info("Slime Rancher version " + getVersion() + " is enabled!");
    }

    /**
     * Get the version of the plugin currently running
     * @return  the current plugin version
     */
    public String getVersion() {
        return getDescription().getVersion();
    }

    /**
     * Register events for the plugin
     */
    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    /**
     * Register commands for the plugin
     */
    public void registerCommands() {

    }
}
