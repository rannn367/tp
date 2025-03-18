package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.drink.Drink;

/**
 * Panel containing the details of a drink.
 */
public class DrinkDetailPanel extends UiPart<Region> {
    private static final String FXML = "DrinkDetailView.fxml";
    private final Logger logger = LogsCenter.getLogger(DrinkDetailPanel.class);

    @FXML
    private Label noSelectionMessage;
    @FXML
    private VBox detailsContent;
    @FXML
    private Label drinkNameHeader;
    @FXML
    private Label price;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private Label stock;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    /**
     * Creates a {@code DrinkDetailPanel}.
     */
    public DrinkDetailPanel() {
        super(FXML);
        // Hide details initially since no drink is selected
        showNoDrinkSelected();
    }

    /**
     * Shows the no drink selected message.
     */
    private void showNoDrinkSelected() {
        noSelectionMessage.setVisible(true);
        detailsContent.setVisible(false);
    }

    /**
     * Updates the UI with the details of the provided drink.
     *
     * @param drink The drink to display details for
     */
    public void updateDrinkDetails(Drink drink) {
        if (drink == null) {
            showNoDrinkSelected();
            return;
        }

        logger.fine("Updating drink details for: " + drink.getName());

        // Show details and hide the no selection message
        noSelectionMessage.setVisible(false);
        detailsContent.setVisible(true);

        // Set all the text fields
        drinkNameHeader.setText(drink.getName());
        price.setText(String.format("$%.2f", drink.getPrice()));
        category.setText(drink.getCategory());
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
        logger.info("Edit button clicked - use 'edit-drink' command instead");
    }

    /**
     * Handles delete button action.
     */
    @FXML
    private void handleDeleteButtonAction() {
        logger.info("Delete button clicked - use 'delete-drink' command instead");
    }
}
