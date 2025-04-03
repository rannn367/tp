package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Customer}'s {@code CustomerId} matches the customer ID given.
 */
public class CustomerIdPredicate implements Predicate<Person> {
    private final CustomerId customerId;

    public CustomerIdPredicate(CustomerId customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Customer && ((Customer) person).getCustomerId().equals(customerId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerIdPredicate)) {
            return false;
        }

        CustomerIdPredicate otherCustomerIdPredicate = (CustomerIdPredicate) other;
        return customerId.equals(otherCustomerIdPredicate.customerId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("customerId", customerId).toString();
    }
}
