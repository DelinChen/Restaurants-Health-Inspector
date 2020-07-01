package ca.cmpt276.project.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestaurantManager implements Iterable<Restaurant> {
    private static RestaurantManager instance = null;
    private final List<Restaurant> restaurants;

    ////////////////////////////////////////////////////////////
    // Singleton pattern

    private RestaurantManager() {
        restaurants = new ArrayList<>();
    }

    public RestaurantManager getInstance() {
        if(instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }


    ////////////////////////////////////////////////////////////
    // Delegate methods

    public Restaurant get(int index) {
        return restaurants.get(index);
    }
    public int indexOf(Object restaurant) {
        return restaurants.indexOf(restaurant);
    }
    public void add(Restaurant restaurant) {
        restaurants.add(restaurant);
    }
    public Restaurant remove(int index) {
        return restaurants.remove(index);
    }
    public boolean remove(Object restaurant) {
        return restaurants.remove(restaurant);
    }
    public void clear() {
        restaurants.clear();
    }


    @NonNull
    @Override
    public Iterator<Restaurant> iterator() {
        return restaurants.iterator();
    }
}
