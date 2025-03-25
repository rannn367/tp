package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.drink.exceptions.DrinkNotFoundException;

/**
 * Represents a Drink in the café management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Drink {

    // Identity fields
    private final DrinkName drinkName;
    private final Price price;
    private final Category category;

    // Optional fields - stored as transient to not affect equals/hashCode
    private transient String description;
    private transient int stock;

    /**
     * Basic constructor with required fields only.
     * Every field must be present and not null.
     */
    public Drink(DrinkName drinkName, Price price, Category category) {
        requireAllNonNull(drinkName, price, category);
        this.drinkName = drinkName;
        this.price = price;
        this.category = category;
        // Default values for optional fields
        this.description = "";
        this.stock = 0;
    }

    /**
     * Constructs a {@code Drink} object using the given drink name.
     * It looks up the drink details from the {@code UniqueDrinkList}.
     *
     * @param drinkName The name of the drink to look up.
     * @throws NullPointerException if {@code drinkName} is null.
     * @throws DrinkNotFoundException if the drink with the specified name is not found in the catalog.
     */
    public Drink(String drinkName) {
        requireNonNull(drinkName);
        Drink foundDrink = UniqueDrinkList.findDrinkByName(drinkName);
        if (foundDrink == null) {
            throw new DrinkNotFoundException();
        }
        this.drinkName = foundDrink.getDrinkName();
        this.price = foundDrink.getPrice();
        this.category = foundDrink.getCategory();
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

    public String getDescription() {
        return description != null ? description : "";
    }

    public int getStock() {
        return stock;
    }

    /**
     * Sets the description without affecting equals/hashCode.
     * Note: This does not affect the immutability of the core fields.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the stock without affecting equals/hashCode.
     * Note: This does not affect the immutability of the core fields.
     */
    public void setStock(int stock) {
        this.stock = stock;
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
        return getDrinkName().toString() + " $" + getPrice().toString() + " Category: " + getCategory().toString();
    }
}
