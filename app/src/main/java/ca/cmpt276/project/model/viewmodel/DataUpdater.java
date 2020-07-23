package ca.cmpt276.project.model.viewmodel;

import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.Restaurant;

class DataUpdater {
    // key for boolean value
    static final String RESTAURANT_DATA_IS_UP_TO_DATE_PREF_KEY = "restaurantDataIsUpToDate";
    static final String INSPECTION_DATA_IS_UP_TO_DATE_PREF_KEY = "inspectionDataIsUpToDate";
    // key for url as String
    static final String RESTAURANT_DATA_DOWNLOAD_URL_KEY = "restaurantDataDownloadUrl";

    static final String INSPECTION_DATA_DOWNLOAD_URL_KEY = "inspectionDataDownloadUrl";

    final SharedPreferences preferences;


    DataUpdater(SharedPreferences defaultSharedPreferences) {
        preferences = defaultSharedPreferences;
    }


    boolean isCacheUpToDateElseUpdateCache(String restUrl, String isUpToDatePrefKey, String downloadPrefKey)
            throws ExecutionException, InterruptedException {
        boolean isUpToDate = preferences.getBoolean(isUpToDatePrefKey, false);

        // Update cached data download link and isUpToDate flag
        if(!isUpToDate) {
            HealthApiResponse apiResponse = new FetchDownloadUrlAsyncTask().execute(restUrl).get();
            updateCachedPreferences(isUpToDatePrefKey, false, downloadPrefKey, apiResponse.dataDownloadUrl);
        }

        return isUpToDate;
    }

    synchronized void updateCachedPreferences(String isUpToDateKey, boolean isUpToDate, String downloadKey, String downloadUrl) {
        preferences.edit()
                .putBoolean(isUpToDateKey, isUpToDate)
                .putString(downloadKey, downloadUrl)
                .apply();
    }


    boolean isRestaurantDataUpToDate() {
        return preferences.getBoolean(RESTAURANT_DATA_IS_UP_TO_DATE_PREF_KEY, false);
    }
    void setRestaurantDataIsUpToDate(boolean isDataUpToDate) {
        preferences.edit()
                .putBoolean(RESTAURANT_DATA_IS_UP_TO_DATE_PREF_KEY, isDataUpToDate)
                .apply();
    }

    boolean isInspectionDataDataUpToDate() {
        return preferences.getBoolean(INSPECTION_DATA_IS_UP_TO_DATE_PREF_KEY, false);
    }
    void setInspectionDataIsUpToDate(boolean isDataUpToDate) {
        preferences.edit()
                .putBoolean(INSPECTION_DATA_IS_UP_TO_DATE_PREF_KEY, isDataUpToDate)
                .apply();
    }

    String getRestaurantDataDownloadUrl() {
        return preferences.getString(RESTAURANT_DATA_DOWNLOAD_URL_KEY, "");
    }
    void setRestaurantDataDownloadUrl(String downloadUrl) {
        preferences.edit()
                .putString(RESTAURANT_DATA_DOWNLOAD_URL_KEY, downloadUrl)
                .apply();
    }

    String getInspectionDataDownloadUrl() {
        return preferences.getString(INSPECTION_DATA_DOWNLOAD_URL_KEY, "");
    }
    void setInspectionDataDownloadUrl(String downloadUrl) {
        preferences.edit()
                .putString(INSPECTION_DATA_DOWNLOAD_URL_KEY, downloadUrl)
                .apply();
    }

    Map<String, Restaurant> parseRestaurantDownloadUrl(String downloadUrl) throws ExecutionException, InterruptedException {
        Map<String, Restaurant> restaurantMap = new ParseRestaurantDownloadUrlAsyncTask().execute(downloadUrl).get();
        return restaurantMap;
    }

    Map<String, List<InspectionDetails>> parseInspectionDownloadUrl(String downloadUrl) throws ExecutionException, InterruptedException {
        Map<String, List<InspectionDetails>> inspectionDetailsMap = new ParseInspectionDownloadUrlAsyncTask().execute(downloadUrl).get();
        return inspectionDetailsMap;
    }
}
