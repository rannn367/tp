package seedu.address;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;

/**
 * Test class for MainApp.
 */
public class MainAppTest {

    @TempDir
    protected static Path tempDir;

    private static final int UI_THREAD_JOIN_TIMEOUT = 20000; // Increased timeout
    private static Thread uiThread;

    /**
     * Tests the default application behavior.
     *
     * @throws Exception if any error occurs during the test.
     */
    @Test
    public void testDefaultApp() throws Exception {
        uiThread = new Thread(() -> {
            Application.launch(
                 TestApp.class,
                "--config=" + tempDir.resolve("testConfig.json").toString()
            );
            Platform.exit();
        });
        uiThread.start();
        uiThread.join(UI_THREAD_JOIN_TIMEOUT);
    }

    /**
     * Initializes the JavaFX application thread and sets up temporary configuration files.
     *
     * @throws Exception if any error occurs during initialization.
     */
    @BeforeAll
    public static void initFX() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Create a temporary preferences file for testing
        Path tempPrefPath = tempDir.resolve("preferences.json");
        Files.deleteIfExists(tempPrefPath);

        // Create a temporary config file for testing
        Path tempConfigPath = tempDir.resolve("testConfig.json");
        ObjectNode configNode = mapper.createObjectNode();
        configNode.put("logLevel", "INFO");
        configNode.put("userPrefsFilePath", tempPrefPath.toString());

        // Write the config to the temporary file
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String configJson = ow.writeValueAsString(configNode);
        Files.writeString(tempConfigPath, configJson);
    }

    /**
     * Exits the JavaFX application thread.
     */
    @AfterAll
    public static void exit() {
    }

    /**
     * Test application class extending MainApp.
     */
    public static class TestApp extends MainApp {
        @Override
        public void init() throws Exception {
            super.init();
        }

        @Override
        public void start(Stage primaryStage) {
            super.start(primaryStage);
        }

        @Override
        protected Config initConfig(Path configFilePath) {
            return super.initConfig(tempDir.resolve("testConfig.json"));
        }
    }
}
