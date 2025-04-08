package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
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
    private static final Logger logger = LogsCenter.getLogger(StaffDetailPanel.class);

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
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code StaffDetailPanel}.
     */
    public StaffDetailPanel() {
        super(FXML);

        // Verify critical components
        boolean componentsOk = verifyComponents();
        if (!componentsOk) {
            logger.warning("Some FXML components were not properly loaded in StaffDetailPanel");
        }

        // Hide details initially since no staff is selected
        showNoStaffSelected();
    }

    /**
     * Verifies that critical FXML components were loaded properly.
     * @return true if all critical components exist
     */
    private boolean verifyComponents() {
        boolean allOk = true;

        if (noSelectionMessage == null) {
            logger.severe("noSelectionMessage is null");
            allOk = false;
        }

        if (detailsContent == null) {
            logger.severe("detailsContent is null");
            allOk = false;
        }

        if (staffNameHeader == null) {
            logger.severe("staffNameHeader is null");
            allOk = false;
        }

        return allOk;
    }

    /**
     * Shows the no staff selected message.
     */
    private void showNoStaffSelected() {
        if (noSelectionMessage != null) {
            noSelectionMessage.setVisible(true);
        }

        if (detailsContent != null) {
            detailsContent.setVisible(false);
        }
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

        try {
            // Show details and hide the no selection message
            if (noSelectionMessage != null) {
                noSelectionMessage.setVisible(false);
            }

            if (detailsContent != null) {
                detailsContent.setVisible(true);
            }

            // Set the header (this is the most crucial part)
            if (staffNameHeader != null) {
                staffNameHeader.setText(staff.getName().fullName);
            }

            // Update all fields with null safety
            safeSetText(staffId, staff.getStaffId() != null ? staff.getStaffId().value : "");
            safeSetText(phone, staff.getPhone() != null ? staff.getPhone().value : "");
            safeSetText(email, staff.getEmail() != null ? staff.getEmail().value : "");
            safeSetText(address, staff.getAddress() != null ? staff.getAddress().value : "");
            safeSetText(role, staff.getRole() != null ? staff.getRole().value : "");
            safeSetText(shiftTiming, staff.getShiftTiming() != null ? staff.getShiftTiming().value : "");
            safeSetText(hoursWorked, staff.getHoursWorked() != null ? staff.getHoursWorked().value : "0");
            safeSetText(performanceRating, staff.getPerformanceRating() != null
                    ? staff.getPerformanceRating().value : "N/A");
            safeSetText(remark, staff.getRemark() != null ? staff.getRemark().value : "");

            // Clear existing tags and add new ones
            if (tags != null) {
                tags.getChildren().clear();

                if (staff.getTags() != null) {
                    staff.getTags().forEach(tag -> {
                        String tagName = tag.tagName;
                        if (tagName.length() > 15) {
                            tagName = tagName.substring(0, 15) + "...";
                        }
                        Label tagLabel = new Label(tagName);
                        tagLabel.getStyleClass().add("tag");
                        tags.getChildren().add(tagLabel);
                    });
                }
            }
        } catch (Exception e) {
            logger.warning("Error updating staff details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Safely sets text to a label, handling null cases.
     *
     * @param label The label to update
     * @param text The text to set
     */
    private void safeSetText(Label label, String text) {
        if (label != null) {
            label.setText(text != null ? text : "");
        }
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
