package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.QuickPurchaseCommand;

public class QuickPurchaseCommandParserTest {
    private QuickPurchaseCommandParser parser = new QuickPurchaseCommandParser();

    @Test
    public void parse_fields_success() {
        // Standard case
        assertParseSuccess(parser, "1:Matcha", new QuickPurchaseCommand(INDEX_FIRST_PERSON, "Matcha", false));

        // With redemption flag
        assertParseSuccess(parser, "1:Matcha:r", new QuickPurchaseCommand(INDEX_FIRST_PERSON, "Matcha", true));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "abc:Matcha",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDrinkName_throwsParseException() {
        assertParseFailure(parser, "1:",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRedemptionFlag_throwsParseException() {
        assertParseFailure(parser, "1:Matcha:x",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_endsWithColon_throwsParseException() {
        assertParseFailure(parser, "1:Matcha:",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraFields_throwsParseException() {
        assertParseFailure(parser, "1:Matcha:r:extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPurchaseFormat_throwsParseException() {
        String userInput = "purchase 1 d/Matcha r/true";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
    }
}
