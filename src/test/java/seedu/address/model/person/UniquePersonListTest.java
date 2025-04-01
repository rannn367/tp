package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;
import static seedu.address.testutil.TypicalStaff.ALEX;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.util.CustomerBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(JAMES));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(JAMES);
        assertTrue(uniquePersonList.contains(JAMES));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(JAMES);
        Person editedJames = new CustomerBuilder(JAMES)
                .withAddress(VALID_ADDRESS_OLIVIA)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedJames));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(JAMES);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(JAMES));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, JAMES));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(JAMES, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(JAMES, JAMES));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(JAMES);
        uniquePersonList.setPerson(JAMES, JAMES);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(JAMES);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(JAMES);
        Person editedJames = new CustomerBuilder(JAMES)
                .withAddress(VALID_ADDRESS_OLIVIA)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(JAMES, editedJames);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedJames);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(JAMES);
        uniquePersonList.setPerson(JAMES, OLIVIA);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(OLIVIA);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(JAMES);
        uniquePersonList.add(OLIVIA);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(JAMES, OLIVIA));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(JAMES));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(JAMES);
        uniquePersonList.remove(JAMES);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void clear_nonEmptyList_clearsAllPersons() {
        uniquePersonList.add(JAMES);
        uniquePersonList.add(OLIVIA);
        uniquePersonList.clear();
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void clear_emptyList_noEffect() {
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        uniquePersonList.clear();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(JAMES);
        List<Person> personList = Collections.singletonList(OLIVIA);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(OLIVIA);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(JAMES, JAMES);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }

    @Test
    public void getFilteredList_validType_returnsFilteredList() {
        uniquePersonList.add(JAMES);
        uniquePersonList.add(OLIVIA);

        FilteredList<Person> filteredList = uniquePersonList.getFilteredList(Person.class);
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(JAMES));
        assertTrue(filteredList.contains(OLIVIA));
    }

    @Test
    public void getFilteredList_subtype_returnsFilteredListOfSubtype() {
        // Assuming Staff is a subclass of Person
        uniquePersonList.add(JAMES);
        uniquePersonList.add(ALEX);

        FilteredList<Staff> filteredList = uniquePersonList.getFilteredList(Staff.class);
        assertEquals(1, filteredList.size());
        assertTrue(filteredList.contains(ALEX));
    }

    @Test
    public void getFilteredList_noMatchingType_returnsEmptyList() {
        uniquePersonList.add(JAMES);
        uniquePersonList.add(ALEX);

        // Assuming Customer is a subclass of Person but no Customer instances are added
        FilteredList<Customer> filteredList = uniquePersonList.getFilteredList(Customer.class);
        assertTrue(filteredList.isEmpty());
    }

    @Test
    public void getFilteredList_emptyList_returnsEmptyList() {
        FilteredList<Person> filteredList = uniquePersonList.getFilteredList(Person.class);
        assertTrue(filteredList.isEmpty());
    }
}
