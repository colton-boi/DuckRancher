package me.colton.slimerancher.Enums;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum SlimeType implements Type {
    PINK(1),
    ROCK(2),
    TABBY(3),
    PHOSPHOR(4),
    BOOM(5),
    CRYSTAL(6),
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
    GORDO(19);

    private final ItemStack headItem;

    SlimeType(Integer modelDataNumber) {
        headItem = new ItemStack(Material.SKELETON_SKULL, 1);
        headItem.getItemMeta().setCustomModelData(modelDataNumber);
    }

    public ItemStack getHeadItem() {
        return this.headItem;
    }
}
