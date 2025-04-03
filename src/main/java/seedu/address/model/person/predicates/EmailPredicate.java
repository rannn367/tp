package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Email} matches the email given.
 */
public class EmailPredicate implements Predicate<Person> {
    private final Email email;

    public EmailPredicate(Email email) {
        this.email = email;
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().equals(email);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailPredicate)) {
            return false;
        }

        EmailPredicate otherEmailPredicate = (EmailPredicate) other;
        return email.equals(otherEmailPredicate.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("email", email).toString();
    }
}
