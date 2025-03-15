package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Customer;

/**
 * An UI component that displays information of a {@code Customer}.
 */
public class CustomerCard extends UiPart<Region> {

    private static final String FXML = "CustomerListCard.fxml";

    public final Customer customer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customerName;
    @FXML
    private Label phone;
    @FXML
    private Label customerId;
    @FXML
    private Label favouriteItem;
    @FXML
    private Label visitCount;
    @FXML
    private Label totalSpent;
    @FXML
    private Label rewardPoints;
    @FXML
    private Label address;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code CustomerCard} with the given {@code Customer} and index to display.
     */
    public CustomerCard(Customer customer, int displayedIndex) {
        super(FXML);
        this.customer = customer;

        id.setText(displayedIndex + ". ");
        customerName.setText(customer.getName().fullName);
        phone.setText("(" + customer.getPhone().value + ", " + customer.getEmail().value + ")");

        customerId.setText("Customer ID: " + customer.getCustomerId());
        favouriteItem.setText("| Favourite Item: " + customer.getFavoriteItem());

        visitCount.setText("Visit Count: " + customer.getVisitCount());
        totalSpent.setText("| Total Spent: $" + customer.getTotalSpent());
        rewardPoints.setText("| Reward Points: " + customer.getRewardPoints());

        address.setText(customer.getAddress().value);
        remark.setText(customer.getRemark().value);

        customer.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
