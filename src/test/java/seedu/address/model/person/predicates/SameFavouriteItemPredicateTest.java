package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Person;
import seedu.address.model.util.TestPersonBuilder;

public class SameFavouriteItemPredicateTest {

    @Test
    public void equals() {
        FavouriteItem item1 = new FavouriteItem("Cappuccino");
        FavouriteItem item2 = new FavouriteItem("Latte");

        SameFavouriteItemPredicate firstPredicate = new SameFavouriteItemPredicate(item1);
        SameFavouriteItemPredicate secondPredicate = new SameFavouriteItemPredicate(item2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameFavouriteItemPredicate firstPredicateCopy = new SameFavouriteItemPredicate(item1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different favourite item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_favouriteItemMatches_returnsTrue() {
        // Match favourite item from JAMES in TypicalCustomers (Flat White)
        SameFavouriteItemPredicate predicate = new SameFavouriteItemPredicate(new FavouriteItem("Flat White"));
        assertTrue(predicate.test(JAMES));
    }

    @Test
    public void test_favouriteItemDoesNotMatch_returnsFalse() {
        // Test with different favourite item
        SameFavouriteItemPredicate predicate = new SameFavouriteItemPredicate(new FavouriteItem("Espresso"));
        assertFalse(predicate.test(JAMES));

        // Test with another customer's favourite item
        predicate = new SameFavouriteItemPredicate(new FavouriteItem("Flat White")); // JAMES's item
        assertFalse(predicate.test(OLIVIA)); // OLIVIA has different favourite item
    }

    @Test
    public void test_nonCustomerInstance_returnsFalse() {
        // Test with a Person that is not a Customer
        SameFavouriteItemPredicate predicate = new SameFavouriteItemPredicate(new FavouriteItem("Cappuccino"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        FavouriteItem item = new FavouriteItem("Cappuccino");
        SameFavouriteItemPredicate predicate = new SameFavouriteItemPredicate(item);

        String expected = SameFavouriteItemPredicate.class.getCanonicalName() + "{favouriteItem=" + item + "}";
        assertEquals(expected, predicate.toString());
    }
}
