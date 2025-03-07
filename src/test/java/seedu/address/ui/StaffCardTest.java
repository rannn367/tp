package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Staff;
import seedu.address.testutil.StaffBuilder;

public class StaffCardTest {

    @Test
    public void createStaffCard_validStaff_success() {
        Staff staff = new StaffBuilder().withName("Alice Tan").withPhone("91234567")
                .withEmail("alice@example.com").withStaffId("S123")
                .withRole("Manager").withShiftTiming("Morning")
                .withHoursWorked(40).withPerformanceRating(5).build();

        StaffCard staffCard = new StaffCard(staff, 1);

        assertNotNull(staffCard);
    }
}
