package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Utility class for background image handling.
 */
public class BackgroundImageManager {
    private static final Logger logger = LogsCenter.getLogger(BackgroundImageManager.class);

    // Cache loaded images to improve performance
    private static final Map<String, Image> imageCache = new HashMap<>();

    /**
     * Preloads background images to improve UI responsiveness.
     */
    public static void preloadBackgroundImages() {
        String[] backgrounds = {
                "/images/cafe-welcome-bg.jpg",
                "/images/background1.png",
                "/images/background2.png",
                "/images/background3.png",
                "/images/background4.png",
                "/images/background5.png"
        };

        for (String path : backgrounds) {
            try {
                // Load image in background thread
                Thread t = new Thread(() -> {
                    try {
                        Image img = new Image(BackgroundImageManager.class.getResourceAsStream(path));
                        imageCache.put(path, img);
                        logger.fine("Preloaded image: " + path);
                    } catch (Exception e) {
                        logger.warning("Failed to preload image " + path + ": " + e.getMessage());
                    }
                });
                t.setDaemon(true);
                t.start();
            } catch (Exception e) {
                logger.warning("Error starting background image preload: " + e.getMessage());
            }
        }
    }

    /**
     * Gets a background image, using cached version if available.
     *
     * @param path The resource path to the image
     * @return The Image object
     */
    public static Image getBackgroundImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        try {
            Image img = new Image(BackgroundImageManager.class.getResourceAsStream(path));
            imageCache.put(path, img);
            return img;
        } catch (Exception e) {
            logger.warning("Failed to load image " + path + ": " + e.getMessage());
            // Return a placeholder or null
            return null;
        }
    }

    /**
     * Sets a background image on a region using CSS.
     *
     * @param region The region to apply the background to
     * @param imagePath The resource path to the image
     */
    public static void setBackgroundImage(Region region, String imagePath) {
        if (region == null) return;

        String url = BackgroundImageManager.class.getResource(imagePath).toExternalForm();
        region.setStyle("-fx-background-image: url('" + url + "'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");
    }
}