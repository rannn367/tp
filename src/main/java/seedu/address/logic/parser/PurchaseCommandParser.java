package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX; // assuming ind/ is already defined as PREFIX_INDEX
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME; // assuming n/ is already defined as PREFIX_NAME

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PurchaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PurchaseCommand object
 */
public class PurchaseCommandParser implements Parser<PurchaseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PurchaseCommand
     * and returns a PurchaseCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public PurchaseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_INDEX, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            String drinkName = argMultimap.getValue(PREFIX_NAME).get().trim();

            if (drinkName.isEmpty()) {
                throw new ParseException("Drink name cannot be empty");
            }

            return new PurchaseCommand(index, drinkName);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PurchaseCommand.MESSAGE_USAGE), ive);
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
