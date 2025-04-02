package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DRINK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDrinkAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDrinks.getTypicalDrinkCatalog;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DRINK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DRINK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Drink;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteDrinkCommand}.
 */
public class DeleteDrinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalDrinkCatalog());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Drink drinkToDelete = model.getFilteredDrinkList().get(INDEX_FIRST_DRINK.getZeroBased());
        DeleteDrinkCommand deleteDrinkCommand = new DeleteDrinkCommand(INDEX_FIRST_DRINK);

        String expectedMessage = String.format(DeleteDrinkCommand.MESSAGE_DELETE_DRINK_SUCCESS, drinkToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getDrinkCatalog());
        expectedModel.deleteDrink(drinkToDelete);

        assertCommandSuccess(deleteDrinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDrinkList().size() + 1);
        DeleteDrinkCommand deleteDrinkCommand = new DeleteDrinkCommand(outOfBoundIndex);

        assertCommandFailure(deleteDrinkCommand, model, MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDrinkAtIndex(model, INDEX_FIRST_DRINK);

        Drink drinkToDelete = model.getFilteredDrinkList().get(INDEX_FIRST_DRINK.getZeroBased());
        DeleteDrinkCommand deleteDrinkCommand = new DeleteDrinkCommand(INDEX_FIRST_DRINK);

        String expectedMessage = String.format(DeleteDrinkCommand.MESSAGE_DELETE_DRINK_SUCCESS, drinkToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getDrinkCatalog());
        expectedModel.deleteDrink(drinkToDelete);
        showNoDrink(expectedModel);

        assertCommandSuccess(deleteDrinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDrinkAtIndex(model, INDEX_FIRST_DRINK);

        Index outOfBoundIndex = INDEX_SECOND_DRINK;
        // ensures that outOfBoundIndex is still in bounds of the drink catalog
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDrinkCatalog().getDrinkList().size());

        DeleteDrinkCommand deleteDrinkCommand = new DeleteDrinkCommand(outOfBoundIndex);

        assertCommandFailure(deleteDrinkCommand, model, MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteDrinkCommand deleteFirstCommand = new DeleteDrinkCommand(INDEX_FIRST_DRINK);
        DeleteDrinkCommand deleteSecondCommand = new DeleteDrinkCommand(INDEX_SECOND_DRINK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDrinkCommand deleteFirstCommandCopy = new DeleteDrinkCommand(INDEX_FIRST_DRINK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different drink -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no drinks.
     */
    private void showNoDrink(Model model) {
        model.updateFilteredDrinkList(p -> false);

        assertTrue(model.getFilteredDrinkList().isEmpty());
    }
}
