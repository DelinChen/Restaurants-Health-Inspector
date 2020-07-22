package ca.cmpt276.project;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.test.annotation.UiThreadTest;
import androidx.test.espresso.core.internal.deps.guava.collect.Ordering;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.RestaurantManager;
import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.model.viewmodel.HealthRepository;
import ca.cmpt276.project.model.viewmodel.HealthViewModel;
import ca.cmpt276.project.model.viewmodel.HealthViewModelFactory;
import ca.cmpt276.project.ui.MainActivity;

@SmallTest
public class HealthViewModelTest {
    private static final String LOG_TAG = "HealthViewModelTest";

    private MainActivity mainActivity;
    private HealthViewModel model;


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initialize() {
        mainActivity = activityTestRule.getActivity();
        HealthViewModelFactory factory = new HealthViewModelFactory(mainActivity);
        model = new ViewModelProvider(mainActivity, factory).get(HealthViewModel.class);
    }

    @Test @UiThreadTest
    public void getAllRestaurants() {
        model.restaurantDetailsData.observe(mainActivity, restaurantDetailsList -> {
            restaurantDetailsList.forEach(restaurantDetails -> Log.i(LOG_TAG, restaurantDetails.toString()));
        });
    }

    @Test @UiThreadTest
    public void updatesRestaurantDetailsMap_afterInsert() {
        // Tests to see if updates to Database update the LiveData<Map<>>
        HealthDatabase db = Room.inMemoryDatabaseBuilder(mainActivity, HealthDatabase.class)
                .allowMainThreadQueries()
                .build();

        LiveData<List<RestaurantDetails>> restaurantData = db.getRestaurantDao().getAllRestaurantsDetails();
        LiveData<Map<String, RestaurantDetails>> restaurantDataMap = HealthRepository.Mapper.getRestaurantDetailsMap(restaurantData);

        restaurantDataMap.observe(mainActivity, printAllRestaurantDetails);

        db.getRestaurantDao().insertAll(RestaurantManager.getInstance(mainActivity).restaurants());
    }

    private Observer<Map<String, RestaurantDetails>> printAllRestaurantDetails = restaurantDetailsMap -> {
        Log.i(LOG_TAG, "---- RestaurantDetails in database ----");
        restaurantDetailsMap.values().forEach(
                restaurantDetails -> Log.i(LOG_TAG, restaurantDetails.toString())
        );
    };
}
