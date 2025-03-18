package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Staff;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    void toModelType_duplicateStaff_throwsIllegalValueException() {
        JsonAdaptedStaff staff = new JsonAdaptedStaff(new Staff(
                new seedu.address.model.person.Name("Alice Tan"),
                new seedu.address.model.person.Phone("81234567"),
                new seedu.address.model.person.Email("alice.tan@cafeexample.com"),
                new seedu.address.model.person.Address("123 Café Street"),
                new seedu.address.model.person.Remark("Punctual and friendly"),
                Collections.emptySet(),
                new seedu.address.model.person.StaffId("S001"),
                new seedu.address.model.person.Role("Barista"),
                new seedu.address.model.person.ShiftTiming("Morning Shift"),
                new seedu.address.model.person.HoursWorked("40"),
                new seedu.address.model.person.PerformanceRating("4.8")
        ));
        List<JsonAdaptedStaff> staffList = List.of(staff, staff);
        JsonSerializableAddressBook jsonAddressBook =
                new JsonSerializableAddressBook(Collections.emptyList(), staffList, Collections.emptyList());
        assertThrows(IllegalValueException.class, jsonAddressBook::toModelType);
    }

}
