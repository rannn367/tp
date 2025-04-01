package seedu.address.model.util;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TestPerson;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building TestPerson objects.
 * Extends PersonBuilder to provide additional functionality specific to TestPerson.
 */
public class TestPersonBuilder extends PersonBuilder<TestPerson, TestPersonBuilder> {

    public TestPersonBuilder() {
        super();
    }

    public TestPersonBuilder(Name name,
            Phone phone,
            Email email,
            Address address,
            Remark remark,
        Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
    }

    /**
     * Creates a {@code TestPersonBuilder} with the data of {@code person}.
     * Every field must be present and not null.
     *
     * @param person The person to copy data from.
     */
    public TestPersonBuilder(Person person) {
        super(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getRemark(), person.getTags());
    }

    @Override
    protected TestPersonBuilder createBuilder(Name name,
            Phone phone,
            Email email,
            Address address,
            Remark remark,
            Set<Tag> tags) {
        return new TestPersonBuilder(name, phone, email, address, remark, tags);
    }

    @Override
    public TestPerson build() {
        return new TestPerson(name, phone, email, address, remark, tags);
    }

}
