package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.util.TestPersonBuilder;

public class SameTotalSpentPredicateTest {

    @Test
    public void equals() {
        TotalSpent spent1 = new TotalSpent("100.00");
        TotalSpent spent2 = new TotalSpent("50.00");

        SameTotalSpentPredicate firstPredicate = new SameTotalSpentPredicate(spent1);
        SameTotalSpentPredicate secondPredicate = new SameTotalSpentPredicate(spent2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameTotalSpentPredicate firstPredicateCopy = new SameTotalSpentPredicate(spent1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different total spent -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_totalSpentMatches_returnsTrue() {
        // Match total spent from JAMES in TypicalCustomers (155.75)
        SameTotalSpentPredicate predicate = new SameTotalSpentPredicate(new TotalSpent("155.75"));
        assertTrue(predicate.test(JAMES));
    }

    @Test
    public void test_totalSpentDoesNotMatch_returnsFalse() {
        // Test with different total spent
        SameTotalSpentPredicate predicate = new SameTotalSpentPredicate(new TotalSpent("200.00"));
        assertFalse(predicate.test(JAMES));

        // Test with another customer's total spent
        predicate = new SameTotalSpentPredicate(new TotalSpent("155.75")); // JAMES's total spent
        assertFalse(predicate.test(OLIVIA)); // OLIVIA has different total spent
    }

    @Test
    public void test_nonCustomerInstance_returnsFalse() {
        // Test with a Person that is not a Customer
        SameTotalSpentPredicate predicate = new SameTotalSpentPredicate(new TotalSpent("100.00"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        TotalSpent totalSpent = new TotalSpent("100.00");
        SameTotalSpentPredicate predicate = new SameTotalSpentPredicate(totalSpent);

        String expected = SameTotalSpentPredicate.class.getCanonicalName() + "{totalSpent=" + totalSpent + "}";
        assertEquals(expected, predicate.toString());
    }
}
