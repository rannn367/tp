package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * A utility class to help with building {@code Customer} objects.
 * Provides methods to set various attributes of a {@code Customer}.
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
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    protected final CustomerId customerId;
    protected final RewardPoints rewardPoints;
    protected final VisitCount visitCount;
    protected final FavouriteItem favouriteItem;
    protected final TotalSpent totalSpent;

    /**
     * Constructs a {@code CustomerBuilder} with default values.
     */
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

    /**
     * Constructs a {@code CustomerBuilder} with the specified attributes.
     */
    protected CustomerBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
            CustomerId customerId, RewardPoints rewardPoints, VisitCount visitCount,
            FavouriteItem favouriteItem, TotalSpent totalSpent) {
        super(name, phone, email, address, remark, tags);
        this.customerId = customerId;
        this.rewardPoints = rewardPoints;
        this.visitCount = visitCount;
        this.favouriteItem = favouriteItem;
        this.totalSpent = totalSpent;
    }

    /**
     * Constructs a {@code CustomerBuilder} by copying attributes from an existing
     * {@code Customer}.
     *
     * @param customer The customer to copy attributes from.
     */
    public CustomerBuilder(Customer customer) {
        super(
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getRemark(),
                customer.getTags());
        this.customerId = customer.getCustomerId();
        this.rewardPoints = customer.getRewardPoints();
        this.visitCount = customer.getVisitCount();
        this.favouriteItem = customer.getFavouriteItem();
        this.totalSpent = customer.getTotalSpent();
    }

    /**
     * Creates a new {@code CustomerBuilder} with updated basic attributes.
     *
     * @return A new {@code CustomerBuilder} instance.
     */
    @Override
    protected CustomerBuilder createBuilder(
            Name name,
            Phone phone,
            Email email,
            Address address,
            Remark remark,
            Set<Tag> tags) {
        return new CustomerBuilder(
                name,
                phone,
                email,
                address,
                remark,
                tags,
                this.customerId,
                this.rewardPoints,
                this.visitCount,
                this.favouriteItem,
                this.totalSpent);
    }

    /**
     * Creates a new {@code CustomerBuilder} with updated customer-specific
     * attributes.
     *
     * @return A new {@code CustomerBuilder} instance.
     */
    protected CustomerBuilder createBuilder(CustomerId customerId,
            RewardPoints rewardPoints,
            VisitCount visitCount,
            FavouriteItem favouriteItem,
            TotalSpent totalSpent) {
        return new CustomerBuilder(this.name,
                this.phone,
                this.email,
                this.address,
                this.remark,
                this.tags,
                customerId,
                rewardPoints,
                visitCount,
                favouriteItem,
                totalSpent);
    }

    /**
     * Returns the error message for a missing field.
     * @param fieldName The name of the field that is missing.
     * @return The error message indicating the missing field.
     */
    @Override
    public String getErrorMessage(String fieldName) {
        return String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName);
    }

    /**
     * Sets the {@code CustomerId} of the customer being built.
     *
     * @param customerId The customer ID to set.
     * @return A new {@code CustomerBuilder} instance with the updated customer ID.
     * @throws IllegalValueException if customerId is invalid.
     */
    public CustomerBuilder withCustomerId(String customerId) throws IllegalValueException {
        if (customerId == null) {
            throw new IllegalValueException(getErrorMessage(CustomerId.class.getSimpleName()));
        }
        if (!CustomerId.isValidCustomerId(customerId)) {
            throw new IllegalValueException(CustomerId.MESSAGE_CONSTRAINTS);
        }
        return withCustomerId(new CustomerId(customerId));
    }

    /**
     * Sets the {@code CustomerId} of the customer being built.
     *
     * @param customerId The customer ID to set.
     * @return A new {@code CustomerBuilder} instance with the updated customer ID.
     */
    public CustomerBuilder withCustomerId(CustomerId customerId) {
        return createBuilder(customerId, this.rewardPoints, this.visitCount,
                this.favouriteItem, this.totalSpent);
    }

    /**
     * Sets the {@code RewardPoints} of the customer being built.
     *
     * @param rewardPoints The reward points to set.
     * @return A new {@code CustomerBuilder} instance with the updated reward
     *         points.
     * @throws IllegalValueException if rewardPoints is invalid.
     */
    public CustomerBuilder withRewardPoints(String rewardPoints) throws IllegalValueException {
        if (rewardPoints == null) {
            throw new IllegalValueException(getErrorMessage(RewardPoints.class.getSimpleName()));
        }
        if (!RewardPoints.isValidRewardPoints(rewardPoints)) {
            throw new IllegalValueException(RewardPoints.MESSAGE_CONSTRAINTS);
        }
        return withRewardPoints(new RewardPoints(rewardPoints));
    }

    /**
     * Sets the {@code RewardPoints} of the customer being built.
     *
     * @param rewardPoints The reward points to set.
     * @return A new {@code CustomerBuilder} instance with the updated reward
     *         points.
     */
    public CustomerBuilder withRewardPoints(RewardPoints rewardPoints) {
        return createBuilder(this.customerId, rewardPoints, this.visitCount,
                this.favouriteItem, this.totalSpent);
    }

    /**
     * Sets the {@code VisitCount} of the customer being built.
     *
     * @param visitCount The visit count to set.
     * @return A new {@code CustomerBuilder} instance with the updated visit count.
     * @throws IllegalValueException if visitCount is invalid.
     */
    public CustomerBuilder withVisitCount(String visitCount) throws IllegalValueException {
        if (visitCount == null) {
            throw new IllegalValueException(getErrorMessage(VisitCount.class.getSimpleName()));
        }
        if (!VisitCount.isValidVisitCount(visitCount)) {
            throw new IllegalValueException(VisitCount.MESSAGE_CONSTRAINTS);
        }
        return withVisitCount(new VisitCount(visitCount));
    }

    /**
     * Sets the {@code VisitCount} of the customer being built.
     *
     * @param visitCount The visit count to set.
     * @return A new {@code CustomerBuilder} instance with the updated visit count.
     */
    public CustomerBuilder withVisitCount(VisitCount visitCount) {
        return createBuilder(this.customerId, this.rewardPoints, visitCount,
                this.favouriteItem, this.totalSpent);
    }

    /**
     * Sets the {@code FavouriteItem} of the customer being built.
     *
     * @param favouriteItem The favourite item to set.
     * @return A new {@code CustomerBuilder} instance with the updated favourite
     *         item.
     * @throws IllegalValueException if favouriteItem is invalid.
     */
    public CustomerBuilder withFavouriteItem(String favouriteItem) throws IllegalValueException {
        if (favouriteItem == null) {
            throw new IllegalValueException(getErrorMessage(FavouriteItem.class.getSimpleName()));
        }
        if (!FavouriteItem.isValidFavouriteItem(favouriteItem)) {
            throw new IllegalValueException(FavouriteItem.MESSAGE_CONSTRAINTS);
        }
        return withFavouriteItem(new FavouriteItem(favouriteItem));
    }

    /**
     * Sets the {@code FavouriteItem} of the customer being built.
     *
     * @param favouriteItem The favourite item to set.
     * @return A new {@code CustomerBuilder} instance with the updated favourite
     *         item.
     */
    public CustomerBuilder withFavouriteItem(FavouriteItem favouriteItem) {
        return createBuilder(this.customerId, this.rewardPoints, this.visitCount,
                favouriteItem, this.totalSpent);
    }

    /**
     * Sets the {@code TotalSpent} of the customer being built.
     *
     * @param totalSpent The total amount spent to set.
     * @return A new {@code CustomerBuilder} instance with the updated total spent.
     * @throws IllegalValueException if totalSpent is invalid.
     */
    public CustomerBuilder withTotalSpent(String totalSpent) throws IllegalValueException {
        if (totalSpent == null) {
            throw new IllegalValueException(getErrorMessage(TotalSpent.class.getSimpleName()));
        }
        if (!TotalSpent.isValidTotalSpent(totalSpent)) {
            throw new IllegalValueException(TotalSpent.MESSAGE_CONSTRAINTS);
        }
        return withTotalSpent(new TotalSpent(totalSpent));
    }

    /**
     * Sets the {@code TotalSpent} of the customer being built.
     *
     * @param totalSpent The total amount spent to set.
     * @return A new {@code CustomerBuilder} instance with the updated total spent.
     */
    public CustomerBuilder withTotalSpent(TotalSpent totalSpent) {
        return createBuilder(this.customerId, this.rewardPoints, this.visitCount,
                this.favouriteItem, totalSpent);
    }

    /**
     * Builds and returns a {@code Customer} object with the current attributes.
     *
     * @return A {@code Customer} object.
     */
    public Customer build() {
        return new Customer(name, phone, email, address, remark, tags,
                customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
    }
}
