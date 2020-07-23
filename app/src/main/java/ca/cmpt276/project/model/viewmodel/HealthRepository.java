package ca.cmpt276.project.model.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.model.database.InspectionDao;
import ca.cmpt276.project.model.database.RestaurantDao;
import ca.cmpt276.project.model.database.ViolationDao;

public class HealthRepository {
    public static final String RESTAURANT_REST_URL = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";
    public static final String INSPECTIONS_REST_URL = "http://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";

    private final RestaurantDao restaurantDao;
    private final InspectionDao inspectionDao;
    private final ViolationDao violationDao;

    private final DataUpdater updater;

    public HealthRepository(final Context anyContext) {
        HealthDatabase db = HealthDatabase.getInstance(anyContext);
        restaurantDao = db.getRestaurantDao();
        inspectionDao = db.getInspectionDao();
        violationDao = db.getViolationDao();

        updater = new DataUpdater(PreferenceManager.getDefaultSharedPreferences(anyContext));
    }


    ////////////////////////////////////////////////////////////////
    // Database access methods

    public LiveData<List<RestaurantDetails>> getAllRestaurantDetails() {
        return restaurantDao.getAllRestaurantsDetails();
    }

    public LiveData<List<InspectionDetails>> getAllInspectionDetails() {
        return inspectionDao.getAllInspectionDetails();
    }

    public LiveData<List<Violation>> getAllViolations() {
        return violationDao.getAllViolations();
    }


    ////////////////////////////////////////////////////////////////
    // Data mapper methods

    public LiveData<Map<String, RestaurantDetails>> getRestaurantDetailsMap() {
        return HealthDataMapper.getRestaurantDetailsMap(getAllRestaurantDetails());
    }

    public LiveData<Map<String, InspectionDetails>> getInspectionDetailsMap() {
        return HealthDataMapper.getInspectionDetailsMap(getAllInspectionDetails());
    }

    public LiveData<Map<Integer, Violation>> getViolationMap() {
        return HealthDataMapper.getViolationMap(getAllViolations());
    }


    ////////////////////////////////////////////////////////////////
    // Asynchronous data fetching task

    public boolean isDataUpToDate() throws ExecutionException, InterruptedException {
        return
                updater.isCacheUpToDate(
                        RESTAURANT_REST_URL,
                        DataUpdater.RESTAURANT_DATA_IS_UP_TO_DATE_PREF_KEY,
                        DataUpdater.RESTAURANT_DATA_DOWNLOAD_URL_KEY)
                || updater.isCacheUpToDate(
                        INSPECTIONS_REST_URL,
                        DataUpdater.INSPECTION_DATA_IS_UP_TO_DATE_PREF_KEY,
                        DataUpdater.INSPECTION_DATA_DOWNLOAD_URL_KEY);
    }

    public void updateData() throws ExecutionException, InterruptedException {
        if(!isDataUpToDate()) {
            return;
        }

    }

    public String getRestaurantDataDownloadUrl() {
        return updater.getRestaurantDataDownloadUrl();
    }

    private static class DataUpdater {
        // key for boolean value
        private static final String RESTAURANT_DATA_IS_UP_TO_DATE_PREF_KEY = "restaurantDataIsUpToDate";
        private static final String INSPECTION_DATA_IS_UP_TO_DATE_PREF_KEY = "inspectionDataIsUpToDate";
        // key for url as String
        private static final String RESTAURANT_DATA_DOWNLOAD_URL_KEY = "restaurantDataDownloadUrl";
        private static final String INSPECTION_DATA_DOWNLOAD_URL_KEY = "inspectionDataDownloadUrl";

        private final SharedPreferences preferences;


        public DataUpdater(SharedPreferences defaultSharedPreferences) {
            preferences = defaultSharedPreferences;
        }


        private boolean isCacheUpToDate(String restUrl, String isUpToDatePrefKey, String downloadPrefKey)
                throws ExecutionException, InterruptedException {
            boolean isUpToDate = preferences.getBoolean(isUpToDatePrefKey, false);

            // Update cached data download link and isUpToDate flag
            if(!isUpToDate) {
                HealthApiResponse apiResponse = new FetchDownloadUrlAsyncTask().execute(restUrl).get();
                updateCachedPreferences(isUpToDatePrefKey, downloadPrefKey, apiResponse.dataDownloadUrl);
            }

            return isUpToDate;
        }

        private synchronized void updateCachedPreferences(String isUpToDateKey, String downloadKey, String downloadUrl) {
            preferences.edit()
                    .putBoolean(isUpToDateKey, false)
                    .putString(downloadKey, downloadUrl)
                    .apply();
        }


        private String getRestaurantDataDownloadUrl() {
            return preferences.getString(RESTAURANT_DATA_DOWNLOAD_URL_KEY, "");
        }
    }

}
