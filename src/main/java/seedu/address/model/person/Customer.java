package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the café management system.
 * Inherits from Person and adds customer-specific attributes.
 */
public class Customer extends Person {

    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    // Customer-specific fields
    private final CustomerId customerId; // Unique identifier for customer
    private final RewardPoints rewardPoints; // Current reward points balance
    private final VisitCount visitCount; // Number of times customer has visited
    private final FavouriteItem favouriteItem; // Customer's most ordered item
    private final TotalSpent totalSpent; // Total amount spent by customer

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                    CustomerId customerId, RewardPoints rewardPoints, VisitCount visitCount,
                    FavouriteItem favouriteItem, TotalSpent totalSpent) {
        super(name, phone, email, address, remark, tags);
        requireAllNonNull(customerId, rewardPoints, visitCount, favouriteItem, totalSpent);

        if (customerId == null || rewardPoints == null || visitCount == null || favouriteItem == null
                || totalSpent == null) {
            logger.log(Level.SEVERE, "Attempted to create Customer with null attributes");
            throw new IllegalArgumentException("Customer attributes cannot be null");
        }

        this.customerId = customerId;
        this.rewardPoints = rewardPoints;
        this.visitCount = visitCount;
        this.favouriteItem = favouriteItem;
        this.totalSpent = totalSpent;

        logger.log(Level.INFO, "Created new Customer: {0}", this);
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RewardPoints getRewardPoints() {
        return rewardPoints;
    }

    public VisitCount getVisitCount() {
        return visitCount;
    }

    public FavouriteItem getFavouriteItem() {
        return favouriteItem;
    }

    public TotalSpent getTotalSpent() {
        return totalSpent;
    }

    /**
     * Returns true if both customers have the same identity fields.
     * This defines a weaker notion of equality between two customers.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        if (!(otherPerson instanceof Customer)) {
            return false;
        }

        // Compare using customerId as unique identifier
        Customer otherCustomer = (Customer) otherPerson;

        boolean isSame = otherCustomer.getCustomerId().equals(getCustomerId());
        logger.log(Level.FINE, "Comparing Staff IDs: {0} and {1}, Result: {2}",
                new Object[]{this.customerId, otherCustomer.customerId, isSame});
        return isSame;
    }

    /**
     * Returns true if both customers have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        boolean isEqual = super.equals(otherCustomer) // Calls the equality check from Person
                && rewardPoints.equals(otherCustomer.rewardPoints)
                && visitCount.equals(otherCustomer.visitCount)
                && favouriteItem.equals(otherCustomer.favouriteItem)
                && totalSpent.equals(otherCustomer.totalSpent)
                && customerId.equals(otherCustomer.customerId);

        logger.log(Level.FINE, "Comparing Customer objects: {0} and {1}, Result: {2}",
                new Object[]{this, otherCustomer, isEqual});
        return isEqual;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("customerId", getCustomerId())
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", getTags())
                .add("rewardPoints", rewardPoints)
                .add("visitCount", visitCount)
                .add("favouriteItem", favouriteItem)
                .add("totalSpent", totalSpent)
                .toString();
    }
}
