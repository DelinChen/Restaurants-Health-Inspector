package ca.cmpt276.project.model;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RestaurantManager {
    private static RestaurantManager instance = null;
    private final Map<String, Restaurant> restaurants;      // maps Tracking Number -> Restaurant

    ////////////////////////////////////////////////////////////
    // Singleton pattern

    RestaurantManager() {
        if(instance != null) {
            throw new UnsupportedOperationException(getClass().getName() + " is a singleton with an existing instance and cannot be reinstantiated");
        }

        restaurants = new HashMap<>();
    }

    public static RestaurantManager getInstance() throws IOException {
        if(instance == null) {
            instance = RestaurantManagerFactory.populateFromCsv(RestaurantManagerFactory.PATH_TO_CSV_DATA);
        }
        return instance;
    }


    ////////////////////////////////////////////////////////////
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
}
