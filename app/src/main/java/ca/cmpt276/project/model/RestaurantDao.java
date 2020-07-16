package ca.cmpt276.project.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class RestaurantDao implements BaseDao<Restaurant> {
    @Transaction
    @Query("SELECT * FROM restaurants")
    public abstract LiveData<List<RestaurantDetails>> getAllRestaurantsDetails();

    @Transaction
    @Query("SELECT * FROM restaurants WHERE tracking_number = :trackingNumber LIMIT 1")
    public abstract RestaurantDetails findRestaurantDetailsWithTrackingNumber(String trackingNumber);

/*
    @Transaction
    @Query("SELECT * FROM restaurants WHERE name = :name")
    public abstract LiveData<List<RestaurantDetails>> findRestaurantDetailsWithName(String name);

    @Transaction
    @Query("SELECT * FROM restaurants WHERE city = :city")
    public abstract LiveData<List<RestaurantDetails>> findRetaurantDetailsInCity(String city);
*/
}
