package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Staff;

/**
 * An UI component that displays information of a {@code Staff}.
 */
public class StaffCard extends UiPart<Region> {

    private static final String FXML = "StaffListCard.fxml";

    public final Staff staff;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
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
    private FlowPane tags;
    @FXML
    private Label remark;

    /**
     * Creates a {@code StaffCard} with the given {@code Staff} and index to display.
     */
    public StaffCard(Staff staff, int displayedIndex) {
        super(FXML);
        this.staff = staff;
        id.setText(displayedIndex + ". ");
        name.setText(staff.getName().fullName);
        phone.setText(staff.getPhone().value);
        address.setText(staff.getAddress().value);
        email.setText(staff.getEmail().value);
        staffId.setText("ID: " + staff.getStaffId());
        role.setText("Role: " + staff.getRole());
        shiftTiming.setText("Shift: " + staff.getShiftTiming());
        hoursWorked.setText("Hours: " + staff.getHoursWorked());
        performanceRating.setText("Rating: " + staff.getPerformanceRating());

        remark.setText(staff.getRemark().value);
        staff.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
