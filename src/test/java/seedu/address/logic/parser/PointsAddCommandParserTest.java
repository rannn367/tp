package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PointsAddCommand;

public class PointsAddCommandParserTest {
    private PointsAddCommandParser parser = new PointsAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Standard case
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_POINTS + "100";
        PointsAddCommand expectedCommand = new PointsAddCommand(INDEX_FIRST_PERSON, 100);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Different order of fields
        userInput = " " + PREFIX_POINTS + "100 " + PREFIX_INDEX + "1";
        assertParseSuccess(parser, userInput, expectedCommand);

        // Whitespace variations
        userInput = " " + PREFIX_INDEX + " 1 " + PREFIX_POINTS + "100 ";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsAddCommand.MESSAGE_USAGE);

        // Missing index prefix
        assertParseFailure(parser, PREFIX_POINTS + "100", expectedMessage);

        // Missing points prefix
        assertParseFailure(parser, PREFIX_INDEX + "1", expectedMessage);

        // Empty string
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsAddCommand.MESSAGE_USAGE);

        // Invalid index
        assertParseFailure(parser, PREFIX_INDEX + "0 " + PREFIX_POINTS + "100", expectedMessage);
        assertParseFailure(parser, PREFIX_INDEX + "-1 " + PREFIX_POINTS + "100", expectedMessage);
        assertParseFailure(parser, PREFIX_INDEX + "abc " + PREFIX_POINTS + "100", expectedMessage);

        // Invalid points (not a number)
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_POINTS + "abc", expectedMessage);

        // Invalid points (negative)
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_POINTS + "-10", expectedMessage);

        // Invalid points (zero)
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_POINTS + "0", expectedMessage);
    }

    @Test
    public void parse_extraneousPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsAddCommand.MESSAGE_USAGE);

        // Non-empty preamble
        assertParseFailure(parser, "some random text " + PREFIX_INDEX + "1 " + PREFIX_POINTS + "100",
                expectedMessage);
    }

    @Test
    public void parse_extraneousArgument_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsAddCommand.MESSAGE_USAGE);

        // Extra arguments
        assertParseFailure(parser, PREFIX_INDEX + "1 " + PREFIX_POINTS + "100 extraArgument",
                expectedMessage);
    }
}
