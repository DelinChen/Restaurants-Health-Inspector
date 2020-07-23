package ca.cmpt276.project.model.database;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.InspectionManager;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.database.RestaurantDao;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.RestaurantManager;

@SmallTest
public class RestaurantDaoTest {
    public static final String LOG_TAG = "RestaurantDao";

    private static final String trackingNumber = "SDFO-8HKP7E";
    private static final String name = "Pattullo A&W";
    private static final String address = "12808 King George Blvd";
    private static final String city = "Surrey";
    private static final double latitude = 40.20610961;
    private static final double longitude = -122.8668064;

    private Context appContext;
    private HealthDatabase db;
    private Restaurant instance;
    private RestaurantDao restaurantDao;


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void initialize() {
        appContext = ApplicationProvider.getApplicationContext();
        List<Inspection> inspections = InspectionManager.getInstance(appContext).get(trackingNumber);
        instance = new Restaurant(trackingNumber, name, address, city, latitude, longitude);
        db = Room.inMemoryDatabaseBuilder(appContext, HealthDatabase.class)
                .fallbackToDestructiveMigration()
                .build();
        restaurantDao = db.getRestaurantDao();
    }


    @Test
    public void insertSingleRestaurant_successTest() {
        restaurantDao.getAllRestaurants().observeForever(printAllRestaurants);
        restaurantDao.getAllRestaurantsDetails().observeForever(printAllRestaurantsDetails);

        restaurantDao.insert(instance);
    }

    @Test
    public void insertAllRestaurants_successTest() {
        restaurantDao.getAllRestaurants().observeForever(printAllRestaurants);
        restaurantDao.getAllRestaurantsDetails().observeForever(printAllRestaurantsDetails);

        restaurantDao.insertAll(RestaurantManager.getInstance(appContext).restaurants());
    }

    @Test
    public void getAllRestaurants_successTest() {
        HealthDatabase testDb = Room.databaseBuilder(appContext, HealthDatabase.class, "health_test.db")
                .createFromAsset("database/health.db")
                .build();

        testDb.getRestaurantDao().getAllRestaurantsDetails().observeForever(printAllRestaurantsDetails);
    }


    private static final Observer<List<Restaurant>> printAllRestaurants = restaurants -> {
        Log.i(LOG_TAG, "Restaurants in database:");
        restaurants.forEach(restaurant -> Log.i(LOG_TAG, restaurant.toString()));
    };

    private static final Observer<List<RestaurantDetails>> printAllRestaurantsDetails = restaurantDetailsList -> {
        Log.i(LOG_TAG, "RestaurantDetails in database:");
        restaurantDetailsList.forEach(restaurantDetails -> Log.i(LOG_TAG, restaurantDetails.toString()));
    };
}
