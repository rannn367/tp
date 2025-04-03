package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Staff;

/**
 * Tests that a {@code Staff}'s {@code Role} matches the role given.
 */
public class RolePredicate implements Predicate<Person> {
    private final Role role;

    public RolePredicate(Role role) {
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
        if (!(other instanceof RolePredicate)) {
            return false;
        }

        RolePredicate otherRolePredicate = (RolePredicate) other;
        return role.equals(otherRolePredicate.role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("role", role).toString();
    }
}
