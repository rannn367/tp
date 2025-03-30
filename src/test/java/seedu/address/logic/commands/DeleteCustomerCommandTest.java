package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.DrinkCatalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Customer;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCustomerCommand}.
 */
public class DeleteCustomerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new DrinkCatalog());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS,
                Messages.format(customerToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new DrinkCatalog());
        expectedModel.deleteCustomer(customerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_PERSON);

        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS,
                Messages.format(customerToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new DrinkCatalog());
        expectedModel.deleteCustomer(customerToDelete);
        showNoCustomer(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCustomerAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCustomerList().size());

        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCustomerCommand deleteFirstCommand = new DeleteCustomerCommand(INDEX_FIRST_PERSON);
        DeleteCustomerCommand deleteSecondCommand = new DeleteCustomerCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCustomerCommand deleteFirstCommandCopy = new DeleteCustomerCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no customers.
     */
    private void showNoCustomer(Model model) {
        model.updateFilteredCustomerList(p -> false);

        assertTrue(model.getFilteredCustomerList().isEmpty());
    }
}
