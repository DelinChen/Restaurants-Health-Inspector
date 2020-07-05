package ca.cmpt276.project.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ca.cmpt276.project.model.RestaurantScanner.PATH_TO_RESTAURANT_CSV;


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

    public static RestaurantManager getInstance() {
        if(instance == null) {
            try {
                instance = fromCsv(PATH_TO_RESTAURANT_CSV);
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
        return Collections.unmodifiableList(
                new ArrayList<>(restaurants.values()) );
    }
    public Set<Map.Entry<String, Restaurant>> entrySet() {
        return Collections.unmodifiableSet(restaurants.entrySet());
    }

    // Used when populating the RestuarantManager from CSV file
    protected Restaurant put(String trackingNumber, Restaurant restaurant) {
        return restaurants.put(trackingNumber, restaurant);
    }


    ////////////////////////////////////////////////////////////////////////
    // Factory method

    /**
     * Uses .csv data to populate the RestaurantManager
     * @param pathToCsvData a String path to the data
     * @param headerRow
     * @return the populated manager
     */
    private static RestaurantManager fromCsv(String pathToCsvData, boolean headerRow) throws IOException {
        RestaurantManager manager = new RestaurantManager();
        RestaurantScanner scanner = new RestaurantScanner(pathToCsvData, headerRow);
        while(scanner.hasNextLine()) {
            Restaurant result = scanner.nextRestaurant();
            manager.put(result.trackingNumber, result);
        }
        return manager;
    }
    private static RestaurantManager fromCsv(String pathToCsvData) throws IOException {
        return fromCsv(pathToCsvData, true);
    }
}
