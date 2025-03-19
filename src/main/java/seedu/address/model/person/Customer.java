package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the café management system.
 * Inherits from Person and adds customer-specific attributes.
 */
public class Customer extends Person {

    // Customer-specific fields
    private final CustomerId customerId; // Customer id
    private final RewardPoints rewardPoints; // Current reward points balance
    private final VisitCount visitCount; // Number of times customer has visited
    private final FavouriteItem favoriteItem; // Customer's most ordered item
    private final TotalSpent totalSpent; // Total amount spent by customer

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                    CustomerId customerId, RewardPoints rewardPoints, VisitCount visitCount,
                    FavouriteItem favoriteItem, TotalSpent totalSpent) {
        super(name, phone, email, address, remark, tags);
        requireAllNonNull(customerId, rewardPoints, visitCount, favoriteItem, totalSpent);

        this.customerId = customerId;
        this.rewardPoints = rewardPoints;
        this.visitCount = visitCount;
        this.favoriteItem = favoriteItem;
        this.totalSpent = totalSpent;
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

    public FavouriteItem getFavoriteItem() {
        return favoriteItem;
    }

    public TotalSpent getTotalSpent() {
        return totalSpent;
    }

    /**
     * Returns true if both customers have the same identity fields.
     * This defines a weaker notion of equality between two customers.
     */
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        if (otherCustomer == null) {
            return false;
        }

        // Using phone number as unique identifier for customers
        return otherCustomer.getPhone().equals(getPhone());
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
        return super.equals(otherCustomer) // Calls the equality check from Person
                && rewardPoints.equals(otherCustomer.rewardPoints)
                && visitCount.equals(otherCustomer.visitCount)
                && favoriteItem.equals(otherCustomer.favoriteItem)
                && totalSpent.equals(otherCustomer.totalSpent)
                && customerId.equals(otherCustomer.customerId);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(customerId, rewardPoints, visitCount, favoriteItem, totalSpent);
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
                .add("favoriteItem", favoriteItem)
                .add("totalSpent", totalSpent)
                .toString();
    }
}
