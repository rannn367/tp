package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;
import seedu.address.model.util.StaffBuilder;
import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Staff} objects to be used in
 * tests.
 */
public class TypicalStaff {

    public static final Staff ALEX = new StaffBuilder().withName(new Name("Alex Tan"))
            .withPhone(new Phone("91234567"))
            .withEmail(new Email("alex.tan@example.com"))
            .withAddress(new Address("456, Bukit Timah Road, #12-34"))
            .withStaffId(new StaffId("S1001"))
            .withRole(new Role("Barista"))
            .withShiftTiming(new ShiftTiming("8am-4pm"))
            .withHoursWorked(new HoursWorked("40"))
            .withPerformanceRating(new PerformanceRating("4.5"))
            .withTags(Set.of(new Tag("teamplayer"))).build();

    public static final Staff BEN = new StaffBuilder().withName(new Name("Ben Chua"))
            .withPhone(new Phone("98765432"))
            .withEmail(new Email("ben.chua@example.com"))
            .withAddress(new Address("789, Orchard Road, #22-56"))
            .withStaffId(new StaffId("S1002"))
            .withRole(new Role("Cashier"))
            .withShiftTiming(new ShiftTiming("4pm-12am"))
            .withHoursWorked(new HoursWorked("38"))
            .withPerformanceRating(new PerformanceRating("4.2"))
            .withTags(Set.of(new Tag("punctual"))).build();

    private TypicalStaff() {
    } // prevents instantiation

    public static List<Staff> getTypicalStaff() {
        return new ArrayList<>(Arrays.asList(ALEX, BEN));
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Staff staff : getTypicalStaff()) {
            ab.addStaff(staff);
        }
        return ab;
    }
}
