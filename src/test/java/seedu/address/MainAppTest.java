package seedu.address;

import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;
import seedu.address.logic.LogicManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonDrinkCatalogStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.UiManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Test class for MainApp.
 */
public class MainAppTest {
    
    private static final int STARTUP_TIMEOUT = 10;
    private static final int WAIT_TIMEOUT = 15;

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data");
    private static final Path TYPICAL_DRINKS_FILE = TEST_DATA_FOLDER.resolve("JsonSerializableAddressBookTest")
            .resolve("validDrinkCatalog.json");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("JsonDrinkCatalogStorageTest")
            .resolve("typicalPersonsAddressBook.json");
    private static final Path TYPICAL_CONFIG_FILE = TEST_DATA_FOLDER.resolve("ConfigUtilTest")
            .resolve("TypicalConfig.json");

    @TempDir
    protected static Path temporaryFolder;

    protected static CountDownLatch startupLatch = new CountDownLatch(1);
    protected static JavaFXHandler handler = new JavaFXHandler();
    public static class JavaFXHandler implements Thread.UncaughtExceptionHandler, Executable{
        private Throwable ex;
        private CountDownLatch failureLatch = new CountDownLatch(1);

        public void uncaughtException(Thread t, Throwable e) {
            ex = e;
            failureLatch.countDown();
        }

        public void execute() throws Throwable {
            if (failureLatch.await(WAIT_TIMEOUT, TimeUnit.SECONDS)) {
                throw ex;
            }
        }
    }

    /**
     * Tests the default application behavior.
     */
    @Test
    public void testDefaultApp() {
        new Thread(() -> {
            Application.launch(TestApp.class);
        }).start();

        try {
            String msg = "Failed to launch FX application " + TestApp.class;
            Assertions.assertTrue(startupLatch.await(STARTUP_TIMEOUT, TimeUnit.SECONDS), msg);
        } catch (InterruptedException e) {
            fail(e);
        }

        Assertions.assertDoesNotThrow(handler);
    }

    /**
     * Exits the JavaFX application thread.
     */
    @AfterAll
    public static void teardownOnce() {
        temporaryFolder.toFile().setWritable(true);
        Platform.runLater(() -> Platform.exit());
    }

    @BeforeAll
    public static void initFx() {
        Platform.startup(() -> Thread.currentThread().setUncaughtExceptionHandler(handler));
    }

    /**
     * Test application class extending MainApp.
     */
    public static class TestApp extends MainApp {

        @Override
        public void init() throws Exception {
            applicationInit();

            Path userPrefPath = temporaryFolder.resolve("userPrefs.json");
            config = initConfig(TYPICAL_CONFIG_FILE);

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
            UserPrefs userPrefs = initPrefs(userPrefsStorage);
            AddressBookStorage addressBookStorage = new JsonAddressBookStorage(TYPICAL_PERSONS_FILE);
            JsonDrinkCatalogStorage drinkCatalogStorage = new JsonDrinkCatalogStorage(TYPICAL_DRINKS_FILE);

            storage = new StorageManager(addressBookStorage, userPrefsStorage, drinkCatalogStorage);

            model = initModelManager(storage, userPrefs);

            logic = new LogicManager(model, storage);

            ui = new UiManager(logic);
        }

        @Override
        public void start(Stage primaryStage) {
            super.start(primaryStage);
            startupLatch.countDown();
        }
    }
}