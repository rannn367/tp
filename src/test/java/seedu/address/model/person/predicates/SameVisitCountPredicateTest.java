package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.VisitCount;
import seedu.address.model.util.TestPersonBuilder;

public class SameVisitCountPredicateTest {

    @Test
    public void equals() {
        VisitCount count1 = new VisitCount("10");
        VisitCount count2 = new VisitCount("20");

        SameVisitCountPredicate firstPredicate = new SameVisitCountPredicate(count1);
        SameVisitCountPredicate secondPredicate = new SameVisitCountPredicate(count2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameVisitCountPredicate firstPredicateCopy = new SameVisitCountPredicate(count1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different visit count -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_visitCountMatches_returnsTrue() {
        // Match visit count from JAMES in TypicalCustomers (12)
        SameVisitCountPredicate predicate = new SameVisitCountPredicate(new VisitCount("12"));
        assertTrue(predicate.test(JAMES));
    }

    @Test
    public void test_visitCountDoesNotMatch_returnsFalse() {
        // Test with a different visit count
        SameVisitCountPredicate predicate = new SameVisitCountPredicate(new VisitCount("100"));
        assertFalse(predicate.test(JAMES));

        // Test with another customer's visit count
        predicate = new SameVisitCountPredicate(new VisitCount("12")); // JAMES's count
        assertFalse(predicate.test(OLIVIA)); // OLIVIA has different count
    }

    @Test
    public void test_nonCustomerInstance_returnsFalse() {
        // Test with a Person that is not a Customer
        SameVisitCountPredicate predicate = new SameVisitCountPredicate(new VisitCount("5"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        VisitCount count = new VisitCount("10");
        SameVisitCountPredicate predicate = new SameVisitCountPredicate(count);

        String expected = SameVisitCountPredicate.class.getCanonicalName() + "{visitCount=" + count + "}";
        assertEquals(expected, predicate.toString());
    }
}
