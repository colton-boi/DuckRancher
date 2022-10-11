package me.colton.slimerancher.Enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum FoodType implements Type {
    CARROT(FoodCategory.VEGGIE, 1),
    HEART_BEET(FoodCategory.VEGGIE, 2),
    ODD_ONION(FoodCategory.VEGGIE, 3),
    WATER_LETTUCE(FoodCategory.VEGGIE, 4),

    POGOFRUIT(FoodCategory.FRUIT, 5),
    CUBERRY(FoodCategory.FRUIT, 6),
    MINT_MANGO(FoodCategory.FRUIT, 7),
    POMEGRANITE(FoodCategory.FRUIT, 8),

    HEN_HEN(FoodCategory.MEAT, 9),
    STONY_HEN(FoodCategory.MEAT, 10),
    BRIAR_HEN(FoodCategory.MEAT, 11),
    SEA_HEN(FoodCategory.MEAT, 12),
    ROOSTRO(FoodCategory.MEAT, 13),
    ELDER_HEN(FoodCategory.MEAT, 14),
    ELDER_ROOSTRO(FoodCategory.MEAT, 15),

    CHICKADOO(FoodCategory.FUTURE_MEAT, 16),
    STONY_CHICKADOO(FoodCategory.FUTURE_MEAT, 17),
    BRIAR_CHICKADOO(FoodCategory.FUTURE_MEAT, 18),
    SEA_CHICKADOO(FoodCategory.FUTURE_MEAT, 19);


    private final ItemStack headItem;
    private final FoodCategory category;

    FoodType(FoodCategory category, Integer modelDataNumber) {
        headItem = new ItemStack(Material.CREEPER_HEAD, 1);
        headItem.getItemMeta().setCustomModelData(modelDataNumber);
        this.category = category;
    }

    public ItemStack getHeadItem() {
        return this.headItem;
    }

    /**
     * Get the category of food it is in
     * @return      the category
     */
    public FoodCategory getCategory() {
        return this.category;
    }
}