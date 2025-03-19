package seedu.address.model.drink;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Drink in the café management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Drink {

    // Identity fields
    private final DrinkName drinkName;
    private final Price price;
    private final Category category;

    /**
     * Every field must be present and not null.
     */
    public Drink(DrinkName drinkName, Price price, Category category) {
        requireAllNonNull(drinkName, price, category);
        this.drinkName = drinkName;
        this.price = price;
        this.category = category;
    }

    public DrinkName getDrinkName() {
        return drinkName;
    }

    public Price getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Returns true if both drinks have the same name.
     * This defines a weaker notion of equality between two drinks.
     */
    public boolean isSameDrink(Drink otherDrink) {
        if (otherDrink == this) {
            return true;
        }

        return otherDrink != null
                && otherDrink.getDrinkName().toString().equalsIgnoreCase(getDrinkName().toString());
    }

    /**
     * Returns true if both drinks have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Drink)) {
            return false;
        }

        Drink otherDrink = (Drink) other;
        return drinkName.getDrinkName().equalsIgnoreCase(otherDrink.drinkName.getDrinkName())
                && Double.compare(price.getPrice(), otherDrink.price.getPrice()) == 0
                && category.equals(otherDrink.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkName, price, category);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("drinkName", drinkName)
                .add("price", price)
                .add("category", category)
                .toString();
    }
}
