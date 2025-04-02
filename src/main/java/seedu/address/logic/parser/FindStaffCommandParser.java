package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindStaffCommand object
 */
public class FindStaffCommandParser implements Parser<FindStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindStaffCommand
     * and returns a FindStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindStaffCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindStaffCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
