package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PerformanceRatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PerformanceRating(null));
    }

    @Test
    public void constructor_invalidPerformanceRating_throwsIllegalArgumentException() {
        String invalidRating = "6.0"; // Outside the valid range
        assertThrows(IllegalArgumentException.class, () -> new PerformanceRating(invalidRating));
    }

    @Test
    public void isValidPerformanceRating() {
        // null performance rating
        assertThrows(NullPointerException.class, () -> PerformanceRating.isValidPerformanceRating(null));

        // invalid performance ratings
        assertFalse(PerformanceRating.isValidPerformanceRating("")); // empty string
        assertFalse(PerformanceRating.isValidPerformanceRating(" ")); // spaces only
        assertFalse(PerformanceRating.isValidPerformanceRating("abc")); // non-numeric
        assertFalse(PerformanceRating.isValidPerformanceRating("-1")); // negative number
        assertFalse(PerformanceRating.isValidPerformanceRating("5.1")); // greater than 5.0
        assertFalse(PerformanceRating.isValidPerformanceRating("6")); // greater than 5
        assertFalse(PerformanceRating.isValidPerformanceRating("3.14159")); // too many decimal places

        // valid performance ratings
        assertTrue(PerformanceRating.isValidPerformanceRating("0")); // lower boundary
        assertTrue(PerformanceRating.isValidPerformanceRating("5.0")); // upper boundary
        assertTrue(PerformanceRating.isValidPerformanceRating("3.5")); // valid decimal value
        assertTrue(PerformanceRating.isValidPerformanceRating("4")); // whole number
    }

    @Test
    public void equals() {
        PerformanceRating rating = new PerformanceRating("4.5");

        // same values -> returns true
        assertTrue(rating.equals(new PerformanceRating("4.5")));

        // same object -> returns true
        assertTrue(rating.equals(rating));

        // null -> returns false
        assertFalse(rating.equals(null));

        // different types -> returns false
        assertFalse(rating.equals(5.0f));

        // different values -> returns false
        assertFalse(rating.equals(new PerformanceRating("3.0")));
    }
}
