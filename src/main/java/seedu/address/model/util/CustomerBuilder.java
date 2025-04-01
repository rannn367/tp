package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

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
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder extends PersonBuilder<Customer, CustomerBuilder> {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "Regular customer";
    public static final String DEFAULT_CUSTOMER_ID = "C1001";
    public static final String DEFAULT_REWARD_POINTS = "100";
    public static final String DEFAULT_VISIT_COUNT = "5";
    public static final String DEFAULT_FAVOURITE_ITEM = "Latte";
    public static final String DEFAULT_TOTAL_SPENT = "75.50";
    public static final String DEFAULT_RATING = "5";

    private final CustomerId customerId;
    private final RewardPoints rewardPoints;
    private final VisitCount visitCount;
    private final FavouriteItem favouriteItem;
    private final TotalSpent totalSpent;

    public CustomerBuilder() {
        super(new Name(DEFAULT_NAME),
                new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL),
                new Address(DEFAULT_ADDRESS),
                new Remark(DEFAULT_REMARK),
                new HashSet<>());
        this.customerId = new CustomerId(DEFAULT_CUSTOMER_ID);
        this.rewardPoints = new RewardPoints(DEFAULT_REWARD_POINTS);
        this.visitCount = new VisitCount(DEFAULT_VISIT_COUNT);
        this.favouriteItem = new FavouriteItem(DEFAULT_FAVOURITE_ITEM);
        this.totalSpent = new TotalSpent(DEFAULT_TOTAL_SPENT);
    }

    private CustomerBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                            CustomerId customerId, RewardPoints rewardPoints, VisitCount visitCount, 
                            FavouriteItem favouriteItem, TotalSpent totalSpent) {
        super(name, phone, email, address, remark, tags);
        this.customerId = customerId;
        this.rewardPoints = rewardPoints;
        this.visitCount = visitCount;
        this.favouriteItem = favouriteItem;
        this.totalSpent = totalSpent;
    }

    public CustomerBuilder(Customer customer) {
        super(customer.getName(), customer.getPhone(), customer.getEmail(), customer.getAddress(), customer.getRemark(), customer.getTags());
        this.customerId = customer.getCustomerId();
        this.rewardPoints = customer.getRewardPoints();
        this.visitCount = customer.getVisitCount();
        this.favouriteItem = customer.getFavouriteItem();
        this.totalSpent = customer.getTotalSpent();
    }

    @Override
    protected CustomerBuilder createBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        return new CustomerBuilder(name, phone, email, address, remark, tags, this.customerId, this.rewardPoints, 
                                    this.visitCount, this.favouriteItem, this.totalSpent);
    }

    protected CustomerBuilder createBuilder(CustomerId customerId, RewardPoints rewardPoints, VisitCount visitCount, 
                                            FavouriteItem favouriteItem, TotalSpent totalSpent) {
        return new CustomerBuilder(this.name, this.phone, this.email, this.address, this.remark, this.tags, 
                                    customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
    }

    public CustomerBuilder withCustomerId(String customerId) {
        return createBuilder(new CustomerId(customerId), this.rewardPoints, this.visitCount, this.favouriteItem, this.totalSpent);
    }

    public CustomerBuilder withCustomerId(CustomerId customerId) {
        return createBuilder(customerId, this.rewardPoints, this.visitCount, this.favouriteItem, this.totalSpent);
    }
    
    public CustomerBuilder withRewardPoints(String rewardPoints) {
        return createBuilder(this.customerId, new RewardPoints(rewardPoints), this.visitCount, this.favouriteItem, this.totalSpent);
    }
    
    public CustomerBuilder withRewardPoints(RewardPoints rewardPoints) {
        return createBuilder(this.customerId, rewardPoints, this.visitCount, this.favouriteItem, this.totalSpent);
    }

    
    public CustomerBuilder withVisitCount(String visitCount) {
        return createBuilder(this.customerId, this.rewardPoints, new VisitCount(visitCount), this.favouriteItem, this.totalSpent);
    }

    public CustomerBuilder withVisitCount(VisitCount visitCount) {
        return createBuilder(this.customerId, this.rewardPoints, visitCount, this.favouriteItem, this.totalSpent);
    }
    
    public CustomerBuilder withFavouriteItem(String favouriteItem) {
        return createBuilder(this.customerId, this.rewardPoints, this.visitCount, new FavouriteItem(favouriteItem), this.totalSpent);
    }
    
    public CustomerBuilder withFavouriteItem(FavouriteItem favouriteItem) {
        return createBuilder(this.customerId, this.rewardPoints, this.visitCount, favouriteItem, this.totalSpent);
    }

    public CustomerBuilder withTotalSpent(String totalSpent) {
        return createBuilder(this.customerId, this.rewardPoints, this.visitCount, this.favouriteItem, new TotalSpent(totalSpent));
    }
    
    public CustomerBuilder withTotalSpent(TotalSpent totalSpent) {
        return createBuilder(this.customerId, this.rewardPoints, this.visitCount, this.favouriteItem, totalSpent);
    }
    /**
     * Builds the customer with the information altogether.
     */
    public Customer build() {
        return new Customer(name, phone, email, address, remark, tags,
                customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
    }
}
