package ca.cmpt276.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Populates the RestaurantManager singleton with data.
 */
final class RestaurantManagerFactory {
    public static final String PATH_TO_CSV_DATA = "assets/ProjectData/restaurants_itr1.csv";

    private RestaurantManagerFactory() throws InstantiationException {
        throw new InstantiationException(getClass().getName() + " is uninstantiable by design and can never be instantiated");
    }

    /**
     * Uses .csv data to populate the RestaurantManager
     * @param pathToCsvData a String path to the data
     * @return the populated manager
     */
    static RestaurantManager populateFromCsv(String pathToCsvData)
            throws IOException, IllegalArgumentException {
        RestaurantManager manager = new RestaurantManager();
        File csvData = new File(pathToCsvData);
        validateDataFile(csvData);

        return manager;
    }

    private static void validateDataFile(File csvData) throws IOException, IllegalArgumentException {
        if(!csvData.exists()) {
            throw new FileNotFoundException(csvData.getPath() + " doesn't exist");
        }
        if(!csvData.isFile()) {
            throw new IllegalArgumentException(csvData.getPath() + " is not a file");
        }
        if(!csvData.canRead()) {
            throw new IOException("Don't have permission to read file " + csvData.getPath());
        }
    }
}
