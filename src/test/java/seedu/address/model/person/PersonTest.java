package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

// Stub class to allow testing of abstract Person
class TestPerson extends Person {
    public TestPerson(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
    }
}

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new TestPerson(new Name("Alice"), new Phone("12345678"), new Email("alice@example.com"),
                new Address("123 Wonderland"), new Remark("Loves rabbits"), new HashSet<>());
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        assertTrue(ALICE.isSamePerson(ALICE));
        assertFalse(ALICE.isSamePerson(null));

        Person editedAlice = new TestPerson(new Name(ALICE.getName().toString()), new Phone(VALID_PHONE_BOB),
                new Email(VALID_EMAIL_BOB), new Address(VALID_ADDRESS_BOB), new Remark(ALICE.getRemark().toString()), new HashSet<>());
        assertTrue(ALICE.isSamePerson(editedAlice));

        editedAlice = new TestPerson(new Name(VALID_NAME_BOB), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(), ALICE.getRemark(), ALICE.getTags());
        assertFalse(ALICE.isSamePerson(editedAlice));

        Person editedBob = new TestPerson(new Name(VALID_NAME_BOB.toLowerCase()), BOB.getPhone(), BOB.getEmail(), BOB.getAddress(), BOB.getRemark(), BOB.getTags());
        assertFalse(BOB.isSamePerson(editedBob));

        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new TestPerson(new Name(nameWithTrailingSpaces), BOB.getPhone(), BOB.getEmail(), BOB.getAddress(), BOB.getRemark(), BOB.getTags());
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        Person aliceCopy = new TestPerson(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(), ALICE.getRemark(), ALICE.getTags());
        assertTrue(ALICE.equals(aliceCopy));

        assertTrue(ALICE.equals(ALICE));
        assertFalse(ALICE.equals(null));
        assertFalse(ALICE.equals(5));
        assertFalse(ALICE.equals(BOB));

        Person editedAlice = new TestPerson(new Name(VALID_NAME_BOB), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(), ALICE.getRemark(), ALICE.getTags());
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new TestPerson(ALICE.getName(), new Phone(VALID_PHONE_BOB), ALICE.getEmail(), ALICE.getAddress(), ALICE.getRemark(), ALICE.getTags());
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new TestPerson(ALICE.getName(), ALICE.getPhone(), new Email(VALID_EMAIL_BOB), ALICE.getAddress(), ALICE.getRemark(), ALICE.getTags());
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new TestPerson(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), new Address(VALID_ADDRESS_BOB), ALICE.getRemark(), ALICE.getTags());
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new TestPerson(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(), ALICE.getRemark(), new HashSet<>());
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = TestPerson.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}