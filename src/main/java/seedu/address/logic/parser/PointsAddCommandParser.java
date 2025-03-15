package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PointsAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PointsAddCommand object
 */
public class PointsAddCommandParser implements Parser<PointsAddCommand> {

    /**
     * Parses input arguments and creates a new PointsAddCommand object
     */
    public PointsAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
            args, PREFIX_INDEX, PREFIX_POINTS);

        // Ensure both prefixes are present and preamble is empty
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_POINTS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsAddCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            int pointsToAdd = parsePointsValue(argMultimap.getValue(PREFIX_POINTS).get());

            return new PointsAddCommand(index, pointsToAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsAddCommand.MESSAGE_USAGE), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses points value into an integer
     */
    private int parsePointsValue(String points) throws IllegalValueException {
        try {
            int value = Integer.parseInt(points.trim());
            if (value <= 0) {
                throw new IllegalValueException("Points must be a positive integer");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Points must be a valid integer");
        }
    }
}
