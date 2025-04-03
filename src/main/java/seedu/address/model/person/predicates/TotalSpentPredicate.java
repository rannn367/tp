package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.TotalSpent;

/**
 * Tests that a {@code Customer}'s {@code TotalSpent} matches the total spent given.
 */
public class TotalSpentPredicate implements Predicate<Person> {
    private final TotalSpent totalSpent;

    public TotalSpentPredicate(TotalSpent totalSpent) {
        this.totalSpent = totalSpent;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Customer && ((Customer) person).getTotalSpent().equals(totalSpent);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TotalSpentPredicate)) {
            return false;
        }

        TotalSpentPredicate otherTotalSpentPredicate = (TotalSpentPredicate) other;
        return totalSpent.equals(otherTotalSpentPredicate.totalSpent);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("totalSpent", totalSpent).toString();
    }
}
