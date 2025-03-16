package seedu.address.testutil;

import seedu.address.model.drink.Drink;

/**
 * A utility class to help with building Drink objects.
 */
public class DrinkBuilder {

    public static final String DEFAULT_NAME = "Latte";
    public static final double DEFAULT_PRICE = 4.50;
    public static final String DEFAULT_CATEGORY = "Coffee";

    private String name;
    private double price;
    private String category;

    /**
     * Creates a `DrinkBuilder` with default values.
     */
    public DrinkBuilder() {
        this.name = DEFAULT_NAME;
        this.price = DEFAULT_PRICE;
        this.category = DEFAULT_CATEGORY;
    }

    /**
     * Sets the `name` of the drink.
     */
    public DrinkBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the `price` of the drink.
     */
    public DrinkBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    /**
     * Sets the `category` of the drink.
     */
    public DrinkBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Builds and returns a `Drink` object.
     */
    public Drink build() {
        return new Drink(name, price, category);
    }
}
