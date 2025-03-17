package seedu.address.ui;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.drink.Drink;

/**
 * Panel containing the list of drinks.
 */
public class DrinkListPanel extends UiPart<Region> {
    private static final String FXML = "DrinkListPanel.fxml";

    @FXML
    private ListView<Drink> drinkListView;

    /**
     * Creates a {@code DrinkListPanel} with the given {@code ObservableList}.
     */
    public DrinkListPanel(ObservableList<Drink> drinkList) {
        super(FXML);
        drinkListView.setItems(drinkList);
        drinkListView.setCellFactory(listView -> new DrinkListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Drink} using a {@code DrinkCard}.
     */
    class DrinkListViewCell extends ListCell<Drink> {
        @Override
        protected void updateItem(Drink drink, boolean empty) {
            super.updateItem(drink, empty);

            if (empty || drink == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DrinkCard(drink, getIndex() + 1).getRoot());
            }
        }
    }
}
