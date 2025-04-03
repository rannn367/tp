package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;

/**
 * Deletes a drink identified using its displayed index from the drink catalog.
 */
public class DeleteDrinkCommand extends Command {

    public static final String COMMAND_WORD = "drinkdelete";
    public static final String COMMAND_WORD_SHORTCUT = "dd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the drink identified by the index number used in the displayed drinks list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DRINK_SUCCESS = "Deleted Drink: %1$s";

    private static final Logger logger = Logger.getLogger(DeleteDrinkCommand.class.getName());

    private final Index targetIndex;

    /**
     * Creates a DeleteDrinkCommand to delete the drink at the specified index.
     *
     * @param targetIndex The index of the drink to be deleted.
     * @throws NullPointerException if targetIndex is null.
     */
    public DeleteDrinkCommand(Index targetIndex) {
        requireNonNull(targetIndex, "Target index cannot be null.");
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command to delete a drink from the catalog.
     *
     * @param model The model in which the drink is deleted.
     * @return The result of executing the command.
     * @throws CommandException If the given index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null.");
        logger.log(Level.INFO, "Executing DeleteDrinkCommand for index: {0}", targetIndex.getOneBased());

        if (targetIndex.getZeroBased() >= model.getFilteredDrinkList().size()) {
            logger.log(Level.WARNING, "Invalid drink index: {0}", targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
        }

        Drink drinkToDelete = model.getFilteredDrinkList().get(targetIndex.getZeroBased());
        model.deleteDrink(drinkToDelete);
        logger.log(Level.INFO, "Successfully deleted drink: {0}", drinkToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_DRINK_SUCCESS, drinkToDelete));
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

        if (!(other instanceof DeleteDrinkCommand)) {
            return false;
        }

        DeleteDrinkCommand otherDeleteCommand = (DeleteDrinkCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
