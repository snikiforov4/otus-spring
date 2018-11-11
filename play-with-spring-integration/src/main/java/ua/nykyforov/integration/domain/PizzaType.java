package ua.nykyforov.integration.domain;

public enum PizzaType {
    HAWAIIAN(1_000),
    VEGETARIAN(500),
    CHEESE(1_000),
    CHICKEN_BBQ_SPECIAL(2_000);

    private final long cookingTime;

    PizzaType(long cookingTime) {
        this.cookingTime = cookingTime;
    }

    public long getCookingTime() {
        return cookingTime;
    }
}
