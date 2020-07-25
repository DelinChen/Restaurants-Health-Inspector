package ca.cmpt276.project.model.viewmodel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.model.database.InspectionDao;
import ca.cmpt276.project.model.database.RestaurantDao;
import ca.cmpt276.project.model.database.ViolationDao;

class HealthRepository {
    static final String RESTAURANT_REST_URL = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";
    static final String INSPECTIONS_REST_URL = "https://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";

    private final RestaurantDao restaurantDao;
    private final InspectionDao inspectionDao;
    private final ViolationDao violationDao;

    private final DataUpdater updater;

    HealthRepository(final Context anyContext) {
        HealthDatabase db = HealthDatabase.getInstance(anyContext);
        restaurantDao = db.getRestaurantDao();
        inspectionDao = db.getInspectionDao();
        violationDao = db.getViolationDao();

        updater = new DataUpdater(PreferenceManager.getDefaultSharedPreferences(anyContext));
    }

    HealthRepository(final HealthDatabase db, final Context anyContext) {
        restaurantDao = db.getRestaurantDao();
        inspectionDao = db.getInspectionDao();
        violationDao = db.getViolationDao();

        updater = new DataUpdater(PreferenceManager.getDefaultSharedPreferences(anyContext));
    }


    ////////////////////////////////////////////////////////////////
    // Database access methods

    LiveData<List<RestaurantDetails>> getAllRestaurantDetails() {
        return restaurantDao.getAllRestaurantsDetails();
    }

    LiveData<List<InspectionDetails>> getAllInspectionDetails() {
        return inspectionDao.getAllInspectionDetails();
    }

    LiveData<List<Violation>> getAllViolations() {
        return violationDao.getAllViolations();
    }


    ////////////////////////////////////////////////////////////////
    // Data mapper methods

    LiveData<Map<String, RestaurantDetails>> getRestaurantDetailsMap() {
        return HealthDataMapper.getRestaurantDetailsMap(getAllRestaurantDetails());
    }

    LiveData<Map<String, InspectionDetails>> getInspectionDetailsMap() {
        return HealthDataMapper.getInspectionDetailsMap(getAllInspectionDetails());
    }

    LiveData<Map<Integer, Violation>> getViolationMap() {
        return HealthDataMapper.getViolationMap(getAllViolations());
    }


    ////////////////////////////////////////////////////////////////
    // Asynchronous data fetching task

    boolean isDataUpToDate() throws ExecutionException, InterruptedException {
        return
                updater.isCacheUpToDateElseUpdateCache(
                        RESTAURANT_REST_URL,
                        DataUpdater.RESTAURANT_DATA_IS_UP_TO_DATE_PREF_KEY,
                        DataUpdater.RESTAURANT_DATA_DOWNLOAD_URL_KEY)
                || updater.isCacheUpToDateElseUpdateCache(
                        INSPECTIONS_REST_URL,
                        DataUpdater.INSPECTION_DATA_IS_UP_TO_DATE_PREF_KEY,
                        DataUpdater.INSPECTION_DATA_DOWNLOAD_URL_KEY);
    }

    void updateData() {
        try {
            Map<String, Restaurant> restaurantMap = parseRestaurantDownloadUrl(getRestaurantDataDownloadUrl());
            Map<String, List<InspectionDetails>> inspectionDetailsMap = parseInspectionDownloadUrl(getInspectionDataDownloadUrl());
            new UpdateDataAsyncTask().execute(
                    restaurantMap,
                    inspectionDetailsMap,
                    restaurantDao
            );
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    boolean isRestaurantDataUpToDate() {
        return updater.isCachedRestaurantDataUpToDate();
    }

    boolean isInspectionDataDataUpToDate() {
        return updater.isCachedInspectionDataDataUpToDate();
    }

    String getRestaurantDataDownloadUrl() {
        return updater.getCachedRestaurantDataDownloadUrl();
    }

    String getInspectionDataDownloadUrl() {
        return updater.getCachedInspectionDataDownloadUrl();
    }

    Map<String, Restaurant> parseRestaurantDownloadUrl(String downloadUrl) throws ExecutionException, InterruptedException {
        return updater.parseRestaurantDownloadUrl(downloadUrl);
    }

    Map<String, List<InspectionDetails>> parseInspectionDownloadUrl(String downloadUrl) throws ExecutionException, InterruptedException {
        return updater.parseInspectionDownloadUrl(downloadUrl);
    }
}
