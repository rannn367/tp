package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Phone;
import seedu.address.model.util.TestPersonBuilder;

public class SamePhonePredicateTest {

    @Test
    public void equals() {
        Phone phone1 = new Phone("12345678");
        Phone phone2 = new Phone("87654321");

        SamePhonePredicate firstPredicate = new SamePhonePredicate(phone1);
        SamePhonePredicate secondPredicate = new SamePhonePredicate(phone2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SamePhonePredicate firstPredicateCopy = new SamePhonePredicate(phone1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneMatches_returnsTrue() {
        // Same phone number -> returns true
        SamePhonePredicate predicate = new SamePhonePredicate(new Phone("12345678"));
        assertTrue(predicate.test(new TestPersonBuilder().withPhone(new Phone("12345678")).build()));
    }

    @Test
    public void test_phoneDoesNotMatch_returnsFalse() {
        // Different phone number -> returns false
        SamePhonePredicate predicate = new SamePhonePredicate(new Phone("12345678"));
        assertFalse(predicate.test(new TestPersonBuilder().withPhone(new Phone("87654321")).build()));
    }

    @Test
    public void toStringMethod() {
        Phone phone = new Phone("12345678");
        SamePhonePredicate predicate = new SamePhonePredicate(phone);

        String expected = SamePhonePredicate.class.getCanonicalName() + "{phone=" + phone + "}";
        assertEquals(expected, predicate.toString());
    }
}
