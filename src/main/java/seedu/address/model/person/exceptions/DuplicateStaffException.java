package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Staff members
 * (Staff members are considered duplicates if they have the same identity).
 */
public class DuplicateStaffException extends RuntimeException {
    public DuplicateStaffException() {
        super("Operation would result in duplicate staff members");
    }
}
