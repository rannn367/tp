package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.CustomerBuilder;

public class UniqueCustomerListTest {

    private final UniqueCustomerList uniqueCustomerList = new UniqueCustomerList();

    @Test
    public void contains_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.contains(null));
    }

    @Test
    public void contains_customerNotInList_returnsFalse() {
        assertFalse(uniqueCustomerList.contains(new CustomerBuilder().build()));
    }

    @Test
    public void contains_customerInList_returnsTrue() {
        Customer customer = new CustomerBuilder().build();
        uniqueCustomerList.add(customer);
        assertTrue(uniqueCustomerList.contains(customer));
    }

    @Test
    public void add_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.add(null));
    }

    @Test
    public void add_duplicateCustomer_throwsDuplicatePersonException() {
        Customer customer = new CustomerBuilder().build();
        uniqueCustomerList.add(customer);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.add(customer));
    }

    @Test
    public void setCustomer_nullTargetCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueCustomerList.setCustomer(null, new CustomerBuilder().build()));
    }

    @Test
    public void setCustomer_nullEditedCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueCustomerList.setCustomer(new CustomerBuilder().build(), null));
    }

    @Test
    public void setCustomer_targetCustomerNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () ->
                uniqueCustomerList.setCustomer(new CustomerBuilder().build(), new CustomerBuilder().build()));
    }

    @Test
    public void setCustomer_editedCustomerIsSameCustomer_success() {
        Customer customer = new CustomerBuilder().build();
        uniqueCustomerList.add(customer);
        uniqueCustomerList.setCustomer(customer, customer);
        assertEquals(1, uniqueCustomerList.asUnmodifiableObservableList().size());
    }

    @Test
    public void setCustomer_editedCustomerHasDifferentIdentity_success() {
        Customer customer = new CustomerBuilder().build();
        uniqueCustomerList.add(customer);
        Customer editedCustomer = new CustomerBuilder().withName("Bob").withPhone("87654321").build();
        uniqueCustomerList.setCustomer(customer, editedCustomer);
        assertEquals(1, uniqueCustomerList.asUnmodifiableObservableList().size());
        assertTrue(uniqueCustomerList.contains(editedCustomer));
    }

    @Test
    public void remove_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.remove(null));
    }

    @Test
    public void remove_customerDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueCustomerList.remove(new CustomerBuilder().build()));
    }

    @Test
    public void remove_existingCustomer_removesCustomer() {
        Customer customer = new CustomerBuilder().build();
        uniqueCustomerList.add(customer);
        uniqueCustomerList.remove(customer);
        assertFalse(uniqueCustomerList.contains(customer));
    }
}
