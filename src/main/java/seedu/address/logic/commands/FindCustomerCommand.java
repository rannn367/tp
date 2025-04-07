package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;

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
public class FindCustomerCommand extends Command {

    public static final String COMMAND_WORD = "customerfind";
    public static final String COMMAND_WORD_SHORTCUT = "cf";

    public static final String MESSAGE_INVALID_ALL = "Invalid usage of all/true flag.\n"
            + " Use 'findcustomer all/true' alone to show all customers.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Finds and filters customers based on specified criteria. "
            + "Shows customers matching ALL the specified search criteria.\n"
            + "Parameters: [all/true] OR "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_CUSTOMER_ID + "CUSTOMER_ID] "
            + "[" + PREFIX_REWARD_POINTS + "REWARD_POINTS] "
            + "[" + PREFIX_VISIT_COUNT + "VISIT_COUNT] "
            + "[" + PREFIX_FAVOURITE_ITEM + "FAVOURITE_ITEM] "
            + "[" + PREFIX_TOTAL_SPENT + "TOTAL_SPENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Find all: " + PREFIX_ALL + "true shows all customers (must be used alone with no other parameters)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_VISIT_COUNT + "5\n"
            + "OR: " + COMMAND_WORD + " " + PREFIX_ALL + "true";

    private final SameFieldsPredicate predicate;

    /**
     * Constructs a FindCustomerCommand with a SameFieldsPredicate.
     *
     * @param predicate The SameFieldsPredicate to use for filtering customers
     */
    public FindCustomerCommand(SameFieldsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Constructs a FindCustomerCommand with a single Predicate, wrapping it in a SameFieldsPredicate.
     *
     * @param predicate The Predicate to use for filtering customers
     */
    public FindCustomerCommand(Predicate<Person> predicate) {
        if (predicate instanceof SameFieldsPredicate) {
            this.predicate = (SameFieldsPredicate) predicate;
        } else {
            this.predicate = new SameFieldsPredicate(new HashSet<>(Arrays.asList(predicate)));
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(predicate);
        int count = model.getFilteredCustomersCount();
        String customerList = model.getFilteredCustomersAsString();
        if (model.getFilteredCustomersCount() == 0) {
            model.updateFilteredCustomerList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
            return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW, count, "\nShowing all customers instead."));
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW, count, customerList));
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
