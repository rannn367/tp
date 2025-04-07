package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private static final Logger logger = LogsCenter.getLogger(AddressBook.class);

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        logger.info("Resetting data of address book");
        persons.clear();
        for (Person person: newData.getPersonList()) {
            persons.add(person);
        }
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Replaces the given staff member {@code target} in the list with {@code editedStaff}.
     * {@code target} must exist in the address book.
     * The staff identity of {@code editedStaff} must not be the same as another existing staff member in the
     * address book.
     */
    public void setStaff(Staff target, Staff editedStaff) {
        requireNonNull(editedStaff);

        logger.info("Replacing staff: " + target + " with " + editedStaff);
        persons.setPerson(target, editedStaff);
    }

    /**
     * Replaces the given customer member {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The staff identity of {@code editedStaff} must not be the same as another existing customer member in the
     * address book.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);

        logger.info("Replacing customer: " + target + " with " + editedCustomer);
        persons.setPerson(target, editedCustomer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    // Staff methods

    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the address book.
     */
    public boolean hasStaff(Staff staffMember) {
        requireNonNull(staffMember);
        return persons.contains(staffMember);
    }

    public void addStaff(Staff staffMember) {
        persons.add(staffMember);
    }

    public void removeStaff(Staff staffMember) {
        persons.remove(staffMember);
    }

    // Customer methods

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return persons.contains(customer);
    }

    public void addCustomer(Customer customer) {
        persons.add(customer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}'s customer list.
     * {@code key} must exist in the address book's customer list.
     */
    public void removeCustomer(Customer key) {
        persons.remove(key);
    }

    //// util methods
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return persons.getFilteredList(Staff.class).sorted((s1, s2) -> s1.getStaffId().compareTo(s2.getStaffId()));
    }

    @Override
    public ObservableList<Customer> getCustomerList() {
        return persons.getFilteredList(Customer.class)
                .sorted((s1, s2) -> s1.getCustomerId().compareTo(s2.getCustomerId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return getCustomerList().equals(otherAddressBook.getCustomerList())
                && getStaffList().equals(otherAddressBook.getStaffList());
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
