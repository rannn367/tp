package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Staff;

/**
 * Adds a staff member to the address book.
 */
public class QuickAddStaffCommand extends Command {

    public static final String COMMAND_WORD = "quickstaffadd";
    public static final String COMMAND_WORD_SHORTCUT = "qsa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Quickly adds a staff member to the address book. "
            + "Parameters: " + COMMAND_WORD_SHORTCUT + " <STAFF_ID>:<NAME>:<PHONE>\n"
            + "Example: " + COMMAND_WORD_SHORTCUT + " S1234:Alice Tan:81234567";

    public static final String MESSAGE_SUCCESS = "New staff added: %1$s";
    public static final String MESSAGE_DUPLICATE_STAFF =
            "Duplicated Staff ID found. This staff already exists in the address book.";

    private static final Logger logger = Logger.getLogger(AddStaffCommand.class.getName());

    private final Staff toAdd;

    /**
     * Creates an AddStaffCommand to add the specified {@code Staff}.
     *
     * @param staff The staff member to be added.
     * @throws NullPointerException if the provided staff is null.
     */
    public QuickAddStaffCommand(Staff staff) {
        requireNonNull(staff, "Staff cannot be null.");
        this.toAdd = staff;
    }

    /**
     * Executes the command to add a staff member to the address book.
     *
     * @param model The model in which the staff member is added.
     * @return The result of executing the command.
     * @throws CommandException If the staff member already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null.");
        logger.log(Level.INFO, "Executing QuickAddStaffCommand for staff: {0}", toAdd);

        if (model.hasStaff(toAdd)) {
            logger.log(Level.WARNING, "Attempted to add duplicate staff: {0}", toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_STAFF);
        }

        model.addStaff(toAdd);
        logger.log(Level.INFO, "Successfully added staff: {0}", toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
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

        if (!(other instanceof QuickAddStaffCommand)) {
            return false;
        }

        QuickAddStaffCommand otherQuickAddStaffCommand = (QuickAddStaffCommand) other;
        return toAdd.equals(otherQuickAddStaffCommand.toAdd);
    }

    /**
     * Returns a string representation of the AddStaffCommand.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
