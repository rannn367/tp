package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.util.TestPersonBuilder;

public class SameShiftTimingPredicateTest {

    @Test
    public void equals() {
        ShiftTiming timing1 = new ShiftTiming("8am-4pm");
        ShiftTiming timing2 = new ShiftTiming("4pm-12am");

        SameShiftTimingPredicate firstPredicate = new SameShiftTimingPredicate(timing1);
        SameShiftTimingPredicate secondPredicate = new SameShiftTimingPredicate(timing2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameShiftTimingPredicate firstPredicateCopy = new SameShiftTimingPredicate(timing1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different shift timing -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_shiftTimingMatches_returnsTrue() {
        // Match shift timing from ALEX in TypicalStaff (8am-4pm)
        SameShiftTimingPredicate predicate = new SameShiftTimingPredicate(new ShiftTiming("8am-4pm"));
        assertTrue(predicate.test(ALEX));
    }

    @Test
    public void test_shiftTimingDoesNotMatch_returnsFalse() {
        // Test with a different shift timing
        SameShiftTimingPredicate predicate = new SameShiftTimingPredicate(new ShiftTiming("10am-6pm"));
        assertFalse(predicate.test(ALEX));

        // Test with another staff's shift timing
        predicate = new SameShiftTimingPredicate(new ShiftTiming("8am-4pm")); // ALEX's shift
        assertFalse(predicate.test(BEN)); // BEN has different shift (4pm-12am)
    }

    @Test
    public void test_nonStaffInstance_returnsFalse() {
        // Test with a Person that is not a Staff
        SameShiftTimingPredicate predicate = new SameShiftTimingPredicate(new ShiftTiming("8am-4pm"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        ShiftTiming shiftTiming = new ShiftTiming("8am-4pm");
        SameShiftTimingPredicate predicate = new SameShiftTimingPredicate(shiftTiming);

        String expected = SameShiftTimingPredicate.class.getCanonicalName() + "{shiftTiming=" + shiftTiming + "}";
        assertEquals(expected, predicate.toString());
    }
}
