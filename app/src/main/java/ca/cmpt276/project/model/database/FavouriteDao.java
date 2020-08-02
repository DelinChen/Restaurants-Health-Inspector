package ca.cmpt276.project.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.Violation;

@Dao
public abstract class FavouriteDao implements BaseDao<Restaurant> {
/*
    @Query("SELECT * FROM favourites")
    public abstract LiveData<List<Restaurant>> getAllFavourites();

    @Query("SELECT * FROM favourites WHERE tracking_number = :trackingNumber LIMIT 1")
    public abstract Restaurant findRestaurantWithTrackingNumber(String trackingNumber);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void _insertRestaurant(Restaurant restaurant);

    @Update
    abstract public void updateRestaurant(Restaurant restaurant);

 */

}
