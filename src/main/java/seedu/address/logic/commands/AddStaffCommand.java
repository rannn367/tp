package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS_WORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERFORMANCE_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAFF_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
public class AddStaffCommand extends Command {

    public static final String COMMAND_WORD = "staffadd";
    public static final String COMMAND_WORD_SHORTCUT = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Adds a staff member to the address book. "
            + "Parameters: "
            + PREFIX_STAFF_ID + "STAFF_ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_SHIFT_TIMING + "SHIFT_TIMING "
            + PREFIX_HOURS_WORKED + "HOURS_WORKED "
            + PREFIX_PERFORMANCE_RATING + "PERFORMANCE_RATING "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STAFF_ID + "S1234 "
            + PREFIX_NAME + "Alice Tan "
            + PREFIX_PHONE + "81234567 "
            + PREFIX_EMAIL + "alice@example.com "
            + PREFIX_ADDRESS + "123, Jurong West Ave 6, #08-111 "
            + PREFIX_ROLE + "Barista "
            + PREFIX_SHIFT_TIMING + "9am-5pm "
            + PREFIX_HOURS_WORKED + "40 "
            + PREFIX_PERFORMANCE_RATING + "4.5 "
            + PREFIX_TAG + "fullTime "
            + PREFIX_TAG + "experienced\n";

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
    public AddStaffCommand(Staff staff) {
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
        logger.log(Level.INFO, "Executing AddStaffCommand for staff: {0}", toAdd);

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

        if (!(other instanceof AddStaffCommand)) {
            return false;
        }

        AddStaffCommand otherAddStaffCommand = (AddStaffCommand) other;
        return toAdd.equals(otherAddStaffCommand.toAdd);
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
