package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Email;

public class SameEmailPredicateTest {

    @Test
    public void equals() {
        Email email1 = new Email("test1@example.com");
        Email email2 = new Email("test2@example.com");

        SameEmailPredicate firstPredicate = new SameEmailPredicate(email1);
        SameEmailPredicate secondPredicate = new SameEmailPredicate(email2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameEmailPredicate firstPredicateCopy = new SameEmailPredicate(email1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different email -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailMatches_returnsTrue() {
        // Match email from JAMES in TypicalCustomers
        SameEmailPredicate predicate = new SameEmailPredicate(new Email("james@example.com"));
        assertTrue(predicate.test(JAMES));
    }

    @Test
    public void test_emailDoesNotMatch_returnsFalse() {
        // Test with a different email
        SameEmailPredicate predicate = new SameEmailPredicate(new Email("wrong@example.com"));
        assertFalse(predicate.test(JAMES));

        // Test with another person's email
        predicate = new SameEmailPredicate(new Email("james@example.com")); // JAMES's email
        assertFalse(predicate.test(OLIVIA)); // OLIVIA has different email
    }

    @Test
    public void toStringMethod() {
        Email email = new Email("test@example.com");
        SameEmailPredicate predicate = new SameEmailPredicate(email);

        String expected = SameEmailPredicate.class.getCanonicalName() + "{email=" + email + "}";
        assertEquals(expected, predicate.toString());
    }
}
