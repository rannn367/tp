package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS_WORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERFORMANCE_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAFF_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.SameFieldsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindStaffCommand extends Command {

    public static final String COMMAND_WORD = "stafffind";
    public static final String COMMAND_WORD_SHORTCUT = "sf";

    public static final String MESSAGE_INVALID_ALL = "/all must be used alone with no other parameters";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Finds and filters staff based on specified criteria. "
            + "Shows staff matching ALL the specified search criteria.\n"
            + "Parameters: [/all] OR "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_STAFF_ID + "STAFF_ID] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_SHIFT_TIMING + "SHIFT_TIMING] "
            + "[" + PREFIX_HOURS_WORKED + "HOURS_WORKED] "
            + "[" + PREFIX_PERFORMANCE_RATING + "PERFORMANCE_RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Special search: " + PREFIX_ALL + " shows all staff (must be used alone with no other parameters)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_ROLE + "Barista\n"
            + "OR: " + COMMAND_WORD + " " + PREFIX_ALL;

    private final SameFieldsPredicate predicate;

    /**
     * Constructs a FindStaffCommand with a SameFieldsPredicate.
     *
     * @param predicate The SameFieldsPredicate to use for filtering staff
     */
    public FindStaffCommand(SameFieldsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Constructs a FindStaffCommand with a single Predicate, wrapping it in a SameFieldsPredicate.
     *
     * @param predicate The Predicate to use for filtering staff
     */
    public FindStaffCommand(Predicate<Person> predicate) {
        if (predicate instanceof SameFieldsPredicate) {
            this.predicate = (SameFieldsPredicate) predicate;
        } else {
            this.predicate = new SameFieldsPredicate(new HashSet<>(Arrays.asList(predicate)));
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStaffList(predicate);
        int count = model.getFilteredStaffsCount();
        String staffList = model.getFilteredStaffsAsString();
        if (model.getFilteredStaffsCount() == 0) {
            model.updateFilteredStaffList(Model.PREDICATE_SHOW_ALL_STAFFS);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_STAFF_LISTED_OVERVIEW, count, staffList));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindStaffCommand)) {
            return false;
        }

        FindStaffCommand otherFindStaffCommand = (FindStaffCommand) other;
        return predicate.equals(otherFindStaffCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

