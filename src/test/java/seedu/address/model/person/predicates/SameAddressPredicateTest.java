package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.util.TestPersonBuilder;

public class SameAddressPredicateTest {

    @Test
    public void equals() {
        Address address1 = new Address("123 Main St");
        Address address2 = new Address("456 Side St");

        SameAddressPredicate firstPredicate = new SameAddressPredicate(address1);
        SameAddressPredicate secondPredicate = new SameAddressPredicate(address2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameAddressPredicate firstPredicateCopy = new SameAddressPredicate(address1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressMatches_returnsTrue() {
        Address address = new Address("123 Main St");
        SameAddressPredicate predicate = new SameAddressPredicate(address);
        Person person = new TestPersonBuilder().withAddress(address).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_addressDoesNotMatch_returnsFalse() {
        // Test with different addresses
        Address predicateAddress = new Address("123 Main St");
        Address personAddress = new Address("456 Side St");
        SameAddressPredicate predicate = new SameAddressPredicate(predicateAddress);
        Person person = new TestPersonBuilder().withAddress(personAddress).build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        Address address = new Address("123 Main St");
        SameAddressPredicate predicate = new SameAddressPredicate(address);

        String expected = SameAddressPredicate.class.getCanonicalName() + "{address=" + address + "}";
        assertEquals(expected, predicate.toString());
    }
}
