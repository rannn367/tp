package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindStaffCommand object
 */
public class FindStaffCommandParser implements Parser<FindStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindStaffCommand
     * and returns a FindStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindStaffCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING,
                        PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING);

        if (argMultimap.getValue(PREFIX_ALL).isPresent()) {
            String allValue = argMultimap.getValue(PREFIX_ALL).get().trim();
            if (!allValue.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_INVALID_ALL));
            }
            if (argMultimap.containsOnlyPrefix(PREFIX_ALL)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_INVALID_ALL));
            }
            return new FindStaffCommand(Model.PREDICATE_SHOW_ALL_STAFFS);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING, PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING);

        // Create a collection of predicates to be combined
        java.util.List<Predicate<Person>> predicates = new java.util.ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(
                    Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+")));
            predicates.add(namePredicate);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneValue = argMultimap.getValue(PREFIX_PHONE).get();
            Phone searchPhone = ParserUtil.parsePhone(phoneValue);
            Predicate<Person> phonePredicate = person -> person.getPhone().equals(searchPhone);
            predicates.add(phonePredicate);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailValue = argMultimap.getValue(PREFIX_EMAIL).get();
            Email searchEmail = ParserUtil.parseEmail(emailValue);
            Predicate<Person> emailPredicate = person -> person.getEmail().equals(searchEmail);
            predicates.add(emailPredicate);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String addressValue = argMultimap.getValue(PREFIX_ADDRESS).get();
            Address searchAddress = ParserUtil.parseAddress(addressValue);
            Predicate<Person> addressPredicate = person -> person.getAddress().equals(searchAddress);
            predicates.add(addressPredicate);
        }
        if (argMultimap.getValue(PREFIX_STAFF_ID).isPresent()) {
            String staffIdValue = argMultimap.getValue(PREFIX_STAFF_ID).get();
            StaffId searchStaffId = ParserUtil.parseStaffId(staffIdValue);
            Predicate<Person> staffIdPredicate = person -> person instanceof Staff
                && ((Staff) person).getStaffId().equals(searchStaffId);
            predicates.add(staffIdPredicate);
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String roleValue = argMultimap.getValue(PREFIX_ROLE).get();
            Role searchRole = ParserUtil.parseRole(roleValue);
            Predicate<Person> rolePredicate = person -> person instanceof Staff
                && ((Staff) person).getRole().equals(searchRole);
            predicates.add(rolePredicate);
        }
        if (argMultimap.getValue(PREFIX_SHIFT_TIMING).isPresent()) {
            String shiftTimingValue = argMultimap.getValue(PREFIX_SHIFT_TIMING).get();
            ShiftTiming searchShiftTiming = ParserUtil.parseShiftTiming(shiftTimingValue);
            Predicate<Person> shiftTimingPredicate = person -> person instanceof Staff
                && ((Staff) person).getShiftTiming().equals(searchShiftTiming);
            predicates.add(shiftTimingPredicate);
        }
        if (argMultimap.getValue(PREFIX_HOURS_WORKED).isPresent()) {
            String hoursWorkedValue = argMultimap.getValue(PREFIX_HOURS_WORKED).get();
            HoursWorked searchHoursWorked = ParserUtil.parseHoursWorked(hoursWorkedValue);
            Predicate<Person> hoursWorkedPredicate = person -> person instanceof Staff
                && ((Staff) person).getHoursWorked().equals(searchHoursWorked);
            predicates.add(hoursWorkedPredicate);
        }
        if (argMultimap.getValue(PREFIX_PERFORMANCE_RATING).isPresent()) {
            String performanceRatingValue = argMultimap.getValue(PREFIX_PERFORMANCE_RATING).get();
            PerformanceRating searchPerformanceRating =
                ParserUtil.parsePerformanceRating(performanceRatingValue);
            Predicate<Person> performanceRatingPredicate = person -> person instanceof Staff
                && ((Staff) person).getPerformanceRating().equals(searchPerformanceRating);
            predicates.add(performanceRatingPredicate);
        }
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 0) {
            Collection<String> tagSet = argMultimap.getAllValues(PREFIX_TAG);
            if (tagSet.size() == 1 && tagSet.contains("")) {
                tagSet = Collections.emptySet();
            }

            Set<Tag> tags = ParserUtil.parseTags(tagSet);
            Predicate<Person> tagPredicate = person -> person.getTags().containsAll(tags);
            predicates.add(tagPredicate);
        }

        if (predicates.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStaffCommand.MESSAGE_USAGE));
        }

        // Combine all predicates with AND logic (person must match all criteria)
        Predicate<Person> combinedPredicate = predicates.stream()
                .reduce(person -> true, Predicate::and);

        return new FindStaffCommand(combinedPredicate);
    }
}
