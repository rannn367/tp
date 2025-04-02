package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
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
import seedu.address.model.util.CustomerBuilder;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final Customer JAMES = new CustomerBuilder().withName(new Name("James Wilson"))
            .withCustomerId(new CustomerId("C10012"))
            .withAddress(new Address("45 Orchard Road, #12-34"))
            .withEmail(new Email("james@example.com"))
            .withPhone(new Phone("91234789"))
            .withRemark(new Remark("Prefers decaf"))
            .withRewardPoints(new RewardPoints("200"))
            .withVisitCount(new VisitCount("12"))
            .withFavouriteItem(new FavouriteItem("Flat White"))
            .withTotalSpent(new TotalSpent("155.75"))
            .withTags(Set.of(new Tag("regular"), new Tag("student"))).build();

    public static final Customer OLIVIA = new CustomerBuilder().withName(new Name("Olivia Chen"))
            .withCustomerId(new CustomerId("C12345"))
            .withAddress(new Address("88 Sunset Drive, #05-10"))
            .withEmail(new Email("olivia@example.com"))
            .withPhone(new Phone("82345678"))
            .withRemark(new Remark("Birthday in March"))
            .withRewardPoints(new RewardPoints("75"))
            .withVisitCount(new VisitCount("3"))
            .withFavouriteItem(new FavouriteItem("Green Tea Latte"))
            .withTotalSpent(new TotalSpent("42.90"))
            .withTags(Set.of(new Tag("new"), new Tag("student"))).build();

    private TypicalCustomers() {} // prevents instantiation

    /**
     * Returns a list of all typical customers for testing.
     */
    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(JAMES, OLIVIA));
    }

    /**
     * Returns a new {@code AddressBook} with all the typical customers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Customer customer : getTypicalCustomers()) {
            ab.addCustomer(customer);
        }
        return ab;
    }
}
