package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Staff member in the café management system.
 * Inherits from Person and adds staff-specific attributes.
 */
public class Staff extends Person {

    // Staff-specific fields
    private final StaffId staffId; // Unique identifier for staff
    private final Role role; // Job role (e.g., "Barista", "Manager")
    private final ShiftTiming shiftTiming; // Work schedule (e.g., "9am-5pm")
    private final HoursWorked hoursWorked; // Total hours worked in a period
    private final PerformanceRating performanceRating; // Performance rating out of 5.0

    /**
     * Every field must be present and not null.
     */
    public Staff(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags, StaffId staffId,
                 Role role, ShiftTiming shiftTiming, HoursWorked hoursWorked, PerformanceRating performanceRating) {
        super(name, phone, email, address, remark, tags);
        requireAllNonNull(staffId, role, shiftTiming, hoursWorked, performanceRating);

        this.staffId = staffId;
        this.role = role;
        this.shiftTiming = shiftTiming;
        this.hoursWorked = hoursWorked;
        this.performanceRating = performanceRating;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public Role getRole() {
        return role;
    }

    public ShiftTiming getShiftTiming() {
        return shiftTiming;
    }

    public HoursWorked getHoursWorked() {
        return hoursWorked;
    }

    public PerformanceRating getPerformanceRating() {
        return performanceRating;
    }

    /**
     * Returns true if both staff members have the same staff ID.
     * This defines a weaker notion of equality between two staff members.
     */
    public boolean isSameStaff(Staff otherStaff) {
        if (otherStaff == this) {
            return true;
        }

        if (otherStaff == null) {
            return false;
        }

        // Compare using staffId, name, or any unique field
        return otherStaff.getStaffId().equals(getStaffId());
    }

    /**
     * Returns true if both staff members have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Staff)) {
            return false;
        }

        Staff otherStaff = (Staff) other;
        return super.equals(otherStaff) // Calls the equality check from Person
                && staffId.equals(otherStaff.staffId)
                && role.equals(otherStaff.role)
                && shiftTiming.equals(otherStaff.shiftTiming)
                && hoursWorked == otherStaff.hoursWorked
                && performanceRating == otherStaff.performanceRating;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(staffId, role, shiftTiming, hoursWorked, performanceRating);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("staffId", getStaffId())
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", getTags())
                .add("role", role)
                .add("shiftTiming", shiftTiming)
                .add("hoursWorked", hoursWorked)
                .add("performanceRating", performanceRating)
                .toString();
    }

}
