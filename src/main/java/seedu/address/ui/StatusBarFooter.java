package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private Label saveLocationStatus;

    private Clock clock = Clock.systemDefaultZone();

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation) {
        super(FXML);

        try {
            // Format the timestamp
            String timestamp = DATETIME_FORMAT.format(LocalDateTime.now(clock));

            // Format the path - with safety checks
            String formattedPath;
            if (saveLocation != null) {
                try {
                    Path base = Paths.get(".").toAbsolutePath().normalize();
                    // Check if relativization is possible (saveLocation must be a descendant of base)
                    if (saveLocation.isAbsolute()
                            && saveLocation.normalize().toString().startsWith(base.toString())) {
                        formattedPath = base.relativize(saveLocation.normalize()).toString();
                    } else {
                        // Just use the filename if relativization isn't possible
                        formattedPath = saveLocation.getFileName().toString();
                    }
                } catch (IllegalArgumentException e) {
                    // Handle case where relativize fails (paths have different roots)
                    formattedPath = saveLocation.toString();
                    logger.warning("Could not relativize path: " + e.getMessage());
                }
            } else {
                formattedPath = "Unknown location";
                logger.warning("Save location is null");
            }

            // Set the status text
            saveLocationStatus.setText("Last Updated: " + timestamp + " • " + formattedPath);

        } catch (Exception e) {
            // Fallback in case of any exception
            logger.warning("Error setting save location: " + e.getMessage());
            saveLocationStatus.setText("Last Updated: "
                    + DATETIME_FORMAT.format(LocalDateTime.now(clock))
                    + " • " + "data/addressbook.json");
        }
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Updates the status bar text with the current time and save location.
     * Can be called to refresh the display.
     *
     * @param saveLocation The current save location
     */
    public void updateSaveLocation(Path saveLocation) {
        try {
            String timestamp = DATETIME_FORMAT.format(LocalDateTime.now(clock));
            String path = saveLocation != null ? saveLocation.getFileName().toString() : "Unknown";
            saveLocationStatus.setText("Last Updated: " + timestamp + " • " + path);
        } catch (Exception e) {
            logger.warning("Error updating save location: " + e.getMessage());
        }
    }
}
