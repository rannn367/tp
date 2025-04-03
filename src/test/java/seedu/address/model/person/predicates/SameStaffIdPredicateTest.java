package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.StaffId;
import seedu.address.model.util.TestPersonBuilder;

public class SameStaffIdPredicateTest {

    @Test
    public void equals() {
        StaffId id1 = new StaffId("S001");
        StaffId id2 = new StaffId("S002");

        SameStaffIdPredicate firstPredicate = new SameStaffIdPredicate(id1);
        SameStaffIdPredicate secondPredicate = new SameStaffIdPredicate(id2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameStaffIdPredicate firstPredicateCopy = new SameStaffIdPredicate(id1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different staff ID -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_staffIdMatches_returnsTrue() {
        // Match staff ID from ALEX in TypicalStaff (S1001)
        SameStaffIdPredicate predicate = new SameStaffIdPredicate(new StaffId("S1001"));
        assertTrue(predicate.test(ALEX));
    }

    @Test
    public void test_staffIdDoesNotMatch_returnsFalse() {
        // Test with a different staff ID
        SameStaffIdPredicate predicate = new SameStaffIdPredicate(new StaffId("S999"));
        assertFalse(predicate.test(ALEX));

        // Test with another staff's ID
        predicate = new SameStaffIdPredicate(new StaffId("S1001")); // ALEX's ID
        assertFalse(predicate.test(BEN)); // BEN has different ID
    }

    @Test
    public void test_nonStaffInstance_returnsFalse() {
        // Test with a Person that is not a Staff
        SameStaffIdPredicate predicate = new SameStaffIdPredicate(new StaffId("S001"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        StaffId id = new StaffId("S001");
        SameStaffIdPredicate predicate = new SameStaffIdPredicate(id);

        String expected = SameStaffIdPredicate.class.getCanonicalName() + "{staffId=" + id + "}";
        assertEquals(expected, predicate.toString());
    }
}
