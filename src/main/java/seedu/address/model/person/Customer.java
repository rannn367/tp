package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;


/**
 * Represents a Customer in the café management system.
 * Inherits from Person and adds customer-specific attributes.
 */
public class Customer extends Person {

    // Customer-specific fields
    private final String customerId; // Customer id
    private final int rewardPoints; // Current reward points balance
    private final int visitCount; // Number of times customer has visited
    private final String favoriteItem; // Customer's most ordered item
    private final double totalSpent; // Total amount spent by customer

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                    String customerId, int rewardPoints, int visitCount, String favoriteItem, double totalSpent) {
        super(name, phone, email, address, remark, tags);
        requireAllNonNull(customerId, rewardPoints, visitCount, favoriteItem, totalSpent);

        this.customerId = customerId;
        this.rewardPoints = rewardPoints;
        this.visitCount = visitCount;
        this.favoriteItem = favoriteItem;
        this.totalSpent = totalSpent;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public String getFavoriteItem() {
        return favoriteItem;
    }

    public double getTotalSpent() {
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
                && rewardPoints == otherCustomer.rewardPoints
                && visitCount == otherCustomer.visitCount
                && favoriteItem.equals(otherCustomer.favoriteItem)
                && Double.compare(totalSpent, otherCustomer.totalSpent) == 0
                && customerId.equals(otherCustomer.customerId);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + customerId.hashCode() + Integer.hashCode(rewardPoints) + Integer.hashCode(visitCount)
                + favoriteItem.hashCode() + Double.hashCode(totalSpent);
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
