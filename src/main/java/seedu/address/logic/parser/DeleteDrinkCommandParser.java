package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteDrinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteDrinkCommand object
 */
public class DeleteDrinkCommandParser implements Parser<DeleteDrinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDrinkCommand
     * and returns a DeleteDrinkCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteDrinkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteDrinkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDrinkCommand.MESSAGE_USAGE), pe);
        }
    }
}
