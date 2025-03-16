package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DRINKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDrinks.LATTE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalStaff.ALEX;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.drink.DrinkCatalog;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new DrinkCatalog(), new DrinkCatalog(modelManager.getDrinkCatalog()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setDrinkCatalogFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDrinkCatalogFilePath(null));
    }

    @Test
    public void setDrinkCatalogFilePath_validPath_setsDrinkCatalogFilePath() {
        Path path = Paths.get("drink/catalog/file/path");
        modelManager.setDrinkCatalogFilePath(path);
        assertEquals(path, modelManager.getDrinkCatalogFilePath());
    }

    @Test
    public void setDrinkCatalog_nullDrinkCatalog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDrinkCatalog(null));
    }

    @Test
    public void setDrinkCatalog_validDrinkCatalog_setsDrinkCatalog() {
        DrinkCatalog drinkCatalog = new DrinkCatalog();
        drinkCatalog.addDrink(LATTE);
        modelManager.setDrinkCatalog(drinkCatalog);
        assertEquals(drinkCatalog, modelManager.getDrinkCatalog());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStaff(null));
    }

    @Test
    public void hasStaff_staffNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasStaff(ALEX));
    }

    @Test
    public void hasStaff_staffInAddressBook_returnsTrue() {
        modelManager.addStaff(ALEX);
        assertTrue(modelManager.hasStaff(ALEX));
    }

    @Test
    public void getFilteredStaffList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStaffList().remove(0));
    }

    @Test
    public void hasDrink_nullDrink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDrink(null));
    }

    @Test
    public void hasDrink_drinkNotInDrinkCatalog_returnsFalse() {
        assertFalse(modelManager.hasDrink(LATTE));
    }

    @Test
    public void hasDrink_drinkInDrinkCatalog_returnsTrue() {
        modelManager.addDrink(LATTE);
        assertTrue(modelManager.hasDrink(LATTE));
    }

    @Test
    public void getFilteredDrinkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDrinkList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        DrinkCatalog drinkCatalog = new DrinkCatalog();
        DrinkCatalog differentDrinkCatalog = new DrinkCatalog();
        differentDrinkCatalog.addDrink(LATTE);

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, drinkCatalog);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, drinkCatalog);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, drinkCatalog)));

        // different drinkCatalog -> returns false
        // assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, differentDrinkCatalog)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, drinkCatalog)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different filteredDrinks -> returns false
        modelManager.updateFilteredDrinkList(d -> d.getName().equals("Tea"));
        // assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, drinkCatalog)));

        // resets filteredDrinks
        modelManager.updateFilteredDrinkList(PREDICATE_SHOW_ALL_DRINKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, drinkCatalog)));
    }
}
