package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Staff;
import seedu.address.model.util.StaffBuilder;

/**
 * Adds hours to a staff member's total hours worked.
 */
public class HoursAddCommand extends Command {
    public static final String COMMAND_WORD = "hoursadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds hours to a staff member. "
            + "Parameters: "
            + PREFIX_INDEX + "<index> "
            + PREFIX_HOURS + "<hours>\n"
            + "Example: " + COMMAND_WORD + " ind/1 h/5";

    public static final String MESSAGE_SUCCESS = "Updated hours for %1$s: %s -> %s";
    public static final String MESSAGE_INVALID_INDEX = "Invalid staff index.";
    public static final String MESSAGE_INVALID_HOURS = "Hours must be a non-negative integer.";

    private final Index targetIndex;
    private final int hoursToAdd;

    /**
     * Constructs a HoursAddCommand.
     */
    public HoursAddCommand(Index targetIndex, int hoursToAdd) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.hoursToAdd = hoursToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Staff> lastShownList = model.getFilteredStaffList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        if (hoursToAdd < 0) {
            throw new CommandException(MESSAGE_INVALID_HOURS);
        }

        Staff staffToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Staff updatedStaff = createEditedStaff(staffToUpdate, hoursToAdd);

        model.setStaff(staffToUpdate, updatedStaff);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedStaff),
                staffToUpdate.getHoursWorked(), updatedStaff.getHoursWorked()));
    }

    /**
     * Creates a new Staff object with updated hours worked.
     * This method is used to create a new Staff object with the updated hours worked.
     *
     * @param staffToUpdate The Staff object to be updated.
     * @return A new Staff object with updated hours worked.
     */
    private static Staff createEditedStaff(Staff staffToUpdate, int hoursToAdd) {
        int currentHours = Integer.parseInt(staffToUpdate.getHoursWorked().value);
        int newHours = currentHours + hoursToAdd;
        HoursWorked updatedHoursWorked = new HoursWorked(String.valueOf(newHours));

        StaffBuilder staffBuilder = new StaffBuilder(staffToUpdate);
        staffBuilder.withHoursWorked(updatedHoursWorked);
        return staffBuilder.build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HoursAddCommand)) {
            return false;
        }
        HoursAddCommand otherCommand = (HoursAddCommand) other;
        return targetIndex.equals(otherCommand.targetIndex) && hoursToAdd == otherCommand.hoursToAdd;
    }
}
