package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;

/**
 * A class to access DrinkCatalog data stored as a json file on the hard disk.
 */
public class JsonDrinkCatalogStorage implements DrinkCatalogStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDrinkCatalogStorage.class);

    private Path filePath;

    public JsonDrinkCatalogStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getDrinkCatalogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDrinkCatalog> readDrinkCatalog() throws DataLoadingException {
        return readDrinkCatalog(filePath);
    }

    /**
     * Similar to {@link #readDrinkCatalog()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyDrinkCatalog> readDrinkCatalog(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        if (!FileUtil.isFileExists(filePath)) {
            logger.info("DrinkCatalog file " + filePath + " not found");
            return Optional.empty();
        }

        try {
            JsonSerializableDrinkCatalog jsonDrinkCatalog =
                    JsonUtil.readJsonFile(filePath,
                        JsonSerializableDrinkCatalog.class).get();
            return Optional.of(jsonDrinkCatalog.toModelType());
        } catch (IllegalValueException e) {
            logger.warning("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataLoadingException(e);
        }
    }

    @Override
    public void saveDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog) throws IOException {
        saveDrinkCatalog(drinkCatalog, filePath);
    }

    /**
     * Similar to {@link #saveDrinkCatalog(ReadOnlyDrinkCatalog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog, Path filePath) throws IOException {
        requireNonNull(drinkCatalog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDrinkCatalog(drinkCatalog), filePath);
    }
}
