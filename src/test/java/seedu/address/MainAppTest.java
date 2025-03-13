package seedu.address;

import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
    
    private static final int STARTUP_TIMEOUT = 15;
    protected static CountDownLatch startupLatch = new CountDownLatch(2);

    /**
     * Tests the default application behavior.
     */
    @Test
    public void testDefaultApp() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void initFileSystem() throws Exception {
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
     * Initializes the JavaFX application thread and sets up temporary configuration files.
     */
    @BeforeAll
    public static void initFX() {
        
        Platform.setImplicitExit(false);
        Platform.startup(startupLatch::countDown);
        
        new Thread(() -> {
            Application.launch(TestApp.class, "--config=" + tempDir.resolve("testConfig.json").toString());
        }).start();

        try {
            String msg = "Failed to launch FX application " + TestApp.class;
            Assertions.assertTrue(startupLatch.await(STARTUP_TIMEOUT, TimeUnit.SECONDS), msg);
        } catch (InterruptedException e) {
            fail(e);
        }
    }

    /**
     * Exits the JavaFX application thread.
     */
    @AfterAll
    public static void teardownOnce() {
        Platform.runLater(() -> {
            Platform.exit();
        });
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
            startupLatch.countDown();
        }

        @Override
        protected Config initConfig(Path configFilePath) {
            return super.initConfig(tempDir.resolve("testConfig.json"));
        }
    }
}
