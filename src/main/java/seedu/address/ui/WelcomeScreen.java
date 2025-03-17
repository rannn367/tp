package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * The welcome screen of the app.
 */
public class WelcomeScreen extends UiPart<Region> {

    private static final String FXML = "WelcomeScreen.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Logic logic;
    private final Stage primaryStage;

    @FXML
    private Button enterButton;

    @FXML
    private StackPane welcomePane;

    /**
     * Creates a welcome screen with the given parameters.
     */
    public WelcomeScreen(Stage primaryStage, Logic logic) {
        super(FXML);
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Set the title
        primaryStage.setTitle("CafeConnect");

        // Set minimum dimensions
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        // Create scene
        Scene scene = new Scene(getRoot());

        // Add default stylesheets
        scene.getStylesheets().add("view/DarkTheme.css");
        scene.getStylesheets().add("view/Extensions.css");
        scene.getStylesheets().add("view/CafeConnect.css");

        primaryStage.setScene(scene);
    }

    /**
     * Shows the welcome screen.
     */
    public void show() {
        logger.info("Showing welcome screen");
        primaryStage.show();
    }

    /**
     * Handles the enter button action.
     */
    @FXML
    private void handleEnterButtonAction() {
        logger.info("Enter button clicked, transitioning to main application");

        // Hide the welcome screen
        primaryStage.hide();

        // Create and display the main window
        MainWindow mainWindow = new MainWindow(primaryStage, logic);
        mainWindow.show();

        // Fill in the components of the main window
        mainWindow.fillInnerParts();
    }
}
