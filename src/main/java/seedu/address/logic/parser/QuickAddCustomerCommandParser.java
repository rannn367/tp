package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.address.logic.commands.QuickAddCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new QuickAddCustomerCommand object
 */
public class QuickAddCustomerCommandParser implements Parser<QuickAddCustomerCommand> {
    private static final String DEFAULT_EMAIL = "default@gmail.com";
    private static final String DEFAULT_ADDRESS = "empty";
    private static final String DEFAULT_REMARK = "";
    private static final String DEFAULT_VISIT_COUNT = "1";
    private static final String DEFAULT_TOTAL_SPENT = "10";
    private static final String DEFAULT_REWARD_POINTS = "0";
    private static final String DEFAULT_FAVOURITE_ITEM = "unknown";

    /**
     * Parses the given {@code String} of arguments in the context of the QuickAddCustomerCommand
     * and returns an QuickAddCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public QuickAddCustomerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!trimmedArgs.matches("^[^\\s:]+:.+:[^\\s:]+$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddCustomerCommand.MESSAGE_USAGE));
        }

        String[] customerInfo = trimmedArgs.split(":");

        if (customerInfo.length != 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddCustomerCommand.MESSAGE_USAGE));
        }

        CustomerId customerId = ParserUtil.parseCustomerId(customerInfo[0]);
        Name name = ParserUtil.parseName(customerInfo[1]);
        Phone phone = ParserUtil.parsePhone(customerInfo[2]);

        Email email = new Email(DEFAULT_EMAIL);
        Address address = new Address(DEFAULT_ADDRESS);
        Remark remark = new Remark(DEFAULT_REMARK);
        Set<Tag> tagList = Set.of();

        RewardPoints rewardPoints = new RewardPoints(DEFAULT_REWARD_POINTS);
        VisitCount visitCount = new VisitCount(DEFAULT_VISIT_COUNT);
        FavouriteItem favouriteItem = new FavouriteItem(DEFAULT_FAVOURITE_ITEM);
        TotalSpent totalSpent = new TotalSpent(DEFAULT_TOTAL_SPENT);

        Customer customer = new Customer(name, phone, email, address, remark, tagList,
                customerId, rewardPoints, visitCount, favouriteItem, totalSpent);

        return new QuickAddCustomerCommand(customer);
    }
}
