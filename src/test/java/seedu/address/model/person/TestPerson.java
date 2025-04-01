package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * A test class for Person that overrides the isSamePerson method.
 * This is used to test the functionality of the
 * {@code seedu.address.model.person.Person} class without needing to
 * create a full implementation of the Person class.
 *
 * Note that Person is an abstract class, so we cannot instantiate it directly.
 * This class is used to create a concrete implementation of Person
 */
public class TestPerson extends Person {

    /**
     * Creates a {@code TestPerson} with the given details.
     * Every field must be present and not null.
     */
    public TestPerson(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
    }

    /**
     * Overrides the isSamePerson method to provide a custom implementation
     * for testing purposes.
     * This implementation checks if the names are the same.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return otherPerson.getName().equals(getName());
    }
}
