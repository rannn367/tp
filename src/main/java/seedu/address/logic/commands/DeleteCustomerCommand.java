package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CUSTOMER_SUCCESS = "Deleted Customer: %1$s";

    private final Index targetIndex;

    public DeleteCustomerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredCustomerList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToDelete = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        model.deleteCustomer(customerToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, Messages.format(customerToDelete)));
    }

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
