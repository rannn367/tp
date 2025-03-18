package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PurchaseCommand;


public class PurchaseCommandParserTest {
    private PurchaseCommandParser parser = new PurchaseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String drinkName = "Matcha";

        // Standard case
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_NAME + drinkName;
        PurchaseCommand expectedCommand = new PurchaseCommand(INDEX_FIRST_PERSON, drinkName);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Different order of fields
        userInput = " " + PREFIX_NAME + drinkName + " " + PREFIX_INDEX + "1";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseCommand.MESSAGE_USAGE);

        // Missing index prefix
        assertParseFailure(parser, " " + PREFIX_NAME + "Matcha", expectedMessage);

        // Missing drink name prefix
        assertParseFailure(parser, " " + PREFIX_INDEX + "1", expectedMessage);

        // Empty string
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_extraneousPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseCommand.MESSAGE_USAGE);

        // Non-empty preamble
        assertParseFailure(parser, "some random text " + PREFIX_INDEX + "1 " + PREFIX_NAME + "Matcha",
                expectedMessage);
    }
}
