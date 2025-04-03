package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;

/**
 * Tests that a {@code Staff}'s {@code ShiftTiming} matches the shift timing given.
 */
public class ShiftTimingPredicate implements Predicate<Person> {
    private final ShiftTiming shiftTiming;

    public ShiftTimingPredicate(ShiftTiming shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Staff && ((Staff) person).getShiftTiming().equals(shiftTiming);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShiftTimingPredicate)) {
            return false;
        }

        ShiftTimingPredicate otherShiftTimingPredicate = (ShiftTimingPredicate) other;
        return shiftTiming.equals(otherShiftTimingPredicate.shiftTiming);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("shiftTiming", shiftTiming).toString();
    }
}
