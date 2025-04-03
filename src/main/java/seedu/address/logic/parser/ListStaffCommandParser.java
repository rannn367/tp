package seedu.address.logic.parser;

import seedu.address.logic.commands.ListStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListStaffCommand object.
 */
public class ListStaffCommandParser implements Parser<ListStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListStaffCommand
     * and returns a ListStaffCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ListStaffCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException("The 'stafflist' command does not accept any arguments.");
        }
        return new ListStaffCommand();
    }
}
