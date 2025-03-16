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
    private final String name;
    private final double price;
    private final String category;

    /**
     * Every field must be present and not null.
     */
    public Drink(String name, double price, String category) {
        requireAllNonNull(name, price, category);
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
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
                && otherDrink.getName().equalsIgnoreCase(getName());
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
        return name.equalsIgnoreCase(otherDrink.name)
                && Double.compare(price, otherDrink.price) == 0
                && category.equals(otherDrink.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), price, category);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("price", price)
                .add("category", category)
                .toString();
    }
}