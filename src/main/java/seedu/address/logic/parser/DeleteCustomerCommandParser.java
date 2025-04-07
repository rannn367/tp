package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCustomerCommand object
 */
public class DeleteCustomerCommandParser implements Parser<DeleteCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCustomerCommand
     * and returns a DeleteCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCustomerCommand parse(String args) throws ParseException {
        if (args.trim().matches("0|-\\d+")) {
            throw new ParseException(MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCustomerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCustomerCommand.MESSAGE_USAGE), pe);
        }
    }
}
