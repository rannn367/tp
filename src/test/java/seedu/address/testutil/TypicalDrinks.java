package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.drink.*;

/**
 * A utility class containing a list of {@code Drink} objects to be used in tests.
 */
public class TypicalDrinks {

    public static final Drink ESPRESSO = new Drink(new DrinkName("Espresso"), new Price(3.50), new Category("Coffee"));
    public static final Drink CAPPUCCINO = new Drink(new DrinkName("Cappuccino"),
            new Price(4.50), new Category("Coffee"));
    public static final Drink LATTE = new Drink(new DrinkName("Latte"), new Price(4.50), new Category("Coffee"));
    public static final Drink MOCHA = new Drink(new DrinkName("Mocha"), new Price(5.00), new Category("Coffee"));
    public static final Drink GREEN_TEA = new Drink(new DrinkName("Green Tea"), new Price(3.00), new Category("Tea"));
    public static final Drink BLACK_TEA = new Drink(new DrinkName("Black Tea"), new Price(3.00), new Category("Tea"));
    public static final Drink EARL_GREY = new Drink(new DrinkName("Earl Grey"), new Price(3.50), new Category("Tea"));
    public static final Drink CHAMOMILE = new Drink(new DrinkName("Chamomile"), new Price(3.50), new Category("Tea"));
    public static final Drink ORANGE_JUICE = new Drink("Orange Juice", 4.00, "Cold Drinks");
    public static final Drink APPLE_JUICE = new Drink("Apple Juice", 4.00, "Cold Drinks");
    public static final Drink ICED_COFFEE = new Drink("Iced Coffee", 4.50, "Cold Drinks");
    public static final Drink CHOCOLATE_COOKIE = new Drink("Chocolate Chip Cookie", 2.50, "Pastries");
    public static final Drink CROISSANT = new Drink("Croissant", 3.00, "Pastries");
    public static final Drink BLUEBERRY_MUFFIN = new Drink("Blueberry Muffin", 3.50, "Pastries");

    // Drinks with unusual properties for testing edge cases
    public static final Drink EXPENSIVE_SPECIALTY = new Drink("Special Reserve Coffee", 15.00, "Specialty");
    public static final Drink LONG_NAME_DRINK = new Drink("Super Deluxe Triple Shot Caramel Vanilla "
        + "Bean Latte with Extra Whipped Cream", 6.00, "Coffee");

    private TypicalDrinks() {} // prevents instantiation

    /**
     * Returns a {@code DrinkCatalog} with all the typical drinks.
     */
    public static DrinkCatalog getTypicalDrinkCatalog() {
        DrinkCatalog dc = new DrinkCatalog();
        for (Drink drink : getTypicalDrinks()) {
            dc.addDrink(drink);
        }
        return dc;
    }

    /**
     * Returns a list containing all typical drinks for testing.
     */
    public static List<Drink> getTypicalDrinks() {
        return new ArrayList<>(Arrays.asList(ESPRESSO, CAPPUCCINO, LATTE, MOCHA,
                GREEN_TEA, BLACK_TEA, EARL_GREY, CHAMOMILE,
                ORANGE_JUICE, APPLE_JUICE, ICED_COFFEE));
    }

    /**
     * Returns a smaller subset of typical drinks for specific tests.
     */
    public static List<Drink> getBasicDrinks() {
        return new ArrayList<>(Arrays.asList(ESPRESSO, LATTE, GREEN_TEA, ORANGE_JUICE));
    }

    /**
     * Returns only the food items (pastries).
     */
    public static List<Drink> getFoodItems() {
        return new ArrayList<>(Arrays.asList(CHOCOLATE_COOKIE, CROISSANT, BLUEBERRY_MUFFIN));
    }

    /**
     * Returns drinks with unusual properties for edge case testing.
     */
    public static List<Drink> getUnusualDrinks() {
        return new ArrayList<>(Arrays.asList(EXPENSIVE_SPECIALTY, LONG_NAME_DRINK));
    }
}
