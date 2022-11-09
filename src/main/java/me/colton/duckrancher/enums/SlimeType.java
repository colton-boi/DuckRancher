package me.colton.duckrancher.enums;

import me.colton.duckrancher.entities.creatures.Slime;
import me.colton.duckrancher.entities.creatures.slimes.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public enum SlimeType implements Type {
    PINK(1, PinkSlime.class),
    ROCK(2, RockSlime.class),
    TABBY(3, TabbySlime.class),
    PHOSPHOR(4, PhosphorSlime.class),
    BOOM(5, BoomSlime.class),
    CRYSTAL(6, CrystalSlime.class),
    HUNTER(7, HunterSlime.class),
    HONEY(8, HoneySlime.class),
    BATTY(9, BattySlime.class),
    FIRE(10, FireSlime.class),
    LUCKY(11, LuckySlime.class),
    GOLD(12, GoldSlime.class),
    TARR(13, TarSlime.class),
    GORDO(14, GordoSlime.class);

    private final ItemStack headItem;
    private final Class<? extends Slime> slimeClass;

    SlimeType(Integer modelDataNumber, Class<? extends Slime> slimeClass) {
        headItem = new ItemStack(Material.SKELETON_SKULL, 1);
        headItem.getItemMeta().setCustomModelData(modelDataNumber);
        this.slimeClass = slimeClass;
    }

    public ItemStack getHeadItem() {
        return this.headItem;
    }

    public Slime getSlimeFromType(Location location, UUID player) {
        try {
            return slimeClass.getDeclaredConstructor().newInstance().init(location, this, player);
        } catch (Exception e) {
            getLogger().severe(e + "\n" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
