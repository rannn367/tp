package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REDEEM;

import seedu.address.commons.core.index.Index;
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
                args, PREFIX_DRINKNAME, PREFIX_REDEEM);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_DRINKNAME).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DRINKNAME, PREFIX_REDEEM);

        String drinkName = argMultimap.getValue(PREFIX_DRINKNAME).get();
        boolean isRedemption = false;

        if (argMultimap.getValue(PREFIX_REDEEM).isPresent()) {
            isRedemption = ParserUtil.parseBoolean(argMultimap.getValue(PREFIX_REDEEM).get());
        }

        return new PurchaseCommand(index, drinkName, isRedemption);
    }
}
