package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Staff's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStaffId(String)}
 */
public class StaffId implements Comparable<StaffId> {

    public static final String MESSAGE_CONSTRAINTS =
            "Staff ID should start with an 'S' followed by digits, e.g., S1012";

    /*
     * The Staff ID must start with an uppercase 'S' followed by at least one digit.
     */
    public static final String VALIDATION_REGEX = "(?i)s\\d{1,9}";

    public final String value;
    private final int numericValue;
    /**
     * Constructs a {@code StaffId}.
     *
     * @param staffId A valid staff ID.
     */
    public StaffId(String staffId) {
        requireNonNull(staffId);
        checkArgument(isValidStaffId(staffId), MESSAGE_CONSTRAINTS);
        numericValue = Integer.parseInt(staffId.substring(1));
        value = "S" + String.valueOf(numericValue);
    }

    /**
     * Returns true if a given string is a valid staff ID.
     */
    public static boolean isValidStaffId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StaffId // instanceof handles nulls
                && value.equals(((StaffId) other).value)); // state check
    }

    @Override
    public int compareTo(StaffId other) {
        return Integer.compare(numericValue, other.numericValue);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
