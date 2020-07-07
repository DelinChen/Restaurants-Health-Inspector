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

    private RestaurantManager(Map<String, Restaurant> restaurants) {
        if(instance != null) {
            throw new IllegalStateException(getClass().getName() + " is a singleton with an existing instance and cannot be reinstantiated");
        }
        instance = this;
        this.restaurants = Collections.unmodifiableMap(restaurants);
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


    ////////////////////////////////////////////////////////////////////////
    // Factory methods

    /**
     * Uses .csv data to populate the RestaurantManager
     * @param pathToCsvData a String path to the data
     * @param hasHeaderRow
     * @return the populated manager
     */
    private static RestaurantManager fromCsv(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        RestaurantScanner scanner = new RestaurantScanner(pathToCsvData, hasHeaderRow);
        Map<String, Restaurant> restaurants = scanner.scanAllRestaurants();

        RestaurantManager manager = new RestaurantManager(restaurants);
        return manager;
    }
    private static RestaurantManager fromCsv(String pathToCsvData) throws IOException {
        return fromCsv(pathToCsvData, true);
    }

    private static RestaurantManager fromCsv(Context anyContext, String pathToCsvData, boolean hasHeaderRow)
            throws IOException {
        InputStream dataStream = anyContext.getApplicationContext().getAssets().open(pathToCsvData);
        RestaurantScanner restaurantScanner = new RestaurantScanner(anyContext, dataStream, hasHeaderRow);
        Map<String, Restaurant> restaurants = restaurantScanner.scanAllRestaurants();

        RestaurantManager manager = new RestaurantManager(restaurants);
        return manager;
    }


}
