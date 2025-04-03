package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;

/**
 * Adds a drink to the drink catalog.
 */
public class AddDrinkCommand extends Command {

    public static final String COMMAND_WORD = "drinkadd";
    public static final String COMMAND_WORD_SHORTCUT = "d";
    public static final String MESSAGE_SUCCESS = "New drink added: %1$s";
    public static final String MESSAGE_DUPLICATE_DRINK = "This drink already exists in the drink catalog";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_WORD_SHORTCUT
            + "): Adds a drink to the drink catalog. "
            + "Parameters: "
            + PREFIX_DRINKNAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINKNAME + "Iced Latte "
            + PREFIX_PRICE + "4.50 "
            + PREFIX_CATEGORY + "Coffee";

    private static final Logger logger = Logger.getLogger(AddDrinkCommand.class.getName());
    private final Drink toAdd;

    /**
     * Creates an AddDrinkCommand to add the specified {@code Drink}.
     *
     * @param drink The drink to be added.
     * @throws NullPointerException if the provided drink is null.
     */
    public AddDrinkCommand(Drink drink) {
        requireNonNull(drink, "Drink cannot be null.");
        this.toAdd = drink;
    }

    /**
     * Executes the command to add a drink to the drink catalog.
     *
     * @param model The model in which the drink is added.
     * @return The result of executing the command.
     * @throws CommandException If the drink already exists in the catalog.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null.");
        logger.log(Level.INFO, "Executing AddDrinkCommand with drink: {0}", toAdd);

        if (model.hasDrink(toAdd)) {
            logger.log(Level.WARNING, "Attempted to add duplicate drink: {0}", toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_DRINK);
        }

        model.addDrink(toAdd);
        logger.log(Level.INFO, "Successfully added drink: {0}", toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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

        if (!(other instanceof AddDrinkCommand)) {
            return false;
        }

        AddDrinkCommand otherAddDrinkCommand = (AddDrinkCommand) other;
        return toAdd.equals(otherAddDrinkCommand.toAdd);
    }

    /**
     * Returns a string representation of the AddDrinkCommand.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("drink", toAdd)
                .toString();
    }
}
