package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Customer's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCustomerId(String)}
 */
public class CustomerId {

    public static final String MESSAGE_CONSTRAINTS =
            "Customer ID should start with a 'C' followed by digits, e.g., C1001";

    /*
     * The Customer ID must start with an uppercase 'C' followed by at least one digit.
     */
    public static final String VALIDATION_REGEX = "(?i)c\\d{1,9}";

    public final String value;

    /**
     * Constructs a {@code CustomerId}.
     *
     * @param customerId A valid customer ID.
     */
    public CustomerId(String customerId) {
        requireNonNull(customerId);
        checkArgument(isValidCustomerId(customerId), MESSAGE_CONSTRAINTS);
        value = customerId.substring(0, 1).toUpperCase() + customerId.substring(1);
    }

    /**
     * Returns true if a given string is a valid customer ID.
     */
    public static boolean isValidCustomerId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerId // instanceof handles nulls
                && value.equals(((CustomerId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
