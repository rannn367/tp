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
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
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
        if (argMultimap.getValue(PREFIX_CUSTOMER_ID).isPresent()) {
            String customerIdValue = argMultimap.getValue(PREFIX_CUSTOMER_ID).get();
            CustomerId searchCustomerId = ParserUtil.parseCustomerId(customerIdValue);
            Predicate<Person> customerIdPredicate = person -> person instanceof Customer
                && ((Customer) person).getCustomerId().equals(searchCustomerId);
            predicates.add(customerIdPredicate);
        }
        if (argMultimap.getValue(PREFIX_REWARD_POINTS).isPresent()) {
            String rewardPointsValue = argMultimap.getValue(PREFIX_REWARD_POINTS).get();
            RewardPoints searchRewardPoints = ParserUtil.parseRewardPoints(rewardPointsValue);
            Predicate<Person> rewardPointsPredicate = person -> person instanceof Customer
                && ((Customer) person).getRewardPoints().equals(searchRewardPoints);
            predicates.add(rewardPointsPredicate);
        }
        if (argMultimap.getValue(PREFIX_VISIT_COUNT).isPresent()) {
            String visitCountValue = argMultimap.getValue(PREFIX_VISIT_COUNT).get();
            VisitCount searchVisitCount = ParserUtil.parseVisitCount(visitCountValue);
            Predicate<Person> visitCountPredicate = person -> person instanceof Customer
                && ((Customer) person).getVisitCount().equals(searchVisitCount);
            predicates.add(visitCountPredicate);
        }
        if (argMultimap.getValue(PREFIX_FAVOURITE_ITEM).isPresent()) {
            String favouriteItemValue = argMultimap.getValue(PREFIX_FAVOURITE_ITEM).get();
            FavouriteItem searchFavouriteItem = ParserUtil.parseFavouriteItem(favouriteItemValue);
            Predicate<Person> favouriteItemPredicate = person -> person instanceof Customer
                && ((Customer) person).getFavouriteItem().equals(searchFavouriteItem);
            predicates.add(favouriteItemPredicate);
        }
        if (argMultimap.getValue(PREFIX_TOTAL_SPENT).isPresent()) {
            String totalSpentValue = argMultimap.getValue(PREFIX_TOTAL_SPENT).get();
            TotalSpent searchTotalSpent = ParserUtil.parseTotalSpent(totalSpentValue);
            Predicate<Person> totalSpentPredicate = person -> person instanceof Customer
                && ((Customer) person).getTotalSpent().equals(searchTotalSpent);
            predicates.add(totalSpentPredicate);
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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
        }

        // Combine all predicates with AND logic (person must match all criteria)
        Predicate<Person> combinedPredicate = predicates.stream()
                .reduce(person -> true, Predicate::and);

        return new FindCustomerCommand(combinedPredicate);
    }
}
