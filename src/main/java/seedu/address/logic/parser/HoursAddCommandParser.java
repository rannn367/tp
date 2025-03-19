package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.HoursAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HoursAddCommand object
 */
public class HoursAddCommandParser implements Parser<HoursAddCommand> {

    private static final int DEFAULT_HOURS = 8;

    /**
     * Parses input arguments and creates a new HoursAddCommand object
     */
    public HoursAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HoursAddCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            int hoursToAdd = argMultimap.getValue(PREFIX_HOURS).map(Integer::parseInt).orElse(DEFAULT_HOURS);

            if (hoursToAdd < 0) {
                throw new IllegalValueException(HoursAddCommand.MESSAGE_INVALID_HOURS);
            }

            return new HoursAddCommand(index, hoursToAdd);
        } catch (IllegalValueException | NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HoursAddCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
