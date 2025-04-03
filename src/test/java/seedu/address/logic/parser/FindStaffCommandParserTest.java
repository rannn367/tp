package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS_WORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERFORMANCE_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAFF_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindStaffCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class FindStaffCommandParserTest {

    /**
     * This is needed because we can't create the exact predicate the parser creates for combined searches.
     */
    private static final Predicate<Person> defaultPred = person -> true;

    private FindStaffCommandParser parser = new FindStaffCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindStaffCommand() {
        // name search
        FindStaffCommand expectedNameCommand = new FindStaffCommand(defaultPred);
        assertParseSuccess(parser, " " + PREFIX_NAME + "John", expectedNameCommand);

        // multiple prefixes
        String userInput = " " + PREFIX_NAME + "John " + PREFIX_ROLE + "Barista";
        assertParseSuccess(parser, userInput,
                new FindStaffCommand(defaultPred));

        // all staff attributes
        String complexInput = " " + PREFIX_NAME + "John "
                + PREFIX_PHONE + "98765432 "
                + PREFIX_EMAIL + "john@example.com "
                + PREFIX_ADDRESS + "Clementi "
                + PREFIX_STAFF_ID + "S1234 "
                + PREFIX_ROLE + "Barista "
                + PREFIX_SHIFT_TIMING + "9am-5pm "
                + PREFIX_HOURS_WORKED + "40 "
                + PREFIX_PERFORMANCE_RATING + "4.5 "
                + PREFIX_TAG + "fullTime";
        assertParseSuccess(parser, complexInput,
                new FindStaffCommand(defaultPred));
    }

    @Test
    public void parse_allFlag_returnsFindStaffCommand() {
        // /all flag
        FindStaffCommand expectedAllCommand =
                new FindStaffCommand(Model.PREDICATE_SHOW_ALL_STAFFS);
        assertParseSuccess(parser, " " + PREFIX_ALL, expectedAllCommand);
    }

    @Test
    public void parse_invalidAllFlag_throwsParseException() {
        // /all with value
        assertParseFailure(parser, " " + PREFIX_ALL + "value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_INVALID_ALL));

        // /all with other parameters
        assertParseFailure(parser, " " + PREFIX_ALL + " " + PREFIX_NAME + "John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_INVALID_ALL));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate name prefix
        assertParseFailure(parser, " " + PREFIX_NAME + "John " + PREFIX_NAME + "Alice",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }
}
