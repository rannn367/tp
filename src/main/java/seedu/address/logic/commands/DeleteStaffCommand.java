package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final String COMMAND_WORD_SHORTCUT = "sd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the staff member identified by the index number used in the displayed staff list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STAFF_SUCCESS = "Deleted Staff: %1$s";

    private static final Logger logger = Logger.getLogger(DeleteStaffCommand.class.getName());

    private final Index targetIndex;

    /**
     * Creates a DeleteStaffCommand to delete the staff at the specified index.
     *
     * @param targetIndex The index of the staff member to be deleted.
     * @throws NullPointerException if targetIndex is null.
     */
    public DeleteStaffCommand(Index targetIndex) {
        requireNonNull(targetIndex, "Target index cannot be null.");
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command to delete a staff member from the address book.
     *
     * @param model The model in which the staff member is deleted.
     * @return The result of executing the command.
     * @throws CommandException If the given index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null.");
        logger.log(Level.INFO, "Executing DeleteStaffCommand for index: {0}", targetIndex.getOneBased());

        if (targetIndex.getZeroBased() >= model.getFilteredStaffList().size()) {
            logger.log(Level.WARNING, "Invalid staff index: {0}", targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToDelete = model.getFilteredStaffList().get(targetIndex.getZeroBased());
        model.deleteStaff(staffToDelete);
        logger.log(Level.INFO, "Successfully deleted staff: {0}", staffToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_STAFF_SUCCESS, Messages.format(staffToDelete)));
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

        if (!(other instanceof DeleteStaffCommand)) {
            return false;
        }

        DeleteStaffCommand otherDeleteCommand = (DeleteStaffCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
