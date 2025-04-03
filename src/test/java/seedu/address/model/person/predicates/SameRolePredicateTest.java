package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.util.TestPersonBuilder;

public class SameRolePredicateTest {

    @Test
    public void equals() {
        Role role1 = new Role("Barista");
        Role role2 = new Role("Manager");

        SameRolePredicate firstPredicate = new SameRolePredicate(role1);
        SameRolePredicate secondPredicate = new SameRolePredicate(role2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameRolePredicate firstPredicateCopy = new SameRolePredicate(role1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different role -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleMatches_returnsTrue() {
        // Match role from ALEX in TypicalStaff (Barista)
        SameRolePredicate predicate = new SameRolePredicate(new Role("Barista"));
        assertTrue(predicate.test(ALEX));
    }

    @Test
    public void test_roleDoesNotMatch_returnsFalse() {
        // Test with a different role
        SameRolePredicate predicate = new SameRolePredicate(new Role("Supervisor"));
        assertFalse(predicate.test(ALEX));

        // Test with another staff's role
        predicate = new SameRolePredicate(new Role("Barista")); // ALEX's role
        assertFalse(predicate.test(BEN)); // BEN has different role (Cashier)
    }

    @Test
    public void test_nonStaffInstance_returnsFalse() {
        // Test with a Person that is not a Staff
        SameRolePredicate predicate = new SameRolePredicate(new Role("Barista"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        Role role = new Role("Barista");
        SameRolePredicate predicate = new SameRolePredicate(role);

        String expected = SameRolePredicate.class.getCanonicalName() + "{role=" + role + "}";
        assertEquals(expected, predicate.toString());
    }
}
