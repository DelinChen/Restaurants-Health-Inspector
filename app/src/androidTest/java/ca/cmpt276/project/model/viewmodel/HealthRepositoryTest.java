package ca.cmpt276.project.model.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.ui.MainActivity;

public class HealthRepositoryTest {
    private HealthRepository repo;
    private MainActivity mainActivity;


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initialize() {
        Context appContext = ApplicationProvider.getApplicationContext();
        mainActivity = activityTestRule.getActivity();
        repo = new HealthRepository(appContext);
    }

    @Test @UiThreadTest
    public void getRestaurantDataDownloadUrl_fromRestApiResponse() {
        MutableLiveData<HealthApiResponse> downloadUrl = new MutableLiveData<>();
        AsyncTask<String, String, HealthApiResponse> getRequest = new FetchDownloadUrlAsyncTask().execute(HealthRepository.RESTAURANT_REST_URL);
        try {
            downloadUrl.postValue(getRequest.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        downloadUrl.observe(mainActivity, response -> {
            Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", response.dataDownloadUrl);
            Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", response.lastModified.toString());
        });
    }

    @Test @UiThreadTest
    public void getRestaurantData_fromDownloadUrl() throws ExecutionException, InterruptedException {
        HealthDatabase db = Room.inMemoryDatabaseBuilder(mainActivity, HealthDatabase.class)
                .fallbackToDestructiveMigration()
                .build();

        String url = PreferenceManager.getDefaultSharedPreferences(mainActivity)
                .getString("restaurantDataDownloadUrl", "");

        MutableLiveData<Map<String, Restaurant>> data = new MutableLiveData<>();
        AsyncTask<String, String, Map<String, Restaurant>> getData = new ParseRestaurantDownloadUrlAsyncTask().execute(repo.getRestaurantDataDownloadUrl());
        try {
            data.postValue(getData.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        data.observe(mainActivity, map -> {
            // Uses the pattern found at https://www.baeldung.com/convert-string-to-input-stream#java
            Log.i("AAAAAAAAAAAAAAAAAA", map.values().toString());
        });
    }

    @Test @UiThreadTest
    public void updateData_successTest() {
        HealthDatabase db = Room.databaseBuilder(mainActivity, HealthDatabase.class, "health_test.db")
                .fallbackToDestructiveMigration()
                .createFromAsset("database/health.db")
                .build();
        repo = new HealthRepository(db, mainActivity);

        db.getRestaurantDao().getAllRestaurantsDetails().observe(mainActivity, restaurantDetailsList -> {
            Log.i("AAAAAAAAAAAAAAAAAAAAAAA", restaurantDetailsList.toString());
        });

        repo.updateData();
    }
}
