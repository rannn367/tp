package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Customer;

/**
 * Panel containing the details of a customer.
 */
public class CustomerDetailPanel extends UiPart<Region> {
    private static final String FXML = "CustomerDetailView.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerDetailPanel.class);

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
    private Label customerNameHeader;
    @FXML
    private Label customerId;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label visitCount;
    @FXML
    private Label totalSpent;
    @FXML
    private Label rewardPoints;
    @FXML
    private Label favoriteItem;
    @FXML
    private Label remark;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    /**
     * Creates a {@code CustomerDetailPanel}.
     */
    public CustomerDetailPanel() {
        super(FXML);
        // Hide details initially since no customer is selected
        showNoCustomerSelected();
    }

    /**
     * Shows the no customer selected message.
     */
    private void showNoCustomerSelected() {
        noSelectionMessage.setVisible(true);
        detailsContent.setVisible(false);
    }

    /**
     * Updates the UI with the details of the provided customer.
     *
     * @param customer The customer to display details for
     */
    public void updateCustomerDetails(Customer customer) {
        if (customer == null) {
            showNoCustomerSelected();
            return;
        }

        logger.fine("Updating customer details for: " + customer.getName().fullName);

        // Show details and hide the no selection message
        noSelectionMessage.setVisible(false);
        detailsContent.setVisible(true);

        // Set all the text fields
        customerNameHeader.setText(customer.getName().fullName);
        customerId.setText(customer.getCustomerId());
        phone.setText(customer.getPhone().value);
        email.setText(customer.getEmail().value);
        address.setText(customer.getAddress().value);
        visitCount.setText(String.valueOf(customer.getVisitCount()));
        totalSpent.setText(String.format("$%.2f", customer.getTotalSpent()));
        rewardPoints.setText(String.valueOf(customer.getRewardPoints()));
        favoriteItem.setText(customer.getFavoriteItem());

        String remarkText = customer.getRemark().value;
        remark.setText(remarkText.isEmpty() ? "No notes" : remarkText);
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
        logger.info("Edit button clicked - use 'edit-customer' command instead");
    }

    /**
     * Handles delete button action.
     */
    @FXML
    private void handleDeleteButtonAction() {
        logger.info("Delete button clicked - use 'delete-customer' command instead");
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
