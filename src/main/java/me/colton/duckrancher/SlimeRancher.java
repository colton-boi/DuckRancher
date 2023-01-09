package me.colton.duckrancher;

import me.colton.duckrancher.entities.creatures.CreatureManager;
import me.colton.duckrancher.events.PlayerJoin;
import me.colton.duckrancher.spawners.SpawnerManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public class SlimeRancher extends JavaPlugin {

    public static SlimeRancher instance;
    public static CreatureManager creatureManager;
    public static SpawnerManager spawnerManager;

    @Override
    public void onEnable() {
        instance = this;
        creatureManager = new CreatureManager(2500, 25);
        spawnerManager = new SpawnerManager(5);

        registerEvents("me.colton.duckrancher.events");
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
     * Register all listeners in folder
     * _package: filter to only classes in package
     */
    public void registerEvents(String _package) {
        Reflections reflections = new Reflections(_package);
        Set<Class<? extends Listener>> listeners = reflections.getSubTypesOf(Listener.class);
        for (Class<? extends Listener> listenerClass: listeners) {
            try {
                Listener listener = listenerClass.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener,this);
            } catch (InstantiationException | NoSuchMethodException  e) {
                instance.getLogger().warning(e.getMessage());
                instance.getLogger().warning(Arrays.toString(e.getStackTrace()));
            } catch (IllegalAccessException | InvocationTargetException  e) {
                instance.getLogger().severe(e.getMessage());
                instance.getLogger().severe(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    /**
     * Register commands for the plugin
     */
    public void registerCommands() {

    }
}
