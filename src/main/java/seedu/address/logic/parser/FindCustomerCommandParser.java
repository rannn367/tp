package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
import seedu.address.model.person.predicates.AddressPredicate;
import seedu.address.model.person.predicates.CombinedPredicate;
import seedu.address.model.person.predicates.CustomerIdPredicate;
import seedu.address.model.person.predicates.EmailPredicate;
import seedu.address.model.person.predicates.FavouriteItemPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhonePredicate;
import seedu.address.model.person.predicates.RewardPointsPredicate;
import seedu.address.model.person.predicates.TagsContainPredicate;
import seedu.address.model.person.predicates.TotalSpentPredicate;
import seedu.address.model.person.predicates.VisitCountPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCustomerCommand object
 */
public class FindCustomerCommandParser implements Parser<FindCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCustomerCommand
     * and returns a FindCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCustomerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_CUSTOMER_ID, PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT,
                        PREFIX_FAVOURITE_ITEM, PREFIX_TOTAL_SPENT);

        if (argMultimap.getValue(PREFIX_ALL).isPresent()) {
            String allValue = argMultimap.getValue(PREFIX_ALL).get().trim();
            if (!allValue.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_INVALID_ALL));
            }
            if (argMultimap.containsOnlyPrefix(PREFIX_ALL)) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_INVALID_ALL));
            }
            return new FindCustomerCommand(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_CUSTOMER_ID, PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_ITEM, PREFIX_TOTAL_SPENT);

        // Create a set of predicates to be combined
        Set<Predicate<Person>> predicateSet = new HashSet<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                    Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+")));
            predicateSet.add(namePredicate);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneValue = argMultimap.getValue(PREFIX_PHONE).get();
            Phone searchPhone = ParserUtil.parsePhone(phoneValue);
            PhonePredicate phonePredicate = new PhonePredicate(searchPhone);
            predicateSet.add(phonePredicate);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailValue = argMultimap.getValue(PREFIX_EMAIL).get();
            Email searchEmail = ParserUtil.parseEmail(emailValue);
            EmailPredicate emailPredicate = new EmailPredicate(searchEmail);
            predicateSet.add(emailPredicate);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String addressValue = argMultimap.getValue(PREFIX_ADDRESS).get();
            Address searchAddress = ParserUtil.parseAddress(addressValue);
            AddressPredicate addressPredicate = new AddressPredicate(searchAddress);
            predicateSet.add(addressPredicate);
        }
        if (argMultimap.getValue(PREFIX_CUSTOMER_ID).isPresent()) {
            String customerIdValue = argMultimap.getValue(PREFIX_CUSTOMER_ID).get();
            CustomerId searchCustomerId = ParserUtil.parseCustomerId(customerIdValue);
            CustomerIdPredicate customerIdPredicate = new CustomerIdPredicate(searchCustomerId);
            predicateSet.add(customerIdPredicate);
        }
        if (argMultimap.getValue(PREFIX_REWARD_POINTS).isPresent()) {
            String rewardPointsValue = argMultimap.getValue(PREFIX_REWARD_POINTS).get();
            RewardPoints searchRewardPoints = ParserUtil.parseRewardPoints(rewardPointsValue);
            RewardPointsPredicate rewardPointsPredicate = new RewardPointsPredicate(searchRewardPoints);
            predicateSet.add(rewardPointsPredicate);
        }
        if (argMultimap.getValue(PREFIX_VISIT_COUNT).isPresent()) {
            String visitCountValue = argMultimap.getValue(PREFIX_VISIT_COUNT).get();
            VisitCount searchVisitCount = ParserUtil.parseVisitCount(visitCountValue);
            VisitCountPredicate visitCountPredicate = new VisitCountPredicate(searchVisitCount);
            predicateSet.add(visitCountPredicate);
        }
        if (argMultimap.getValue(PREFIX_FAVOURITE_ITEM).isPresent()) {
            String favouriteItemValue = argMultimap.getValue(PREFIX_FAVOURITE_ITEM).get();
            FavouriteItem searchFavouriteItem = ParserUtil.parseFavouriteItem(favouriteItemValue);
            FavouriteItemPredicate favouriteItemPredicate = new FavouriteItemPredicate(searchFavouriteItem);
            predicateSet.add(favouriteItemPredicate);
        }
        if (argMultimap.getValue(PREFIX_TOTAL_SPENT).isPresent()) {
            String totalSpentValue = argMultimap.getValue(PREFIX_TOTAL_SPENT).get();
            TotalSpent searchTotalSpent = ParserUtil.parseTotalSpent(totalSpentValue);
            TotalSpentPredicate totalSpentPredicate = new TotalSpentPredicate(searchTotalSpent);
            predicateSet.add(totalSpentPredicate);
        }
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 0) {
            Collection<String> tagSet = argMultimap.getAllValues(PREFIX_TAG);
            if (tagSet.size() == 1 && tagSet.contains("")) {
                tagSet = Collections.emptySet();
            }

            Set<Tag> tags = ParserUtil.parseTags(tagSet);
            TagsContainPredicate tagPredicate = new TagsContainPredicate(tags);
            predicateSet.add(tagPredicate);
        }

        if (predicateSet.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
        }

        // Use the CombinedPredicate class to combine predicates in an order-insensitive way
        CombinedPredicate combinedPredicate = new CombinedPredicate(predicateSet);

        return new FindCustomerCommand(combinedPredicate);
    }
}
