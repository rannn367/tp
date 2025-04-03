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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.SameAddressPredicate;
import seedu.address.model.person.predicates.SameCustomerIdPredicate;
import seedu.address.model.person.predicates.SameEmailPredicate;
import seedu.address.model.person.predicates.SameFavouriteItemPredicate;
import seedu.address.model.person.predicates.SameFieldsPredicate;
import seedu.address.model.person.predicates.SamePhonePredicate;
import seedu.address.model.person.predicates.SameRewardPointsPredicate;
import seedu.address.model.person.predicates.SameTotalSpentPredicate;
import seedu.address.model.person.predicates.SameVisitCountPredicate;
import seedu.address.model.person.predicates.TagsContainPredicate;
import seedu.address.model.tag.Tag;

public class FindCustomerCommandParserTest {

    private FindCustomerCommandParser parser = new FindCustomerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCustomerCommand() {
        // name search
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"), true);
        Set<Predicate<Person>> namePredicateSet = new HashSet<>(Collections.singletonList(namePredicate));
        SameFieldsPredicate namePredicateCombined = new SameFieldsPredicate(namePredicateSet);
        FindCustomerCommand expectedNameCommand = new FindCustomerCommand(namePredicateCombined);
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice", expectedNameCommand);

        // multiple prefixes
        NameContainsKeywordsPredicate namePredicate2 = new NameContainsKeywordsPredicate(Arrays.asList("Alice"), true);
        SameVisitCountPredicate visitCountPredicate = new SameVisitCountPredicate(new VisitCount("5"));
        Set<Predicate<Person>> multiPredicateSet = new HashSet<>();
        multiPredicateSet.add(namePredicate2);
        multiPredicateSet.add(visitCountPredicate);
        SameFieldsPredicate multiPredicateCombined = new SameFieldsPredicate(multiPredicateSet);

        String userInput = " " + PREFIX_NAME + "Alice " + PREFIX_VISIT_COUNT + "5";
        assertParseSuccess(parser, userInput, new FindCustomerCommand(multiPredicateCombined));
    }

    @Test
    public void parse_complexInput_returnsFindCustomerCommand() {
        // all customer attributes
        Set<Predicate<Person>> allPredicateSet = new HashSet<>();
        allPredicateSet.add(new NameContainsKeywordsPredicate(Arrays.asList("John"), true));
        allPredicateSet.add(new SamePhonePredicate(new Phone("98765432")));
        allPredicateSet.add(new SameEmailPredicate(new Email("john@example.com")));
        allPredicateSet.add(new SameAddressPredicate(new Address("Clementi")));
        allPredicateSet.add(new SameCustomerIdPredicate(new CustomerId("C001")));
        allPredicateSet.add(new SameRewardPointsPredicate(new RewardPoints("150")));
        allPredicateSet.add(new SameVisitCountPredicate(new VisitCount("8")));
        allPredicateSet.add(new SameFavouriteItemPredicate(new FavouriteItem("Cappuccino")));
        allPredicateSet.add(new SameTotalSpentPredicate(new TotalSpent("120")));

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("regular"));
        allPredicateSet.add(new TagsContainPredicate(tagSet));

        SameFieldsPredicate allPredicatesCombined = new SameFieldsPredicate(allPredicateSet);

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
        assertParseSuccess(parser, complexInput, new FindCustomerCommand(allPredicatesCombined));
    }

    @Test
    public void parse_allFlag_returnsFindCustomerCommand() {
        // all prefix
        FindCustomerCommand expectedAllCommand =
                new FindCustomerCommand(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        assertParseSuccess(parser, " " + PREFIX_ALL + "true", expectedAllCommand);
    }

    @Test
    public void parse_invalidAllFlag_throwsParseException() {
        // all prefix with incorrect value
        assertParseFailure(parser, " " + PREFIX_ALL + "not-true",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_INVALID_ALL));

        // all/true with other parameters
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
