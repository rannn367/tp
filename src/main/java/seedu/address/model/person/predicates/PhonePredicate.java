package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the phone number given.
 */
public class PhonePredicate implements Predicate<Person> {
    private final Phone phone;

    public PhonePredicate(Phone phone) {
        this.phone = phone;
    }

    @Override
    public boolean test(Person person) {
        return person.getPhone().equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhonePredicate)) {
            return false;
        }

        PhonePredicate otherPhonePredicate = (PhonePredicate) other;
        return phone.equals(otherPhonePredicate.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phone", phone).toString();
    }
}
