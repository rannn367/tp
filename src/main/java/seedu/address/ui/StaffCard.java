package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Staff;

public class StaffCard extends UiPart<Region> {

    private static final String FXML = "StaffListCard.fxml";

    public final Staff staff;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label staffName;
    @FXML
    private Label contactDetails;
    @FXML
    private Label staffId;
    @FXML
    private Label role;
    @FXML
    private Label shiftTiming;
    @FXML
    private Label hoursWorked;
    @FXML
    private Label performanceRating;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;

    public StaffCard(Staff staff, int displayedIndex) {
        super(FXML);
        this.staff = staff;

        id.setText(displayedIndex + ". ");
        staffName.setText(staff.getName().fullName);
        contactDetails.setText("(" + staff.getPhone().value + ", " + staff.getEmail().value + ")");

        staffId.setText("Staff ID: " + staff.getStaffId());
        role.setText("| Role: " + staff.getRole());
        shiftTiming.setText("| Shift: " + staff.getShiftTiming());

        hoursWorked.setText("Total Hours: " + staff.getHoursWorked());
        performanceRating.setText("| Performance Rating: " + staff.getPerformanceRating());

        address.setText(staff.getAddress().value);

        staff.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
