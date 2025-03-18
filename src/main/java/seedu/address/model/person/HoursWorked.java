package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's hours worked in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHoursWorked(String)}
 */
public class HoursWorked {

    public static final String MESSAGE_CONSTRAINTS =
            "Hours worked should only contain digits, and it should be at least 1 digit long";

    // Regex to match one or more digits (no negative or decimal values)
    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs a {@code HoursWorked}.
     *
     * @param hoursWorked A valid number of hours worked.
     */
    public HoursWorked(String hoursWorked) {
        requireNonNull(hoursWorked);
        checkArgument(isValidHoursWorked(hoursWorked), MESSAGE_CONSTRAINTS);
        value = hoursWorked;
    }

    /**
     * Returns true if a given string is a valid hours worked.
     */
    public static boolean isValidHoursWorked(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof HoursWorked
                && value.equals(((HoursWorked) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
