package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDrinks.LATTE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedDrinkTest {
    private static final String VALID_NAME = LATTE.getName();
    private static final double VALID_PRICE = LATTE.getPrice();
    private static final String VALID_CATEGORY = LATTE.getCategory();

    @Test
    public void toModelType_validDrinkDetails_returnsDrink() throws Exception {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(LATTE);
        assertEquals(LATTE, drink.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                "", VALID_PRICE, VALID_CATEGORY);
        String expectedMessage = "Drink name cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                null, VALID_PRICE, VALID_CATEGORY);
        String expectedMessage = "Drink name cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                VALID_NAME, 0, VALID_CATEGORY);
        String expectedMessage = "Drink price must be a positive number";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                VALID_NAME, VALID_PRICE, "");
        String expectedMessage = "Drink category cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                VALID_NAME, VALID_PRICE, null);
        String expectedMessage = "Drink category cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }
}
