package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindStaffCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindStaffCommandParserTest {

    private FindStaffCommandParser parser = new FindStaffCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser,
                "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindStaffCommand() {
        // no leading and trailing whitespaces
        FindStaffCommand expectedFindStaffCommand =
                new FindStaffCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindStaffCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindStaffCommand);
    }

}
