package seedu.address.model.drink;

import java.util.List;

/**
 * Unmodifiable view of a drink catalog
 */
public interface ReadOnlyDrinkCatalog {

    /**
     * Returns an unmodifiable view of the drinks list.
     * This list will not contain any duplicate drinks.
     */
    List<Drink> getDrinkList();
}
