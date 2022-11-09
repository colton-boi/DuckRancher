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
    /*CRYSTAL(6),
    HUNTER(7),
    HONEY(8),
    PUDDLE(9),
    COTTON(10),
    FLUTTER(11),
    BATTY(12),
    ANGLER(13),
    RINGTAIL(14),
    FIRE(15),
    LUCKY(16),
    GOLD(17),
    TARR(18),
    GORDO(19)*/;

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
