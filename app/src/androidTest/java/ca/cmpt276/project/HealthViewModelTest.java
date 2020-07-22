package ca.cmpt276.project;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.annotation.UiThreadTest;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
    public void getRestaurants() {
        model.restaurantDetailsData.observe(mainActivity, restaurantDetailsList ->
                restaurantDetailsList.forEach(restaurantDetails -> Log.i(LOG_TAG, restaurantDetails.toString()))
        );
    }

    @Test @UiThreadTest
    public void updatesRestaurantDetailsMap_afterInsert() {

    }
}
