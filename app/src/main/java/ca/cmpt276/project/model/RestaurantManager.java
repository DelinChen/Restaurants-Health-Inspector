package ca.cmpt276.project.model;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ca.cmpt276.project.model.RestaurantScanner.PATH_TO_RESTAURANT_CSV_FROM_ASSETS;
import static ca.cmpt276.project.model.RestaurantScanner.PATH_TO_RESTAURANT_CSV_FROM_SRC;


public final class RestaurantManager {
    private static RestaurantManager instance = null;
    private final Map<String, Restaurant> restaurants;      // maps Tracking Number -> Restaurant


    ////////////////////////////////////////////////////////////////////////////
    // Singleton pattern

    private RestaurantManager() {
        if(instance != null) {
            throw new IllegalStateException(getClass().getName() + " is a singleton with an existing instance and cannot be reinstantiated");
        }
        instance = this;
        restaurants = new HashMap<>();
    }

    public static RestaurantManager getInstance(Context anyContext) {
        if(instance == null) {
            try {
                instance = fromCsv(anyContext, PATH_TO_RESTAURANT_CSV_FROM_ASSETS, true);
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }
    public static RestaurantManager getInstance() {
        if(instance == null) {
            try {
                instance = fromCsv(PATH_TO_RESTAURANT_CSV_FROM_SRC);
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Delegate methods

    public boolean containsTrackingNumber(String trackingNumber) {
        return restaurants.containsKey(trackingNumber);
    }
    public boolean containsRestaurant(Restaurant restaurant) {
        return restaurants.containsValue(restaurant);
    }

    public Restaurant get(String trackingNumber) {
        return restaurants.get(trackingNumber);
    }

    public int size() {
        return restaurants.size();
    }

    public Set<String> trackingNumberSet() {
        return Collections.unmodifiableSet(restaurants.keySet());
    }
    public List<Restaurant> restaurants() {
        List<Restaurant> values = new ArrayList<>(restaurants.values());
        Collections.sort(values);
        return Collections.unmodifiableList(values);
    }
    public Set<Map.Entry<String, Restaurant>> entrySet() {
        return Collections.unmodifiableSet(restaurants.entrySet());
    }

    // Used when populating the RestuarantManager from CSV file
    protected Restaurant put(String trackingNumber, Restaurant restaurant) {
        return restaurants.put(trackingNumber, restaurant);
    }


    ////////////////////////////////////////////////////////////////////////
    // Factory methods

    /**
     * Uses .csv data to populate the RestaurantManager
     * @param pathToCsvData a String path to the data
     * @param hasHeaderRow
     * @return the populated manager
     */
    private static RestaurantManager fromCsv(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        RestaurantManager manager = new RestaurantManager();
        RestaurantScanner scanner = new RestaurantScanner(pathToCsvData, hasHeaderRow);
        scanUntilEnd(manager, scanner);
        return manager;
    }
    private static RestaurantManager fromCsv(String pathToCsvData) throws IOException {
        return fromCsv(pathToCsvData, true);
    }

    private static RestaurantManager fromCsv(Context anyContext, String pathToCsvData, boolean hasHeaderRow)
            throws IOException {
        InputStream dataStream = anyContext.getApplicationContext().getAssets()
                                    .open(pathToCsvData);
        RestaurantManager manager = new RestaurantManager();
        RestaurantScanner scanner = new RestaurantScanner(dataStream, hasHeaderRow);
        scanUntilEnd(manager, scanner);
        return manager;
    }


    private static void scanUntilEnd(RestaurantManager manager, RestaurantScanner scanner) {
        while(scanner.hasNextLine()) {
            Restaurant result = scanner.nextRestaurant();
            manager.put(result.trackingNumber, result);
        }
        scanner.close();
    }
}
