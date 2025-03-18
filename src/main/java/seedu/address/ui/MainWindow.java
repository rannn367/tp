package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Staff;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StaffListPanel staffListPanel;
    private CustomerListPanel customerListPanel;
    private DrinkListPanel drinkListPanel;
    private StaffDetailPanel staffDetailPanel;
    private CustomerDetailPanel customerDetailPanel;
    private DrinkDetailPanel drinkDetailPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private StatusBarFooter statusBarFooter;
    private HelpWindow helpWindow;

    private int currentTheme = 0; // 0 = Dark, 1 = Light
    private int currentBackground = 1; // Track current background (1-5)

    // Store last selected items to restore when switching tabs
    private Staff lastSelectedStaff = null;
    private Customer lastSelectedCustomer = null;
    private Drink lastSelectedDrink = null;

    @FXML
    private TabPane tabPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane staffListPanelPlaceholder;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane drinksListPanelPlaceholder;

    @FXML
    private StackPane staffDetailsPlaceholder;

    @FXML
    private StackPane customerDetailsPlaceholder;

    @FXML
    private StackPane drinksDetailsPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox mainPane;

    @FXML
    private Button toggleBackgroundButton;

    @FXML
    private Button toggleThemeButton;

    @FXML private MenuBar menuBar;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();
        helpWindow = new HelpWindow();
    }

    /**
     * Initialize method called by JavaFX after FXML loading.
     * Sets up all event handlers programmatically.
     */
    @FXML
    private void initialize() {
        // Set up toggle button handlers
        toggleBackgroundButton.setOnAction(event -> handleToggleBackground());
        toggleThemeButton.setOnAction(event -> handleToggleTheme());

        // Set up menu handlers
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(event -> handleExit());
        helpMenuItem.setOnAction(event -> handleHelp());

        // Set window close handler
        primaryStage.setOnCloseRequest(event -> handleExit());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Sets up tab change handlers to preserve selections when switching tabs.
     */
    private void setupTabChangeHandlers() {
        // Handle tab changes
        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() == 0) {
                // Staff tab selected
                logger.info("Switched to Staff tab");
                // Restore previous staff selection if any
                if (lastSelectedStaff != null) {
                    staffListPanel.selectStaff(lastSelectedStaff);
                }
            } else if (newVal.intValue() == 1) {
                // Customer tab selected
                logger.info("Switched to Customer tab");
                // Restore previous customer selection if any
                if (lastSelectedCustomer != null) {
                    customerListPanel.selectCustomer(lastSelectedCustomer);
                }
            } else if (newVal.intValue() == 2) {
                // Drinks tab selected
                logger.info("Switched to Drinks tab");
                // Restore previous drink selection if any
                if (lastSelectedDrink != null && drinkListPanel != null) {
                    drinkListPanel.selectDrink(lastSelectedDrink);
                }
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        // Staff components
        staffListPanel = new StaffListPanel(logic.getFilteredStaffList());
        staffListPanelPlaceholder.getChildren().add(staffListPanel.getRoot());

        staffDetailPanel = new StaffDetailPanel();
        staffDetailsPlaceholder.getChildren().add(staffDetailPanel.getRoot());

        // Customer components
        customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
        customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());

        customerDetailPanel = new CustomerDetailPanel();
        customerDetailsPlaceholder.getChildren().add(customerDetailPanel.getRoot());

        // Sample drinks for testing (since you might not have model support yet)
        ObservableList<Drink> sampleDrinks = FXCollections.observableArrayList(
                new Drink("Espresso", 3.50, "Coffee"),
                new Drink("Latte", 4.50, "Coffee"),
                new Drink("Cappuccino", 4.00, "Coffee"),
                new Drink("Green Tea", 3.00, "Tea"),
                new Drink("Orange Juice", 2.50, "Juice")
        );

        // Drinks components
        try {
            drinkListPanel = new DrinkListPanel(logic.getFilteredDrinkList());
        } catch (Exception e) {
            // Fall back to sample drinks if the method doesn't exist
            logger.info("Using sample drinks data since getFilteredDrinksList() is not available: " + e.getMessage());
            drinkListPanel = new DrinkListPanel(sampleDrinks);
        }
        drinksListPanelPlaceholder.getChildren().add(drinkListPanel.getRoot());

        drinkDetailPanel = new DrinkDetailPanel();
        drinksDetailsPlaceholder.getChildren().add(drinkDetailPanel.getRoot());

        // Set up selection handlers
        staffListPanel.setStaffSelectionHandler(this::handleStaffSelection);
        customerListPanel.setCustomerSelectionHandler(this::handleCustomerSelection);
        drinkListPanel.setDrinkSelectionHandler(this::handleDrinkSelection);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // Setup tab change handlers
        setupTabChangeHandlers();
    }

    /**
     * Handles staff selection from the list.
     */
    private void handleStaffSelection(Staff staff) {
        logger.info("Staff selected: " + (staff != null ? staff.getName().fullName : "none"));
        lastSelectedStaff = staff;
        staffDetailPanel.updateStaffDetails(staff);
    }

    /**
     * Handles customer selection from the list.
     */
    private void handleCustomerSelection(Customer customer) {
        logger.info("Customer selected: " + (customer != null ? customer.getName().fullName : "none"));
        lastSelectedCustomer = customer;
        customerDetailPanel.updateCustomerDetails(customer);
    }

    /**
     * Handles drink selection from the list.
     */
    private void handleDrinkSelection(Drink drink) {
        logger.info("Drink selected: " + (drink != null ? drink.getName() : "none"));
        lastSelectedDrink = drink;
        drinkDetailPanel.updateDrinkDetails(drink);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Toggles the background image.
     */
    private void handleToggleBackground() {
        // Remove all background classes first
        mainPane.getStyleClass().removeIf(style -> style.startsWith("main-pane-"));

        // Cycle through backgrounds (1 to 5)
        currentBackground = (currentBackground % 5) + 1;

        // Add the new background class
        String newStyle = "main-pane-" + currentBackground;
        mainPane.getStyleClass().add(newStyle);

        logger.info("Background changed to: " + newStyle);
    }

    /**
     * Toggles between dark and light themes.
     */
    private void handleToggleTheme() {
        Scene scene = primaryStage.getScene();

        // Toggle between dark theme (default) and light theme
        if (currentTheme == 0) {
            // Switch to light theme
            if (!scene.getStylesheets().contains("view/LightTheme.css")) {
                scene.getStylesheets().add("view/LightTheme.css");
            }
            scene.getRoot().getStyleClass().add("light-theme");
            currentTheme = 1;
        } else {
            // Switch back to dark theme
            scene.getStylesheets().remove("view/LightTheme.css");
            scene.getRoot().getStyleClass().remove("light-theme");
            currentTheme = 0;
        }

        logger.info("Theme toggled to: " + (currentTheme == 0 ? "Dark" : "Light"));
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Selects the specified tab.
     * @param tabIndex The index of the tab to select (0-based)
     */
    public void selectTab(int tabIndex) {
        if (tabPane != null && tabIndex >= 0 && tabIndex < tabPane.getTabs().size()) {
            tabPane.getSelectionModel().select(tabIndex);
        }
    }
}
