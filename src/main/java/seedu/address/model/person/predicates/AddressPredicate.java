package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Address} matches the address given.
 */
public class AddressPredicate implements Predicate<Person> {
    private final Address address;

    public AddressPredicate(Address address) {
        this.address = address;
    }

    @Override
    public boolean test(Person person) {
        return person.getAddress().equals(address);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressPredicate)) {
            return false;
        }

        AddressPredicate otherAddressPredicate = (AddressPredicate) other;
        return address.equals(otherAddressPredicate.address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("address", address).toString();
    }
}
