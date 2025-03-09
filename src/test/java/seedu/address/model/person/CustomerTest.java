package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class CustomerTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Customer customer = new CustomerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> customer.getTags().remove(0));
    }

    @Test
    public void isSameCustomer() {
        // same object -> returns true
        assertTrue(JAMES.isSameCustomer(JAMES));

        // Different phone, rest same -> returns false
        Customer editedJames = new CustomerBuilder(JAMES).withPhone("91234567").build();
        assertFalse(JAMES.isSameCustomer(editedJames));
    }

    @Test
    public void hashCode_sameCustomer_returnsSameHashCode() {
        Customer jamesCopy = new CustomerBuilder(JAMES).build();
        assertEquals(JAMES.hashCode(), jamesCopy.hashCode()); // Same fields -> same hashcode
    }

    @Test
    public void hashCode_differentCustomer_returnsDifferentHashCode() {
        Customer editedJames = new CustomerBuilder(JAMES).withRewardPoints(999).build();
        assertNotEquals(JAMES.hashCode(), editedJames.hashCode()); // Different reward points -> different hashcode
    }

    @Test
    public void isSameCustomer_nullCustomer_returnsFalse() {
        assertFalse(OLIVIA.isSameCustomer(null)); // null -> should return false
    }

    @Test
    public void equals() {
        // same values -> returns true
        Customer jamesCopy = new CustomerBuilder(JAMES).build();
        assertTrue(JAMES.equals(jamesCopy));

        // same object -> returns true
        assertTrue(JAMES.equals(JAMES));

        // null -> returns false
        assertFalse(JAMES.equals(null));

        // different type -> returns false
        assertFalse(JAMES.equals(5));

        // different customer -> returns false
        assertFalse(JAMES.equals(OLIVIA));

        // different reward points -> returns false
        Customer editedJames = new CustomerBuilder(JAMES).withRewardPoints(1000).build();
        assertFalse(JAMES.equals(editedJames));

        // different visit count -> returns false
        editedJames = new CustomerBuilder(JAMES).withVisitCount(20).build();
        assertFalse(JAMES.equals(editedJames));

        // different favorite item -> returns false
        editedJames = new CustomerBuilder(JAMES).withFavoriteItem("Espresso").build();
        assertFalse(JAMES.equals(editedJames));

        // different total spent -> returns false
        editedJames = new CustomerBuilder(JAMES).withTotalSpent(999.99).build();
        assertFalse(JAMES.equals(editedJames));

        // different rating -> returns false
        editedJames = new CustomerBuilder(JAMES).withRating(2).build();
        assertFalse(JAMES.equals(editedJames));
    }

    @Test
    public void toStringMethod() {
        String expected = Customer.class.getCanonicalName() + "{name=" + JAMES.getName() + ", "
                + "phone=" + JAMES.getPhone() + ", email=" + JAMES.getEmail()
                + ", address=" + JAMES.getAddress() + ", tags=" + JAMES.getTags()
                + ", rewardPoints=" + JAMES.getRewardPoints() + ", visitCount=" + JAMES.getVisitCount()
                + ", favoriteItem=" + JAMES.getFavoriteItem() + ", totalSpent=" + JAMES.getTotalSpent()
                + ", rating=" + JAMES.getRating() + "}";
        assertEquals(expected, JAMES.toString());
    }
}
