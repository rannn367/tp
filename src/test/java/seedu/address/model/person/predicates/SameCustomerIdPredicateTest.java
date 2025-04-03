package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Person;
import seedu.address.model.util.TestPersonBuilder;

public class SameCustomerIdPredicateTest {

    @Test
    public void equals() {
        CustomerId id1 = new CustomerId("C001");
        CustomerId id2 = new CustomerId("C002");

        SameCustomerIdPredicate firstPredicate = new SameCustomerIdPredicate(id1);
        SameCustomerIdPredicate secondPredicate = new SameCustomerIdPredicate(id2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameCustomerIdPredicate firstPredicateCopy = new SameCustomerIdPredicate(id1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different customer ID -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_customerIdMatches_returnsTrue() {
        // Match customer ID from JAMES in TypicalCustomers
        SameCustomerIdPredicate predicate = new SameCustomerIdPredicate(new CustomerId("C10012"));
        assertTrue(predicate.test(JAMES));
    }

    @Test
    public void test_customerIdDoesNotMatch_returnsFalse() {
        // Test with a different customer ID
        SameCustomerIdPredicate predicate = new SameCustomerIdPredicate(new CustomerId("C999"));
        assertFalse(predicate.test(JAMES));

        // Test with a customer having different ID
        predicate = new SameCustomerIdPredicate(new CustomerId("C10012")); // JAMES's ID
        assertFalse(predicate.test(OLIVIA)); // OLIVIA has different ID
    }

    @Test
    public void test_nonCustomerInstance_returnsFalse() {
        // Test with a Person that is not a Customer
        SameCustomerIdPredicate predicate = new SameCustomerIdPredicate(new CustomerId("C001"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        CustomerId id = new CustomerId("C001");
        SameCustomerIdPredicate predicate = new SameCustomerIdPredicate(id);

        String expected = SameCustomerIdPredicate.class.getCanonicalName() + "{customerId=" + id + "}";
        assertEquals(expected, predicate.toString());
    }
}
