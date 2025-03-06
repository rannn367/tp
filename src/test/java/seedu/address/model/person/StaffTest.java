package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StaffBuilder;

public class StaffTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Staff staff = new StaffBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> staff.getTags().remove(0));
    }

    @Test
    public void isSameStaff() {
        // same object -> returns true
        assertTrue(ALEX.isSameStaff(ALEX));

        // null -> returns false
        assertFalse(ALEX.isSameStaff(null));

        // same staffId, all other attributes different -> returns true
        Staff editedAlex = new StaffBuilder(ALEX).withRole("Manager").withShiftTiming("10am-6pm")
                .withHoursWorked(40).withPerformanceRating(4.8).build();
        assertTrue(ALEX.isSameStaff(editedAlex));

        // different staffId, all other attributes same -> returns false
        Staff editedBen = new StaffBuilder(BEN).withStaffId("S1002").build();
        assertFalse(BEN.isSameStaff(editedBen));

        // staffId differs in case, all other attributes same -> still true since IDs are case-insensitive
        Staff editedAlexCase = new StaffBuilder(ALEX).withStaffId(ALEX.getStaffId().toLowerCase()).build();
        assertTrue(ALEX.isSameStaff(editedAlexCase));

        // staffId has trailing spaces -> returns false
        Staff editedAlexTrailingSpace = new StaffBuilder(ALEX).withStaffId(ALEX.getStaffId() + " ").build();
        assertFalse(ALEX.isSameStaff(editedAlexTrailingSpace));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Staff alexCopy = new StaffBuilder(ALEX).build();
        assertTrue(ALEX.equals(alexCopy));

        // same object -> returns true
        assertTrue(ALEX.equals(ALEX));

        // null -> returns false
        assertFalse(ALEX.equals(null));

        // different type -> returns false
        assertFalse(ALEX.equals(5));

        // different staff -> returns false
        assertFalse(ALEX.equals(BEN));

        // different staffId -> returns false
        Staff editedAlex = new StaffBuilder(ALEX).withStaffId("S1003").build();
        assertFalse(ALEX.equals(editedAlex));

        // different role -> returns false
        editedAlex = new StaffBuilder(ALEX).withRole("Manager").build();
        assertFalse(ALEX.equals(editedAlex));

        // different shiftTiming -> returns false
        editedAlex = new StaffBuilder(ALEX).withShiftTiming("10am-6pm").build();
        assertFalse(ALEX.equals(editedAlex));

        // different hoursWorked -> returns false
        editedAlex = new StaffBuilder(ALEX).withHoursWorked(45).build();
        assertFalse(ALEX.equals(editedAlex));

        // different performanceRating -> returns false
        editedAlex = new StaffBuilder(ALEX).withPerformanceRating(4.8).build();
        assertFalse(ALEX.equals(editedAlex));
    }

    @Test
    public void toStringMethod() {
        String expected = Staff.class.getCanonicalName() + "{name=" + ALEX.getName() + ", phone=" + ALEX.getPhone()
                + ", email=" + ALEX.getEmail() + ", address=" + ALEX.getAddress() + ", tags=" + ALEX.getTags()
                + ", staffId=" + ALEX.getStaffId() + ", role="
                + ALEX.getRole() + ", shiftTiming=" + ALEX.getShiftTiming()
                + ", hoursWorked=" + ALEX.getHoursWorked() + ", performanceRating=" + ALEX.getPerformanceRating() + "}";
        assertEquals(expected, ALEX.toString());
    }
}
