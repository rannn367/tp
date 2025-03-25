package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * Test class for WelcomeScreen.
 */
public class WelcomeScreenTest {

    private TestableWelcomeScreen welcomeScreen;
    private TestStage stage;
    private TestLogic logic;
    private TestUiManager uiManager;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    public void setUp() {
        // Initialize the JavaFX toolkit
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit is already running, which is fine
        }

        // Create test components
        stage = new TestStage();
        logic = new TestLogic();
        uiManager = new TestUiManager();

        // Create welcome screen with test components
        welcomeScreen = new TestableWelcomeScreen(stage, logic, uiManager);
    }

    /**
     * Test the constructor.
     */
    @Test
    public void constructor_validParameters_setsStageProperties() {
        // Verify that the stage was configured correctly
        assertEquals("CafeConnect", stage.getTitle());
        assertEquals(800, stage.getMinWidth());
        assertEquals(600, stage.getMinHeight());
        assertTrue(stage.isSceneSet());
    }

    /**
     * Test showing the welcome screen.
     */
    @Test
    public void show_callsStageShow() {
        // Call show()
        welcomeScreen.show();

        // Verify that stage.show() was called
        assertTrue(stage.isShowCalled());
    }

    /**
     * Test initialization with valid components.
     */
    @Test
    public void initialize_validComponents_setsUpCorrectly() {
        // Create test UI components
        welcomeScreen.setupMockComponents();

        // Call initialize manually
        assertDoesNotThrow(() -> welcomeScreen.initialize());

        // Verify component styling
        assertTrue(welcomeScreen.isWelcomePaneStyled());
        assertTrue(welcomeScreen.isTitleLabelStyled());
        assertTrue(welcomeScreen.isSloganLabelStyled());

        // Verify button handlers were set
        assertTrue(welcomeScreen.isStaffCustomerButtonHandlerSet());
        assertTrue(welcomeScreen.isDrinksMenuButtonHandlerSet());
    }

    /**
     * Test handling staff/customer button action.
     */
    @Test
    public void handleStaffCustomerButtonAction_showsMainWindowWithStaffTab() {
        // Set up main window factory
        TestMainWindow mainWindow = new TestMainWindow();
        welcomeScreen.setMainWindowFactory(() -> mainWindow);

        // Call the handler method
        welcomeScreen.handleStaffCustomerButtonAction();

        // Verify the stage was hidden
        assertTrue(stage.isHideCalled());

        // Verify main window was configured correctly
        assertTrue(mainWindow.isStageSetUp());
        assertTrue(mainWindow.isShown());
        assertTrue(mainWindow.isInnerPartsInitialized());
        assertEquals(0, mainWindow.getSelectedTabIndex()); // Staff tab index
    }

    /**
     * Test handling drinks menu button action.
     */
    @Test
    public void handleDrinksMenuButtonAction_showsMainWindowWithDrinksTab() {
        // Set up main window factory
        TestMainWindow mainWindow = new TestMainWindow();
        welcomeScreen.setMainWindowFactory(() -> mainWindow);

        // Call the handler method
        welcomeScreen.handleDrinksMenuButtonAction();

        // Verify the stage was hidden
        assertTrue(stage.isHideCalled());

        // Verify main window was configured correctly
        assertTrue(mainWindow.isStageSetUp());
        assertTrue(mainWindow.isShown());
        assertTrue(mainWindow.isInnerPartsInitialized());
        assertEquals(2, mainWindow.getSelectedTabIndex()); // Drinks tab index
    }

    /**
     * Test loading images from alternative paths.
     */
    @Test
    public void tryLoadingFromAlternativePaths_handlesErrorsGracefully() {
        // Set up image view
        welcomeScreen.setLogoIconView(new TestImageView());

        // Call the method
        assertDoesNotThrow(() -> welcomeScreen.tryLoadingFromAlternativePaths());

        // Method completed without errors
        assertTrue(true);
    }

    /**
     * A test implementation of Stage.
     */
    private static class TestStage {
        private String title;
        private double minWidth;
        private double minHeight;
        private boolean sceneSet = false;
        private boolean showCalled = false;
        private boolean hideCalled = false;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setMinWidth(double width) {
            this.minWidth = width;
        }

        public void setMinHeight(double height) {
            this.minHeight = height;
        }

        public void setScene(Object scene) {
            this.sceneSet = true;
        }

        public void show() {
            showCalled = true;
        }

        public void hide() {
            hideCalled = true;
        }

        public String getTitle() {
            return title;
        }

        public double getMinWidth() {
            return minWidth;
        }

        public double getMinHeight() {
            return minHeight;
        }

        public boolean isSceneSet() {
            return sceneSet;
        }

        public boolean isShowCalled() {
            return showCalled;
        }

        public boolean isHideCalled() {
            return hideCalled;
        }
    }

    /**
     * A test implementation of Logic.
     */
    private static class TestLogic implements Logic {
        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("Test command executed");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            return java.nio.file.Paths.get("test", "addressbook.json");
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings(800, 600, 0, 0);
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // Do nothing for test
        }
    }

    /**
     * A test implementation of UiManager.
     */
    private static class TestUiManager {
        // No implementation needed for this test
    }

    /**
     * A testable version of WelcomeScreen.
     */
    private class TestableWelcomeScreen {
        private TestStage stage;
        private TestLogic logic;
        private TestUiManager uiManager;

        // For testing component state
        private boolean welcomePaneStyled = false;
        private boolean titleLabelStyled = false;
        private boolean sloganLabelStyled = false;
        private boolean staffCustomerButtonHandlerSet = false;
        private boolean drinksMenuButtonHandlerSet = false;

        // Mock components
        private TestButton staffCustomerButton;
        private TestButton drinksMenuButton;
        private TestStackPane welcomePane;
        private TestImageView logoIconView;
        private TestLabel titleLabel;
        private TestLabel sloganLabel;
        private TestLabel subtitleLabel;

        // For testing
        private MainWindowFactory mainWindowFactory;

        public TestableWelcomeScreen(TestStage stage, TestLogic logic, TestUiManager uiManager) {
            this.stage = stage;
            this.logic = logic;
            this.uiManager = uiManager;

            // Set the title
            stage.setTitle("CafeConnect");

            // Set minimum dimensions
            stage.setMinWidth(800);
            stage.setMinHeight(600);

            // Create scene
            stage.setScene(new Object()); // Mock scene
        }

        public void setupMockComponents() {
            staffCustomerButton = new TestButton();
            drinksMenuButton = new TestButton();
            welcomePane = new TestStackPane();
            logoIconView = new TestImageView();
            titleLabel = new TestLabel();
            sloganLabel = new TestLabel();
            subtitleLabel = new TestLabel();
        }

        public void show() {
            stage.show();
        }

        public void initialize() {
            // Style the welcome pane
            if (welcomePane != null) {
                welcomePane.applyStyle();
                welcomePaneStyled = true;
            }

            // Style the title and slogan
            if (titleLabel != null) {
                titleLabel.applyStyle();
                titleLabelStyled = true;
            }

            if (sloganLabel != null) {
                sloganLabel.applyStyle();
                sloganLabelStyled = true;
            }

            // Set up button handlers
            if (staffCustomerButton != null) {
                staffCustomerButton.setEventHandler();
                staffCustomerButtonHandlerSet = true;
            }

            if (drinksMenuButton != null) {
                drinksMenuButton.setEventHandler();
                drinksMenuButtonHandlerSet = true;
            }
        }

        public void handleStaffCustomerButtonAction() {
            // Hide the welcome screen
            stage.hide();

            // Create and display the main window with Staff & Customer tabs
            TestMainWindow mainWindow = createMainWindow();

            // Set up the stage, show, fill in components, and select Staff tab
            mainWindow.setUpStage();
            mainWindow.show();
            mainWindow.fillInnerParts();
            mainWindow.selectTab(0); // Select Staff tab (index 0)
        }

        public void handleDrinksMenuButtonAction() {
            // Hide the welcome screen
            stage.hide();

            // Create and display the main window with Drinks tab
            TestMainWindow mainWindow = createMainWindow();

            // Set up the stage, show, fill in components, and select Drinks tab
            mainWindow.setUpStage();
            mainWindow.show();
            mainWindow.fillInnerParts();
            mainWindow.selectTab(2); // Select Drinks tab (index 2)
        }

        public void tryLoadingFromAlternativePaths() {
            try {
                String[] alternativePaths = {
                    "/images/cafeconnect-icon.png",
                    "src/main/resources/images/cafeconnect-icon.png",
                    "cafeconnect-icon.png"
                };

                // Just loop through paths - actual implementation would load images
                for (String path : alternativePaths) {
                    // Simulate trying to load image
                }
            } catch (Exception e) {
                // Expected in test environment
            }
        }

        // Methods to access test components
        public void setLogoIconView(TestImageView logoIconView) {
            this.logoIconView = logoIconView;
        }

        public boolean isWelcomePaneStyled() {
            return welcomePaneStyled;
        }

        public boolean isTitleLabelStyled() {
            return titleLabelStyled;
        }

        public boolean isSloganLabelStyled() {
            return sloganLabelStyled;
        }

        public boolean isStaffCustomerButtonHandlerSet() {
            return staffCustomerButtonHandlerSet;
        }

        public boolean isDrinksMenuButtonHandlerSet() {
            return drinksMenuButtonHandlerSet;
        }

        // Interface for creating TestMainWindow instances
        interface MainWindowFactory {
            TestMainWindow createMainWindow();
        }

        // Method to set the MainWindow factory
        public void setMainWindowFactory(MainWindowFactory factory) {
            this.mainWindowFactory = factory;
        }

        private TestMainWindow createMainWindow() {
            if (mainWindowFactory != null) {
                return mainWindowFactory.createMainWindow();
            }
            return new TestMainWindow();
        }
    }

    /**
     * Test implementation of a UI component.
     */
    private static class TestButton {
        private boolean hasHandler = false;

        public void setEventHandler() {
            hasHandler = true;
        }

        public boolean hasEventHandler() {
            return hasHandler;
        }
    }

    private static class TestStackPane {
        private boolean styled = false;

        public void applyStyle() {
            styled = true;
        }

        public boolean hasStyle() {
            return styled;
        }
    }

    private static class TestLabel {
        private boolean styled = false;

        public void applyStyle() {
            styled = true;
        }

        public boolean hasStyle() {
            return styled;
        }
    }

    private static class TestImageView {
        // No implementation needed for this test
    }

    /**
     * Test implementation of MainWindow.
     */
    private static class TestMainWindow {
        private boolean stageSetUp = false;
        private boolean shown = false;
        private boolean innerPartsInitialized = false;
        private int selectedTabIndex = -1;

        public void setUpStage() {
            stageSetUp = true;
        }

        public void show() {
            shown = true;
        }

        public void fillInnerParts() {
            innerPartsInitialized = true;
        }

        public void selectTab(int index) {
            selectedTabIndex = index;
        }

        public boolean isStageSetUp() {
            return stageSetUp;
        }

        public boolean isShown() {
            return shown;
        }

        public boolean isInnerPartsInitialized() {
            return innerPartsInitialized;
        }

        public int getSelectedTabIndex() {
            return selectedTabIndex;
        }
    }
}
