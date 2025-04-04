package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCustomerCommandParserTest {

    private FindCustomerCommandParser parser = new FindCustomerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser,
                "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCustomerCommand() {
        // no leading and trailing whitespaces
        FindCustomerCommand expectedFindCustomerCommand =
                new FindCustomerCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCustomerCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCustomerCommand);
    }
}
