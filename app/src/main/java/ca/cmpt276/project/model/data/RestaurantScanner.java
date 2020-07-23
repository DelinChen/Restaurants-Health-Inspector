package ca.cmpt276.project.model.data;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ca.cmpt276.project.model.data.RestaurantScanner.RestaurantCsvColumns.*;

public class RestaurantScanner extends CsvScanner {
    public static final String PATH_TO_RESTAURANT_CSV_FROM_SRC = "src/main/assets/ProjectData/restaurants_itr1.csv";
    public static final String PATH_TO_RESTAURANT_CSV_FROM_ASSETS = "ProjectData/restaurants_itr1.csv";


    ///////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public RestaurantScanner(InputStream input, boolean hasHeaderRow) {
        super(input, hasHeaderRow);
    }
    public RestaurantScanner(InputStream input) {
        this(input, true);
    }

    public RestaurantScanner(Context anyContext, InputStream input, boolean hasHeaderRow) {
        super(input, hasHeaderRow);
    }
    public RestaurantScanner(Context anyContext, InputStream input) {
        this(anyContext, input, true);
    }

    public RestaurantScanner(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        super(pathToCsvData, hasHeaderRow);
    }

    public RestaurantScanner(String pathToCsvData) throws IOException {
        this(pathToCsvData, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Methods

    public Restaurant nextRestaurant() {
        String line = super.nextLine();
        line = line.replace("\"", "");
        String[] buffer = Arrays.stream(line.split(","))
                .map(String::trim)
                .toArray(String[]::new);

        String trackingNumber = buffer[TRACKING_NUMBER];
        String name         = buffer[NAME];
        String address      = buffer[ADDRESS];
        String city         = buffer[CITY];
        double latitude     = Double.parseDouble(buffer[LATITUDE]);
        double longitude    = Double.parseDouble(buffer[LONGITUDE]);

        return new Restaurant(trackingNumber, name, address, city, latitude, longitude);
    }

    public Map<String, Restaurant> scanAllRestaurants() {
        Map<String, Restaurant> allRestaurants = new HashMap<>();
        while(hasNextLine()) {
            Restaurant nextResult = nextRestaurant();
            allRestaurants.put(nextResult.trackingNumber, nextResult);
        }
        close();
        return allRestaurants;
    }

    public List<Restaurant> scanAllRestaurantsAsList() {
        List<Restaurant> allRestaurants = new ArrayList<>();
        while(hasNextLine()) {
            Restaurant nextResult = nextRestaurant();
            allRestaurants.add(nextResult);
        }
        return allRestaurants;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // For clarity when parsing the csv data

    static final class RestaurantCsvColumns {
        private RestaurantCsvColumns() throws InstantiationException {
            // should never be instantiated
            throw new InstantiationException(getClass().getName() + "is not instantiable");
        }
        static final int TRACKING_NUMBER = 0;
        static final int NAME = 1;
        static final int ADDRESS = 2;
        static final int CITY = 3;
        static final int FACILITY_TYPE = 4;
        static final int LATITUDE = 5;
        static final int LONGITUDE = 6;
    }
}
