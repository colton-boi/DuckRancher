package me.colton.duckrancher.enums;

import org.bukkit.inventory.ItemStack;

public interface Type {

    /**
     * Get the item to put on the head of a creature with this type
     * @return      the item
     */
    ItemStack getHeadItem();
}