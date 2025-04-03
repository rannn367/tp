package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all staff in the address book to the user.
 */
public class ListStaffCommand extends Command {

    public static final String COMMAND_WORD = "stafflist";
    public static final String COMMAND_WORD_SHORTCUT = "sl";
    public static final String MESSAGE_SUCCESS = "Listed all staff.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStaffList(Model.PREDICATE_SHOW_ALL_STAFFS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
