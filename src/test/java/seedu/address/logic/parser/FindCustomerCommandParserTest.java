package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class FindCustomerCommandParserTest {

    private static final Predicate<Person> defaultPred = person -> true;

    private FindCustomerCommandParser parser = new FindCustomerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCustomerCommand() {
        // name search
        FindCustomerCommand expectedNameCommand = new FindCustomerCommand(defaultPred);
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice", expectedNameCommand);

        // multiple prefixes
        String userInput = " " + PREFIX_NAME + "Alice " + PREFIX_VISIT_COUNT + "5";
        assertParseSuccess(parser, userInput,
                new FindCustomerCommand(defaultPred));

        // all customer attributes
        String complexInput = " " + PREFIX_NAME + "John "
                + PREFIX_PHONE + "98765432 "
                + PREFIX_EMAIL + "john@example.com "
                + PREFIX_ADDRESS + "Clementi "
                + PREFIX_CUSTOMER_ID + "C001 "
                + PREFIX_REWARD_POINTS + "150 "
                + PREFIX_VISIT_COUNT + "8 "
                + PREFIX_FAVOURITE_ITEM + "Cappuccino "
                + PREFIX_TOTAL_SPENT + "120 "
                + PREFIX_TAG + "regular";
        assertParseSuccess(parser, complexInput,
                new FindCustomerCommand(defaultPred));
    }

    @Test
    public void parse_allFlag_returnsFindCustomerCommand() {
        // /all flag
        FindCustomerCommand expectedAllCommand =
                new FindCustomerCommand(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        assertParseSuccess(parser, " " + PREFIX_ALL, expectedAllCommand);
    }

    @Test
    public void parse_invalidAllFlag_throwsParseException() {
        // /all with value
        assertParseFailure(parser, " " + PREFIX_ALL + "value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_INVALID_ALL));

        // /all with other parameters
        assertParseFailure(parser, " " + PREFIX_ALL + " " + PREFIX_NAME + "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_INVALID_ALL));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate name prefix
        assertParseFailure(parser, " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }
}
