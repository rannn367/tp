package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Staff;

/**
 * Panel containing the details of a staff member.
 */
public class StaffDetailPanel extends UiPart<Region> {
    private static final String FXML = "StaffDetailView.fxml";
    private final Logger logger = LogsCenter.getLogger(StaffDetailPanel.class);

    /**
     * Interface to execute commands from the UI.
     */
    public interface CommandExecutor {
        /**
         * Executes a command and returns the result.
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    private CommandExecutor commandExecutor;

    @FXML
    private Label noSelectionMessage;
    @FXML
    private VBox detailsContent;
    @FXML
    private Label staffNameHeader;
    @FXML
    private Label staffId;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
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
    private Button editButton;
    @FXML
    private Button deleteButton;

    /**
     * Creates a {@code StaffDetailPanel}.
     */
    public StaffDetailPanel() {
        super(FXML);
        // Hide details initially since no staff is selected
        showNoStaffSelected();
    }

    /**
     * Shows the no staff selected message.
     */
    private void showNoStaffSelected() {
        noSelectionMessage.setVisible(true);
        detailsContent.setVisible(false);
    }

    /**
     * Updates the UI with the details of the provided staff.
     *
     * @param staff The staff to display details for
     */
    public void updateStaffDetails(Staff staff) {
        if (staff == null) {
            showNoStaffSelected();
            return;
        }

        logger.fine("Updating staff details for: " + staff.getName().fullName);

        // Show details and hide the no selection message
        noSelectionMessage.setVisible(false);
        detailsContent.setVisible(true);

        // Set all the text fields
        staffNameHeader.setText(staff.getName().fullName);
        staffId.setText(staff.getStaffId().value);
        phone.setText(staff.getPhone().value);
        email.setText(staff.getEmail().value);
        address.setText(staff.getAddress().value);
        role.setText(staff.getRole().value);
        shiftTiming.setText(staff.getShiftTiming().value);
        hoursWorked.setText(staff.getHoursWorked().value);
        performanceRating.setText(staff.getPerformanceRating().value);

        // Clear existing tags
        tags.getChildren().clear();

        // Add tags
        staff.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add("tag");
            tags.getChildren().add(tagLabel);
        });
    }

    @FXML
    private void initialize() {
        editButton.setOnAction(event -> handleEditButtonAction());
        deleteButton.setOnAction(event -> handleDeleteButtonAction());
    }

    /**
     * Handles edit button action.
     */
    @FXML
    private void handleEditButtonAction() {
        logger.info("Edit button clicked - use 'edit-staff' command instead");
    }

    /**
     * Handles delete button action.
     */
    @FXML
    private void handleDeleteButtonAction() {
        logger.info("Delete button clicked - use 'delete-staff' command instead");
    }

    /**
     * Sets the command executor for this panel.
     *
     * @param executor the command executor to use
     */
    public void setCommandExecutor(CommandExecutor executor) {
        this.commandExecutor = executor;
    }
}
