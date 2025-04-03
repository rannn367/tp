package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Person;
import seedu.address.model.util.TestPersonBuilder;

public class SameHoursWorkedPredicateTest {

    @Test
    public void equals() {
        HoursWorked hours1 = new HoursWorked("40");
        HoursWorked hours2 = new HoursWorked("35");

        SameHoursWorkedPredicate firstPredicate = new SameHoursWorkedPredicate(hours1);
        SameHoursWorkedPredicate secondPredicate = new SameHoursWorkedPredicate(hours2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameHoursWorkedPredicate firstPredicateCopy = new SameHoursWorkedPredicate(hours1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different hours worked -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_hoursWorkedMatches_returnsTrue() {
        // Match hours worked from ALEX in TypicalStaff (40)
        SameHoursWorkedPredicate predicate = new SameHoursWorkedPredicate(new HoursWorked("40"));
        assertTrue(predicate.test(ALEX));
    }

    @Test
    public void test_hoursWorkedDoesNotMatch_returnsFalse() {
        // Test with different hours worked
        SameHoursWorkedPredicate predicate = new SameHoursWorkedPredicate(new HoursWorked("20"));
        assertFalse(predicate.test(ALEX));

        // Test with another staff's hours worked
        predicate = new SameHoursWorkedPredicate(new HoursWorked("40")); // ALEX's hours
        assertFalse(predicate.test(BEN)); // BEN has different hours (38)
    }

    @Test
    public void test_nonStaffInstance_returnsFalse() {
        // Test with a Person that is not a Staff
        SameHoursWorkedPredicate predicate = new SameHoursWorkedPredicate(new HoursWorked("40"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        HoursWorked hoursWorked = new HoursWorked("40");
        SameHoursWorkedPredicate predicate = new SameHoursWorkedPredicate(hoursWorked);

        String expected = SameHoursWorkedPredicate.class.getCanonicalName() + "{hoursWorked=" + hoursWorked + "}";
        assertEquals(expected, predicate.toString());
    }
}
