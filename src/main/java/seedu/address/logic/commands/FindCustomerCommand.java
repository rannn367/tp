package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Customer;

/**
 * Finds and lists all customers in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCustomerCommand extends Command {

    public static final String COMMAND_WORD = "customerfind";
    public static final String COMMAND_WORD_SHORTCUT = "cf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCustomerCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(predicate);
        ObservableList<Customer> filteredCustomers = model.updateFilteredCustomerList(predicate);
        int count = filteredCustomers.size();
        String result = IntStream.range(0, count)
            .mapToObj(i -> (i + 1) + ". " + filteredCustomers.get(i).getName())
            .collect(Collectors.joining("\n"));
            return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW, count, result));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCustomerCommand)) {
            return false;
        }

        FindCustomerCommand otherFindCustomerCommand = (FindCustomerCommand) other;
        return predicate.equals(otherFindCustomerCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
