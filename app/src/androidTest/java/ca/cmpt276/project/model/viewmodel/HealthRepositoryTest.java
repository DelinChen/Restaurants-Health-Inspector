package ca.cmpt276.project.model.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
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

import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantScanner;
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

        MutableLiveData<List<Restaurant>> data = new MutableLiveData<>();
        AsyncTask<String, String, List<Restaurant>> getData = new ParseRestaurantDownloadUrlAsyncTask().execute(repo.getRestaurantDataDownloadUrl());
        try {
            data.postValue(getData.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        data.observe(mainActivity, restaurantList-> {
            // Uses the pattern found at https://www.baeldung.com/convert-string-to-input-stream#java
            db.getRestaurantDao().insertAll(restaurantList);
            Log.i("AAAAAAAAAAAAAAAAAA", restaurantList.toString());
        });
    }
}
