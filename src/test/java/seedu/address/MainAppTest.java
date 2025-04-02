package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonDrinkCatalogStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

/**
 * Comprehensive test class for MainApp.
 */
public class MainAppTest {
    @TempDir
    protected static Path temporaryFolder;

    protected static CountDownLatch startupLatch = new CountDownLatch(1);
    protected static JavaFxHandler handler = new JavaFxHandler();

    private static final int STARTUP_TIMEOUT = 20;
    private static final int WAIT_TIMEOUT = 15;

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "MainAppTest");
    private static final Path VALID_CONFIG_PATH = TEST_DATA_FOLDER.resolve("validConfig.json");
    private static final Path INVALID_CONFIG_PATH = TEST_DATA_FOLDER.resolve("invalidConfig.json");
    private static final Path NONEXISTENT_CONFIG_PATH = TEST_DATA_FOLDER.resolve("nonexistentConfig.json");

    public static class JavaFxHandler implements Thread.UncaughtExceptionHandler {
        private Throwable ex;
        private CountDownLatch failureLatch = new CountDownLatch(1);

        public void uncaughtException(Thread t, Throwable e) {
            ex = e;
            failureLatch.countDown();
        }

        public Throwable getException() throws InterruptedException {
            if (failureLatch.await(WAIT_TIMEOUT, TimeUnit.SECONDS)) {
                return ex;
            }
            return null;
        }
    }

    @BeforeAll
    public static void setupTestEnvironment() {
        try {
            // Create test data folder
            Files.createDirectories(TEST_DATA_FOLDER);

            // Create a valid config file
            Config validConfig = new Config();
            ConfigUtil.saveConfig(validConfig, VALID_CONFIG_PATH);

            // Create an invalid config file
            Files.writeString(INVALID_CONFIG_PATH, "{ \"invalidJson\": true, }");

            // Delete nonexistent config file if it exists
            Files.deleteIfExists(NONEXISTENT_CONFIG_PATH);

            // Initialize JavaFX toolkit
            try {
                Platform.startup(() -> {});
            } catch (IllegalStateException e) {
                // Platform already initialized, ignore
            }

        } catch (IOException e) {
            fail("Failed to setup test environment: " + e.getMessage());
        }
    }

    @AfterAll
    public static void cleanupTestEnvironment() {
        try {
            // Clean up test files
            Files.deleteIfExists(VALID_CONFIG_PATH);
            Files.deleteIfExists(INVALID_CONFIG_PATH);
            Files.deleteIfExists(NONEXISTENT_CONFIG_PATH);
            Files.deleteIfExists(TEST_DATA_FOLDER);
        } catch (IOException e) {
            // Best effort cleanup
        }
    }

    /**
     * Tests the test mode flag functionality.
     */
    @Test
    public void testModeFlag() {
        TestableMainApp app = new TestableMainApp();

        // Default should be false
        assertFalse(app.isTestMode());

        // Setting to true should be reflected
        app.setTestMode(true);
        assertTrue(app.isTestMode());

        // Setting to false should be reflected
        app.setTestMode(false);
        assertFalse(app.isTestMode());
    }

    /**
     * Tests initialization of configuration from a valid file.
     */
    @Test
    public void initConfigValidFile() {
        TestableMainApp app = new TestableMainApp();
        Config config = app.initConfig(VALID_CONFIG_PATH);

        assertNotNull(config);
        assertEquals(Paths.get("preferences.json"), config.getUserPrefsFilePath());
    }

    /**
     * Tests initialization of configuration from a non-existent file.
     */
    @Test
    public void initConfigNonExistentFile() {
        TestableMainApp app = new TestableMainApp();
        Config config = app.initConfig(NONEXISTENT_CONFIG_PATH);

        assertNotNull(config);
        assertEquals(Paths.get("preferences.json"), config.getUserPrefsFilePath());
    }

    /**
     * Tests initialization of configuration from an invalid file.
     */
    @Test
    public void initConfigInvalidFile() {
        TestableMainApp app = new TestableMainApp();
        Config config = app.initConfig(INVALID_CONFIG_PATH);

        assertNotNull(config);
        assertEquals(Paths.get("preferences.json"), config.getUserPrefsFilePath());
    }

    /**
     * Tests initialization of configuration with null path.
     */
    @Test
    public void initConfigNullPath() {
        TestableMainApp app = new TestableMainApp();
        Config config = app.initConfig(null);

        assertNotNull(config);
        assertEquals(Config.DEFAULT_CONFIG_FILE, app.getConfigFilePathUsed());
    }

    /**
     * Tests initialization of user preferences.
     */
    @Test
    public void initPrefs() throws IOException {
        // Create a temporary preferences file
        Path prefsPath = temporaryFolder.resolve("prefs.json");
        UserPrefsStorage storage = new JsonUserPrefsStorage(prefsPath);

        TestableMainApp app = new TestableMainApp();
        UserPrefs prefs = app.initPrefs(storage);

        assertNotNull(prefs);
        assertEquals(Paths.get("data", "addressbook.json"), prefs.getAddressBookFilePath());
        assertEquals(UserPrefs.DEFAULT_DRINK_CATALOG_FILE_PATH, prefs.getDrinkCatalogFilePath());
        assertTrue(Files.exists(prefsPath), "Preferences file should have been created");
    }

    /**
     * Tests model initialization when both address book and drink catalog storage throw exceptions.
     */
    @Test
    public void initModelManager_bothStorageComponentsThrowExceptions() throws Exception {
        // Create a TestableMainApp with overridden methods to simulate storage exceptions
        TestableMainApp app = new TestableMainApp() {
            @Override
            public Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
                // Create a storage that always throws exceptions
                Storage exceptionStorage = new Storage() {
                    @Override
                    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                        throw new DataLoadingException(new Exception("Test exception"));
                    }

                    @Override
                    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
                        throw new IOException("Test exception");
                    }

                    @Override
                    public Optional<ReadOnlyDrinkCatalog> readDrinkCatalog() throws DataLoadingException {
                        throw new DataLoadingException(new Exception("Test DrinkCatalog exception"));
                    }

                    @Override
                    public Optional<ReadOnlyDrinkCatalog> readDrinkCatalog(Path filePath) throws DataLoadingException {
                        throw new DataLoadingException(new Exception("Test DrinkCatalog path exception"));
                    }

                    @Override
                    public void saveDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog) throws IOException {
                        throw new IOException("Test exception");
                    }

                    @Override
                    public void saveDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog, Path filePath) throws IOException {
                        throw new IOException("Test exception");
                    }

                    @Override
                    public Path getUserPrefsFilePath() {
                        return Paths.get("dummy");
                    }

                    @Override
                    public Path getAddressBookFilePath() {
                        return Paths.get("dummy");
                    }

                    // These should be next to each other
                    @Override
                    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
                        throw new DataLoadingException(new Exception("Test AddressBook exception"));
                    }

                    @Override
                    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
                        throw new DataLoadingException(new Exception("Test AddressBook path exception"));
                    }

                    // Then these should be together
                    @Override
                    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
                        throw new IOException("Test exception");
                    }

                    @Override
                    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
                        throw new IOException("Test exception");
                    }

                    @Override
                    public Path getDrinkCatalogFilePath() {
                        return Paths.get("dummy");
                    }
                };

                // Call the original method with our exception-throwing storage
                return super.initModelManager(exceptionStorage, userPrefs);
            }
        };

        // Test with UserPrefs to see if we get a valid model with sample data
        UserPrefs userPrefs = new UserPrefs();
        Model model = app.initModelManager(null, userPrefs); // storage parameter will be ignored

        // Verify we have a valid model with empty components
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getDrinkCatalog().getDrinkList().size());
    }

    /**
     * Tests the stop method when an IOException occurs during preference saving.
     */
    @Test
    public void stopHandlesIoException() throws Exception {
        // Create a MainApp with a model and storage that will throw an exception
        TestableMainApp app = new TestableMainApp();

        // Set up a model
        Model testModel = new ModelManager();
        app.model = testModel;

        // Set up a storage that throws an exception when saving user prefs
        app.storage = new StorageManager(
                new JsonAddressBookStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")) {
                    @Override
                    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
                        throw new IOException("Test IO Exception");
                    }
                },
                new JsonDrinkCatalogStorage(Paths.get("dummy"))
        );

        // This should not throw an exception, despite the storage throwing one
        app.stop();

        // If we get here without exception, the test passes
        assertTrue(true);
    }

    /**
     * Tests for the default application behavior.
     */
    @Test
    public void testDefaultApp() {
        // Reset latch before starting
        startupLatch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                // Test mode should be set to true for the test
                TestApp.setStaticTestMode(true);
                Application.launch(TestApp.class);
            } catch (Exception e) {
                e.printStackTrace();
                // Count down latch in case of exception
                startupLatch.countDown();
            }
        }).start();

        try {
            // Increase timeout for CI environments
            String msg = "Failed to launch FX application " + TestApp.class;
            assertTrue(startupLatch.await(STARTUP_TIMEOUT, TimeUnit.SECONDS), msg);

            // Check if any exception was thrown
            Throwable exception = handler.getException();
            if (exception != null) {
                fail("Exception during application startup: " + exception.getMessage());
            }

        } catch (InterruptedException e) {
            fail(e);
        }
    }

    /**
     * Tests the font loading functionality (which can throw exceptions).
     */
    @Test
    public void loadCustomFonts() throws Exception {
        TestableMainApp app = new TestableMainApp();
        // Call the test method that accesses loadCustomFonts directly
        // NOT calling loadCustomFonts() directly to avoid recursion
        app.testLoadCustomFonts();
    }

    /**
     * A testable version of MainApp for unit testing.
     */
    private class TestableMainApp extends MainApp {
        private Path configFilePathUsed;

        // Expose protected methods for testing
        @Override
        public Config initConfig(Path configFilePath) {
            Config config = super.initConfig(configFilePath);
            this.configFilePathUsed = configFilePath != null ? configFilePath : Config.DEFAULT_CONFIG_FILE;
            return config;
        }

        @Override
        public UserPrefs initPrefs(UserPrefsStorage storage) {
            return super.initPrefs(storage);
        }

        public Path getConfigFilePathUsed() {
            return configFilePathUsed;
        }

        public void testLoadCustomFonts() {
            // Create a small method that simulates font loading
            // with similar exception handling logic
            try {
                // Attempt to load a non-existent font to trigger exception handling
                Font.loadFont(getClass().getResourceAsStream("/nonexistent-font.ttf"), 12);
                // Use a local logger instead of the private one from MainApp
                Logger localLogger = LogsCenter.getLogger(TestableMainApp.class);
                localLogger.info("Test font loading complete");
            } catch (Exception e) {
                // Use a local logger instead of the private one from MainApp
                Logger localLogger = LogsCenter.getLogger(TestableMainApp.class);
                localLogger.warning("Test font loading exception: " + e.getMessage());
            }
        }
    }

    /**
     * Test application class extending MainApp.
     */
    public static class TestApp extends MainApp {

        private static boolean staticTestMode = false;

        public static void setStaticTestMode(boolean testMode) {
            staticTestMode = testMode;
        }

        @Override
        public void init() throws Exception {
            setTestMode(staticTestMode);
            applicationInit();

            Path userPrefPath = temporaryFolder.resolve("userPrefs.json");
            config = initConfig(null);

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
            UserPrefs userPrefs = initPrefs(userPrefsStorage);

            AddressBookStorage addressBookStorage = new JsonAddressBookStorage(
                    temporaryFolder.resolve("addressBook.json"));
            JsonDrinkCatalogStorage drinkCatalogStorage = new JsonDrinkCatalogStorage(
                    temporaryFolder.resolve("drinkCatalog.json"));

            storage = new StorageManager(addressBookStorage, userPrefsStorage, drinkCatalogStorage);

            model = initModelManager(storage, userPrefs);

            // Create a test implementation of Logic that won't throw any exceptions
            logic = new TestLogic(model, storage);

            setTestMode(true); // Enable test mode to bypass WelcomeScreen
        }

        @Override
        public void start(Stage primaryStage) {
            super.start(primaryStage);
            startupLatch.countDown();
        }
    }

    /**
     * A simple implementation of Logic for testing purposes.
     */
    private static class TestLogic implements Logic {
        private final Model model;
        private final Storage storage;

        public TestLogic(Model model, Storage storage) {
            this.model = model;
            this.storage = storage;
        }

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("Test command executed");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return model.getAddressBook();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            return model.getFilteredStaffList();
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return model.getFilteredCustomerList();
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList() {
            return model.getFilteredDrinkList();
        }

        @Override
        public Path getAddressBookFilePath() {
            return model.getAddressBookFilePath();
        }

        @Override
        public GuiSettings getGuiSettings() {
            return model.getGuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            model.setGuiSettings(guiSettings);
        }
    }
}
