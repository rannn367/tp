package seedu.address;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.UiManager;

import static seedu.address.testutil.TypicalDrinks.getTypicalDrinkCatalog;

/**
 * Launcher for TestApp to be used in process-based tests.
 */
public class DefaultAppLauncher {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    public static void main(String[] args) {
        try {
            Application.launch(TestApp.class, args);
            System.exit(0); // Exit successfully if no errors
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); // Exit with error code on failure
        }
    }

    /**
     * Test application class extending MainApp.
     */
    public static class TestApp extends MainApp {

        @Override
        public void init() throws Exception {
            // Always set logs to severe
            config = new Config();
            config.setLogLevel(Level.SEVERE);
            LogsCenter.init(config);

            applicationInit();
            Path userPrefPath = File.createTempFile("userPrefs", ".json").toPath();

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
            UserPrefs userPrefs = initPrefs(userPrefsStorage);
            AddressBookStorage addressBookStorage = new JsonAddressBookStorage(TYPICAL_PERSONS_FILE);
            storage = new StorageManager(addressBookStorage, userPrefsStorage, getTypicalDrinkCatalog());

            model = initModelManager(storage, userPrefs);

            logic = new LogicManager(model, storage);

            ui = new UiManager(logic);
        }

        @Override
        public void start(Stage primaryStage) {
            super.start(primaryStage);
            Platform.runLater(() -> Platform.exit());
        }
    }
}
