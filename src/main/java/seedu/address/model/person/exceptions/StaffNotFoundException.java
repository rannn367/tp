package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified staff member.
 */
public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException() {
        super("Staff member not found");
    }
}
