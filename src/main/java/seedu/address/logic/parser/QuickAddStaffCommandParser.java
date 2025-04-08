package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.address.logic.commands.QuickAddStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new QuickAddStaffCommand object
 */
public class QuickAddStaffCommandParser implements Parser<QuickAddStaffCommand> {

    private static final String DEFAULT_EMAIL = "default@gmail.com";
    private static final String DEFAULT_ADDRESS = "empty";
    private static final String DEFAULT_REMARK = "From QuickAddStaff. Edit with staffedit.";
    private static final String DEFAULT_ROLE = "empty";
    private static final String DEFAULT_SHIFT_TIMING = "empty";
    private static final String DEFAULT_HOURS_WORKED = "0";
    private static final String DEFAULT_PERFORMANCE_RATING = "0";

    /**
     * Parses the given {@code String} of arguments in the context of the QuickAddStaffCommand
     * and returns an QuickAddStaffCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public QuickAddStaffCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!trimmedArgs.matches("^[^\\s:]+:.+:[^\\s:]+$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddStaffCommand.MESSAGE_USAGE));
        }

        String[] staffInfo = trimmedArgs.split(":");

        if (staffInfo.length != 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddStaffCommand.MESSAGE_USAGE));
        }

        StaffId staffId = ParserUtil.parseStaffId(staffInfo[0]);
        Name name = ParserUtil.parseName(staffInfo[1]);
        Phone phone = ParserUtil.parsePhone(staffInfo[2]);

        Email email = new Email(DEFAULT_EMAIL);
        Address address = new Address(DEFAULT_ADDRESS);
        Remark remark = new Remark(DEFAULT_REMARK);
        Set<Tag> tagList = Set.of();

        Role role = new Role(DEFAULT_ROLE);
        ShiftTiming shiftTiming = new ShiftTiming(DEFAULT_SHIFT_TIMING);
        HoursWorked hoursWorked = new HoursWorked(DEFAULT_HOURS_WORKED);
        PerformanceRating performanceRating = new PerformanceRating(DEFAULT_PERFORMANCE_RATING);

        Staff staff = new Staff(name, phone, email, address, remark, tagList,
                staffId, role, shiftTiming, hoursWorked, performanceRating);

        return new QuickAddStaffCommand(staff);
    }
}
