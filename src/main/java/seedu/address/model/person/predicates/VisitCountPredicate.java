package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.VisitCount;

/**
 * Tests that a {@code Customer}'s {@code VisitCount} matches the visit count given.
 */
public class VisitCountPredicate implements Predicate<Person> {
    private final VisitCount visitCount;

    public VisitCountPredicate(VisitCount visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Customer && ((Customer) person).getVisitCount().equals(visitCount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VisitCountPredicate)) {
            return false;
        }

        VisitCountPredicate otherVisitCountPredicate = (VisitCountPredicate) other;
        return visitCount.equals(otherVisitCountPredicate.visitCount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("visitCount", visitCount).toString();
    }
}
