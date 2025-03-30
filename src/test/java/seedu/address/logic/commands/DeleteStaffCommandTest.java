package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStaffAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalStaff.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.DrinkCatalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Staff;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStaffCommand}.
 */
public class DeleteStaffCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new DrinkCatalog());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteStaffCommand deleteCommand = new DeleteStaffCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStaffCommand.MESSAGE_DELETE_STAFF_SUCCESS,
                Messages.format(staffToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new DrinkCatalog());
        expectedModel.deleteStaff(staffToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        DeleteStaffCommand deleteCommand = new DeleteStaffCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStaffAtIndex(model, INDEX_FIRST_PERSON);

        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteStaffCommand deleteCommand = new DeleteStaffCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStaffCommand.MESSAGE_DELETE_STAFF_SUCCESS,
                Messages.format(staffToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new DrinkCatalog());
        expectedModel.deleteStaff(staffToDelete);
        showNoStaff(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStaffAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStaffList().size());

        DeleteStaffCommand deleteCommand = new DeleteStaffCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStaffCommand deleteFirstCommand = new DeleteStaffCommand(INDEX_FIRST_PERSON);
        DeleteStaffCommand deleteSecondCommand = new DeleteStaffCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStaffCommand deleteFirstCommandCopy = new DeleteStaffCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different staff -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no staff.
     */
    private void showNoStaff(Model model) {
        model.updateFilteredStaffList(p -> false);

        assertTrue(model.getFilteredStaffList().isEmpty());
    }
}
