package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;

/**
 * Deletes a customer identified using its displayed index from the address book.
 */
public class DeleteCustomerCommand extends Command {

    public static final String COMMAND_WORD = "customerdelete";
    public static final String COMMAND_WORD_SHORTCUT = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CUSTOMER_SUCCESS = "Deleted Customer: %1$s";

    private static final Logger logger = Logger.getLogger(DeleteCustomerCommand.class.getName());

    private final Index targetIndex;

    /**
     * Creates a DeleteCustomerCommand to delete the customer at the specified index.
     *
     * @param targetIndex The index of the customer to be deleted.
     * @throws NullPointerException if targetIndex is null.
     */
    public DeleteCustomerCommand(Index targetIndex) {
        requireNonNull(targetIndex, "Target index cannot be null.");
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command to delete a customer from the address book.
     *
     * @param model The model in which the customer is deleted.
     * @return The result of executing the command.
     * @throws CommandException If the given index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null.");
        logger.log(Level.INFO, "Executing DeleteCustomerCommand for index: {0}", targetIndex.getOneBased());

        if (targetIndex.getZeroBased() >= model.getFilteredCustomerList().size()) {
            logger.log(Level.WARNING, "Invalid customer index: {0}", targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToDelete = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        model.deleteCustomer(customerToDelete);
        logger.log(Level.INFO, "Successfully deleted customer: {0}", customerToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, Messages.format(customerToDelete)));
    }

    /**
     * Checks if this command is equal to another object.
     *
     * @param other The other object to compare.
     * @return True if both are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCustomerCommand)) {
            return false;
        }

        DeleteCustomerCommand otherDeleteCommand = (DeleteCustomerCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
