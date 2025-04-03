package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCustomerCommand object.
 */
public class ListCustomerCommandParser implements Parser<ListCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCustomerCommand
     * and returns a ListCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ListCustomerCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException("The 'customerlist' command does not accept any arguments.");
        }
        return new ListCustomerCommand();
    }
}
