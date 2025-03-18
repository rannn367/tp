package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ShiftTimingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShiftTiming(null));
    }

    @Test
    public void constructor_invalidShiftTiming_throwsIllegalArgumentException() {
        String invalidShiftTiming = "";
        assertThrows(IllegalArgumentException.class, () -> new ShiftTiming(invalidShiftTiming));
    }

    @Test
    public void isValidShiftTiming() {
        // null shift timing
        assertThrows(NullPointerException.class, () -> ShiftTiming.isValidShiftTiming(null));

        // invalid shift timings
        assertFalse(ShiftTiming.isValidShiftTiming("")); // empty string
        assertFalse(ShiftTiming.isValidShiftTiming(" ")); // single space
        assertFalse(ShiftTiming.isValidShiftTiming("    ")); // multiple spaces

        // valid shift timings
        assertTrue(ShiftTiming.isValidShiftTiming("8am-4pm"));
        assertTrue(ShiftTiming.isValidShiftTiming("10:00 - 18:00"));
        assertTrue(ShiftTiming.isValidShiftTiming("Night shift"));
        assertTrue(ShiftTiming.isValidShiftTiming("3PM-11PM"));
    }

    @Test
    public void equals() {
        ShiftTiming shiftTiming = new ShiftTiming("8am-4pm");

        // same values -> returns true
        assertTrue(shiftTiming.equals(new ShiftTiming("8am-4pm")));

        // same object -> returns true
        assertTrue(shiftTiming.equals(shiftTiming));

        // null -> returns false
        assertFalse(shiftTiming.equals(null));

        // different types -> returns false
        assertFalse(shiftTiming.equals(5.0f));

        // different values -> returns false
        assertFalse(shiftTiming.equals(new ShiftTiming("9am-5pm")));
    }
}
