package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        assertFalse(uniqueCustomerList.contains(JAMES));
    }

    @Test
    public void contains_customerInList_returnsTrue() {
        uniqueCustomerList.add(JAMES);
        assertTrue(uniqueCustomerList.contains(JAMES));
    }

    @Test
    public void add_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.add(null));
    }

    @Test
    public void add_duplicateCustomer_throwsDuplicatePersonException() {
        uniqueCustomerList.add(JAMES);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.add(JAMES));
    }

    @Test
    public void setCustomer_nullTargetCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomer(null, JAMES));
    }

    @Test
    public void setCustomer_nullEditedCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomer(JAMES, null));
    }

    @Test
    public void setCustomer_targetCustomerNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueCustomerList.setCustomer(JAMES, JAMES));
    }

    @Test
    public void setCustomer_editedCustomerIsSameCustomer_success() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.setCustomer(JAMES, JAMES);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(JAMES);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomer_editedCustomerHasSameIdentity_success() {
        uniqueCustomerList.add(JAMES);
        Customer editedJames = new CustomerBuilder(JAMES).withAddress("different address").withRewardPoints("300")
                .withTags("regular").build();
        uniqueCustomerList.setCustomer(JAMES, editedJames);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(editedJames);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomer_editedCustomerHasDifferentIdentity_success() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.setCustomer(JAMES, OLIVIA);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(OLIVIA);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomer_editedCustomerHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.add(OLIVIA);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.setCustomer(JAMES, OLIVIA));
    }

    @Test
    public void remove_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.remove(null));
    }

    @Test
    public void remove_customerDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueCustomerList.remove(JAMES));
    }

    @Test
    public void remove_existingCustomer_removesCustomer() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.remove(JAMES);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomers_nullUniqueCustomerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomers((UniqueCustomerList) null));
    }

    @Test
    public void setCustomers_uniqueCustomerList_replacesOwnListWithProvidedUniqueCustomerList() {
        uniqueCustomerList.add(JAMES);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(OLIVIA);
        uniqueCustomerList.setCustomers(expectedUniqueCustomerList);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomers((List<Customer>) null));
    }

    @Test
    public void setCustomers_list_replacesOwnListWithProvidedList() {
        uniqueCustomerList.add(JAMES);
        List<Customer> customerList = Collections.singletonList(OLIVIA);
        uniqueCustomerList.setCustomers(customerList);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(OLIVIA);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomers_listWithDuplicateCustomers_throwsDuplicatePersonException() {
        List<Customer> listWithDuplicateCustomers = Arrays.asList(JAMES, JAMES);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.setCustomers(listWithDuplicateCustomers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueCustomerList.asUnmodifiableObservableList().remove(0));
    }
}
