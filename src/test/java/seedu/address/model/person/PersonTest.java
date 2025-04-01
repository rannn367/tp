package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class PersonTest {

    private final TestPerson amy = new TestPerson(
            new Name(VALID_NAME_AMY),
            new Phone(VALID_PHONE_AMY),
            new Email(VALID_EMAIL_AMY),
            new Address(VALID_ADDRESS_AMY),
            new Remark(VALID_REMARK_AMY),
            new HashSet<>()
    );

    private final TestPerson bob = new TestPerson(
            new Name(VALID_NAME_BOB),
            new Phone(VALID_PHONE_BOB),
            new Email(VALID_EMAIL_BOB),
            new Address(VALID_ADDRESS_BOB),
            new Remark(VALID_REMARK_BOB),
            new HashSet<>(Set.of(new Tag(VALID_TAG_HUSBAND)))
    );

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> amy.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(amy.isSamePerson(amy));

        // null -> returns false
        assertFalse(amy.isSamePerson(null));

        // same name, all other attributes different -> returns true
        TestPerson editedAmy = new TestPerson(
                amy.getName(),
                new Phone(VALID_PHONE_BOB),
                new Email(VALID_EMAIL_BOB),
                new Address(VALID_ADDRESS_BOB),
                amy.getRemark(),
                amy.getTags()
        );
        assertTrue(amy.isSamePerson(editedAmy));

        // different name, all other attributes same -> returns false
        editedAmy = new TestPerson(
                new Name(VALID_NAME_BOB),
                amy.getPhone(),
                amy.getEmail(),
                amy.getAddress(),
                amy.getRemark(),
                amy.getTags()
        );
        assertFalse(amy.isSamePerson(editedAmy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        TestPerson amyCopy = new TestPerson(
                amy.getName(),
                amy.getPhone(),
                amy.getEmail(),
                amy.getAddress(),
                amy.getRemark(),
                amy.getTags()
        );
        assertTrue(amy.equals(amyCopy));

        // same object -> returns true
        assertTrue(amy.equals(amy));

        // null -> returns false
        assertFalse(amy.equals(null));

        // different type -> returns false
        assertFalse(amy.equals(5));

        // different person -> returns false
        assertFalse(amy.equals(bob));

        // different name -> returns false
        TestPerson editedAmy = new TestPerson(
                new Name(VALID_NAME_BOB),
                amy.getPhone(),
                amy.getEmail(),
                amy.getAddress(),
                amy.getRemark(),
                amy.getTags()
        );
        assertFalse(amy.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        String expected = amy.getClass().getCanonicalName() + "{name=" + amy.getName()
                + ", phone=" + amy.getPhone() + ", email=" + amy.getEmail()
                + ", address=" + amy.getAddress() + ", tags=" + amy.getTags() + "}";
        assertEquals(expected, amy.toString());
    }
}
