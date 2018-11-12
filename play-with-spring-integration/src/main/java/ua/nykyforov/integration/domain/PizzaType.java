package ua.nykyforov.integration.domain;

public enum PizzaType {
    HAWAIIAN("Hawaiian", 1_000),
    VEGETARIAN("Vegetarian", 500),
    CHEESE("Cheese", 1_000),
    CHICKEN_BBQ_SPECIAL("Chicken BBQ Special", 2_000);

    private final String name;
    private final long cookingTime;

    PizzaType(String name, long cookingTime) {
        this.name = name;
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public long getCookingTime() {
        return cookingTime;
    }
}
