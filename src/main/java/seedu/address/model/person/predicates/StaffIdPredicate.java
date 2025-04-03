package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;

/**
 * Tests that a {@code Staff}'s {@code StaffId} matches the staff ID given.
 */
public class StaffIdPredicate implements Predicate<Person> {
    private final StaffId staffId;

    public StaffIdPredicate(StaffId staffId) {
        this.staffId = staffId;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Staff && ((Staff) person).getStaffId().equals(staffId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StaffIdPredicate)) {
            return false;
        }

        StaffIdPredicate otherStaffIdPredicate = (StaffIdPredicate) other;
        return staffId.equals(otherStaffIdPredicate.staffId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("staffId", staffId).toString();
    }
}
