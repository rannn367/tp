package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the drink identified by the index number used in the displayed drinks list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DRINK_SUCCESS = "Deleted Drink: %1$s";

    private final Index targetIndex;

    public DeleteDrinkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredDrinkList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
        }

        Drink drinkToDelete = model.getFilteredDrinkList().get(targetIndex.getZeroBased());
        model.deleteDrink(drinkToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_DRINK_SUCCESS, drinkToDelete));
    }

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
