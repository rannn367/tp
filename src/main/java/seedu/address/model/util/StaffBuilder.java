package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Staff objects.
 */
public class StaffBuilder extends PersonBuilder<Staff, StaffBuilder> {
    
    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_PHONE = "12345678";
    public static final String DEFAULT_EMAIL = "johndoe@example.com";
    public static final String DEFAULT_ADDRESS = "123 Main Street";
    public static final String DEFAULT_REMARK = "No remarks";
    public static final String DEFAULT_STAFF_ID = "S1001";
    public static final String DEFAULT_ROLE = "Barista";
    public static final String DEFAULT_SHIFT_TIMING = "8am-4pm";
    public static final String DEFAULT_HOURS_WORKED = "40";
    public static final String DEFAULT_PERFORMANCE_RATING = "4.5";

    protected final StaffId staffId;
    protected final Role role;
    protected final ShiftTiming shiftTiming;
    protected final HoursWorked hoursWorked;
    protected final PerformanceRating performanceRating;

    public StaffBuilder() {
        super(new Name(DEFAULT_NAME),
                new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL),
                new Address(DEFAULT_ADDRESS),
                new Remark(DEFAULT_REMARK),
                new HashSet<>());
        this.staffId = new StaffId(DEFAULT_STAFF_ID);
        this.role = new Role(DEFAULT_ROLE);
        this.shiftTiming = new ShiftTiming(DEFAULT_SHIFT_TIMING);
        this.hoursWorked = new HoursWorked(DEFAULT_HOURS_WORKED);
        this.performanceRating = new PerformanceRating(DEFAULT_PERFORMANCE_RATING);
    }

    protected StaffBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                           StaffId staffId, Role role, ShiftTiming shiftTiming, HoursWorked hoursWorked, 
                           PerformanceRating performanceRating) {
        super(name, phone, email, address, remark, tags);
        this.staffId = staffId;
        this.role = role;
        this.shiftTiming = shiftTiming;
        this.hoursWorked = hoursWorked;
        this.performanceRating = performanceRating;
    }

    public StaffBuilder(Staff staff) {
        super(staff.getName(), staff.getPhone(), staff.getEmail(), staff.getAddress(), staff.getRemark(), staff.getTags());
        this.staffId = staff.getStaffId();
        this.role = staff.getRole();
        this.shiftTiming = staff.getShiftTiming();
        this.hoursWorked = staff.getHoursWorked();
        this.performanceRating = staff.getPerformanceRating();
    }

    @Override
    protected StaffBuilder createBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        return new StaffBuilder(name, phone, email, address, remark, tags, this.staffId, this.role, 
                                this.shiftTiming, this.hoursWorked, this.performanceRating);
    }

    protected StaffBuilder createBuilder(StaffId staffId, Role role, ShiftTiming shiftTiming, 
                                         HoursWorked hoursWorked, PerformanceRating performanceRating) {
        return new StaffBuilder(this.name, this.phone, this.email, this.address, this.remark, this.tags, 
                                staffId, role, shiftTiming, hoursWorked, performanceRating);
    }

    public StaffBuilder withStaffId(String staffId) {
        return createBuilder(new StaffId(staffId), this.role, this.shiftTiming, this.hoursWorked, this.performanceRating);
    }

    public StaffBuilder withStaffId(StaffId staffId) {
        return createBuilder(staffId, this.role, this.shiftTiming, this.hoursWorked, this.performanceRating);
    }

    public StaffBuilder withRole(String role) {
        return createBuilder(this.staffId, new Role(role), this.shiftTiming, this.hoursWorked, this.performanceRating);
    }

    public StaffBuilder withRole(Role role) {
        return createBuilder(this.staffId, role, this.shiftTiming, this.hoursWorked, this.performanceRating);
    }

    public StaffBuilder withShiftTiming(String shiftTiming) {
        return createBuilder(this.staffId, this.role, new ShiftTiming(shiftTiming), this.hoursWorked, this.performanceRating);
    }

    public StaffBuilder withShiftTiming(ShiftTiming shiftTiming) {
        return createBuilder(this.staffId, this.role, shiftTiming, this.hoursWorked, this.performanceRating);
    }

    public StaffBuilder withHoursWorked(String hoursWorked) {
        return createBuilder(this.staffId, this.role, this.shiftTiming, new HoursWorked(hoursWorked), this.performanceRating);
    }

    public StaffBuilder withHoursWorked(HoursWorked hoursWorked) {
        return createBuilder(this.staffId, this.role, this.shiftTiming, hoursWorked, this.performanceRating);
    }

    public StaffBuilder withPerformanceRating(String performanceRating) {
        return createBuilder(this.staffId, this.role, this.shiftTiming, this.hoursWorked, new PerformanceRating(performanceRating));
    }

    public StaffBuilder withPerformanceRating(PerformanceRating performanceRating) {
        return createBuilder(this.staffId, this.role, this.shiftTiming, this.hoursWorked, performanceRating);
    }

    /**
     * Builds the staff with the information altogether.
     */
    @Override
    public Staff build() {
        return new Staff(name, phone, email, address, remark, tags,
                staffId, role, shiftTiming, hoursWorked, performanceRating);
    }
}
