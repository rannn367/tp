package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HoursWorkedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HoursWorked(null));
    }

    @Test
    public void constructor_invalidHoursWorked_throwsIllegalArgumentException() {
        String invalidHoursWorked = "";
        assertThrows(IllegalArgumentException.class, () -> new HoursWorked(invalidHoursWorked));
    }

    @Test
    public void isValidHoursWorked() {
        // null hours worked
        assertThrows(NullPointerException.class, () -> HoursWorked.isValidHoursWorked(null));

        // invalid hours worked
        assertFalse(HoursWorked.isValidHoursWorked("")); // empty string
        assertFalse(HoursWorked.isValidHoursWorked(" ")); // spaces only
        assertFalse(HoursWorked.isValidHoursWorked("abc")); // alphabets
        assertFalse(HoursWorked.isValidHoursWorked("12a")); // alphanumeric
        assertFalse(HoursWorked.isValidHoursWorked("-5")); // negative numbers
        assertFalse(HoursWorked.isValidHoursWorked("3.5")); // decimal numbers

        // valid hours worked
        assertTrue(HoursWorked.isValidHoursWorked("0")); // zero is allowed
        assertTrue(HoursWorked.isValidHoursWorked("1")); // minimum valid number
        assertTrue(HoursWorked.isValidHoursWorked("40")); // typical work hours
        assertTrue(HoursWorked.isValidHoursWorked("168")); // max possible in a week
    }

    @Test
    public void equals() {
        HoursWorked hoursWorked = new HoursWorked("40");

        // same values -> returns true
        assertTrue(hoursWorked.equals(new HoursWorked("40")));

        // same object -> returns true
        assertTrue(hoursWorked.equals(hoursWorked));

        // null -> returns false
        assertFalse(hoursWorked.equals(null));

        // different types -> returns false
        assertFalse(hoursWorked.equals(5.0f));

        // different values -> returns false
        assertFalse(hoursWorked.equals(new HoursWorked("35")));
    }
}
