package ca.cmpt276.project.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao{
    @Insert
    void insert(Restaurant restaurant);

    //should be no use in this itr
    @Update
    void update(Restaurant restaurant);

    //No use on this itr too
    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurants")
    void deleteAllRestaurants();

    @Query("SELECT * FROM restaurants WHERE trackingNumber = :trackingNumber")
    LiveData<Restaurant> getRestaurantById(int trackingNumber);

    @Query("SELECT * FROM restaurants")
    LiveData<List<Restaurant>> getAllRestaurant();
}


