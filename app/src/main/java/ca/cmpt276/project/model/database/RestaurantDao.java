package ca.cmpt276.project.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Collection;
import java.util.List;

import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.Violation;

@Dao
public abstract class RestaurantDao implements BaseDao<Restaurant> {
    @Transaction
    public void insertRestaurantDetails(Restaurant restaurant, Collection<InspectionDetails> inspectionDetailsCollection) {
        _insertRestaurantDetails(restaurant, inspectionDetailsCollection);
    }
    @Transaction
    public void insertAllRestaurantDetails(Collection<RestaurantDetails> restaurantDetailsCollection) {
        restaurantDetailsCollection.forEach(
                restaurantDetails -> insertRestaurantDetails(restaurantDetails.restaurant, restaurantDetails.inspectionDetailsList)
        );
    }

    @Transaction
    void _insertRestaurantDetails(Restaurant restaurant, Collection<InspectionDetails> inspectionDetailsCollection) {
        _insertRestaurant(restaurant);
        _insertAllInspectionDetails(inspectionDetailsCollection);
    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void _insertRestaurant(Restaurant restaurant);

    @Transaction
    void _insertAllInspectionDetails(Collection<InspectionDetails> inspectionDetailsCollection) {
        inspectionDetailsCollection.forEach(inspectionDetails -> _insertInspectionDetails(inspectionDetails.inspection, inspectionDetails.violations));
    }
    @Transaction
    void _insertInspectionDetails(Inspection inspection, Collection<Violation> violations) {
        _insertInspection(inspection);
        _insertViolations(violations);
        violations.forEach(violation -> _insertInspectionViolationCrossref(
                new InspectionViolationCrossref(inspection, violation))
        );
    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void _insertInspection(Inspection inspection);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = InspectionViolationCrossref.class)
    abstract void _insertInspectionViolationCrossref(InspectionViolationCrossref crossref);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void _insertViolations(Collection<Violation> violations);


    @Query("SELECT * FROM restaurants ORDER BY name")
    public abstract LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM restaurants WHERE tracking_number = :trackingNumber LIMIT 1")
    public abstract Restaurant findRestaurantWithTrackingNumber(String trackingNumber);

    @Transaction
    @Query("SELECT * FROM restaurants ORDER BY name")
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
