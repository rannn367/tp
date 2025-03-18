package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StaffIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StaffId(null));
    }

    @Test
    public void constructor_invalidStaffId_throwsIllegalArgumentException() {
        String invalidStaffId = "";
        assertThrows(IllegalArgumentException.class, () -> new StaffId(invalidStaffId));
    }

    @Test
    public void isValidStaffId() {
        // null staff ID
        assertThrows(NullPointerException.class, () -> StaffId.isValidStaffId(null));

        // invalid staff IDs
        assertFalse(StaffId.isValidStaffId("")); // empty string
        assertFalse(StaffId.isValidStaffId(" ")); // spaces only
        assertFalse(StaffId.isValidStaffId("S")); // missing digits
        assertFalse(StaffId.isValidStaffId("1234")); // missing 'S' prefix
        assertFalse(StaffId.isValidStaffId("s1001")); // lowercase 's'
        assertFalse(StaffId.isValidStaffId("S10A1")); // non-numeric characters after 'S'

        // valid staff IDs
        assertTrue(StaffId.isValidStaffId("S1")); // minimal valid case
        assertTrue(StaffId.isValidStaffId("S100"));
        assertTrue(StaffId.isValidStaffId("S99999"));
    }

    @Test
    public void equals() {
        StaffId staffId = new StaffId("S1001");

        // same values -> returns true
        assertTrue(staffId.equals(new StaffId("S1001")));

        // same object -> returns true
        assertTrue(staffId.equals(staffId));

        // null -> returns false
        assertFalse(staffId.equals(null));

        // different types -> returns false
        assertFalse(staffId.equals(5.0f));

        // different values -> returns false
        assertFalse(staffId.equals(new StaffId("S1002")));
    }
}
