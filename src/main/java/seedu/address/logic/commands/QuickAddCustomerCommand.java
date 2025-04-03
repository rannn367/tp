package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
public class QuickAddCustomerCommand extends Command {

    public static final String COMMAND_WORD = "quickcustomeradd";
    public static final String COMMAND_WORD_SHORTCUT = "qca";
    public static final String MESSAGE_SUCCESS = "New customer added: %1$s";
    public static final String MESSAGE_DUPLICATE_CUSTOMER =
            "Duplicated Customer ID found. This customer already exists in the address book.";

    private static final Logger logger = Logger.getLogger(AddCustomerCommand.class.getName());

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Adds a customer to the address book, quickly. "
            + "Parameters: " + COMMAND_WORD_SHORTCUT + " <CUSTOMER_ID>:<NAME>:<PHONE>\n"
            + "Example: " + COMMAND_WORD_SHORTCUT + " C1234:John Doe:98765432";

    private final Customer toAdd;

    /**
     * Creates an AddCustomerCommand to add the specified {@code Customer}.
     *
     * @param customer The customer to be added.
     */
    public QuickAddCustomerCommand(Customer customer) {
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

        if (!(other instanceof QuickAddCustomerCommand)) {
            return false;
        }

        QuickAddCustomerCommand otherQuickAddCustomerCommand = (QuickAddCustomerCommand) other;
        return toAdd.equals(otherQuickAddCustomerCommand.toAdd);
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
