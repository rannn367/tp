package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;

/**
 * Adds a customer to the address book.
 */
public class AddCustomerCommand extends Command {

    public static final String COMMAND_WORD = "customeradd";
    public static final String COMMAND_WORD_SHORTCUT = "ca";
    public static final String MESSAGE_SUCCESS = "New customer added: %1$s";
    public static final String MESSAGE_DUPLICATE_CUSTOMER =
            "Duplicated Customer ID found. This customer already exists in the address book.";

    private static final Logger logger = Logger.getLogger(AddCustomerCommand.class.getName());

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Adds a customer to the address book. "
            + "Parameters: "
            + PREFIX_CUSTOMER_ID + "CUSTOMER_ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_REWARD_POINTS + "REWARD_POINTS "
            + PREFIX_VISIT_COUNT + "VISIT_COUNT "
            + PREFIX_FAVOURITE_ITEM + "FAVOURITE_ITEM "
            + PREFIX_TOTAL_SPENT + "TOTAL_SPENT "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER_ID + "C1234 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_REWARD_POINTS + "150 "
            + PREFIX_VISIT_COUNT + "8 "
            + PREFIX_FAVOURITE_ITEM + "Cappuccino "
            + PREFIX_TOTAL_SPENT + "120.50 "
            + PREFIX_TAG + "regular "
            + PREFIX_TAG + "vip\n";

    private final Customer toAdd;

    /**
     * Creates an AddCustomerCommand to add the specified {@code Customer}.
     *
     * @param customer The customer to be added.
     */
    public AddCustomerCommand(Customer customer) {
        requireNonNull(customer);
        toAdd = customer;
    }

    /**
     * Executes the command to add a customer to the address book.
     *
     * @param model The model in which the customer is added.
     * @return The result of executing the command.
     * @throws CommandException If the customer already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing AddCustomerCommand with customer: {0}", toAdd);

        if (model.hasCustomer(toAdd)) {
            logger.log(Level.WARNING, "Attempted to add duplicate customer: {0}", toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.addCustomer(toAdd);
        logger.log(Level.INFO, "Successfully added customer: {0}", toAdd);
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

        if (!(other instanceof AddCustomerCommand)) {
            return false;
        }

        AddCustomerCommand otherAddCustomerCommand = (AddCustomerCommand) other;
        return toAdd.equals(otherAddCustomerCommand.toAdd);
    }

    /**
     * Returns a string representation of the AddCustomerCommand.
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
