package me.colton.duckrancher.enums;

public enum FoodCategory {

    VEGGIE(true),
    FRUIT(true),
    MEAT(true),
    FUTURE_MEAT(false),
    NECTAR(true),
    OTHER(true),
    ALL(true);

    private final boolean edible;

    FoodCategory(boolean isEdible) {
        this.edible = isEdible;
    }

    public boolean isEdible() {
        return edible;
    }
}
