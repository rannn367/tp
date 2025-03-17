package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.BackgroundImageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;
import seedu.address.ui.WelcomeScreen;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected Ui ui;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing CafeConnect ]===========================");
        super.init();

        // Load custom fonts first
        loadCustomFonts();

        // Preload background images
        BackgroundImageManager.preloadBackgroundImages();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Application initialization helper method.
     * Added for testing purposes.
     */
    protected void applicationInit() throws Exception {
        // This method exists to support testing
        logger.info("Application init called");
    }

    /**
     * Loads custom fonts for the application UI.
     */
    private void loadCustomFonts() {
        try {
            // Load Playfair Display font (serif for headings)
            Font.loadFont(getClass().getResourceAsStream("/fonts/PlayfairDisplay-Regular.ttf"), 12);
            Font.loadFont(getClass().getResourceAsStream("/fonts/PlayfairDisplay-Bold.ttf"), 12);
            Font.loadFont(getClass().getResourceAsStream("/fonts/PlayfairDisplay-Italic.ttf"), 12);

            // Load Lora font (serif for body text)
            Font.loadFont(getClass().getResourceAsStream("/fonts/Lora-Regular.ttf"), 12);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Lora-Bold.ttf"), 12);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Lora-Italic.ttf"), 12);

            logger.info("Custom fonts loaded successfully");
        } catch (Exception e) {
            logger.warning("Failed to load custom fonts: " + e.getMessage());
            // App will fall back to system fonts
        }
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting CafeConnect " + MainApp.VERSION);

        // Show welcome screen instead of directly going to main window
        WelcomeScreen welcomeScreen = new WelcomeScreen(primaryStage, logic);
        welcomeScreen.show();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    protected Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (Exception e) {
            logger.warning("Data file could not be read. Will be starting with an empty AddressBook: "
                    + StringUtil.getDetails(e));
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (Exception e) {
            logger.warning("Config file at " + configFilePathUsed
                    + " is not in the correct format or could not be read. "
                    + "Using default config properties: " + StringUtil.getDetails(e));
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (Exception e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " could not be read. "
                    + "Using default user prefs: " + StringUtil.getDetails(e));
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void stop() {
        logger.info("============================ Stopping CafeConnect =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
