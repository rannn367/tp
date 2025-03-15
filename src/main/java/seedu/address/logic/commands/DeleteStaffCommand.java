package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Staff;

/**
 * Deletes a staff member identified using its displayed index from the address book.
 */
public class DeleteStaffCommand extends Command {

    public static final String COMMAND_WORD = "staffdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the staff member identified by the index number used in the displayed staff list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STAFF_SUCCESS = "Deleted Staff: %1$s";

    private final Index targetIndex;

    public DeleteStaffCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredStaffList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToDelete = model.getFilteredStaffList().get(targetIndex.getZeroBased());
        model.deleteStaff(staffToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_STAFF_SUCCESS, Messages.format(staffToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStaffCommand)) {
            return false;
        }

        DeleteStaffCommand otherDeleteCommand = (DeleteStaffCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
