package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.QuickAddStaffCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;

public class QuickAddStaffCommandParserTest {
    private QuickAddStaffCommandParser parser = new QuickAddStaffCommandParser();

    @Test
    public void parse_validFastAdd_success() {
        String userInput = "S0101:John:92012012"; // Simulating fast-add format

        // Define the expected AddStaffCommand with the correct default values
        QuickAddStaffCommand expectedCommand = new QuickAddStaffCommand(
                new Staff(
                        new Name("John"),
                        new Phone("92012012"),
                        new Email("default@gmail.com"),
                        new Address("empty"),
                        new Remark(""),
                        Set.of(),
                        new StaffId("S0101"),
                        new Role("Full Time worker"),
                        new ShiftTiming("9am-5pm"),
                        new HoursWorked("0"),
                        new PerformanceRating("0")
                )
        );

        // Assert that the parsed command matches the expected command
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String userInput = "S0101:John"; // Missing phone number
        assertParseFailure(parser, userInput, 
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddStaffCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidAddStaffFormat_throwsParseException() {
        String userInput = "addstaff n/John p/92012012 e/john@example.com a/123 Street sid/S0101 role/Barista shift/9am-5pm hours/40 rating/4.5";
        assertParseFailure(parser, userInput, 
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddStaffCommand.MESSAGE_USAGE));
    }
}
