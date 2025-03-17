package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @FXML
    private Label saveLocationStatus;

    private Clock clock = Clock.systemDefaultZone();

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        saveLocationStatus.setText("Last Updated: " + DATETIME_FORMAT.format(LocalDateTime.now(clock))
                + " • " + Paths.get(".").toAbsolutePath().normalize().relativize(saveLocation));
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public void setClock(Clock clock) {
        this.clock = clock;
    }

}
