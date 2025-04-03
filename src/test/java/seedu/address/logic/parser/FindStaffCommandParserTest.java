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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindStaffCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.StaffId;
import seedu.address.model.person.predicates.AddressPredicate;
import seedu.address.model.person.predicates.CombinedPredicate;
import seedu.address.model.person.predicates.EmailPredicate;
import seedu.address.model.person.predicates.HoursWorkedPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PerformanceRatingPredicate;
import seedu.address.model.person.predicates.PhonePredicate;
import seedu.address.model.person.predicates.RolePredicate;
import seedu.address.model.person.predicates.ShiftTimingPredicate;
import seedu.address.model.person.predicates.StaffIdPredicate;
import seedu.address.model.person.predicates.TagsContainPredicate;
import seedu.address.model.tag.Tag;

public class FindStaffCommandParserTest {

    private FindStaffCommandParser parser = new FindStaffCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindStaffCommand() {
        // name search
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("John"));
        Set<Predicate<Person>> namePredicateSet = new HashSet<>(Collections.singletonList(namePredicate));
        CombinedPredicate namePredicateCombined = new CombinedPredicate(namePredicateSet);
        FindStaffCommand expectedNameCommand = new FindStaffCommand(namePredicateCombined);
        assertParseSuccess(parser, " " + PREFIX_NAME + "John", expectedNameCommand);

        // multiple prefixes
        NameContainsKeywordsPredicate namePredicate2 = new NameContainsKeywordsPredicate(Arrays.asList("John"));
        RolePredicate rolePredicate = new RolePredicate(new Role("Barista"));
        Set<Predicate<Person>> multiPredicateSet = new HashSet<>();
        multiPredicateSet.add(namePredicate2);
        multiPredicateSet.add(rolePredicate);
        CombinedPredicate multiPredicateCombined = new CombinedPredicate(multiPredicateSet);

        String userInput = " " + PREFIX_NAME + "John " + PREFIX_ROLE + "Barista";
        assertParseSuccess(parser, userInput, new FindStaffCommand(multiPredicateCombined));
    }

    @Test
    public void parse_complexInput_returnsFindStaffCommand() {
        // all staff attributes
        Set<Predicate<Person>> allPredicateSet = new HashSet<>();
        allPredicateSet.add(new NameContainsKeywordsPredicate(Arrays.asList("John")));
        allPredicateSet.add(new PhonePredicate(new Phone("98765432")));
        allPredicateSet.add(new EmailPredicate(new Email("john@example.com")));
        allPredicateSet.add(new AddressPredicate(new Address("Clementi")));
        allPredicateSet.add(new StaffIdPredicate(new StaffId("S1234")));
        allPredicateSet.add(new RolePredicate(new Role("Barista")));
        allPredicateSet.add(new ShiftTimingPredicate(new ShiftTiming("9am-5pm")));
        allPredicateSet.add(new HoursWorkedPredicate(new HoursWorked("40")));
        allPredicateSet.add(new PerformanceRatingPredicate(new PerformanceRating("4.5")));

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("fullTime"));
        allPredicateSet.add(new TagsContainPredicate(tagSet));

        CombinedPredicate allPredicatesCombined = new CombinedPredicate(allPredicateSet);

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
        assertParseSuccess(parser, complexInput, new FindStaffCommand(allPredicatesCombined));
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
