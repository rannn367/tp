package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Staff;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 * This class provides a method to create a typical AddressBook with a mix of customers and staff.
 * It is used to simplify the creation of test data and ensure consistency across tests.
 */
public class TypicalAddressBook {
    /**
     * Returns a new {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Customer customer : TypicalCustomers.getTypicalCustomers()) {
            ab.addCustomer(customer);
        }
        for (Staff staff : TypicalStaff.getTypicalStaff()) {
            ab.addStaff(staff);
        }
        return ab;
    }
}
