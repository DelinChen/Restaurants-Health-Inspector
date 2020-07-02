package ca.cmpt276.project.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RestaurantManager {
    private static RestaurantManager instance = null;
    private final Map<String, Restaurant> restaurants;      // maps Tracking Number -> Restaurant

    ////////////////////////////////////////////////////////////
    // Singleton pattern

    private RestaurantManager() {
        restaurants = new HashMap<>();
    }

    public RestaurantManager getInstance() {
        if(instance == null) {
            instance = new RestaurantManager();
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

    /* Shouldn't ever need to add/remove entries dynamically: all objects are encoded in dataset

    public Restaurant remove(String trackingNumber) {
        return restaurants.remove(trackingNumber);
    }

    public void clear() {
        restaurants.clear();
    }
    */
}
