package seedu.address.ui;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Utility class for background image handling with robust fallback mechanisms.
 */
public class BackgroundImageManager {
    private static final Logger logger = LogsCenter.getLogger(BackgroundImageManager.class);

    // Cache loaded images to improve performance
    private static final Map<String, Image> imageCache = new HashMap<>();

    // Alternative directories to check for image files if not found in classpath
    private static final String[] ALTERNATIVE_DIRECTORIES = {
        "src/main/resources",
        "build/resources/main",
        "resources",
        "docs/images",
        "docs/images/cafeconnect_graphics",
        "."
    };

    /**
     * Preloads background images to improve UI responsiveness.
     */
    public static void preloadBackgroundImages() {
        String[] backgrounds = {
            "/images/cafe-welcome-bg.jpg",
            "/images/cafeconnect-logo-with-slogan.png",
        };

        for (String path : backgrounds) {
            try {
                // Try to load from classpath first
                InputStream is = BackgroundImageManager.class.getResourceAsStream(path);
                if (is != null) {
                    Image img = new Image(is);
                    imageCache.put(path, img);
                    logger.info("Preloaded image: " + path);
                } else {
                    // If not found in classpath, try alternative locations
                    logger.warning("Failed to preload image " + path + ": Input stream must not be null");
                    tryLoadingFromAlternativeLocations(path);
                }
            } catch (Exception e) {
                logger.warning("Failed to preload image " + path + ": " + e.getMessage());
                tryLoadingFromAlternativeLocations(path);
            }
        }
    }

    /**
     * Attempts to load an image from various alternative locations.
     */
    private static void tryLoadingFromAlternativeLocations(String path) {
        // Extract just the filename from the path
        String filename = path.substring(path.lastIndexOf('/') + 1);
        logger.info("Trying to find " + filename + " in alternative locations");

        // Try each alternative directory
        for (String directory : ALTERNATIVE_DIRECTORIES) {
            String altPath = directory + path;
            File file = new File(altPath);

            if (file.exists() && file.isFile()) {
                try {
                    Image img = new Image(file.toURI().toString());
                    imageCache.put(path, img);
                    logger.info("Successfully loaded image from: " + altPath);
                    return;
                } catch (Exception e) {
                    logger.warning("Failed to load image from " + altPath + ": " + e.getMessage());
                }
            }

            // Also try without the leading slash
            if (path.startsWith("/")) {
                altPath = directory + path.substring(1);
                file = new File(altPath);

                if (file.exists() && file.isFile()) {
                    try {
                        Image img = new Image(file.toURI().toString());
                        imageCache.put(path, img);
                        logger.info("Successfully loaded image from: " + altPath);
                        return;
                    } catch (Exception e) {
                        logger.warning("Failed to load image from " + altPath + ": " + e.getMessage());
                    }
                }
            }

            // Try just with the filename (in case it's in the directory directly)
            altPath = directory + "/" + filename;
            file = new File(altPath);

            if (file.exists() && file.isFile()) {
                try {
                    Image img = new Image(file.toURI().toString());
                    imageCache.put(path, img);
                    logger.info("Successfully loaded image from: " + altPath);
                    return;
                } catch (Exception e) {
                    logger.warning("Failed to load image from " + altPath + ": " + e.getMessage());
                }
            }
        }

        logger.warning("Could not find image " + filename + " in any location");
    }

    /**
     * Gets a background image, using cached version if available.
     *
     * @param path The resource path to the image
     * @return The Image object or null if not found
     */
    public static Image getBackgroundImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        try {
            InputStream is = BackgroundImageManager.class.getResourceAsStream(path);
            if (is != null) {
                Image img = new Image(is);
                imageCache.put(path, img);
                return img;
            } else {
                // If not in cache and not found in classpath, try alternatives
                tryLoadingFromAlternativeLocations(path);
                return imageCache.getOrDefault(path, null);
            }
        } catch (Exception e) {
            logger.warning("Failed to load image " + path + ": " + e.getMessage());

            // Try alternatives as a last resort
            tryLoadingFromAlternativeLocations(path);
            return imageCache.getOrDefault(path, null);
        }
    }

    /**
     * Sets a background image on a region using CSS, with robust error handling.
     *
     * @param region The region to apply the background to
     * @param imagePath The resource path to the image
     */
    public static void setBackgroundImage(Region region, String imagePath) {
        if (region == null) {
            return;
        }

        try {
            // First check if we can get a direct URL to the resource
            URL resourceUrl = BackgroundImageManager.class.getResource(imagePath);

            if (resourceUrl != null) {
                // Resource exists in classpath
                String url = resourceUrl.toExternalForm();
                applyBackgroundStyle(region, url);
                return;
            }

            // If resource not in classpath, check if we've loaded it from an alternative location
            if (imageCache.containsKey(imagePath)) {
                // Use the file:// URL directly since we've already loaded it into the cache
                // We need to extract that URL from the loaded image
                Image cachedImage = imageCache.get(imagePath);
                String url = cachedImage.getUrl();

                if (url != null && !url.isEmpty()) {
                    applyBackgroundStyle(region, url);
                    return;
                }
            }

            // If we get here, we need to try to load the image first
            Image image = getBackgroundImage(imagePath);
            if (image != null && image.getUrl() != null) {
                applyBackgroundStyle(region, image.getUrl());
            } else {
                logger.warning("Could not set background image for " + imagePath);
            }
        } catch (Exception e) {
            logger.warning("Error setting background image: " + e.getMessage());
        }
    }

    /**
     * Applies the CSS background styling to a region.
     */
    private static void applyBackgroundStyle(Region region, String url) {
        region.setStyle("-fx-background-image: url('" + url + "'); "
                + "-fx-background-size: cover; "
                + "-fx-background-position: center;");
    }
}
