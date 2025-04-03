package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * Tests that a {@code Staff}'s {@code HoursWorked} matches the hours worked given.
 */
public class SameHoursWorkedPredicate implements Predicate<Person> {
    private final HoursWorked hoursWorked;

    public SameHoursWorkedPredicate(HoursWorked hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Staff && ((Staff) person).getHoursWorked().equals(hoursWorked);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SameHoursWorkedPredicate)) {
            return false;
        }

        SameHoursWorkedPredicate otherHoursWorkedPredicate = (SameHoursWorkedPredicate) other;
        return hoursWorked.equals(otherHoursWorkedPredicate.hoursWorked);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("hoursWorked", hoursWorked).toString();
    }
}
