package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the drink-catalog level
 * Duplicates are not allowed (by .isSameDrink comparison)
 */
public class DrinkCatalog implements ReadOnlyDrinkCatalog {

    private final ObservableList<Drink> drinks;
    private final ObservableList<Drink> unmodifiableDrinks;

    /**
     * Creates an empty DrinkCatalog.
     */
    public DrinkCatalog() {
        drinks = FXCollections.observableArrayList();
        unmodifiableDrinks = FXCollections.unmodifiableObservableList(drinks);
    }

    /**
     * Creates a DrinkCatalog using the Drinks in the {@code toBeCopied}
     */
    public DrinkCatalog(ReadOnlyDrinkCatalog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code DrinkCatalog} with {@code newData}.
     */
    public void resetData(ReadOnlyDrinkCatalog newData) {
        requireNonNull(newData);

        drinks.clear();
        drinks.addAll(newData.getDrinkList());
    }

    /**
     * Returns true if a drink with the same identity as {@code drink} exists in the drink catalog.
     */
    public boolean hasDrink(Drink drink) {
        requireNonNull(drink);
        return drinks.stream().anyMatch(d -> d.isSameDrink(drink));
    }

    /**
     * Adds a drink to the drink catalog.
     * The drink must not already exist in the drink catalog.
     */
    public void addDrink(Drink drink) {
        requireNonNull(drink);
        drinks.add(drink);
    }

    /**
     * Removes a drink from the drink catalog.
     * The drink must exist in the drink catalog.
     */
    public void removeDrink(Drink key) {
        drinks.remove(key);
    }

    /**
     * Replaces the given drink {@code target} in the list with {@code editedDrink}.
     * {@code target} must exist in the drink catalog.
     */
    public void setDrink(Drink target, Drink editedDrink) {
        requireNonNull(editedDrink);

        int index = drinks.indexOf(target);
        drinks.set(index, editedDrink);
    }

    @Override
    public List<Drink> getDrinkList() {
        return new ArrayList<>(drinks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Drink> getObservableDrinkList() {
        return unmodifiableDrinks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DrinkCatalog)) {
            return false;
        }

        DrinkCatalog otherDrinkCatalog = (DrinkCatalog) other;

        // Use a list comparison since order might matter for display
        return drinks.equals(otherDrinkCatalog.drinks);
    }

    @Override
    public int hashCode() {
        return drinks.hashCode();
    }
}
