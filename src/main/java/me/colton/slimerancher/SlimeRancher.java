package me.colton.slimerancher;

import me.colton.slimerancher.Events.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

public class SlimeRancher extends JavaPlugin {

    public static SlimeRancher instance;

    @Override
    public void onEnable() {
        instance = this;

        registerEvents();
        registerCommands();

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
