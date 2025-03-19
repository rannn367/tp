package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HoursAddCommand;

public class HoursAddCommandParserTest {
    private HoursAddCommandParser parser = new HoursAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Standard case
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_HOURS + "8";
        HoursAddCommand expectedCommand = new HoursAddCommand(INDEX_FIRST_PERSON, 8);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Different order of fields
        userInput = " " + PREFIX_HOURS + "8 " + PREFIX_INDEX + "1";
        assertParseSuccess(parser, userInput, expectedCommand);

        // Whitespace variations
        userInput = " " + PREFIX_INDEX + " 1 " + PREFIX_HOURS + "8 ";
        assertParseSuccess(parser, userInput, expectedCommand);

        // Default hours (no hours specified)
        userInput = " " + PREFIX_INDEX + "1";
        expectedCommand = new HoursAddCommand(INDEX_FIRST_PERSON, 8); // Default value 8
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HoursAddCommand.MESSAGE_USAGE);

        // Missing index prefix
        assertParseFailure(parser, PREFIX_HOURS + "8", expectedMessage);

        // Missing hours prefix
        assertParseFailure(parser, PREFIX_INDEX + "1", expectedMessage);

        // Empty string
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HoursAddCommand.MESSAGE_USAGE);

        // Invalid index
        assertParseFailure(parser, PREFIX_INDEX + "0 " + PREFIX_HOURS + "8", expectedMessage);
        assertParseFailure(parser, PREFIX_INDEX + "-1 " + PREFIX_HOURS + "8", expectedMessage);
        assertParseFailure(parser, PREFIX_INDEX + "abc " + PREFIX_HOURS + "8", expectedMessage);

        // Invalid hours (not a number)
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_HOURS + "abc", expectedMessage);

        // Invalid hours (negative)
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_HOURS + "-8", expectedMessage);
    }

    @Test
    public void parse_extraneousPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HoursAddCommand.MESSAGE_USAGE);

        // Non-empty preamble
        assertParseFailure(parser, "some random text " + PREFIX_INDEX + "1 " + PREFIX_HOURS + "8",
                expectedMessage);
    }

    @Test
    public void parse_extraneousArgument_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HoursAddCommand.MESSAGE_USAGE);

        // Extra arguments
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_HOURS + "8 extraArgument",
                expectedMessage);
    }
}
