package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindStaffCommand extends Command {

    public static final String COMMAND_WORD = "stafffind";
    public static final String COMMAND_WORD_SHORTCUT = "sf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Predicate<Person> predicate;

    public FindStaffCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public FindStaffCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Staff> filteredStaffs = model.updateFilteredStaffList(predicate);
        int count = filteredStaffs.size();
        String result = IntStream.range(0, count)
            .mapToObj(i -> (i + 1) + ". " + filteredStaffs.get(i).getName())
            .collect(Collectors.joining("\n"));
        return new CommandResult(
                String.format(Messages.MESSAGE_STAFF_LISTED_OVERVIEW, count, result));
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
