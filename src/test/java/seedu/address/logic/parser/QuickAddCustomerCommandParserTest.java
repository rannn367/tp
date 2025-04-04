package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.QuickAddCustomerCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;

public class QuickAddCustomerCommandParserTest {
    private QuickAddCustomerCommandParser parser = new QuickAddCustomerCommandParser();

    @Test
    public void parse_valid_success() {
        String userInput = "C1230:John:91234567"; // Simulating fast-add format

        // Define the expected QuickAddCustomerCommand with the correct default values
        QuickAddCustomerCommand expectedCommand = new QuickAddCustomerCommand(
                new Customer(
                        new Name("John"),
                        new Phone("91234567"),
                        new Email("default@gmail.com"),
                        new Address("empty"),
                        new Remark(""),
                        Set.of(),
                        new CustomerId("C1230"),
                        new RewardPoints("0"),
                        new VisitCount("1"),
                        new FavouriteItem("unknown"),
                        new TotalSpent("10")
                )
        );

        // Assert that the parsed command matches the expected command
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String userInput = "C0101:John"; // Missing phone number
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidAddCustomerFormat_throwsParseException() {
        String userInput = "addcustomer n/John p/92012012 e/john@example.com a/123 Street cid/C0101";
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddCustomerCommand.MESSAGE_USAGE));
    }
}
