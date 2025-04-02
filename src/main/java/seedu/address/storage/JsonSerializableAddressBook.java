package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Staff;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_STAFF = "Staff list contains duplicate staff member(s).";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customer list contains duplicate customer(s).";

    private final List<JsonAdaptedStaff> staff = new ArrayList<>();
    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and staff.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("staff") List<JsonAdaptedStaff> staff,
                                       @JsonProperty("customers") List<JsonAdaptedCustomer> customers) {
        if (staff != null) {
            this.staff.addAll(staff);
        }
        if (customers != null) {
            this.customers.addAll(customers);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        for (Staff staffMember : source.getStaffList()) {
            staff.add(new JsonAdaptedStaff(staffMember));
        }

        for (Customer customer : source.getCustomerList()) {
            customers.add(new JsonAdaptedCustomer(customer));
        }
    }


    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedStaff jsonAdaptedStaff : staff) {
            Staff staffMember = jsonAdaptedStaff.toModelType();
            if (addressBook.hasStaff(staffMember)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            addressBook.addStaff(staffMember);
        }

        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (addressBook.hasCustomer(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            addressBook.addCustomer(customer);
        }

        return addressBook;
    }
}
