package seedu.address;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.address.commons.core.Config;

public class MainAppTest {
    
    @TempDir
    static Path tempDir;

    @Test
    public void testDefaultApp() throws Exception {
        Thread.sleep(5000);
    }

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

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String configJson = ow.writeValueAsString(configNode);
        Files.writeString(tempConfigPath, configJson);

        new Thread(() -> Application.launch(
                MainApp.class,
                "--config=" + tempDir.resolve("testConfig.json").toString()
        )).start();
    }

    @AfterAll
    public static void exit() {
        Platform.exit();
    }

    public class TestApp extends MainApp {
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