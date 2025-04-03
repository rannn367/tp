package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Staff;

/**
 * Tests that a {@code Staff}'s {@code Role} matches the role given.
 */
public class SameRolePredicate implements Predicate<Person> {
    private final Role role;

    public SameRolePredicate(Role role) {
        this.role = role;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Staff && ((Staff) person).getRole().equals(role);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SameRolePredicate)) {
            return false;
        }

        SameRolePredicate otherRolePredicate = (SameRolePredicate) other;
        return role.equals(otherRolePredicate.role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("role", role).toString();
    }
}
