package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalStaff.ALEX;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

public class StaffCardTest {
    @BeforeAll
    static void setupJavaFX() {
        Platform.startup(() -> {}); // Ensure JavaFX initializes once before tests run
    }

    @Test
    public void createStaffCard_validStaff_success() {

        StaffCard staffCard = new StaffCard(ALEX, 1);

        assertNotNull(staffCard);
    }
}
