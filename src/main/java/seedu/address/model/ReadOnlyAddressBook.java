package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Staff> getStaffList();

    /**
     * Returns an unmodifiable view of the customer list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();
}
